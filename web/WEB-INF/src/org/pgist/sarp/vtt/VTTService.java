package org.pgist.sarp.vtt;

import java.util.Collection;
import java.util.List;

import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.users.User;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface VTTService {
    
    
    void toggleVTT(Long vttId, boolean closed) throws Exception;

    InfoObject publish(Long vttId, String property) throws Exception;

    VTT getVTTById(Long vttId) throws Exception;

    CategoryReference setRootCatReference(VTT vtt, User user) throws Exception;

    VTT createVTT(Long id, Long chtId, String name, String purpose, String instruction) throws Exception;

    List<User> getOtherUsers(VTT vtt) throws Exception;

    void setClearVTTWinner(Long vttId) throws Exception;

    Collection<VTTComment> getComments(Long catRefId, PageSetting setting) throws Exception;

    VTTComment createComment(Long catRefId, String title, String content, boolean emailNotify) throws Exception;

    void deleteComment(VTTComment comment) throws Exception;

    VTTComment getCommentById(Long cid) throws Exception;

    VTTComment setVotingOnComment(Long cid, boolean agree) throws Exception;

    CategoryValue getCategoryValueById(Long id) throws Exception;
    
    
}//interface VTTService
