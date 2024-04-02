package me.lecter.whitealbum.client.valorant;

import java.util.ArrayList;
import java.util.List;

public class SingleItemOffers {
	public List<ItemOffer> offers = new ArrayList<ItemOffer>();

	public List<ItemOffer> getOffers() {
		return this.offers;
	}

	public void setOffers(List<ItemOffer> offers) {
		this.offers = offers;
	}

	public void addOffer(ItemOffer offer) {
		this.offers.add(offer);
	}
}
