package org.pgist.sarp.cht;

import java.util.Collection;
import java.util.List;

import org.pgist.sarp.bct.TagReference;
import org.pgist.sarp.cst.CategoryReference;
import org.pgist.system.BaseDAO;
import org.pgist.users.User;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface CHTDAO extends BaseDAO {
    
    
    TagReference getTagReferenceById(Long tagRefId) throws Exception;

    CategoryReference getCategoryReferenceById(Long categoryId) throws Exception;

    Collection getRealtedTags(Long chtId, Long categoryId, PageSetting setting) throws Exception;

	CHT getCHTById(Long chtId) throws Exception;

    List<User> getOtherUsers(CHT CHT) throws Exception;

    Collection<CHTComment> getComments(Long catRefId, PageSetting setting) throws Exception;

    void increaseVoting(CHTComment comment, boolean agree) throws Exception;

    List<CategoryPath> getPathsByChtId(Long chtId, String orderby);

    void increaseVoting(CategoryPath path, boolean agree) throws Exception;


}//interface CHTDAO
