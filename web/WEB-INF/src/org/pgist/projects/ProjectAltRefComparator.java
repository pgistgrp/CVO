package org.pgist.projects;

import java.util.Comparator;

public class ProjectAltRefComparator implements Comparator {
    private boolean caseSensitive = false;
	public int compare(Object obj1, Object obj2) {
        ProjectAltRef ref1 = (ProjectAltRef) obj1;
        ProjectAltRef ref2 = (ProjectAltRef) obj2;
        
        if (caseSensitive) return ref1.getAlternative().getName().compareTo(ref2.getAlternative().getName());
        else return ref1.getAlternative().getName().compareToIgnoreCase(ref2.getAlternative().getName());
	}	
}
