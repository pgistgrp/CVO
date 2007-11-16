package org.pgist.criteria;

import java.util.Comparator;


/**
 * 
 * @author kenny
 *
 */
public class ObjectiveComparator implements Comparator {
    
    
    private boolean caseSensitive = true;
    
    
    public ObjectiveComparator() {
    }
    
    
    public ObjectiveComparator(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }
    
    
    public boolean isCaseSensitive() {
        return caseSensitive;
    }


    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    
    /*
     * ------------------------------------------------------------------------
     */


    public int compare(Object obj1, Object obj2) {
        Objective o1 = (Objective) obj1;
        Objective o2 = (Objective) obj2;
        
        if (caseSensitive) return o1.getDescription().compareTo(o2.getDescription());
        else return o1.getDescription().compareToIgnoreCase(o2.getDescription());
    }//compare()
    
    
}//class ObjectiveComparator
