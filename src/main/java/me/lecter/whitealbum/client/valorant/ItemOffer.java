package me.lecter.whitealbum.client.valorant;

public class ItemOffer {
	private String offerId;
	private int cost;
	private String displayName;
	private String displayIcon;

	public ItemOffer(String offerId, int cost, String displayName, String displayIcon) {
		this.offerId = offerId;
		this.cost = cost;
		this.displayName = displayName;
		this.displayIcon = displayIcon;
	}

	public String getOfferId() {
		return this.offerId;
	}

	public int getCost() {
		return this.cost;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public String getDisplayIcon() {
		return this.displayIcon;
	}
}
