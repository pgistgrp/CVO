package org.pgist.lm;

import java.util.Collection;

import org.pgist.projects.ProjectAlternative;

public interface LmService {

	Collection getProjects() throws Exception;
	
	ProjectAlternative getAlt(Long altId) throws Exception;
	
}
