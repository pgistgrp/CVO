package org.pgist.system;

import org.pgist.users.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 
 * @author kenny
 *
 */
public class BaseDAOImpl extends HibernateDaoSupport implements BaseDAO {
    
    
    public Object load(Class klass, Long id) throws Exception {
        return getHibernateTemplate().load(klass, id);
    }//load()
    
    
    public void save(Object object) throws Exception {
        getHibernateTemplate().save(object);
    }//save()

    
    /**
     * Get a user object with the given id.
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public User getUserById(Long id) throws Exception {
        return (User) getHibernateTemplate().load(User.class, id);
    }//getUserById()


}//class BaseDAOImpl
