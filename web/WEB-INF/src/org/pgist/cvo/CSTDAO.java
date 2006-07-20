package org.pgist.cvo;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface CSTDAO extends CVODAO {
    
    
    Category getCategoryByName(String name) throws Exception;
    
    CategoryReference getCategoryReferenceByName(Long cctId, String name) throws Exception;

    Collection getConcernsByTag(Long cctId, Long tagRefId, PageSetting setting) throws Exception;
    
    Collection getRealtedTags(Long cctId, Long categoryId, PageSetting setting) throws Exception;

    Collection getUnrelatedTags(Long cctId, Long categoryId, PageSetting setting) throws Exception;

    Collection getOrphanTags(Long cctId, PageSetting setting) throws Exception;

    Theme getThemeById(Long themeId) throws Exception;

    void save(Theme theme) throws Exception;

    void delete(CategoryReference ref) throws Exception;

    
}//interface CSTDAO
