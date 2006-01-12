package org.pgist.cvo;

import java.util.Collection;

import org.pgist.model.DiscourseObject;
import org.pgist.model.Post;


public interface CVODAO {
    
    
    void savePost(Post post) throws Exception;
    
    void saveDO(DiscourseObject dobj) throws Exception;
    
    void saveCVO(CVO cvo) throws Exception;
    
    Collection getCVOList() throws Exception;

    CVO getCVOById(Long id);
    
    
}
