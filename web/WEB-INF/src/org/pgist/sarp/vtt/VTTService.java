package org.pgist.sarp.vtt;

import java.util.List;

import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public interface VTTService {
    
    
    void toggleVTT(Long chtId, boolean closed) throws Exception;

    InfoObject publish(Long vttId, String property) throws Exception;

    VTT getVTTById(Long id) throws Exception;

    CategoryReference setRootCatReference(VTT vtt, User user) throws Exception;

    VTT createVTT(Long id, Long chtId, String name, String purpose, String instruction) throws Exception;

    List<User> getOtherUsers(VTT vtt) throws Exception;

    void setClearVTTWinner(Long vttId) throws Exception;
    
    
}//interface VTTService
