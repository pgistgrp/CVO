package org.pgist.tags;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.criteria.Criteria;
import org.pgist.criteria.CriteriaRef;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.discussion.InfoObject;
import org.pgist.funding.FundingSource;
import org.pgist.funding.FundingSourceAltRef;
import org.pgist.funding.FundingSourceAlternative;
import org.pgist.funding.FundingSourceRef;
import org.pgist.funding.FundingSourceSuite;
import org.pgist.projects.Project;
import org.pgist.projects.ProjectAltRef;
import org.pgist.projects.ProjectAlternative;
import org.pgist.projects.ProjectRef;
import org.pgist.projects.ProjectSuite;
import org.pgist.sarp.cht.CHTComment;
import org.pgist.sarp.cst.CSTComment;
import org.pgist.sarp.drt.Comment;
import org.pgist.system.SystemService;
import org.pgist.system.YesNoVoting;
import org.springframework.web.context.WebApplicationContext;

/**
 * PGIST JSP Expression Language functions.<br>
 * 
 * Function list:
 * <ul>
 * <li>contains(collection, object) - return true if object contained in
 * collection</li>
 * </ul>
 * 
 * @author kenny
 * 
 */
public class PgistELFunctions extends SimpleTagSupport {

	public static String verboseGradeSwitch(String strGrade) {
		
		float grade = Float.parseFloat(strGrade);
		String result = "unknown";
		
		
		if(grade < -2.51) {
			result = "major negative impact";
		} else if(grade < -1.51) {
			result = "moderate negative impact";
		} else if(grade < -.5) {
			result = "minor negative impact";
		} else if(grade < 0.5) {
			result = "no significant impact";
		} else if(grade < 1.5) {
			result = "minor positive impact";
		} else if(grade < 2.5) {
			result = "moderate positive impact";
		} else if(grade < 3.01) {
			result = "major positive impact";
		}
		
		return result;
    }// contains()
	
	public static String gradeSwitch(String ingrade) {
		
		if(ingrade.length() > 0) {
			char lastChar = ingrade.charAt(ingrade.length() -1);
			String result;
			switch (lastChar) {
			case '-':
				result = ingrade.substring(0, ingrade.length() -1) + "Minus";
				return result;
			case '+':
				result = ingrade.substring(0, ingrade.length() -1) + "Plus";	
				return result;
			}			
		}
		return ingrade;	
    }// contains()
	
	public static boolean containsProjAltRef(Collection collection, Long id) {
		Iterator i = collection.iterator();
		ProjectAltRef tempRef;
		while(i.hasNext()) {
			tempRef = (ProjectAltRef)i.next();
			if(tempRef.getId().equals(id)) return true;
		}
		return false;
    }// contains()

	public static boolean containsFundAltRef(Collection collection, Long id) {
		Iterator i = collection.iterator();
		FundingSourceAltRef tempRef;
		while(i.hasNext()) {
			tempRef = (FundingSourceAltRef)i.next();
			if(tempRef.getId().equals(id)) return true;
		}
		return false;
    }// contains()
	
	
	public static boolean contains(Collection collection, Object object) {
        if (collection==null) return false;
        return collection.contains(object);
    }// contains()
    
	public static boolean containsRef(ProjectSuite suite, Project project, ProjectAlternative alt) {
        if (suite==null) return false;
        if (project==null) return false;
        if (alt==null) return false;
        
        // First check that the project is in the suite
        ProjectRef ref = suite.getProjectReference(project);
        if(ref == null) return false;
        
        // Now check that the alternative is in one of the alt references
        return ref.containsAlternative(alt);
    }// containsRef()

	public static boolean containsRef(FundingSourceSuite suite, FundingSource funding, FundingSourceAlternative alt) {		
        if (suite==null) return false;
        if (funding==null) return false;
        if (alt==null) return false;
        
        // First check that the project is in the suite
        FundingSourceRef ref = suite.getFundingSourceReference(funding);
        if(ref == null) return false;
        
        // Now check that the alternative is in one of the alt references
        return ref.containsAlternative(alt);
    }// containsRef()
	
	public static boolean containsCriteria(CriteriaSuite cs, Criteria c) {
        if (cs==null) return false;
        if (c==null) return false;
        
        // First check that the criteria is in the suite
        CriteriaRef cr = cs.getCriteriaReference(c);
        if(cr == null) return false;
        
		return true;
	}

	public static boolean containsInfoObject(Criteria c, InfoObject io) {
        if (c==null) return false;
        if (io==null) return false;
        
        // First check that the theme is in the suite
        for(InfoObject infoObject: c.getInfoObjects()) {
        	if(infoObject.getId().equals(io.getId())) {
        		return true;
        	}
        }
		return false;
	}
	
	public static boolean voted(PageContext context, Object object) {
        WebApplicationContext appContext = (WebApplicationContext) context.getServletContext()
        	.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        SystemService systemService = (SystemService) appContext.getBean("systemService");
        
        try {
            YesNoVoting voting = null;
            if (object!=null) {
                if (object instanceof Comment) {
                    Comment comment = (Comment) object;
                    voting = systemService.getVoting(YesNoVoting.TYPE_SARP_DRT_COMMENT, comment.getId());
                } else if (object instanceof CSTComment) {
                    CSTComment comment = (CSTComment) object;
                    voting = systemService.getVoting(YesNoVoting.TYPE_SARP_CST_COMMENT, comment.getId());
                } else if (object instanceof CHTComment) {
                    CHTComment comment = (CHTComment) object;
                    voting = systemService.getVoting(YesNoVoting.TYPE_SARP_CHT_COMMENT, comment.getId());
                } else {
                    //unsopported type
                }
            }
            
        	if (voting!=null) return true;
        } catch (Exception e) {
		}
        
		return false;
	}//voted()
	
	
}// class PgistELFunctions
