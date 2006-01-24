package org.pgist.cvo;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.pgist.model.Category;
import org.pgist.model.DiscourseObject;
import org.pgist.model.Post;
import org.pgist.model.Tag;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


public class CVODAOImpl extends HibernateDaoSupport implements CVODAO {
    
    
    public void savePost(Post post) throws Exception {
        getSession().save(post);
    }
    
    
    public void saveDO(DiscourseObject dobj) throws Exception {
        getSession().save(dobj);
    }
    
    
    public void saveCVO(CVO cvo) throws Exception {
        getSession().save(cvo);
    }
    
    
    private final static String hql_getCVOList = "from CVO cvo where cvo.deleted=:deleted";
    
    
    public Collection getCVOList() throws Exception {
        Query query = getSession().createQuery(hql_getCVOList);
        query.setBoolean("deleted", false);
        
        return query.list();
    }//getCVOList()
    
    
    private final static String hql_getCVOById = "from CVO cvo where cvo.deleted=:deleted and cvo.id=:id";
    
    
    public CVO getCVOById(Long id) throws Exception {
        CVO cvo = null;
        Query query = getSession().createQuery(hql_getCVOById);
        query.setBoolean("deleted", false);
        query.setLong("id", id.longValue());
        List list = query.list(); 
        if (list.size()>0) {
            cvo = (CVO) list.get(0);
        }
        return cvo;
    }//getCVOById()


    private final static String hql_getPostById = "from Post post where post.id=:id";
    
    
    public Post getPostById(Long id) throws Exception {
        Post post = null;
        Query query = getSession().createQuery(hql_getPostById);
        query.setLong("id", id.longValue());
        List list = query.list(); 
        if (list.size()>0) {
            post = (Post) list.get(0);
        }
        return post;
    }//getPostById()


    private final static String hql_getCategory = "from Category category where category.parent=null and category.deleted=?";
    
    
    public Category getCategory() throws Exception {
        List list = getHibernateTemplate().find(hql_getCategory, new Boolean(false));
        if (list.size()>0) return (Category) list.get(0);
        return null;
    }//getCategory()


    private final static String hql_getCategoryById = "from Category category where category.id=? and category.deleted=?";
    
    
    public Category getCategoryById(Long id) throws Exception {
        List list = getHibernateTemplate().find(hql_getCategoryById, new Object[] {id, new Boolean(false)});
        if (list.size()>0) return (Category) list.get(0);
        return null;
    }//getCategory()


    private final static String hql_getAllTags = "from Tag tag where tag.deleted=?";
    
    
    public Collection getAllTags() throws Exception {
        return getHibernateTemplate().find(hql_getAllTags, new Boolean(false));
    }//getAllTags()


    public Category createCategory(Category parent, String name) throws Exception {
        if (parent==null) parent = getCategory();
        
        Category category = new Category();
        category.setDeleted(false);
        category.setName(name);
        category.setParent(parent);
        getHibernateTemplate().save(category);
        parent.getChildren().add(category);
        getHibernateTemplate().update(parent);
        
        return category;
    }//createCategory()


    private final static String hql_getTagById = "from Tag tag where tag.id=? and tag.deleted=?";
    
    
    public Tag getTagById(Long id) throws Exception {
        List list = getHibernateTemplate().find(hql_getTagById, new Object[] {id, new Boolean(false)});
        if (list.size()>0) return (Tag) list.get(0);
        return null;
    }//getTagById()


    public void saveCategory(Category category) throws Exception {
        getHibernateTemplate().saveOrUpdate(category);
    }//saveCategory()


    public void saveTag(Tag tag) throws Exception {
        getHibernateTemplate().saveOrUpdate(tag);
    }//saveTag()
    
    
}
