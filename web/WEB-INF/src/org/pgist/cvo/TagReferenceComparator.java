package org.pgist.cvo;

import java.util.Comparator;


/**
 * 
 * @author kenny
 *
 */
public class TagReferenceComparator implements Comparator {
    
    
    public int compare(Object obj1, Object obj2) {
        TagReference ref1 = (TagReference) obj1;
        TagReference ref2 = (TagReference) obj2;
        
        return ref1.getTag().getName().compareTo(ref2.getTag().getName());
    }//compare()
    
    
}//class TagReferenceComparator
