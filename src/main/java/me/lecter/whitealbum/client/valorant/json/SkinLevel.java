package me.lecter.whitealbum.client.valorant.json;

public class SkinLevel {

	private String uuid;
	private String displayName;
	private String levelItem;
	private String displayIcon;
	private String streamedVideo;
	private String assetPath;
	
	
	
	public SkinLevel(String uuid, String displayName, String levelItem, String displayIcon, String streamedVideo,
			String assetPath) {
		super();
		this.uuid = uuid;
		this.displayName = displayName;
		this.levelItem = levelItem;
		this.displayIcon = displayIcon;
		this.streamedVideo = streamedVideo;
		this.assetPath = assetPath;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getLevelItem() {
		return levelItem;
	}
	public void setLevelItem(String levelItem) {
		this.levelItem = levelItem;
	}
	public String getDisplayIcon() {
		return displayIcon;
	}
	public void setDisplayIcon(String displayIcon) {
		this.displayIcon = displayIcon;
	}
	public String getStreamedVideo() {
		return streamedVideo;
	}
	public void setStreamedVideo(String streamedVideo) {
		this.streamedVideo = streamedVideo;
	}
	public String getAssetPath() {
		return assetPath;
	}
	public void setAssetPath(String assetPath) {
		this.assetPath = assetPath;
	}
	
	
}
