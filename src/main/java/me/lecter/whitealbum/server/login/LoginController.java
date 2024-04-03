package me.lecter.whitealbum.server.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import me.lecter.whitealbum.account.AccountManager;
import me.lecter.whitealbum.client.RiotClient;
import me.lecter.whitealbum.client.ValorantAPI;
import me.lecter.whitealbum.client.exceptions.RiotException;

@Controller
public class LoginController {
	@RequestMapping(value={"/", "/index.html", "/index.php", "/lecterkn/" }, method = RequestMethod.GET)
	public String get(Model model) {
		
		return "login.html";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> post(@RequestBody LoginService account) {
		Map<String, Object> map = new HashMap<String, Object>();
		RiotClient client = RiotClient.create(account.getUsername(), account.getPassword());
		
		try {
			if (!client.login()) {
				map.put("message", "failed to logging in");
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.FORBIDDEN);
			}
		} catch (RiotException e) {
			e.printStackTrace();
			map.put("message", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (account.isRemember()) {
			AccountManager.save(client.getUsername(), client.getPassword());
		}
		
		ValorantAPI api = ValorantAPI.create(client.getAccess_token(), client.getEntitlements_token());
		
		map.put("username", api.getStoreFront().getUsername());
		map.put("singleItemOffers", api.getStoreFront().getSingleItemOffers());
		map.put("nightmarket", api.getStoreFront().getNightmarket());
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}
