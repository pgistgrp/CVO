package org.pgist.projects;

import java.util.Comparator;

public class ProjectAltRefComparator implements Comparator {
    private boolean caseSensitive = false;
	public int compare(Object obj1, Object obj2) {
        ProjectAlternative ref1 = (ProjectAlternative) obj1;
        ProjectAlternative ref2 = (ProjectAlternative) obj2;
        
        if (caseSensitive) return ref1.getName().compareTo(ref2.getName());
        else return ref1.getName().compareToIgnoreCase(ref2.getName());
	}
}
