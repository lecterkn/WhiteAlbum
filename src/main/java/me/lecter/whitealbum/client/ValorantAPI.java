package me.lecter.whitealbum.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.lecter.whitealbum.client.enums.APILanguage;
import me.lecter.whitealbum.client.enums.Region;
import me.lecter.whitealbum.client.valorant.StoreFront;

public class ValorantAPI {
	
	private String access_token;
	private String entitlements_token;
	private String user_id;
	private String gamename;
	private static Region region = Region.AP;
	private static APILanguage language = APILanguage.EN;
	public static final String TOKEN_TYPE = "Bearer";
	public static final String ENCODING = "UTF-8";

	private ValorantAPI(String access_token, String entitlements_token) {
		this.access_token = access_token;
		this.entitlements_token = entitlements_token;
		this.setPlayerID();
	}

	public void setPlayerID() {
		String[] payloads = this.getAccess_token().split("\\.");
		String decoded = new String(Base64.decodeBase64URLSafe(payloads[1] + "==="), StandardCharsets.UTF_8);
		JsonObject payload = new Gson().fromJson(decoded, JsonObject.class);
		this.user_id = payload.get("sub").getAsString();
		this.gamename = "ValoTools";

		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpPut httpPut = new HttpPut("https://pd." + getRegion().getParam() + ".a.pvp.net/name-service/v2/players");
			this.setDefaultHeaders(httpPut);
			httpPut.addHeader("Content-Type", "application/json");
			httpPut.setEntity(new StringEntity("[\"" + this.getUser_id() + "\"]", "UTF-8"));
			try (CloseableHttpResponse httpResponse = httpClient.execute(httpPut)) {
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					JsonArray responseJson = (JsonArray)(new Gson()).fromJson(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"), JsonArray.class);
					JsonObject data = responseJson.get(0).getAsJsonObject();
					this.gamename = data.get("GameName").getAsString() + "#" + data.get("TagLine").getAsString();
				} else {
					System.out.println("failed to get player data \"" + httpResponse.getStatusLine().getStatusCode() + "\"");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public StoreFront getStoreFront() {
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpGet httpGet = new HttpGet("https://pd." + getRegion().getParam() + ".a.pvp.net/store/v2/storefront/" + this.getUser_id());
			this.setDefaultHeaders(httpGet);
			try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					String responseBody = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
					return StoreFront.create(this.getGamename(), responseBody);
				}
				else {
					System.out.println("failed to get storefront \"" + httpResponse.getStatusLine().getStatusCode() + "\"");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setDefaultHeaders(HttpRequestBase request) {
		request.addHeader("user-agent", RiotClient.USER_AGENT);
		request.addHeader("Accept", "application/json");
		request.addHeader("Authorization", "Bearer " + this.getAccess_token());
		request.addHeader("X-Riot-Entitlements-JWT", this.getEntitlements_token());
		request.addHeader("Content-Type", "application/json");
	}

	public static ValorantAPI create(String access_token, String entitlements_token) {
		return new ValorantAPI(access_token, entitlements_token);
	}

	public String getAccess_token() {
		return this.access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getEntitlements_token() {
		return this.entitlements_token;
	}

	public void setEntitlements_token(String entitlements_token) {
		this.entitlements_token = entitlements_token;
	}

	public static Region getRegion() {
		return region;
	}

	public static void setRegion(Region theRegion) {
		region = theRegion;
	}

	public static APILanguage getLanguage() {
		return language;
	}

	public static void setLanguage(APILanguage theLanguage) {
		language = theLanguage;
	}

	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getGamename() {
		return this.gamename;
	}

	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
}
