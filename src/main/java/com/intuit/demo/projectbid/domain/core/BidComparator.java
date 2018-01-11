package com.intuit.demo.projectbid.domain.core;

import java.io.Serializable;
import java.util.Comparator;

public class BidComparator implements Comparator<Bid>, Serializable {

	// Return lowest bidAmount, if more than one bids have equal amount, then use earliest createdDate
	@Override
	public int compare(Bid one, Bid two) {
		if(0 == Double.compare(one.getBidAmount(), two.getBidAmount()))
			return one.getCreatedDate().compareTo(two.getCreatedDate());
		else
			return Double.compare(one.getBidAmount(), two.getBidAmount());
	}

}
