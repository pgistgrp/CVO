package org.pgist.cvo;

import java.util.Collection;
import java.util.List;


/**
 * 
 * @author kenny
 *
 */
public class TagDAOImpl extends CVODAOImpl implements TagDAO {
    
    
    public List addTags(String[] tags) throws Exception {
        return null;
    }//addTags()
    
    
    private static String hql_getTagsByRank = "from TagReference tr where tr.cct=? order by tr.times desc";
    
    
    public Collection getTagsByRank(CCT cct, int count) throws Exception {
        getHibernateTemplate().setMaxResults(count);
        return getHibernateTemplate().find(hql_getTagsByRank, cct);
    }//getTagsByRank()
    
    
}//class TagDAOImpl
