package org.pgist.cvo;

import java.util.Collection;
import java.util.List;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface CSTService {
    
    
    void save(Category category) throws Exception;
    
    void save(CategoryReference categoryReference) throws Exception;
    
    CategoryReference getCategoryReferenceById(Long categoryId) throws Exception;
    
    void addCategoryReference(Long cctId, Long parentId, String name) throws Exception;

    void copyCategoryReference(Long cctId, Long parentId, Long categoryId) throws Exception;
    
    void duplicateCategoryReference(Long cctId, Long parentId, Long categoryId, String name) throws Exception;

    void moveCategoryReference(Long cctId, Long parent0Id, Long parent1Id, Long categoryId) throws Exception;

    void editCategoryReference(Long cctId, Long catRefId, String name) throws Exception;

    void deleteCategoryReference(Long cctId, Long parentId, Long categoryId) throws Exception;

    void relateTagToCategory(Long cctId, Long categoryId, Long tagId) throws Exception;

    void deleteTagFromCategory(Long cctId, Long categoryId, Long tagId) throws Exception;

    Object[] getConcernsByTag(Long cctId, Long tagId, PageSetting setting) throws Exception;

    Collection getRealtedTags(Long cctId, Long categoryId, PageSetting setting) throws Exception;

    Collection getUnrelatedTags(Long cctId, Long categoryId, PageSetting setting) throws Exception;

    Collection getOrphanTags(Long cctId, PageSetting setting) throws Exception;

    void saveSummary(Long cctId, Long themeId, String summary) throws Exception;

    List getThemes(CCT cct) throws Exception;

    
}//interface CSTService
