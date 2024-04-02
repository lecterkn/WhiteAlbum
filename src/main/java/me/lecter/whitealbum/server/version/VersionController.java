package me.lecter.whitealbum.server.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {
	@Autowired
	private VersionService versionService;

	@RequestMapping({"/version"})
	public Version version() {
		return this.versionService.getVersion();
	}
}
