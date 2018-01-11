package com.intuit.demo.projectbid.service.impl.v2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.intuit.demo.projectbid.domain.core.Project;
import com.intuit.demo.projectbid.domain.core.ProjectStatusType;
import com.intuit.demo.projectbid.domain.errorhandling.AppException;
import com.intuit.demo.projectbid.helper.UniqueIdGenerator;
import com.intuit.demo.projectbid.service.ProjectService;

/**
 * @author Jeyavelu Pillay
 * Service Implementation - Project
 */
@Service("projectServiceV2")
public class ProjectServiceImplV2 implements ProjectService {
	
	private static UniqueIdGenerator idGenerator;
	private Map<String, Project> projectMap;
	
	//@PostConstruct
	public void init() throws Exception {
		if(null == projectMap || projectMap.isEmpty()) {
			projectMap = new HashMap<String, Project>();
			preloadProjectMap();		
		}
	}
	
	/**
	 * Method to retrieve project by projectId
	 * @param projectId String
	 * @return Project
	 * @throws AppException
	 */
	@Override
	public Project retrieveProject(String projectId) throws AppException {		
		Project current = projectMap.get(projectId);
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
		for(Map.Entry<String, Project> entry : projectMap.entrySet()) {
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
		//System.out.println(project.getTargetDate());
		//System.out.println(project.getTargetDate().getTime());
		//System.out.println("ID " + project.getProjectId());
		project.setCreatedDate(new Date());
		project.setProjectId(generateProjectSequenceId());
		
		if(null == project.getStatus())
			project.setStatus(ProjectStatusType.O.name());
		
		projectMap.put(project.getProjectId(), project);
		return project.getProjectId();		
	}
	

	/**
	 * Method to init project Map
	 * @return Map<String, Project>
	 * @throws AppException
	 */
	private void preloadProjectMap() throws AppException {
		Project project = new Project();
		//project.setProjectId("1000");
		//project.setProjectId(generateSequenceId());
		project.setDescription("Implement Hello World using Spring Boot");
		project.setSellerId("100");
		project.setTitle("Hello World Project");
		project.setMaxBudget(10000);
		project.setStatus(ProjectStatusType.C.name());
		int dayAfter = 1;
		project.setTargetDate(new Date(new Date().getTime()+(dayAfter*24*60*60*1000)));
		//projectMap.put(project.getProjectId(), project);
		createProject(project);
		
		project = new Project();
		//project.setProjectId("2000");
		//project.setProjectId(generateSequenceId());
		project.setDescription("Implement Demo App using Spring MVC");
		project.setSellerId("101");
		project.setTitle("Spring MVC Project");
		project.setMaxBudget(2000);
		project.setStatus(ProjectStatusType.O.name());
		dayAfter = 2;
		project.setTargetDate(new Date(new Date().getTime()+(dayAfter*24*60*60*1000)));
		//projectMap.put(project.getProjectId(), project);
		createProject(project);

		
		project = new Project();
		//project.setProjectId("3000");
		//project.setProjectId(generateSequenceId());
		project.setDescription("Implement Sample Project by Node JS");
		project.setSellerId("100");
		project.setTitle("Node JS Project");
		project.setMaxBudget(6000);
		project.setStatus(ProjectStatusType.C.name());
		//projectMap.put(project.getProjectId(), project);
		dayAfter = 1;
		project.setTargetDate(new Date(new Date().getTime()+(dayAfter*24*60*60*1000)));
		createProject(project);

		project = new Project();
		//project.setProjectId("4000");
		//project.setProjectId(generateSequenceId());
		project.setDescription("Implement Demo App using Django");
		project.setSellerId("101");
		project.setTitle("Django Project");
		project.setMaxBudget(6000);
		project.setStatus(ProjectStatusType.O.name());
		//projectMap.put(project.getProjectId(), project);
		dayAfter = 3;
		project.setTargetDate(new Date(new Date().getTime()+(dayAfter*24*60*60*1000)));
		createProject(project);
		
	}
	
	private static String generateProjectSequenceId() {
		return Long.toString(idGenerator.INSTANCE.incrementProjectSequence());
	}
	
	
}
