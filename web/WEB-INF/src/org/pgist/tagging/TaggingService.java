package org.pgist.tagging;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface TaggingService {
    
    
    Tag createTag(String name, boolean included) throws Exception;

    Collection getTags(PageSetting setting, boolean included) throws Exception;

    void deleteTag(Long id, boolean included) throws Exception;

    Collection searchTags(String name, boolean included) throws Exception;

    
}//interface TaggingService
