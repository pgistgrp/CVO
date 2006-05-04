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
    
    void addChildCategoryReference(Long cctId, Long parentId, String name) throws Exception;

    void editCategoryReference(Long cctId, Long catRefId, String name) throws Exception;

    void deleteCategoryReference(Long cctId, Long parentId, Long categoryId) throws Exception;
    
    
}//interface CSTService
