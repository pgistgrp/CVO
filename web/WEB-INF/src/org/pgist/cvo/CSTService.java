package org.pgist.cvo;


/**
 * 
 * @author kenny
 *
 */
public interface CSTService {
    
    
    void save(Category category) throws Exception;
    
    void save(CategoryReference categoryReference) throws Exception;
    
    CategoryReference getCategoryReferenceById(Long categoryId) throws Exception;

    Category getCategoryByName(String name) throws Exception;
    
    CategoryReference getCategoryReferenceByName(String name) throws Exception;
    
    
}//interface CSTService
