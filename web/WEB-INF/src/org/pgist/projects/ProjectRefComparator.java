package org.pgist.projects;

import java.util.Comparator;

public class ProjectRefComparator implements Comparator {
    private boolean caseSensitive = false;
	public int compare(Object obj1, Object obj2) {
        ProjectRef ref1 = (ProjectRef) obj1;
        ProjectRef ref2 = (ProjectRef) obj2;
        
        if (caseSensitive) return ref1.getProject().getName().compareTo(ref2.getProject().getName());
        else return ref1.getProject().getName().compareToIgnoreCase(ref2.getProject().getName());
	}
}
