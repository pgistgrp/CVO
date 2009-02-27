package org.pgist.lm;

import java.util.Collection;

import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectService;

public class LmServiceImpl implements LmService{

	private LmDAO lmDAO;
	
	private ProjectService projectService;
	
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public void setLmDAO(LmDAO lmDAO) {
		this.lmDAO = lmDAO;
	}

	public ProjectService getProjectService() {
		return projectService;
	}
	
	public Collection getProjects() throws Exception {
		return projectService.getProjects();
	}
	
	public ProjectAlternative getAlt(Long altId) throws Exception {
		return projectService.getProjectAlternativeById(altId);
	}
}
