package org.pgist.sarp.cht;

import java.util.Collection;
import java.util.List;

import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.tagging.Category;
import org.pgist.users.User;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface CHTService {
    
    
    void save(Category category) throws Exception;
    
    void save(CategoryReference categoryReference) throws Exception;
    
    CategoryReference getCategoryReferenceById(Long categoryId) throws Exception;
    
    Collection getRealtedTags(Long chtId, Long categoryId, PageSetting setting) throws Exception;
    
	InfoObject publish(Long chtId, String property) throws Exception;

	CHT createCHT(Long workflowId, Long cstId, String name, String purpose, String instruction) throws Exception;

	void toggleCHT(Long chtId, boolean closed) throws Exception;

	CHT getCHTById(Long chtId) throws Exception;

    List<User> getOtherUsers(CHT cht) throws Exception;

    Collection<CHTComment> getComments(Long catRefId, PageSetting setting) throws Exception;

    CHTComment createComment(Long catRefId, String title, String content, boolean emailNotify) throws Exception;

    CHTComment getCommentById(Long cid) throws Exception;

    CHTComment setVotingOnComment(Long cid, boolean agree) throws Exception;

    void deleteComment(CHTComment comment) throws Exception;

    CategoryReference setRootCatReference(CHT cht, User user) throws Exception;

    void publish(Long chtId) throws Exception;

    void setClearCHTWinner(Long chtId) throws Exception;

    CategoryReference moveCategoryReference(Long catRefId, int direction) throws Exception;

    void setClusteredCategory(Long chtId) throws Exception;

    List<CategoryPath> getPathsByChtId(Long chtId, String orderby);

    CategoryPath getPathById(Long pathId) throws Exception;

    CategoryPath setVotingOnPath(Long pathId, boolean agree) throws Exception;

    CategoryPath createPath(Long chtId, String pathIds) throws Exception;
    
    
}//interface CHTService
