package org.pgist.projects;

import java.util.Comparator;

public class GradedObjectiveComparator implements Comparator {
    private boolean caseSensitive = false;
	public int compare(Object obj1, Object obj2) {
        GradedObjective ref1 = (GradedObjective) obj1;
        GradedObjective ref2 = (GradedObjective) obj2;
        
        if (caseSensitive) return ref1.getObjective().getDescription().compareTo(ref2.getObjective().getDescription());
        else return ref1.getObjective().getDescription().compareToIgnoreCase(ref2.getObjective().getDescription());
	}
}
