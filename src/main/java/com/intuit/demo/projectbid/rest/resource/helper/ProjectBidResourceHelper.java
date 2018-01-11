package com.intuit.demo.projectbid.rest.resource.helper;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.intuit.demo.projectbid.domain.core.Bid;
import com.intuit.demo.projectbid.domain.core.Project;
import com.intuit.demo.projectbid.domain.core.ProjectStatusType;
import com.intuit.demo.projectbid.domain.errorhandling.AppException;

/**
 * @author Jeyavelu Pillay
 * Service Helper
 */
public class ProjectBidResourceHelper {
	
	public static void validateCreateProjectRequest(Project project)
			throws AppException {
	   	
    	if(StringUtils.isEmpty(project.getTitle())) {
     	    throw new AppException("Invalid Title");
    	}
    	
    	if(StringUtils.isEmpty(project.getDescription())) {
     	    throw new AppException("Invalid Description");
    	}
    	
    	if(StringUtils.isEmpty(project.getSellerId())) {
     	    throw new AppException("Invalid SellerId");
    	}		
					
		if(project.getMaxBudget() <= 0 || Double.isNaN(project.getMaxBudget()))
			throw new AppException("Invalid MaxBudget amount");
		
		if(project.getTargetDate().before((new Date())))
			throw new AppException("TargetDate cannot be lesser than current date/time");

	}	
	
	
	public static void validateCreateBidRequest(Project project, Bid bid)
			throws AppException {
		
		if(project.getTargetDate().before((new Date())))
			throw new AppException("Bid cannot be placed after target date/time");
		
		if(project.getStatus().equals(ProjectStatusType.C.name()))
			throw new AppException("Bid cannot be placed for closed project");
 	
    	if(StringUtils.isEmpty(bid.getBuyerId()))
     	    throw new AppException("Invalid BuyerId");
    	else if(bid.getBuyerId().equals(project.getSellerId()))
    		 throw new AppException("SellerId cannot be same as BuyerId");
					
		if(bid.getBidAmount() <= 0 || Double.isNaN(bid.getBidAmount()))
			throw new AppException("Invalid Bid Amount");
		else if(bid.getBidAmount() > project.getMaxBudget())
			throw new AppException("Bid Amount cannot be greater than max budget");
		
	}	

}
