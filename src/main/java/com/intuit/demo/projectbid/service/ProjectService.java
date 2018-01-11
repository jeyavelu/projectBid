package com.intuit.demo.projectbid.service;

import java.util.List;

import com.intuit.demo.projectbid.domain.core.Project;
import com.intuit.demo.projectbid.domain.core.ProjectStatusType;
import com.intuit.demo.projectbid.domain.errorhandling.AppException;

/**
 * @author Jeyavelu Pillay
 * Service interface - Project
 */
public interface ProjectService {
	
	/**
	 * Method to retrieve project by projectId
	 * @param projectId String
	 * @return Project
	 * @throws AppException
	 */
	public Project retrieveProject(String projectId) throws AppException;

	/**
	 * Method to retrieve projects list by status
	 * @param projectStatusType ProjectStatusType
	 * @return List<Project>
	 * @throws AppException
	 */
	public List<Project> retrieveProjects(ProjectStatusType projectStatusType) throws AppException;

	/**
	 * Method to create a new project
	 * @param project Project
	 * @return String projectId
	 * @throws AppException
	 */
	public String createProject(Project project) throws AppException;

}
