package me.lecter.whitealbum.client.valorant;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import me.lecter.whitealbum.client.ValorantAPI;
import me.lecter.whitealbum.client.valorant.json.SkinLevel;

public class StoreFront {
	
	private String username;
	private List<ItemOffer> singleItemOffers;
	private List<NightMarketOffer> nightmarket;
	public static List<SkinLevel> skinLevels = null;
	private static final Type SKINLEVELS_TYPE = (new TypeToken<List<SkinLevel>>() {}).getType();

	private StoreFront(String username, String json) {
		this.username = username;
		this.singleItemOffers = new ArrayList<ItemOffer>();
		this.nightmarket = new ArrayList<NightMarketOffer>();
//		this.singleItemOffers = new SingleItemOffers();
//		this.nightmarket = new NightMarket();
		
		// get skinlevels
		if (skinLevels == null || skinLevels.size() == 0) {
			setSkinLevels();
		}
		JsonObject storefrontJson = new Gson().fromJson(json, JsonObject.class);
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			// loading SingleItemOffers
			for (JsonElement element : storefrontJson.getAsJsonObject("SkinsPanelLayout").getAsJsonArray("SingleItemStoreOffers").asList()) {
				JsonObject jsonObject = element.getAsJsonObject();
				String offerId = jsonObject.get("OfferID").getAsString();
				SkinLevel skinLevel = getSkinLevel(offerId);
				if (skinLevel != null) {
					ItemOffer itemOffer = new ItemOffer(offerId, jsonObject.getAsJsonObject("Cost").get("85ad13f7-3d1b-5128-9eb2-7cd8ee0b5741").getAsInt(), skinLevel.getDisplayName(), skinLevel.getDisplayIcon());
					this.singleItemOffers.add(itemOffer);
				}

			}
			
			// loading NightMarket
			if (storefrontJson.has("BonusStore")) {
				for (JsonElement element : storefrontJson.getAsJsonObject("BonusStore").getAsJsonArray("BonusStoreOffers").asList()) {
					JsonObject jsonObject = element.getAsJsonObject();
					String offerId = jsonObject.getAsJsonObject("Offer").get("OfferId").getAsString();
					
					SkinLevel skinLevel = getSkinLevel(offerId);
					if (skinLevel != null) {
						NightMarketOffer itemOffer = new NightMarketOffer(offerId, jsonObject.getAsJsonObject("Offer").getAsJsonObject("Cost").get("85ad13f7-3d1b-5128-9eb2-7cd8ee0b5741").getAsInt(), skinLevel.getDisplayName(), skinLevel.getDisplayIcon(), jsonObject.get("DiscountPercent").getAsInt(), jsonObject.getAsJsonObject("DiscountCosts").get("85ad13f7-3d1b-5128-9eb2-7cd8ee0b5741").getAsInt());
						
						this.nightmarket.add(itemOffer);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SkinLevel getSkinLevel(String uuid) {
		for (SkinLevel skinLevel : skinLevels) {
			if (skinLevel.getUuid().equals(uuid)) {
				return skinLevel;
			}
		}
		return null;
	}
	
	private static void setSkinLevels() {
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpGet httpGet = new HttpGet("https://valorant-api.com/v1/weapons/skinlevels/?language=" + ValorantAPI.getLanguage().getParam());
			try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					String responseBody = EntityUtils.toString(httpResponse.getEntity());
					JsonElement responseJson = new Gson().fromJson(responseBody, JsonObject.class).get("data");
					skinLevels = new Gson().fromJson(responseJson, SKINLEVELS_TYPE);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static StoreFront create(String username, String json) {
		return new StoreFront(username, json);
	}

	public List<ItemOffer> getSingleItemOffers() {
		return this.singleItemOffers;
	}

	public List<NightMarketOffer> getNightmarket() {
		return this.nightmarket;
	}

	public String getUsername() {
		return this.username;
	}
}
