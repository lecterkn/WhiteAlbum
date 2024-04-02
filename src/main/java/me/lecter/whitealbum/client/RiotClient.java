package me.lecter.whitealbum.client;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.lecter.whitealbum.client.body.AuthBody;
import me.lecter.whitealbum.client.body.LoginBody;
import me.lecter.whitealbum.client.exceptions.RiotAuthenticationException;
import me.lecter.whitealbum.client.exceptions.RiotException;
import me.lecter.whitealbum.client.exceptions.RiotRatelimitException;
import me.lecter.whitealbum.client.exceptions.RiotUnknownException;

public class RiotClient {
	
	private String username;
	private String password;
	private String access_token;
	private String entitlements_token;
	private boolean isLogged;
	public static final String USER_AGENT = "RiotClient/" + getBuildVersion() + " %s (Windows;10;;Professional, x64)";
	public static final String ENCODING = "UTF-8";
	public static final String TOKEN_TYPE = "Bearer";

	private RiotClient(String username, String password) {
		this.username = username;
		this.password = password;
		this.access_token = null;
		this.entitlements_token = null;
		this.isLogged = false;
	}

	public static RiotClient create(String username, String password) {
		return new RiotClient(username, password);
	}

	public boolean login() throws RiotException {
		BasicCookieStore cookieStore = new BasicCookieStore();

		try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).setSSLSocketFactory(getSSLSocketFactory()).build()) {
			HttpPost httpPost = new HttpPost("https://auth.riotgames.com/api/v1/authorization");
			this.setDefaultHeaders(httpPost);
			AuthBody authBody = new AuthBody();
			String authJson = (new Gson()).toJson(authBody, AuthBody.class);
			httpPost.setEntity(new StringEntity(authJson, "UTF-8"));
			httpPost.addHeader("Content-Type", "application/json");
			try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
				if (httpResponse.getStatusLine().getStatusCode() != 200) {
					return false;
				}
			}
			HttpPut httpPut = new HttpPut("https://auth.riotgames.com/api/v1/authorization");
			this.setDefaultHeaders(httpPut);
			LoginBody loginBody = new LoginBody(this.username, this.password);
			String loginJson = (new Gson()).toJson(loginBody, LoginBody.class);
			httpPut.setEntity(new StringEntity(loginJson, "UTF-8"));
			httpPut.addHeader("Content-Type", "application/json");
			try (CloseableHttpResponse httpResponse = httpClient.execute(httpPut)) {
				String body = EntityUtils.toString(httpResponse.getEntity());
				JsonObject responseJson = new Gson().fromJson(body, JsonObject.class);
				String type = responseJson.get("type").getAsString();
				if (type.equalsIgnoreCase("auth")) {
					String error = responseJson.get("error").getAsString();
					if (error.equalsIgnoreCase("auth_failure")) {
						throw new RiotAuthenticationException();
					}
					if (error.equalsIgnoreCase("rate_limited")) {
						throw new RiotRatelimitException();
					}
					throw new RiotUnknownException();
				}
				if (type.equalsIgnoreCase("response")) {
					String uri = responseJson.getAsJsonObject("response").getAsJsonObject("parameters").get("uri").getAsString();
					Pattern pattern = Pattern.compile("access_token=(.+)&scope=");
					Matcher matcher = pattern.matcher(uri);
					if (matcher.find()) {
						this.access_token = matcher.group(1);
					}
					else {
						System.out.println("access token does not found.");
						return false;
					}
				}

			}

			HttpPost httpPost2 = new HttpPost("https://entitlements.auth.riotgames.com/api/token/v1");
			this.setDefaultHeaders(httpPost2);
			httpPost2.addHeader("Authorization", "Bearer " + this.getAccess_token());
			httpPost2.setEntity(new StringEntity("{}", "UTF-8"));
			httpPost2.addHeader("Content-Type", "application/json");
			try (CloseableHttpResponse httpResponse = httpClient.execute(httpPost2)) {
				String responseBody = EntityUtils.toString(httpResponse.getEntity());
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					JsonObject responseJson = new Gson().fromJson(responseBody, JsonObject.class);
					this.entitlements_token = responseJson.get("entitlements_token").getAsString();
					return true;
				}
				System.out.println("failed to get entitlements_token");
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	private void setDefaultHeaders(HttpRequestBase request) {
		request.addHeader("Accept-Encoding", "deflate, gzip, zstd");
		request.addHeader("user-agent", USER_AGENT);
		request.addHeader("Cache-Control", "no-cache");
		request.addHeader("Accept", "application/json");
	}

	private static SSLConnectionSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException {
		return new SSLConnectionSocketFactory(SSLContext.getDefault(), new String[]{"TLSv1", "TLSv1.1", "TLSv1.2", "TLSv1.3"}, new String[]{"TLS_CHACHA20_POLY1305_SHA256", "TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384"}, new DefaultHostnameVerifier());
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getAccess_token() {
		return this.access_token;
	}

	public String getEntitlements_token() {
		return this.entitlements_token;
	}

	public boolean isLogged() {
		return this.isLogged;
	}

	public static String getBuildVersion() {
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpGet httpGet = new HttpGet("https://valorant-api.com/v1/version");
			httpGet.addHeader("accept", "application/json");
			try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					JsonObject json = new Gson().fromJson(EntityUtils.toString(httpResponse.getEntity()), JsonObject.class);
					return json.getAsJsonObject("data").get("riotClientBuild").getAsString();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "82.0.3.1237.2870";
	}
}
