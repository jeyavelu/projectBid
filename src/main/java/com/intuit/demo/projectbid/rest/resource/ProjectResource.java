package com.intuit.demo.projectbid.rest.resource;


import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.intuit.demo.projectbid.domain.core.Bid;
import com.intuit.demo.projectbid.domain.core.Project;
import com.intuit.demo.projectbid.domain.errorhandling.AppException;;

/**
 * @author Jeyavelu Pillay
 * REST interface for Project
 */
public interface ProjectResource {
	
    public Response getProject(@PathParam("project-id") String projectId) throws AppException;

	public Response getAllProjectsByStatus(@PathParam("statusType") String statusType) throws AppException;
	
	public Response getAllProjects() throws AppException;
	
	public Response createProject(Project project) throws AppException;	
	
	public Response createBid(String projectId, Bid bid) throws AppException;

	Response getBidsByProject(String projectId) throws AppException;
    
}