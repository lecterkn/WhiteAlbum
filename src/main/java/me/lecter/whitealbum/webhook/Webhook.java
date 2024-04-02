package me.lecter.whitealbum.webhook;

import com.google.gson.Gson;

import me.lecter.whitealbum.WhiteAlbum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Webhook {
	private String username;
	private String avatar_url;
	private List<Embed> embeds;

	private Webhook(String username, String avatar_url, List<Embed> embeds) {
		this.username = username;
		this.avatar_url = avatar_url;
		this.embeds = embeds;
	}

	public static Webhook create(String username, String avatar_url, List<Embed> embeds) {
		return new Webhook(username, avatar_url, embeds);
	}

	public static Webhook create(String username, String avatar_url) {
		return create(username, avatar_url, new ArrayList<Embed>());
	}

	public static boolean send_embed(Webhook webhook) {
		if (!WhiteAlbum.getConfigs().getWebhook_url().startsWith("http")) {
			return false;
		}
		String json = new Gson().toJson(webhook);
		System.out.println(json);
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpPost httpPost = new HttpPost(WhiteAlbum.getConfigs().getWebhook_url());
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(json, "UTF-8"));
			try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
				System.out.println("[Webhook] Status \"" + response.getStatusLine().getStatusCode() + "\"");
				if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 204) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar_url() {
		return this.avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public List<Embed> getEmbeds() {
		return this.embeds;
	}

	public void setEmbeds(List<Embed> embeds) {
		this.embeds = embeds;
	}
}
