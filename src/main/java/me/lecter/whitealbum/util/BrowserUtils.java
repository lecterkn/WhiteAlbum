package me.lecter.whitealbum.util;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import me.lecter.whitealbum.Properties;

public class BrowserUtils {
	public static boolean open(String URL) {
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
			try {
				Desktop.getDesktop().browse(new URI(URL));
				return true;
			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			if (Properties.OS == Properties.OSType.WINDOWS) {
				return exec("cmd /c start " + URL);
			}
			else {
				return exec("open " + URL);
			}
		}
	}

	private static boolean exec(String cmd) {
		try {
			Runtime.getRuntime().exec(cmd);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
