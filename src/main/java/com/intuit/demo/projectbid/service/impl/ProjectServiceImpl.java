package com.intuit.demo.projectbid.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intuit.demo.projectbid.domain.core.Project;
import com.intuit.demo.projectbid.domain.core.ProjectStatusType;
import com.intuit.demo.projectbid.domain.errorhandling.AppException;
import com.intuit.demo.projectbid.helper.UniqueIdGenerator;
import com.intuit.demo.projectbid.service.ProjectService;

/**
 * @author Jeyavelu Pillay
 * Service Implementation - Project
 */
@Repository("projectService")
@Transactional
public class ProjectServiceImpl implements ProjectService {
	
	private static UniqueIdGenerator idGenerator;
	
	private static final String KEY = "projectMap";
	  
	// Map of ProjectId to Project
	@Resource(name="redisTemplate")
	private HashOperations<String, String, Project> hashOps;	
	
	@PostConstruct
	public void init() throws Exception {
			System.out.println("projects - REDIS CACHE PRELOADED !!");
			preloadProjectMap();		
	}
	
	/**
	 * Method to retrieve project by projectId
	 * @param projectId String
	 * @return Project
	 * @throws AppException
	 */
	@Override
	public Project retrieveProject(String projectId) throws AppException {	
		Project current = hashOps.get(KEY, projectId);
		return current;
	}
	
	/**
	 * Method to retrieve projects list by status
	 * @param projectStatusType ProjectStatusType
	 * @return List<Project>
	 * @throws AppException
	 */
	@Override
	public List<Project> retrieveProjects(ProjectStatusType projectStatusType) throws AppException {		
		List<Project> projectList = new ArrayList<Project>();	
		for(Map.Entry<String, Project> entry : hashOps.entries(KEY).entrySet()) {
			if(null != projectStatusType) {
				if(entry.getValue().getStatus().equals(projectStatusType.name()))
					projectList.add(entry.getValue());
			} else {
				projectList.add(entry.getValue());
			}
		}
		return projectList;
	}
	
	/**
	 * Method to create a new project
	 * @param project Project
	 * @return String projectId
	 * @throws AppException
	 */
	public String createProject(Project project) throws AppException {
		
		project.setCreatedDate(new Date());
		project.setProjectId(generateProjectSequenceId());
		
		if(null == project.getStatus())
			project.setStatus(ProjectStatusType.O.name());
		
		hashOps.putIfAbsent(KEY, project.getProjectId(), project);

		return project.getProjectId();		
	}
	

	/**
	 * Method to init project Map
	 * @return Map<String, Project>
	 * @throws AppException
	 */
	private void preloadProjectMap() throws AppException {
		Project project = new Project();
		project.setDescription("Implement Hello World using Spring Boot");
		project.setSellerId("100");
		project.setTitle("Hello World Project");
		project.setMaxBudget(10000);
		project.setStatus(ProjectStatusType.C.name());
		int dayAfter = 1;
		project.setTargetDate(new Date(new Date().getTime()+(dayAfter*24*60*60*1000)));
		createProject(project);
		
		project = new Project();
		project.setDescription("Implement Demo App using Spring MVC");
		project.setSellerId("101");
		project.setTitle("Spring MVC Project");
		project.setMaxBudget(2000);
		project.setStatus(ProjectStatusType.O.name());
		dayAfter = 2;
		project.setTargetDate(new Date(new Date().getTime()+(dayAfter*24*60*60*1000)));
		createProject(project);

		
		project = new Project();
		project.setDescription("Implement Sample Project by Node JS");
		project.setSellerId("100");
		project.setTitle("Node JS Project");
		project.setMaxBudget(6000);
		project.setStatus(ProjectStatusType.C.name());
		dayAfter = 1;
		project.setTargetDate(new Date(new Date().getTime()+(dayAfter*24*60*60*1000)));
		createProject(project);

		project = new Project();
		project.setDescription("Implement Demo App using Django");
		project.setSellerId("101");
		project.setTitle("Django Project");
		project.setMaxBudget(6000);
		project.setStatus(ProjectStatusType.O.name());
		dayAfter = 3;
		project.setTargetDate(new Date(new Date().getTime()+(dayAfter*24*60*60*1000)));
		createProject(project);
		
	}
	
	private static String generateProjectSequenceId() {
		return Long.toString(idGenerator.INSTANCE.incrementProjectSequence());
	}
	
	
}
