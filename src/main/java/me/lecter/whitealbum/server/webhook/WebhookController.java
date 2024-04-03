package me.lecter.whitealbum.server.webhook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.lecter.whitealbum.WhiteAlbum;
import me.lecter.whitealbum.account.RiotAccount;
import me.lecter.whitealbum.client.RiotClient;
import me.lecter.whitealbum.client.ValorantAPI;
import me.lecter.whitealbum.client.exceptions.RiotException;
import me.lecter.whitealbum.client.valorant.ItemOffer;
import me.lecter.whitealbum.client.valorant.StoreFront;
import me.lecter.whitealbum.webhook.Embed;
import me.lecter.whitealbum.webhook.Webhook;

@RestController
public class WebhookController {

	@RequestMapping(value="/webhook", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> get(@RequestHeader("varification_code") String varification_code) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (!varification_code.equals(WhiteAlbum.getConfigs().getVarification_code())) {
			map.put("message", "unauthorized");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.UNAUTHORIZED);
		}

		map.put("url", WhiteAlbum.getConfigs().getWebhook_url());
		map.put("icon", WhiteAlbum.getConfigs().getAvatar_url());
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value="/webhook", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> execute(@RequestHeader("varification_code") String varification_code) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (WhiteAlbum.getConfigs().getWebhook_url() == null || WhiteAlbum.getConfigs().getWebhook_url().isEmpty()) {
			map.put("message", "webhook url error");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (!varification_code.equals(WhiteAlbum.getConfigs().getVarification_code())) {
			map.put("message", "unauthorized");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.UNAUTHORIZED);
		}

		List<StoreFront> storefronts = this.getStoreFronts();

		for (StoreFront storefront : storefronts) {
			Webhook webhook = Webhook.create(storefront.getUsername(), WhiteAlbum.getConfigs().getAvatar_url());
			for (ItemOffer offer : storefront.getSingleItemOffers()) {
				webhook.getEmbeds().add(Embed.create(offer.getDisplayName(), String.valueOf(offer.getCost()), 65280, Embed.Image.create(offer.getDisplayIcon())));
			}
			if (Webhook.send_embed(webhook)) {
				System.out.println("[Webhook] Success \"" + storefront.getUsername() + "\"");
			}
		}

		map.put("data", storefronts);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	private List<StoreFront> getStoreFronts() {
		List<StoreFront> storefronts = new ArrayList<StoreFront>();
		List<RiotAccount> accounts = WhiteAlbum.getAccounts();
		
		if (accounts.size() < 1) {
			return null;
		}
		
		for (RiotAccount account : accounts) {
			RiotClient client = RiotClient.create(account.getUsername(), account.getPassword());
			try {
				if (client.login()) {
					ValorantAPI api = ValorantAPI.create(client.getAccess_token(), client.getEntitlements_token());
					storefronts.add(api.getStoreFront());
				}
			} catch (RiotException e) {
				e.printStackTrace();
			}
		}
		return storefronts;
	}
}
