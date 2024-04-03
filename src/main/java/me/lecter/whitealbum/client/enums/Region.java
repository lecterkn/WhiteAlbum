package me.lecter.whitealbum.client.enums;

public enum Region {
	AP("ap"),
	NA("na"),
	LATAM("latam"),
	BR("br"),
	EU("eu"),
	KR("kr");

	private String region;

	private Region(String region) {
		this.region = region;
	}

	public String getParam() {
		return this.region;
	}
	
	public static Region getRegion(String region) {
		for (Region reg : Region.values()) {
			if (reg.getParam().equalsIgnoreCase(region)) {
				return reg;
			}
		}
		return null;
	}
}
