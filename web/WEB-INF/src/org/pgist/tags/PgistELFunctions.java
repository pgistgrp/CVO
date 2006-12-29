package org.pgist.tags;

import java.util.Collection;


/**
 * PGIST JSP Expression Language functions.<br>
 * 
 * Function list:
 * <ul>
 *   <li>contains(collection, object) - return true if object contained in collection</li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class PgistELFunctions {
    
    
    public static boolean contains(Collection collection, Object object) {
        return collection.contains(object);
    }//contains()
    
    
}//class PgistELFunctions
