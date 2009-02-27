package org.pgist.tagging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.pgist.system.BaseDAOImpl;
import org.pgist.util.PageSetting;


/**
 *
 * @author kenny
 *
 */
public class TagDAOImpl extends BaseDAOImpl implements TagDAO {


    public Tag getTagById(Long id) throws Exception {
        return (Tag) getHibernateTemplate().load(Tag.class, id);
    }//getTagById()


    private static final String hql_getTagByName = "from Tag t where t.type=? and t.name=?";
    
    
    public Tag getTagByName(String name) throws Exception {
        List tags = getHibernateTemplate().find(hql_getTagByName, new Object[] {
                Tag.TYPE_INCLUDED,
                name.toLowerCase(),
        });
        
        if (tags.size()>0) return (Tag) tags.get(0);
        else return null;
    }//getTagByName()


    private static final String hql_addTag = "from Tag t where t.status!=? and lower(t.name)=?";
    
    
    synchronized public Tag addTag(String name, int status, boolean included) throws Exception {
        int type = included ? Tag.TYPE_INCLUDED : Tag.TYPE_EXCLUDED;
        
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


    private static String hql_getTags = "from Tag t where t.status!=? and t.type=?";


    public Collection getTags(boolean included) throws Exception {
        int type = included ? Tag.TYPE_INCLUDED : Tag.TYPE_EXCLUDED;
        
        Query query = getSession().createQuery(hql_getTags);
        
        query.setInteger(0, Tag.STATUS_REJECTED);
        
        query.setInteger(1, type);
        
        return query.list();
    } //getTags()


    private static String hql_getTags1 = "select count(t.id) from Tag t where t.status!=? and t.type=?";

    private static String hql_getTags2 = "from Tag t where t.status!=? and t.type=? order by t.name";
    

    public Collection getTags(PageSetting setting, boolean included) throws Exception {
        List result = new ArrayList();
        
        int type = included ? Tag.TYPE_INCLUDED : Tag.TYPE_EXCLUDED;

        List list = getHibernateTemplate().find(hql_getTags1, new Object[] {
                Tag.STATUS_REJECTED,
                type,
        });
        
        if (list == null || list.size() == 0) return result;

        int total = ((Number) list.get(0)).intValue();
        if (setting.getRowOfPage() == -1) setting.setRowOfPage(total);
        setting.setRowSize(total);

        Query query = getSession().createQuery(hql_getTags2);
        query.setInteger(0, Tag.STATUS_REJECTED);
        query.setInteger(1, type);
        query.setFirstResult(setting.getFirstRow());
        query.setMaxResults(setting.getRowOfPage());

        return query.list();
    }//getTags()

    
    private static String hql_getAllTags_1 = "from Tag t where t.status!=? and t.type=? order by t.name";
    
    
    public Collection getAllTags(boolean included) throws Exception {
        int type = included ? Tag.TYPE_INCLUDED : Tag.TYPE_EXCLUDED;
        
        return getHibernateTemplate().find(hql_getAllTags_1, new Object[] {
                Tag.STATUS_REJECTED,
                type,
        });
    }//getAllTags()


    private static String hql_getAllTags_2 = "from Tag t where t.status!=? order by t.name";
    
    
    public Collection getAllTags() throws Exception {
        return getHibernateTemplate().find(hql_getAllTags_2, Tag.STATUS_REJECTED);
    }//getAllTags()


    public void deleteTag(Long id) throws Exception {
        Tag tag = (Tag) getHibernateTemplate().load(Tag.class, id);
        if (tag != null) getHibernateTemplate().delete(tag);
    }//deleteTag()


    private static String hql_searchTags = "from Tag t where t.status!=? and t.type=? and t.name like ? order by t.name";
    
    
    public Collection searchTags(String name, boolean included) throws Exception {
        int type = included ? Tag.TYPE_INCLUDED : Tag.TYPE_EXCLUDED;
        
        return getHibernateTemplate().find(hql_searchTags, new Object[] {
                Tag.STATUS_REJECTED,
                type,
                name + '%',
        });
    }//searchTags()


}//class TagDAOImpl
