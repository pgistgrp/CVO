package org.pgist.projects;

import java.util.Comparator;

public class ProjectAlternativeComparator implements Comparator {
    private boolean caseSensitive = false;
	public int compare(Object obj1, Object obj2) {
        ProjectAlternative ref1 = (ProjectAlternative) obj1;
        ProjectAlternative ref2 = (ProjectAlternative) obj2;
        
        int result;
        
        if (caseSensitive) {
        	result = ref1.getName().compareTo(ref2.getName());
        } else {
        	result = ref1.getName().compareToIgnoreCase(ref2.getName());
        }
        
        if(result == 0) {
        	result = ref1.getId().compareTo(ref2.getId());        	
        }
        return result;
	}
}
