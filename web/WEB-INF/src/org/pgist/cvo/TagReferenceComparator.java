package org.pgist.cvo;

import java.util.Comparator;


/**
 * 
 * @author kenny
 *
 */
public class TagReferenceComparator implements Comparator {
    
    
    private boolean caseSensitive = true;
    
    
    public TagReferenceComparator() {
    }
    
    
    public TagReferenceComparator(boolean caseSensitive) {
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
        TagReference ref1 = (TagReference) obj1;
        TagReference ref2 = (TagReference) obj2;
        
        if (caseSensitive) return ref1.getTag().getName().compareTo(ref2.getTag().getName());
        else return ref1.getTag().getName().compareToIgnoreCase(ref2.getTag().getName());
    }//compare()
    
    
}//class TagReferenceComparator
