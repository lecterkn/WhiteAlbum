package me.lecter.whitealbum.server.version;

import org.springframework.stereotype.Service;

import me.lecter.whitealbum.WhiteAlbum;

@Service
public class VersionService {
	private Version version = new Version(WhiteAlbum.NAME, WhiteAlbum.VERSION);

	public Version getVersion() {
		return this.version;
	}
}
