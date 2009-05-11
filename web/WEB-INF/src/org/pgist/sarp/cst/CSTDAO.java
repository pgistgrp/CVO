package org.pgist.sarp.cst;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.pgist.sarp.bct.TagReference;
import org.pgist.system.BaseDAO;
import org.pgist.tagging.Category;
import org.pgist.tagging.Tag;
import org.pgist.users.User;
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

    TagReference getTagReferenceByName(Long id, String name) throws Exception;
    
    Tag getTagByName(String name) throws Exception;

    Collection getConcernsByTag(Long cstId, Long tagRefId, PageSetting setting) throws Exception;
    
    Collection getConcernsByTags(Long cstId, int[] tagIds, PageSetting setting) throws Exception;

    Collection getRealtedTags(Long cstId, Long categoryId, PageSetting setting) throws Exception;

    Collection getUnrelatedTags(Long cstId, Long categoryId, PageSetting setting) throws Exception;

    Collection getOrphanTags(Long cstId, PageSetting setting, boolean modtool) throws Exception;

    Collection getOrphanTags(Long cstId, Long categoryId, PageSetting setting) throws Exception;

    void delete(CategoryReference ref) throws Exception;

    Long getInfoObjectIdByThemeId(Long themeId) throws Exception;

	CST getCSTById(Long cstId) throws Exception;

    List<User> getOtherUsers(CST cst) throws Exception;

    Collection<CSTComment> getComments(Long catRefId, PageSetting setting) throws Exception;

    void increaseVoting(CSTComment comment, boolean agree) throws Exception;

    Set<User> getThreadUsers(Long catRefId) throws Exception;

    CSTComment getCommentById(Long commentId) throws Exception;


}//interface CSTDAO
