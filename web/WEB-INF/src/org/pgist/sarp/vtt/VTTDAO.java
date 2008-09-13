package org.pgist.sarp.vtt;

import java.util.Collection;
import java.util.List;

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

    MUnitSet getMUnitSetByPathId(Long pathId) throws Exception;

    Collection<VTTSpecialistComment> getSpecialistComments(Long userId, Long vttId, PageSetting setting) throws Exception;
    
    void increaseSpecialistVoting(VTTSpecialistComment comment, boolean agree) throws Exception;

} //interface VTTDAO
