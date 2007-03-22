package org.pgist.tags;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.projects.Project;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectSuite;


/**
 * PGIST JSP Expression Language functions.<br>
 * 
 * Special tag that turns into a true or false is the project alternative is in the provided suite
 * 
 * @author Matt Paulin
 *
 */
public class ContainsRef extends SimpleTagSupport {
    
	private ProjectSuite suite;
	private Project project;
	private ProjectAlternative alt;
	
	public void setAlt(ProjectAlternative alt) {
		this.alt = alt;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setSuite(ProjectSuite suite) {
		this.suite = suite;
	}	
	
	public static boolean contains(ProjectSuite suite, Project project, ProjectAlternative alt) {
        if (suite==null) return false;
        if (project==null) return false;
        if (alt==null) return false;
        return true;
    }//contains()
    
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();
        if(contains(this.suite, this.project, this.alt)) {
            writer.write("true");        	        	
        } else {
            writer.write("false");        	
        }    	
    }


}//class ContainsRef
