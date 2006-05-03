package org.pgist.cvo;

import java.util.List;


/**
 * 
 * @author kenny
 *
 */
public class CSTDAOImpl extends CVODAOImpl implements CSTDAO {
    
    
    private static final String hql_getCategoryByName = "from Category c where c.deleted=? and lower(c.name)=?";
    
    
    public Category getCategoryByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getCategoryByName, new Object[] {
                new Boolean(false),
                name
        });
        if (list.size()>0) return (Category) list.get(0);
        return null;
    }//getCategoryByName()
    
    
    private static final String hql_getCategoryReferenceByName = "from CategoryReference cr where cr.deleted=? and lower(cr.name)=?";
    
    
    public CategoryReference getCategoryReferenceByName(String name) throws Exception {
        List list = getHibernateTemplate().find(hql_getCategoryByName, new Object[] {
                new Boolean(false),
                name
        });
        if (list.size()>0) return (CategoryReference) list.get(0);
        return null;
    }//getCategoryReferenceByName()
    
    
}//class CSTDAOImpl
