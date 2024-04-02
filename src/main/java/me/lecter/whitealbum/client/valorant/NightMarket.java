package me.lecter.whitealbum.client.valorant;

import java.util.ArrayList;
import java.util.List;

public class NightMarket {
	
	private List<NightMarketOffer> offers = new ArrayList<NightMarketOffer>();

	public List<NightMarketOffer> getOffers() {
		return offers;
	}

	public void setOffers(List<NightMarketOffer> offers) {
		this.offers = offers;
	}
	
	public void addOffer(NightMarketOffer offer) {
		this.offers.add(offer);
	}
}
