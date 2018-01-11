package com.intuit.demo.projectbid.domain.core;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "projectId", "status", "title",
	"maxBudget", "targetDate", "sellerId", "lowestBid", "description", "createdDate", "links" })

public class Project extends ResourceSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("projectId")
	private String projectId;
  
	@JsonProperty("description")
	private String description;
   
	@JsonProperty("maxBudget")
	private double maxBudget;
		
	@JsonProperty("title")
    private String title;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("sellerId")
	private String sellerId;
	
	@JsonProperty("lowestBid")
	private Bid lowestBid;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss a z", timezone="GMT")
	private Date createdDate;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss a z", timezone="GMT")
	private Date targetDate;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(double maxBudget) {
		this.maxBudget = maxBudget;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}	

	public Bid getLowestBid() {
		return lowestBid;
	}

	public void setLowestBid(Bid lowestBid) {
		this.lowestBid = lowestBid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", description=" + description + ", maxBudget=" + maxBudget
				+ ", title=" + title + ", status=" + status + ", ownerId=" + sellerId + "]";
	}


}
