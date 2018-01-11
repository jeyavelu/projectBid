package com.intuit.demo.projectbid.domain.core;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "bidId", "projectId", "bidAmount",
	"status", "buyerId", "createdDate", "links" })
public class Bid implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("bidId")
	private String bidId;
	
	@JsonProperty("projectId")
	private String projectId;
   
	@JsonProperty("bidAmount")
	private double bidAmount;
		
	//@JsonProperty("status")
	//private String status;
	
	//@JsonProperty("sellerId")
	//private String sellerId;
	
	@JsonProperty("buyerId")
	private String buyerId;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss a z", timezone="GMT")
	private Date createdDate;

	public String getBidId() {
		return bidId;
	}

	public void setBidId(String bidId) {
		this.bidId = bidId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public double getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(double bidAmount) {
		this.bidAmount = bidAmount;
	}

	/*public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}*/

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Bid [bidId=" + bidId + ", projectId=" + projectId + ", bidAmount=" + bidAmount/* + ", status=" + status*/
				/*+ ", sellerId=" + sellerId */+ ", buyerId=" + buyerId + ", createdDate=" + createdDate + "]";
	}



}
