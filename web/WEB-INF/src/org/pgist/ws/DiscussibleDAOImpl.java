package org.pgist.ws;

import java.util.Collection;

import org.pgist.model.Discussible;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 
 * @author kenny
 *
 */
public class DiscussibleDAOImpl extends HibernateDaoSupport implements DiscussibleDAO {
    
    
    /**
     * @param id
     * @return
     * @throws Exception
     */
    public Discussible getDiscussible(Long id) throws Exception {
        Discussible discussible = (Discussible) getHibernateTemplate().load(Discussible.class, id);
        if (discussible==null || discussible.isDeleted()) return null;
        return discussible;
    }//getDiscussible()
    
    
    private static final String hql_getBriefList = "from DiscussionPost p where p.deleted=? and p.root.id=? order by p.time desc";
    
    
    /**
     * @param id
     * @param count
     * @return
     * @throws Exception
     */
    public Collection getBriefList(Long rootId, int count) throws Exception {
        getHibernateTemplate().setMaxResults(count);
        return getHibernateTemplate().find(
            hql_getBriefList,
            new Object[] {
                new Boolean(false),
                rootId,
            }
        );
    }//getBriefList()
    
    
}//class DiscussibleDAOImp
