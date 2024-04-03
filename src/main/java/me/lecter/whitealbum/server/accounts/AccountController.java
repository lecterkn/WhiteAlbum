package me.lecter.whitealbum.server.accounts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.lecter.whitealbum.WhiteAlbum;
import me.lecter.whitealbum.account.AccountManager;
import me.lecter.whitealbum.account.RiotAccount;
import me.lecter.whitealbum.account.SavedAccount;
import me.lecter.whitealbum.client.RiotClient;
import me.lecter.whitealbum.client.ValorantAPI;
import me.lecter.whitealbum.client.exceptions.RiotException;

@RestController
public class AccountController {

	@RequestMapping(value="/accounts",method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getAccount() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> savedNames = new ArrayList<String>();
		List<String> accountNames = new ArrayList<String>();

		for (SavedAccount saved : AccountManager.getSavedAccount()) {
			savedNames.add(saved.getUsername());
		}

		for (RiotAccount account : WhiteAlbum.getAccounts()) {
			accountNames.add(account.getUsername());
		}

		map.put("saved", savedNames);
		map.put("txt", accountNames);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value="/saved/login", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> loginSaved(@RequestBody AccountLoginBody body) {
		Map<String, Object> map = new HashMap<String, Object>();

		for (SavedAccount account : AccountManager.getSavedAccount()) {
			if (account.getUsername().equals(body.getUsername())) {
				RiotClient client = RiotClient.create(account.getUsername(), account.getPassword());
				try {
					if (client.login()) {
						ValorantAPI api = ValorantAPI.create(client.getAccess_token(), client.getEntitlements_token());
						
						map.put("username", api.getStoreFront().getUsername());
						map.put("singleItemOffers", api.getStoreFront().getSingleItemOffers());
						map.put("nightmarket", api.getStoreFront().getNightmarket());
						return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
					} else {
						map.put("message", "failed to login");
						return new ResponseEntity<Map<String, Object>>(map, HttpStatus.UNAUTHORIZED);
					}
				} catch (RiotException e) {
					e.printStackTrace();
					map.put("message", e.getMessage());
					return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}

		map.put("message", "account not found");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value="/txt/login", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> loginTxt(@RequestBody AccountLoginBody body) {
		Map<String, Object> map = new HashMap<String, Object>();

		for (RiotAccount account : WhiteAlbum.getAccounts()) {
			if (account.getUsername().equals(body.getUsername())) {
				RiotClient client = RiotClient.create(account.getUsername(), account.getPassword());
				try {
					if (client.login()) {
						ValorantAPI api = ValorantAPI.create(client.getAccess_token(), client.getEntitlements_token());
						
						map.put("username", api.getStoreFront().getUsername());
						map.put("singleItemOffers", api.getStoreFront().getSingleItemOffers());
						map.put("nightmarket", api.getStoreFront().getNightmarket());
						return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
					} else {
						map.put("message", "failed to login");
						return new ResponseEntity<Map<String, Object>>(map, HttpStatus.UNAUTHORIZED);
					}
				} catch (RiotException e) {
					e.printStackTrace();
					map.put("message", e.getMessage());
					return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		map.put("message", "account not found.");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.FORBIDDEN);
	}
}
