package org.pgist.system;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.exceptions.RoleExistException;
import org.pgist.exceptions.UserExistException;
import org.pgist.users.Role;
import org.pgist.users.User;
import org.pgist.util.PageSetting;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * DAO for query related to Users
 * @author kenny
 *
 */
public class UserDAOImpl extends HibernateDaoSupport implements UserDAO {

    
    public void saveUser(User user) throws Exception {
        getSession().saveOrUpdate(user);
    }//saveUser()


    private final static String hql_getRoleByName = "from Role where name=:name and deleted=:deleted";
    
    
    public Role getRoleByName(String roleName) throws Exception {
        Role role = null;
        
        Session session = getSession();
        
        Query query = session.createQuery(hql_getRoleByName);
        query.setString("name", roleName);
        query.setBoolean("deleted", false);
        List list = query.list();
        if (list.size()>0) {
            role = (Role) list.get(0);
        }
        
        return role;
    }//getRoleByName()
    
    
    private final static String hql_getUserById = "from User where id=:id and enabled=:enabled and deleted=:deleted";
    
    
    /**
     * 
     * @param id
     * @param enabled
     * @param deleted
     * @return
     * @throws Exception
     */
    public User getUserById(Long id, boolean enabled, boolean deleted) throws Exception {
        User user = null;
        
        Query query = getSession().createQuery(hql_getUserById);
        query.setLong("id", id.longValue());
        query.setBoolean("enabled", true);
        query.setBoolean("deleted", false);
        List list = query.list();
        if (list.size()>0) {
            user = (User) list.get(0);
        }
        
        return user;
    }//getUserById()


    private final static String hql_getUserByName = "from User where loginname=:loginname and enabled=:enabled and deleted=:deleted";
    
    
    /**
     * Get User object by the given name and query conditions
     * @param loginname
     * @param enabled
     * @param deleted
     * @return
     */
    public User getUserByName(String loginname, boolean enabled, boolean deleted) throws Exception {
        User user = null;
        
        Query query = getSession().createQuery(hql_getUserByName);
        query.setString("loginname", loginname);
        query.setBoolean("enabled", true);
        query.setBoolean("deleted", false);
        List list = query.list();
        if (list.size()>0) {
            user = (User) list.get(0);
        }
        
        return user;
    }//getUserByName()
    
    
    /**
     * Get all users, enabled or disabled
     * @param setting
     * @return
     * @throws Exception
     */
    public List getUserList(PageSetting setting) throws Exception {
        List list = null;
        
        Session session = getSession();
        
        StringBuffer hql = new StringBuffer("from User as user where user.deleted=:deleted");
        String nameFilter = (String) setting.get("nameFilter");
        if (nameFilter!=null && !"".equals(nameFilter)) hql.append(" and loginname like :nameFilter");
        
        Query query = session.createQuery("select count(id) "+hql.toString());
        query.setBoolean("deleted", false);
        if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
        list = query.list();
        
        if (list.size()>0) {
            setting.setRowSize(((Integer)list.get(0)).intValue());
            
            hql.append(" order by user.id");
            query = session.createQuery(hql.toString());
            query.setBoolean("deleted", false);
            if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
            query.setFirstResult(setting.getFirstRow());
            query.setMaxResults(setting.getRowOfPage());
            list = query.list();
        }

        return list;
    }//getUserList()
    
    
    /**
     * Get all users, enabled or disabled
     * @param enabled
     * @return
     * @throws Exception
     */
    public List getUserList(boolean enabled, PageSetting setting) throws Exception {
        List list = null;
        
        Session session = getSession();
        
        StringBuffer hql = new StringBuffer("from User where enabled=:enabled and deleted=:deleted");
        String nameFilter = (String) setting.get("nameFilter");
        if (nameFilter!=null && !"".equals(nameFilter)) hql.append(" and loginname like :nameFilter");
        
        Query query = session.createQuery("select count(id) "+hql.toString());
        query.setBoolean("enabled", enabled);
        query.setBoolean("deleted", false);
        if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
        list = query.list();
        
        if (list.size()>0) {
            setting.setRowSize(((Integer)list.get(0)).intValue());
            
            query = session.createQuery(hql.toString());
            query.setBoolean("enabled", enabled);
            query.setBoolean("deleted", false);
            if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
            query.setFirstResult(setting.getFirstRow());
            query.setMaxResults(setting.getRowOfPage());
            list = query.list();
        }

        return list;
    }
    
    
    /**
     * Add a new user to system
     * @param user
     * @throws Exception
     */
    public void addUser(User user) throws UserExistException, Exception {
        addUser(user, null);
    }//addUser()
    
    
    /**
     * Add a new user to system
     * @param user
     * @throws Exception
     */
    public void addUser(User user, String[] idList) throws UserExistException, Exception {
        Session session = getSession();
        
        StringBuffer hql = new StringBuffer("from User where loginname=:loginname and enabled=:enabled and deleted=:deleted");
        
        Query query = session.createQuery(hql.toString());
        query.setString("loginname", user.getLoginname());
        query.setBoolean("enabled", true);
        query.setBoolean("deleted", false);
        
        List list = query.list();
        if (list.size()>0) {
            throw new UserExistException("User already exists!");
        }
        
        user.getRoles().clear();
        
        if (idList!=null) {
            for (int i=0; i<idList.length; i++) {
                Role role = (Role) session.load(Role.class, new Long(idList[i]));
                user.addRole(role);
            }//for i
        }
        
        if (user.getPassword().length()<=31) user.encodePassword();
        
        session.save(user);
    }//addUser()
    

