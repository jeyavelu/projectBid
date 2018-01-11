package com.intuit.demo.projectbid.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.intuit.demo.projectbid.domain.core.Bid;
import com.intuit.demo.projectbid.domain.core.BidComparator;
import com.intuit.demo.projectbid.domain.errorhandling.AppException;
import com.intuit.demo.projectbid.helper.UniqueIdGenerator;
import com.intuit.demo.projectbid.service.BidService;

/**
 * @author Jeyavelu Pillay
 * Service Implementation - Bid
 */
@Repository("bidService")
@Transactional
public class BidServiceImpl implements BidService {
	
	private static UniqueIdGenerator idGenerator;
	
	private static final String MAPKEY = "bidMap";
	 
	// Map of ProjectId-BuyerId to Bid
	@Resource(name="redisTemplate")
	private HashOperations<String, String, Bid> hashOps;	
	
	// SortedSet of ProjectId-BuyerId keys sorted by bidAmount
	@Resource(name="redisTemplate")
	private ZSetOperations<String, String> zSetOps;	
	
	@PostConstruct
	public void init() throws Exception {
		System.out.println("bids - REDIS CACHE PRELOADED !!");
		preloadBidMap();		
	}
	
	public String getBidKey(Bid bid) {
		return bid.getProjectId() + "_" + bid.getBuyerId();
	}
	
		
	/**
	 * Method to create a new bid
	 * @param bid Bid
	 * @return String bidId
	 * @throws AppException
	 */
	public String createBid(Bid bid) throws AppException {

		bid.setCreatedDate(new Date());
		bid.setBidId(generateBidSequenceId());
		String bidKey = getBidKey(bid);
		if(hashOps.hasKey(MAPKEY, bidKey)) {
			hashOps.put(MAPKEY, bidKey, bid);
			zSetOps.remove(bid.getProjectId(), bidKey);
			zSetOps.add(bid.getProjectId(), bidKey, bid.getBidAmount());
		} else {
			hashOps.put(MAPKEY, bidKey, bid);
			zSetOps.add(bid.getProjectId(), bidKey, bid.getBidAmount());
		}
		
		return bid.getBidId();		
	}
	
	/**
	 * Method to retrieve bids list by projectId
	 * @param projectId String
	 * @return List<Bid>
	 * @throws AppException
	 */
	@Override
	public List<Bid> retrieveBids(String projectId) throws AppException {	
		Set<String> bidKeys = zSetOps.range(projectId, 0, -1);
		//System.out.println(bidKeys.size());
		
		List<Bid> bidList = new ArrayList<Bid>();
		for(String bidKey : bidKeys) {
			bidList.add(hashOps.get(MAPKEY, bidKey));
		}			
		return bidList;
	}
	
	/**
	 * Method to retrieve lowest Bid for a projectId
	 * @param projectId String
	 * @return Bid bid
	 * @throws AppException
	 */
	public Bid retrieveLowestBid(String projectId) throws AppException {
		if(StringUtils.isEmpty(projectId) || zSetOps.size(projectId) == 0)
			return null;
		else {
			//System.out.println(zSetOps.range(projectId, 0, 0).size());
			String lowestBidKey = zSetOps.range(projectId, 0, 0).iterator().next();
			return hashOps.get(MAPKEY, lowestBidKey);
		}
	}
	

	/**
	 * Method to init project Map
	 * @return Map<String, Bid>
	 * @throws AppException
	 */
	private void preloadBidMap() throws AppException {
		Bid bid = new Bid();
		bid.setProjectId("1001");
		bid.setBuyerId("203");
		bid.setBidAmount(100);
		createBid(bid);
		
		bid = new Bid();
		bid.setProjectId("1000");
		bid.setBuyerId("202");
		bid.setBidAmount(99);
		createBid(bid);

		
		bid = new Bid();
		bid.setProjectId("1000");
		bid.setBuyerId("201");
		bid.setBidAmount(1000);
		createBid(bid);

		bid = new Bid();
		bid.setProjectId("1000");
		bid.setBuyerId("200");
		bid.setBidAmount(500);
		createBid(bid);
		
	}
	
	private static String generateBidSequenceId() {
		return Long.toString(idGenerator.INSTANCE.incrementBidSequence());
	}
	
	
}
