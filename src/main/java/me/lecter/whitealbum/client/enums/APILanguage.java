package me.lecter.whitealbum.client.enums;

public enum APILanguage {
	AE("ar-AE"),
	DE("de-DE"),
	EN("en-US"),
	ES("es-ES"),
	MX("es-MX"),
	FR("fr-FR"),
	ID("id-ID"),
	IT("it-IT"),
	JP("ja-JP"),
	KR("ko-KR"),
	PL("pl-PL"),
	BR("pt-BR"),
	RU("ru-RU"),
	TH("th-TH"),
	TR("tr-TR"),
	VN("vi-VN"),
	CN("zh-CN"),
	TW("zh-TW");

	private String language;

	private APILanguage(String language) {
		this.language = language;
	}

	public String getParam() {
		return this.language;
	}
	
	public static APILanguage getLanguage(String language) {
		for (APILanguage lang : APILanguage.values()) {
			if (lang.getParam().equalsIgnoreCase(language)) {
				return lang;
			}
		}
		return null;
	}
}
