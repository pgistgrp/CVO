package org.pgist.tags;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.tagext.SimpleTagSupport;

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
    }//contains()	
	
	public static boolean containsProjAltRef(Collection collection, Long id) {
		Iterator i = collection.iterator();
		ProjectAltRef tempRef;
		while(i.hasNext()) {
			tempRef = (ProjectAltRef)i.next();
			if(tempRef.getId().equals(id)) return true;
		}
		return false;
    }//contains()

	public static boolean containsFundAltRef(Collection collection, Long id) {
		Iterator i = collection.iterator();
		FundingSourceAltRef tempRef;
		while(i.hasNext()) {
			tempRef = (FundingSourceAltRef)i.next();
			if(tempRef.getId().equals(id)) return true;
		}
		return false;
    }//contains()
	
	
	public static boolean contains(Collection collection, Object object) {
        if (collection==null) return false;
        return collection.contains(object);
    }//contains()
    
	public static boolean containsRef(ProjectSuite suite, Project project, ProjectAlternative alt) {
        if (suite==null) return false;
        if (project==null) return false;
        if (alt==null) return false;
        
        //First check that the project is in the suite
        ProjectRef ref = suite.getProjectReference(project);
        if(ref == null) return false;
        
        //Now check that the alternative is in one of the alt references
        return ref.containsAlternative(alt);
    }//containsRef()

	public static boolean containsRef(FundingSourceSuite suite, FundingSource funding, FundingSourceAlternative alt) {		
        if (suite==null) return false;
        if (funding==null) return false;
        if (alt==null) return false;
        
        //First check that the project is in the suite
        FundingSourceRef ref = suite.getFundingSourceReference(funding);
        if(ref == null) return false;
        
        //Now check that the alternative is in one of the alt references
        return ref.containsAlternative(alt);
    }//containsRef()	
	
}//class PgistELFunctions
