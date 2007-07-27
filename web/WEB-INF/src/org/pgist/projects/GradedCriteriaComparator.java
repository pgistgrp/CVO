package org.pgist.projects;

import java.util.Comparator;


/**
 * 
 * @author John
 *
 */
public class GradedCriteriaComparator implements Comparator {
    
    
    private boolean caseSensitive = false;
    
    
	public int compare(Object obj1, Object obj2) {
        GradedCriteria ref1 = (GradedCriteria) obj1;
        GradedCriteria ref2 = (GradedCriteria) obj2;
        
        if (caseSensitive) return ref1.getCriteria().getName().compareTo(ref2.getCriteria().getName());
        else return ref1.getCriteria().getName().compareToIgnoreCase(ref2.getCriteria().getName());
	}//compare()
	
	
}//class GradedCriteriaComparator
