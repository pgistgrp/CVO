package org.pgist.sarp.cst;

import java.util.Collection;

import org.pgist.sarp.bct.TagReference;
import org.pgist.system.BaseDAO;
import org.pgist.tagging.Category;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface CSTDAO extends BaseDAO {
    
    
    TagReference getTagReferenceById(Long tagRefId) throws Exception;

    CategoryReference getCategoryReferenceById(Long categoryId) throws Exception;

    Category getCategoryByName(String name) throws Exception;
    
    CategoryReference getCategoryReferenceByName(Long cstId, String name) throws Exception;

    Collection getConcernsByTag(Long cstId, Long tagRefId, PageSetting setting) throws Exception;
    
    Collection getConcernsByTags(Long cstId, int[] tagIds, PageSetting setting) throws Exception;

    Collection getRealtedTags(Long cstId, Long categoryId, PageSetting setting) throws Exception;

    Collection getUnrelatedTags(Long cstId, Long categoryId, PageSetting setting) throws Exception;

    Collection getOrphanTags(Long cstId, PageSetting setting) throws Exception;

    void delete(CategoryReference ref) throws Exception;

    Long getInfoObjectIdByThemeId(Long themeId) throws Exception;

	CST getCSTById(Long cstId) throws Exception;


}//interface CSTDAO
