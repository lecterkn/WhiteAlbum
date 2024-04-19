package me.lecter.whitealbum.server.terminate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import me.lecter.whitealbum.WhiteAlbum;

@RestController
public class TerminateController {

	@RequestMapping(value="/terminate", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> execute(@RequestHeader("varification_code") String varification_code) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (!varification_code.equals(WhiteAlbum.getConfigs().getVarification_code())) {
			map.put("message", "unauthorized");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.UNAUTHORIZED);
		}

		map.put("message", "success");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}
