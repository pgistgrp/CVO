package org.pgist.tags;

import java.util.Collection;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.projects.Project;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectSuite;


/**
 * PGIST JSP Expression Language functions.<br>
 * 
 * Function list:
 * <ul>
 *   <li>contains(collection, object) - return true if object contained in collection</li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class PgistELFunctions extends SimpleTagSupport {
    
	public static boolean contains(Collection collection, Object object) {
        if (collection==null) return false;
        return collection.contains(object);
    }//contains()
    
	public static boolean containsRef(ProjectSuite suite, Project project, ProjectAlternative alt) {
        if (suite==null) return false;
        if (project==null) return false;
        if (alt==null) return false;
        return true;
    }//containsRef()
	
}//class PgistELFunctions
