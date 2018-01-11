package com.intuit.demo.projectbid.service;

import java.util.List;

import com.intuit.demo.projectbid.domain.core.Bid;
import com.intuit.demo.projectbid.domain.errorhandling.AppException;

/**
 * @author Jeyavelu Pillay
 * Service interface - Bid
 */
public interface BidService {
	

	/**
	 * Method to create a new bid
	 * @param bid Bid
	 * @return String bidId
	 * @throws AppException
	 */
	public String createBid(Bid bid) throws AppException;
	
	/**
	 * Method to retrieve lowest Bid for a projectId
	 * @param projectId String
	 * @return Bid bid
	 * @throws AppException
	 */
	public Bid retrieveLowestBid(String projectId) throws AppException;

	/**
	 * Method to retrieve bids list by projectId
	 * @param projectId String
	 * @return List<Bid>
	 * @throws AppException
	 */
	List<Bid> retrieveBids(String projectId) throws AppException;


}
