package org.pgist.cvo;

import java.util.Collection;

import org.pgist.model.DiscourseObject;
import org.pgist.model.Post;


public interface CVODAO1 {
    
    
    void savePost(Post post) throws Exception;
    
    void saveDO(DiscourseObject dobj) throws Exception;
    
    void saveCVO(CVO cvo) throws Exception;
    
    Collection getCVOList() throws Exception;

    CVO getCVOById(Long id) throws Exception;

    Post getPostById(Long id) throws Exception;
    
    Category getCategory() throws Exception;
    
    Category getCategoryById(Long id) throws Exception;

    Collection getAllTags() throws Exception;

    Category createCategory(Category parent, String name) throws Exception;

    Tag getTagById(Long id) throws Exception;

    void saveCategory(Category category) throws Exception;

    void saveTag(Tag tag) throws Exception;

    Tag createTag(String name) throws Exception;
    
    
}