    /**
     * Add a new user to system
     * @param user
     * @throws Exception
     */
    public void updateUser(User user, String[] idList) throws UserExistException, Exception {
        Session session = getSession();
        
        StringBuffer hql = new StringBuffer("from User where loginname=:loginname and enabled=:enabled and deleted=:deleted and id!=:id");
        
        Query query = session.createQuery(hql.toString());
        query.setString("loginname", user.getLoginname());
        query.setBoolean("enabled", true);
        query.setBoolean("deleted", false);
        query.setLong("id", user.getId().longValue());
        
        List list = query.list();
        if (list.size()>0) {
            throw new UserExistException("User already exists!");
        }
        
        user.getRoles().clear();
        
        if (idList!=null) {
            for (int i=0; i<idList.length; i++) {
                Role role = (Role) session.load(Role.class, new Long(idList[i]));
                user.addRole(role);
            }//for i
        }
        
        if (user.getPassword().length()<=31) user.encodePassword();
        
        session.update(user);
    }//updateUser()
    

    /**
     * Delete users according to the idList. The deletion is a transaction
     * so that either all users are deleted or none of them is deleted.
     * @param idList
     * @return
     */
    public boolean delUsers(List idList) throws Exception {
        
        Session session = getSession();
        
        for (int i=0, size=idList.size(); i<size; i++) {
            User user = (User) session.load(User.class, (Long)idList.get(i));
            user.setDeleted(true);
            user.setEnabled(false);
            session.update(user);
        }//for i
        
        return true;
    }//delUsers()
    
    
    /**
     * Edit the infomation of a user
     * @param user
     * @throws Exception
     */
    public void editUser(User user) throws Exception {
        if (user.getPassword().length()<=31) user.encodePassword();
        getSession().update(user);
    }//editUser()
    
    
    /**
     * Enable or disable selected users
     * @param idList
     * @param enable
     * @throws Exception
     */
    public void enableUsers(List idList, boolean enable) throws Exception {
        if (idList==null || idList.size()==0) return;

        Session session = getSession();
        
        for (int i=0, size=idList.size(); i<size; i++) {
            User user = (User) session.load(User.class, (Long)idList.get(i));
            user.setEnabled(enable);
            session.update(user);
        }//for i
    }//enableUsers()
    
    
    /**
     * Get all roles
     * @param setting
     * @return
     * @throws Exception
     */
    public List getRoleList(PageSetting setting) throws Exception {
        List list = null;
        
        Session session = getSession();
        
        StringBuffer hql = new StringBuffer("from Role where deleted=:deleted");
        String nameFilter = (String) setting.get("nameFilter");
        if (nameFilter!=null && !"".equals(nameFilter)) hql.append(" and name like :nameFilter");
        
        Query query = session.createQuery("select count(id) "+hql.toString());
        query.setBoolean("deleted", false);
        if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
        list = query.list();
        
        if (list.size()>0) {
            setting.setRowSize(((Integer)list.get(0)).intValue());
            
            query = session.createQuery(hql.toString());
            query.setBoolean("deleted", false);
            if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
            query.setFirstResult(setting.getFirstRow());
            query.setMaxResults(setting.getRowOfPage());
            list = query.list();
        }

        return list;
    }//getRoleList()
    
    
    private static final String hql_getRoleList = "from Role where deleted=:deleted order by id";

    
    /**
     * Get all roles
     * @param setting
     * @return
     * @throws Exception
     */
    public List getRoleList() {
        List list = null;
        
        Query query = getSession().createQuery(hql_getRoleList);
        query.setBoolean("deleted", false);
        list = query.list();

        return list;
    }//getRoleList()
    
    
    /**
     * Add a new role to system
     * @param role
     * @throws Exception
     */
    public void addRole(Role role) throws RoleExistException, Exception {
        Session session = getSession();
        
        Query query = session.createQuery("from Role where name=:name and deleted=:deleted");
        query.setString("name", role.getName());
        query.setBoolean("deleted", false);
        List list = query.list();
        if (list.size()>0) {
            throw new RoleExistException("Role already exists!");
        }
        
        session.save(role);
    }//addRole()
    
    
    /**
     * Delete roles according to the idList. The deletion is a transaction
     * so that either all roles are deleted or none of them is deleted.
     * @param idList
     * @return
     */
    public boolean delRoles(List idList) throws Exception {
        
        Session session = getSession();
        
        for (int i=0, size=idList.size(); i<size; i++) {
            Role role = (Role) session.load(Role.class, (Long)idList.get(i));
            role.setDeleted(true);
            session.update(role);
        }//for i
        
        return true;
    }//delRoles()
    
    
    /**
     * Delete role according to the id.
     * @param id
     * @return
     */
    public boolean delRole(Long id) throws Exception {
        
        Session session = getSession();
        
        Role role = (Role) session.load(Role.class, id);
        role.setDeleted(true);
        session.update(role);
        
        return true;
    }//delRole()
    
    
    /**
     * Edit the infomation of a role
     * @param user
     * @throws Exception
     */
    public void editRole(Role role) throws Exception {
        Session session = getSession();
        session.update(role);
    }//editRole()


    /**
     * Update the user profile
     * @param user
     */
    public void updateProfile(User user) throws Exception {
        Session session = getSession();
        if (user.getPassword().length()<=31) user.encodePassword();
        session.update(user);
    }//updateProfile()


    private static final String hql_getUsersByRole = "from User where roles.name=?";
    
    
    public Collection getUsersByRole(String role) throws Exception {
        return getHibernateTemplate().find(hql_getUsersByRole, role.toLowerCase());
    }//getUsersByRole()


}//class UserDAO
