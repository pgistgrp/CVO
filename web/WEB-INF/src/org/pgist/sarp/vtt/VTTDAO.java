package org.pgist.sarp.vtt;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.pgist.sarp.cht.CategoryPath;
import org.pgist.system.BaseDAO;
import org.pgist.users.User;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface VTTDAO extends BaseDAO {
    
    VTT getVTTById(Long vttId) throws Exception;

    Collection<VTTComment> getComments(Long userId, Long vttId, PageSetting setting) throws Exception;

    void increaseVoting(VTTComment comment, boolean agree) throws Exception;

    List<User> getOtherUsers(VTT vtt) throws Exception;

    CategoryPathValue getCategoryPathValueByPathId(Long userId, Long pathId) throws Exception;

    List<CategoryPathValue> getCategoryPathValuesByPathId(Long id) throws Exception;

    List<MUnitSet> getMUnitSetsByPathId(Long pathId) throws Exception;

    Collection<VTTSpecialistComment> getSpecialistComments(Long targetUserId, Long vttId, PageSetting setting) throws Exception;
    
    void increaseSpecialistVoting(VTTSpecialistComment comment, boolean agree) throws Exception;

    MUnitSet getMUnitSetById(Long musetId) throws Exception;

    boolean checkPath(Long vttId, String title) throws Exception;

    CategoryPath getCategoryPathById(Long pathId) throws Exception;

    void delete(Object object) throws Exception;

    Set<User> getThreadUsers(Long ownerId, Long vttId) throws Exception;

    ExpertPathComment getExpertPathComment(Long pathId, Long userId) throws Exception;

    VTTComment getCommentById(Long commentId) throws Exception;

} //interface VTTDAO
