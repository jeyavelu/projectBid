package com.intuit.demo.projectbid.service.impl.v2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.intuit.demo.projectbid.domain.core.Bid;
import com.intuit.demo.projectbid.domain.core.BidComparator;
import com.intuit.demo.projectbid.domain.core.Project;
import com.intuit.demo.projectbid.domain.core.ProjectStatusType;
import com.intuit.demo.projectbid.domain.errorhandling.AppException;
import com.intuit.demo.projectbid.helper.UniqueIdGenerator;
import com.intuit.demo.projectbid.service.BidService;

/**
 * @author Jeyavelu Pillay
 * Service Implementation - Bid
 */
@Service("bidServiceV2")
public class BidServiceImplV2 implements BidService {
	
	private static UniqueIdGenerator idGenerator;
	private Map<String, PriorityQueue<Bid>> bidMap;
	
	//@PostConstruct
	public void init() throws Exception {
		if(null == bidMap || bidMap.isEmpty()) {
			bidMap = new HashMap<String, PriorityQueue<Bid>>();
			preloadBidMap();		
		}
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
		
		//if(null == bid.getStatus())
			//bid.setStatus(ProjectStatusType.O.name());
		
		PriorityQueue<Bid> bidQueue;
		if(bidMap.containsKey(bid.getProjectId())) {
			bidQueue = bidMap.get(bid.getProjectId());
		} else {
			bidQueue = new PriorityQueue<Bid>(new BidComparator());
		}
		
		bidQueue.add(bid);
		bidMap.put(bid.getProjectId(), bidQueue);
		
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
		PriorityQueue<Bid> bidQueue = bidMap.get(projectId);
		List<Bid> bidList = new ArrayList<Bid>(bidQueue);	
		return bidList;
	}
	
	/**
	 * Method to retrieve lowest Bid for a projectId
	 * @param projectId String
	 * @return Bid bid
	 * @throws AppException
	 */
	public Bid retrieveLowestBid(String projectId) throws AppException {
		if(StringUtils.isEmpty(projectId) || !bidMap.containsKey(projectId))
			return null;
		else
			return bidMap.get(projectId).peek();		
	}
	

	/**
	 * Method to init project Map
	 * @return Map<String, Bid>
	 * @throws AppException
	 */
	private void preloadBidMap() throws AppException {
		Bid bid = new Bid();
		bid.setProjectId("1001");
		//bid.setSellerId("100");
		bid.setBuyerId("203");
		bid.setBidAmount(100);
		//bid.setStatus(ProjectStatusType.O.name());
		createBid(bid);
		
		bid = new Bid();
		bid.setProjectId("1000");
		//bid.setSellerId("101");
		bid.setBuyerId("202");
		bid.setBidAmount(99);
		//bid.setStatus(ProjectStatusType.O.name());
		createBid(bid);

		
		bid = new Bid();
		bid.setProjectId("1000");
		//bid.setSellerId("100");
		bid.setBuyerId("201");
		bid.setBidAmount(1000);
		//bid.setStatus(ProjectStatusType.O.name());
		createBid(bid);

		bid = new Bid();
		bid.setProjectId("1000");
		//bid.setSellerId("101");
		bid.setBuyerId("200");
		bid.setBidAmount(500);
		//bid.setStatus(ProjectStatusType.O.name());
		createBid(bid);
		
	}
	
	private static String generateBidSequenceId() {
		return Long.toString(idGenerator.INSTANCE.incrementBidSequence());
	}
	
	
}
