package org.pgist.model;

import java.util.Collection;


/**
 * 
 * @author kenny
 *
 */
public interface Node {
    
    
    Long getId();
    
    Collection getChildren();
    
    String getCaption();
    
    
}
