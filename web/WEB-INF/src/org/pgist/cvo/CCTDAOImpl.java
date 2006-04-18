package org.pgist.cvo;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;


/**
 * 
 * @author kenny
 *
 */
public class CCTDAOImpl extends CVODAOImpl implements CCTDAO {
    
    
    private static final String hql_getCCTs = "from CCT c where c.deleted=? order by c.id";
    
    
    public Collection getCCTs() throws Exception {
        return getHibernateTemplate().find(hql_getCCTs, new Boolean(false));
    }//getCCTs()


    private static final String hql_getMyConcerns = "from Concern c where c.deleted=? and c.cct.id=? and c.author.id=? order by c.createTime desc";
    
    
    public Collection getMyConcerns(Long cctId, Long userId) throws Exception {
        return getHibernateTemplate().find(hql_getMyConcerns, new Object[] {
            new Boolean(false),
            cctId,
            userId
        });
    }//getMyConcerns()


    private static final String hql_getOthersConcerns = "from Concern c where c.deleted=? and c.cct.id=? and author.id<>? order by c.createTime";
    
    
    public Collection getOthersConcerns(Long cctId, Long userId, int count) throws Exception {
        getHibernateTemplate().setMaxResults(count);
        return getHibernateTemplate().find(hql_getOthersConcerns, new Object[] {
            new Boolean(false),
            cctId,
            userId
        });
    }//getOthersConcerns()


    public Collection getRandomConcerns(Long cctId, Long userId, int count) throws Exception {
        Set set = new HashSet(count);
        
        List concerns = getHibernateTemplate().find(hql_getOthersConcerns, new Object[] {
            new Boolean(false),
            cctId,
            userId
        });
        
        if (concerns.size()<count) return concerns;
        
        List full = new LinkedList(concerns);
        Random random = new Random();
        
        while (set.size()<count) {
            int index = random.nextInt(full.size());
            set.add(full.remove(index));
        }//while
        
        return set;
    }//getRandomConcerns()


    private static final String hql_getConcernByTag = "from Concern c where c.deleted=? and c.tags.id=?";
    
    
    public Collection getConcernsByTag(TagReference tagRef, int count) throws Exception {
        if (count>0) getHibernateTemplate().setMaxResults(count);
        return getHibernateTemplate().find(hql_getConcernByTag, new Object[] {
                new Boolean(false),
                tagRef.getId()
        });
    }//getConcernByTag()

    
    private static final String hql_getConcernsTotal0 = "select count(c.id) from Concern c where c.deleted=?";
    private static final String hql_getConcernsTotal1 = "select count(c.id) from Concern c where c.deleted=? and c.author.id=?";
    private static final String hql_getConcernsTotal2 = "select count(c.id) from Concern c where c.deleted=? and c.author.id!=?";
    

    public int getConcernsTotal(CCT cct, int whose, Long userId) throws Exception {
        List list = null;
        
        switch (whose) {
            case 0:
                list = getHibernateTemplate().find(hql_getConcernsTotal0, new Boolean(false));
                break;
            case 1:
                list = getHibernateTemplate().find(hql_getConcernsTotal1, new Object[] {
                        new Boolean(false),
                        userId
                });
                break;
            case 2:
                list = getHibernateTemplate().find(hql_getConcernsTotal2, new Object[] {
                        new Boolean(false),
                        userId
                });
                break;
            default:
        }
        
        if (list!=null && list.size()>0) return ((Integer)list.get(0)).intValue();
        
        return 0;
    }//getConcernsTotal()
    
    
}//class CCTDAOImpl
