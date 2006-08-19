package org.pgist.tag;

import java.util.Comparator;


public class CategoryComparator implements Comparator {
    
    
    public int compare(Object obj1, Object obj2) throws ClassCastException {
        if (obj1==obj2) return 0;
        
        Category cat1 = (Category) obj1;
        Category cat2 = (Category) obj2;
        
        if (cat1.getName()==null) return -1;
        
        return cat1.getName().compareTo(cat2.getName());
    }//compare()
    

}//class CategoryComparator
