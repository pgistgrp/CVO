package org.pgist.cvo;


/**
 * 
 * @author kenny
 *
 */
public interface CSTDAO extends CVODAO {
    
    
    Category getCategoryByName(String name) throws Exception;
    
    CategoryReference getCategoryReferenceByName(String name) throws Exception;
    
    
}//interface CSTDAO
