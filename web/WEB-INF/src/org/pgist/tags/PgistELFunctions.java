package org.pgist.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.taglibs.standard.tag.common.core.Util;
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
import org.pgist.sarp.cht.CategoryPath;
import org.pgist.sarp.cst.CSTComment;
import org.pgist.sarp.drt.Comment;
import org.pgist.sarp.drt.DRTAnnouncement;
import org.pgist.sarp.vtt.VTTComment;
import org.pgist.sarp.vtt.VTTSpecialistComment;
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
                } else if (object instanceof DRTAnnouncement) {
                    DRTAnnouncement announcement = (DRTAnnouncement) object;
                    voting = systemService.getVoting(YesNoVoting.TYPE_SARP_DRT_ANNOUNCEMENT, announcement.getId());
                } else if (object instanceof CSTComment) {
                    CSTComment comment = (CSTComment) object;
                    voting = systemService.getVoting(YesNoVoting.TYPE_SARP_CST_COMMENT, comment.getId());
                } else if (object instanceof CHTComment) {
                    CHTComment comment = (CHTComment) object;
                    voting = systemService.getVoting(YesNoVoting.TYPE_SARP_CHT_COMMENT, comment.getId());
                } else if (object instanceof VTTComment) {
                    VTTComment comment = (VTTComment) object;
                    voting = systemService.getVoting(YesNoVoting.TYPE_SARP_VTT_COMMENT, comment.getId());
                } else if (object instanceof VTTSpecialistComment) {
                    VTTSpecialistComment comment = (VTTSpecialistComment) object;
                    voting = systemService.getVoting(YesNoVoting.TYPE_SARP_VTT_SPEC_COMMENT, comment.getId());
                } else if (object instanceof CategoryPath) {
                    CategoryPath path = (CategoryPath) object;
                    voting = systemService.getVoting(YesNoVoting.TYPE_SARP_CHT_PATH, path.getId());
                } else {
                    //unsopported type
                }
            }
            
        	if (voting!=null) return true;
        } catch (Exception e) {
		}
        
		return false;
	}//voted()
	
	public static List<Object> reverse(List<Object> list) {
	    List<Object> newList = new ArrayList<Object>(list.size());
	    
	    for (int i=list.size()-1; i>=0; i--) {
	        newList.add(list.get(i));
	    }
	    
	    return newList;
	}
	
	public static String purify(String html) {
	    if (html == null) return "";
	    
	    html = Util.escapeXml(html);
	    html = html.replace("\r\n", "<br>");
	    html = html.replace("\n", "<br>");
	    html = html.replace("\r", "<br>");
	    
	    return html;
	}
	
	public static List<CategoryPath> sortPaths(List<CategoryPath> paths, String order) {
	    if ("a-z".equalsIgnoreCase(order)) {
	        Collections.sort(paths, new Comparator<CategoryPath>() {
                @Override
                public int compare(CategoryPath path1, CategoryPath path2) {
                    return path1.getTitle().compareToIgnoreCase(path2.getTitle());
                }
	        });
	    } else if ("z-a".equalsIgnoreCase(order)) {
	        Collections.sort(paths, new Comparator<CategoryPath>() {
                @Override
                public int compare(CategoryPath path1, CategoryPath path2) {
                    return path2.getTitle().compareToIgnoreCase(path1.getTitle());
                }
            });
        } else if ("0-9".equalsIgnoreCase(order)) {
            Collections.sort(paths, new Comparator<CategoryPath>() {
                @Override
                public int compare(CategoryPath path1, CategoryPath path2) {
                    int v1 = path1.getNumVote();
                    int v2 = path2.getNumVote();
                    int a1 = path1.getNumAgree();
                    int a2 = path2.getNumAgree();
                    
                    if (v1==v2) {
                        if (a1==a2) {
                            return path1.getTitle().compareToIgnoreCase(path2.getTitle());
                        } else {
                            return a1-a2;
                        }
                    } else {
                        return v1-v2;
                    }
                }
            });
        } else if ("9-0".equalsIgnoreCase(order)) {
            Collections.sort(paths, new Comparator<CategoryPath>() {
                @Override
                public int compare(CategoryPath path1, CategoryPath path2) {
                    int v1 = path1.getNumVote();
                    int v2 = path2.getNumVote();
                    int a1 = path1.getNumAgree();
                    int a2 = path2.getNumAgree();
                    
                    if (v1==v2) {
                        if (a1==a2) {
                            return path1.getTitle().compareToIgnoreCase(path2.getTitle());
                        } else {
                            return a2-a1;
                        }
                    } else {
                        return v2-v1;
                    }
                }
            });
	    }
	    
	    return paths;
	}
}// class PgistELFunctions
