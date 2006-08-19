package org.pgist.tag;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;


/**
 *
 * @author kenny
 *
 */
public class TagDAOImpl extends BaseDAOImpl implements TagDAO {


    public Tag getTagById(Long id) throws Exception {
        return (Tag) getHibernateTemplate().load(Tag.class, id);
    }//getTagById()


    private static final String hql_addTag = "from Tag t where t.status!=? and lower(t.name)=?";
    
    
    synchronized public Tag addTag(String name, int type, int status) throws Exception {
        List list = getHibernateTemplate().find(hql_addTag, new Object[] {
                new Integer(Tag.STATUS_REJECTED),
                name.toLowerCase(),
        });
        
        if (list.size()==0) {
            Tag t = new Tag();
            t.setName(name.toLowerCase());
            t.setType(type);
            t.setStatus(status);
            
            save(t);
            
            return t;
        }
        return (Tag) list.get(0);
    }//addTag()


    private static String hql_getAllTags = "from Tag t where t.status!=?";


    public Collection getAllTags() throws Exception {
        Query query = getSession().createQuery(hql_getAllTags);
        query.setInteger(0, Tag.STATUS_REJECTED);
        
        return query.list();
    } //getAllTags


    private static String hql_getTags = "from Tag t where t.status!=? and t.type=?";


    public Collection getTags(boolean included) throws Exception {
        Query query = getSession().createQuery(hql_getTags);
        
        query.setInteger(0, Tag.STATUS_REJECTED);
        
        if (included) {
            query.setInteger(1, Tag.TYPE_INCLUDED);
        } else {
            query.setInteger(1, Tag.TYPE_EXCLUDED);
        }
        
        return query.list();
    } //getTags


}//class TagDAOImpl
