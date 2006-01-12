package org.pgist.cvo;

import java.util.Collection;

import org.hibernate.Query;
import org.pgist.model.DiscourseObject;
import org.pgist.model.Post;
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
    
    
}
