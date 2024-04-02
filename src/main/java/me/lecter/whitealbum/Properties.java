package me.lecter.whitealbum;

public class Properties {
	public static final String VARIFICATION_CODE = "3b4gBY4C75sx8DD9lvZL0mM1wLGnysYHFERx9136YnURDBbXwb";
	public static final Properties.OSType OS;

	static {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.startsWith("win")) {
			OS = Properties.OSType.WINDOWS;
		} else if (osName.startsWith("mac")) {
			OS = Properties.OSType.MAC;
		} else {
			OS = Properties.OSType.LINUX;
		}
	}

	public static enum OSType {
		WINDOWS,
		MAC,
		LINUX;
	}
}
