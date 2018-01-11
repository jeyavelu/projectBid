package com.intuit.demo.projectbid.rest.resource.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.intuit.demo.projectbid.domain.core.Bid;
import com.intuit.demo.projectbid.domain.core.Project;
import com.intuit.demo.projectbid.domain.core.ProjectStatusType;
import com.intuit.demo.projectbid.domain.errorhandling.AppException;
import com.intuit.demo.projectbid.rest.resource.ProjectResource;
import com.intuit.demo.projectbid.rest.resource.helper.ProjectBidResourceHelper;
import com.intuit.demo.projectbid.service.BidService;
import com.intuit.demo.projectbid.service.ProjectService;
import com.intuit.demo.projectbid.application.AppConstants;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


/**
 * @author Jeyavelu Pillay
 * REST service for Project
 */
@Service("projectRestService")
@ExposesResourceFor(Project.class)
@Path("/projects")
public class ProjectResourceImpl implements ProjectResource {    

	@Inject
	private EntityLinks entityLinks;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private BidService bidService;
	
	
   /* public ProjectService getProjectService() {
		return projectService;
	}

	
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}*/

	@GET
    @Path("/{project-id}")
    @Produces("application/json")
	@Override
	/**
	 * REST service to return Project by projectId
     * @param projectId String
     * @return Project
     * @throws AppException
	 */
    public Response getProject(@PathParam("project-id") String projectId) throws AppException {
       Project project = projectService.retrieveProject(projectId);   
       
       if(null == project)
    	   throw new AppException("Invalid Project Id", AppConstants.API_URL, AppConstants.PROJECTBID_ERRORCODE);
            
       Bid lowestBid = bidService.retrieveLowestBid(projectId);
       if(null != lowestBid)
    	   project.setLowestBid(lowestBid);
       
       Resource<Project> resource = new Resource<>(project);
       Link selfRel = entityLinks.linkToSingleResource(Project.class, projectId);
       resource.add(selfRel);
       
       // Add Create Bid link only for Open projects
       if(project.getStatus().equals(ProjectStatusType.O.name()))
    	   resource.add(linkTo(ProjectResourceImpl.class).slash("projects").slash(projectId).slash("bids").withRel("Create Bid"));
       
       return Response
               .status(200)
               .entity(resource).build();
    }	
	
	@GET
    @Path("/status={status}")
    @Produces("application/json")
	@Override
	/**
	 * REST service to return all Projects by status
     * @param statusType String
     * @return Project
     * @throws AppException
	 */
    public Response getAllProjectsByStatus(@PathParam("status") String statusType) throws AppException {
       
		if(!StringUtils.isEmpty(statusType) && ProjectStatusType.contains(statusType)) {
			List<Project> projectList = projectService.retrieveProjects(ProjectStatusType.valueOf(statusType));	       
	       
			List<Resource> resourceList = new ArrayList<Resource>();
			for(Project project : projectList) {
				Resource<Project> resource = new Resource<>(project);
				resource.add(entityLinks.linkToSingleResource(Project.class, project.getProjectId()));
				
		       // Add Create Bid link only for Open projects
			    if(project.getStatus().equals(ProjectStatusType.O.name()))
		    	   resource.add(linkTo(ProjectResourceImpl.class).slash("projects").slash(project.getProjectId()).slash("bids").withRel("Create Bid"));
								
				resourceList.add(resource);
			}
			
			Resources<Resource> resources = new Resources<>(resourceList);
			resources.add(entityLinks.linkToCollectionResource(Project.class).withRel("list"));
			
			return Response
	               .status(200)
	               .entity(resources).build();			
		} else {
        	String supportedValues = Arrays.toString(ProjectStatusType.values());
    	   throw new AppException("Invalid Project Status. Valid values are : " + supportedValues, AppConstants.API_URL, AppConstants.PROJECTBID_ERRORCODE);
		}		
	
    }
	
