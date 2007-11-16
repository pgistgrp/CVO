package org.pgist.cvo;

import java.util.Collection;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 
 * @author kenny
 *
 */
public class CCTDAOImpl extends HibernateDaoSupport implements CCTDAO {
    
    
    private static final String hql_getCCTs = "from CCT c where c.deleted=? order by c.id";
    
    
    public Collection getCCTs() throws Exception {
        return getHibernateTemplate().find(hql_getCCTs, new Boolean(false));
    }//getCCTs()


    public void save(CCT cct) throws Exception {
        getHibernateTemplate().saveOrUpdate(cct);
    }//save()


    public CCT getCCTById(Long cctId) throws Exception {
        CCT cct = (CCT) getHibernateTemplate().get(CCT.class, cctId);
        if (cct.isDeleted()) return null;
        return cct;
    }//getCCTById()
    
    
}//class CCTDAOImpl
