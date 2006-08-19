package org.pgist.tag;

import java.util.Comparator;


public class TagComparator implements Comparator {
    
    
    public int compare(Object obj1, Object obj2) throws ClassCastException {
        if (obj1==obj2) return 0;
        
        Tag tag1 = (Tag) obj1;
        Tag tag2 = (Tag) obj2;
        
        if (tag1.getName()==null) return -1;
        
        return tag1.getName().compareTo(tag2.getName());
    }//compare()
    

}//class TagComparator
