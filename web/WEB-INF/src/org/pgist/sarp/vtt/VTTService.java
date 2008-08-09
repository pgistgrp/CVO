package org.pgist.sarp.vtt;

import java.util.Collection;

import org.pgist.sarp.cht.CategoryPath;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface VTTService {
    
    
    void toggleVTT(Long vttId, boolean closed) throws Exception;

    InfoObject publish(Long vttId, String title) throws Exception;

    VTT getVTTById(Long vttId) throws Exception;

    VTT createVTT(Long id, Long chtId, String name, String purpose, String instruction) throws Exception;

    Collection<VTTComment> getComments(Long userId, Long vttId, PageSetting setting) throws Exception;

    VTTComment createComment(Long userId, Long vttId, String title, String content, boolean emailNotify) throws Exception;

    void deleteComment(VTTComment comment) throws Exception;

    VTTComment getCommentById(Long cid) throws Exception;

    VTTComment setVotingOnComment(Long cid, boolean agree) throws Exception;

    CategoryReference getCategoryReferenceById(Long id) throws Exception;
    
    CategoryPathValue getCategoryPathValueByPathId(Long userId, Long pathId) throws Exception;
    
    void saveCategoryPathValue(Long userId, Long pathId, String name, String unit) throws Exception;
    
    void publish(Long vttId, Long userId) throws Exception;

    VTTSpecialistComment createSpecialistComment(Long vttId, String title, String content, boolean emailNotify) throws Exception;

    void setClusteredPaths(Long vttId) throws Exception;

    CategoryPath getCategoryPathById(Long pathId) throws Exception;

    
}//interface VTTService
