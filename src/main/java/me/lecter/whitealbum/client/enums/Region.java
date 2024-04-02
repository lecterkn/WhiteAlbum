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
}
