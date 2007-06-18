package org.pgist.criteria;

import java.util.Comparator;


/**
 * 
 * @author kenny
 *
 */
public class CriteriaRefComparator implements Comparator {
    
    
    private boolean caseSensitive = true;
    
    
    public CriteriaRefComparator() {
    }
    
    
    public CriteriaRefComparator(boolean caseSensitive) {
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
        CriteriaRef o1 = (CriteriaRef) obj1;
        CriteriaRef o2 = (CriteriaRef) obj2;
        
        if (caseSensitive) return o1.getCriterion().getName().compareTo(o2.getCriterion().getName());
        else return o1.getCriterion().getName().compareToIgnoreCase(o2.getCriterion().getName());
    }//compare()
    
    
}//class CriteriaRefComparator
