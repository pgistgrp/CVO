package org.pgist.cvo;

import java.util.Random;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 
 * @author kenny
 *
 */
public class CVODAOImpl extends HibernateDaoSupport implements CVODAO {
    
    
    private static Random random = new Random();
    
    
    public CCT getCCTById(Long cctId) throws Exception {
        return (CCT) getHibernateTemplate().get(CCT.class, cctId);
    }//getCCTById()


    public Concern getConcernById(Long concernId) throws Exception {
        return (Concern) getHibernateTemplate().get(Concern.class, concernId);
    }//getConcernById()


    public Tag getTagById(Long tagId) throws Exception {
        return (Tag) getHibernateTemplate().get(Tag.class, tagId);
    }//getTagById()
    
    
    public TagReference getTagReferenceById(Long refId) throws Exception {
        return (TagReference) getHibernateTemplate().get(TagReference.class, refId);
    }//getTagReferenceById()


    public void save(CCT cct) throws Exception {
        getHibernateTemplate().saveOrUpdate(cct);
    }//save()


    public void save(Concern concern) throws Exception {
        concern.setSortOrder(random.nextInt(65535));
        getHibernateTemplate().saveOrUpdate(concern);
    }//save()


    public void save(Tag tag) throws Exception {
        getHibernateTemplate().saveOrUpdate(tag);
    }//save()


    public void save(TagReference ref) throws Exception {
        getHibernateTemplate().saveOrUpdate(ref);
    }//save()


}//class CVODAOImpl
