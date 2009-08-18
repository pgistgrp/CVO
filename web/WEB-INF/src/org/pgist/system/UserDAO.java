package org.pgist.system;

import java.util.Collection;
import java.util.List;

import org.pgist.exceptions.RoleExistException;
import org.pgist.exceptions.UserExistException;
import org.pgist.exceptions.AssocExistsException;
import org.pgist.users.Role;
import org.pgist.users.Assoc;
import org.pgist.users.User;
import org.pgist.util.PageSetting;


/**
 * DAO for query related to Users
 * @author kenny
 *
 */
public interface UserDAO extends BaseDAO {

    
    Role getRoleByName(String roleName) throws Exception;
    

    Assoc getAssocByName(String assocName) throws Exception;

    
    User getUserById(Long id, boolean enabled, boolean deleted) throws Exception;
    
    
    User getUserByName(String loginname, boolean deleted) throws Exception;


    User getUserByName(String loginname, boolean enabled, boolean deleted) throws Exception;
    
    
    List getUserList(PageSetting setting) throws Exception;


    List getUserList(boolean enabled, PageSetting setting) throws Exception;
    
    
    void addUser(User user) throws UserExistException, Exception;
    
    
    void addUser(User user, String[] idList) throws UserExistException, Exception;
    

    void updateUser(User user, String[] idList) throws UserExistException, Exception;
    

    boolean delUsers(List idList) throws Exception;
    
    
    void editUser(User user) throws Exception;
    
    
    void enableUsers(List idList, boolean enable) throws Exception;
    
    
    List getRoleList(PageSetting setting) throws Exception;
    

    List getAssocList(PageSetting setting) throws Exception;

    
    List getRoleList() throws Exception;


    List getAssocList() throws Exception;
    
    
    void addRole(Role role) throws RoleExistException, Exception;
    

    void addAssoc(Assoc assoc) throws AssocExistsException, Exception;

    
    boolean delRoles(List idList) throws Exception;


    boolean delAssocs(List idList) throws Exception;
    
    
    boolean delRole(Long id) throws Exception;


    boolean delAssoc(Long id) throws Exception;
    
    
    void editRole(Role role) throws Exception;


    void editAssoc(Assoc assoc) throws Exception;


    void updateProfile(User user) throws Exception;


    void saveUser(User user) throws Exception;


    Collection getUsersByRole(String role) throws Exception;


    Collection getUsersByAssoc(String assoc) throws Exception;


}//class UserDAO