	@GET
    @Path("/")
    @Produces("application/json")
	@Override
	/**
	 * REST service to return all Projects
     * @return Response Project List
     * @throws AppException
	 */
    public Response getAllProjects() throws AppException {
		
       
		List<Project> projectList = projectService.retrieveProjects(null);	   
		List<Resource> resourceList = new ArrayList<Resource>();
		for(Project project : projectList) {
			Resource<Project> resource = new Resource<>(project);
			resource.add(entityLinks.linkToSingleResource(Project.class, project.getProjectId()));
			
	       // Add Create Bid link only for Open projects
		   if(project.getStatus().equals(ProjectStatusType.O.name()))
	    	   resource.add(linkTo(ProjectResourceImpl.class).slash("projects").slash(project.getProjectId()).slash("bids").withRel("Create Bid"));
		   
			resourceList.add(resource);

		}
		
		Resources<Resource> resources = new Resources<>(resourceList);
		resources.add(entityLinks.linkToCollectionResource(Project.class).withRel("list"));
		
	    return Response
           .status(200)
           .entity(resources).build();	
    }

	@POST
    @Path("/")
    @Produces("application/json")
	@Override
	public Response createProject(Project project) throws AppException {
		
		ProjectBidResourceHelper.validateCreateProjectRequest(project);
		
		String projectID = projectService.createProject(project);
		if(StringUtils.isEmpty(projectID)) {
	    	   throw new AppException("System error. Project cannot be created.", AppConstants.API_URL, AppConstants.PROJECTBID_ERRORCODE);
		} else  {
		    Link selfRel = entityLinks.linkToSingleResource(Project.class, projectID);
		    Resource<Project> resource = new Resource<>(project);
		    resource.add(selfRel);
		    
	        // Add Create Bid link only for Open projects
	        resource.add(linkTo(ProjectResourceImpl.class).slash("projects").slash(project.getProjectId()).slash("bids").withRel("Create Bid"));
		  		    
		    return Response
		            .status(201)
		            .entity(resource)
		            .build();	
		}
	}
	
	
	@GET
    @Path("/{project-id}/bids")
    @Produces("application/json")
	@Override
	/**
	 * REST service to return bids by projectId
     * @param projectId String
     * @return Project
     * @throws AppException
	 */
    public Response getBidsByProject(@PathParam("project-id") String projectId) throws AppException {
       Project project = projectService.retrieveProject(projectId);   
       
       if(null == project)
    	   throw new AppException("Invalid Project Id", AppConstants.API_URL, AppConstants.PROJECTBID_ERRORCODE);
             
		List<Bid> bidList = bidService.retrieveBids(projectId);
		Resources<Bid> resources = new Resources<>(bidList);
        
		// Add Create Bid link only for Open projects
		if(project.getStatus().equals(ProjectStatusType.O.name()))
			resources.add(linkTo(ProjectResourceImpl.class).slash("projects").slash(project.getProjectId()).slash("bids").withRel("Create Bid"));
	  			
	    return Response
           .status(200)
           .entity(resources).build();	
    }	
	
	@POST
    @Path("/{project-id}/bids")
    @Produces("application/json")
	@Override
	public Response createBid(@PathParam("project-id") String projectId, Bid bid) throws AppException {
       Project project = projectService.retrieveProject(projectId);   
       
       if(null == project)
    	   throw new AppException("Invalid Project Id", AppConstants.API_URL, AppConstants.PROJECTBID_ERRORCODE);
	     
		ProjectBidResourceHelper.validateCreateBidRequest(project, bid);
		
		bid.setProjectId(projectId);
		String bidId = bidService.createBid(bid);
		
		if(StringUtils.isEmpty(bidId)) {
	    	   throw new AppException("System error. Bid cannot be created.", AppConstants.API_URL, AppConstants.PROJECTBID_ERRORCODE);
		} else  {
		    return Response
		            .status(201)
		            .entity(bid)
		            .build();	
		}
	}
   
}