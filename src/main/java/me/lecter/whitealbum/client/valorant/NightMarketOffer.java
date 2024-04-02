package me.lecter.whitealbum.client.valorant;

public class NightMarketOffer extends ItemOffer {

	private int discountPercent;
	private int finalCost;
	
	public NightMarketOffer(String offerId, int originalCost, String displayName, String displayIcon, int discountPercent, int finalCost) {
		super(offerId, originalCost, displayName, displayIcon);
		this.discountPercent = discountPercent;
		this.finalCost = finalCost;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public int getFinalCost() {
		return finalCost;
	}

	public void setFinalCost(int finalCost) {
		this.finalCost = finalCost;
	}
	
	
}
