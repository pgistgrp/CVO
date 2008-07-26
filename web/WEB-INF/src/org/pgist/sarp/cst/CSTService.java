package org.pgist.sarp.cst;

import java.util.Collection;
import java.util.List;

import org.pgist.sarp.bct.BCT;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.tagging.Category;
import org.pgist.users.User;
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
    
    CategoryReference addCategoryReference(Long cstId, Long parentId, String name) throws Exception;

    void copyCategoryReference(Long cstId, Long parentId, Long categoryId) throws Exception;
    
    CategoryReference duplicateCategoryReference(Long cstId, Long parentId, Long categoryId, String name) throws Exception;

    void moveCategoryReference(Long cstId, Long parent0Id, Long parent1Id, Long categoryId) throws Exception;

    void editCategoryReference(Long cstId, Long catRefId, String name) throws Exception;

    void deleteCategoryReference(Long cstId, Long parentId, Long categoryId) throws Exception;

    void relateTagToCategory(Long cstId, Long categoryId, Long tagId) throws Exception;

    void deleteTagFromCategory(Long cstId, Long categoryId, Long tagId) throws Exception;

    Object[] getConcernsByTag(Long cstId, Long tagId, PageSetting setting) throws Exception;

    Object[] getConcernsByTags(Long cstId, int[] tagIds, PageSetting setting) throws Exception;

    Collection getRealtedTags(Long cstId, Long categoryId, PageSetting setting) throws Exception;

    Collection getUnrelatedTags(Long cstId, Long categoryId, PageSetting setting) throws Exception;

    Collection getOrphanTags(Long cstId, PageSetting setting, boolean modtool) throws Exception;
    
    Collection getOrphanTags(Long cstId, Long categoryId, PageSetting setting) throws Exception;

    void saveSummary(Long catRefId, String summary) throws Exception;

    List getThemes(BCT bct) throws Exception;
    
	InfoObject publish(Long cstId, String property) throws Exception;

	CST createCST(Long workflowId, Long bctId, String name, String purpose, String instruction) throws Exception;

	void toggleCST(Long cstId, boolean closed) throws Exception;

	CST getCSTById(Long cstId) throws Exception;

    CategoryReference setRootCatReference(CST cst, User user) throws Exception;

    List<User> getOtherUsers(CST cst) throws Exception;

    Collection<CSTComment> getComments(Long catRefId, PageSetting setting) throws Exception;

    CSTComment createComment(Long catRefId, String title, String content, boolean emailNotify) throws Exception;

    CSTComment getCommentById(Long cid) throws Exception;

    CSTComment setVotingOnComment(Long cid, boolean agree) throws Exception;

    void deleteComment(CSTComment comment) throws Exception;

    void setClearCSTWinner(Long cstId) throws Exception;

    void publish(Long cstId) throws Exception;

    void setClusteredCategory(Long cstId) throws Exception;

    CategoryInfo getCategoryInfoById(Long categoryId) throws Exception;

    
}//interface CSTService
