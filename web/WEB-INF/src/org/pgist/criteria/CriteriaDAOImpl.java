package org.pgist.criteria;

import java.util.Collection;
import java.util.Map;

import org.pgist.discussion.DiscussionReply;
import org.pgist.system.BaseDAOImpl;
import org.pgist.tagging.Tag;
import java.util.List;

/**
 * 
 * @author kenny
 *
 */
public class CriteriaDAOImpl extends BaseDAOImpl implements CriteriaDAO {

	
	private static final String hql_addCriterion = "from Criteria c where lower(c.name)=?";
	
    public Criteria addCriterion(String name, String low, String medium, String high, String na) throws Exception {
    	//check to see if criteria already exist
    	
		Criteria c = new Criteria();
		c.setName(name);
		c.setLow(low);
		c.setMedium(medium);
		c.setHigh(high);
		c.setNa(na);
		
    	List list = getHibernateTemplate().find(hql_addCriterion, new Object[] {
                name.toLowerCase(),
        });
    	
    	if(list.size()>0) {
    		throw new Exception("Criteria already exist.");
    	}  		
		save(c);		
		return c;
    }//addCriterion()
    
    
    public void deleteCriterion(Long id) throws Exception {
        Criteria criteria = (Criteria) getHibernateTemplate().load(Criteria.class, id);
        if (criteria != null) getHibernateTemplate().delete(criteria);
    }//deleteCriteria()
    
    
    public void editCriterion(Criteria c, String name, String low, String medium, String high, String na) throws Exception {
		c.setName(name);
		c.setLow(low);
		c.setMedium(medium);
		c.setHigh(high);
		c.setNa(na);
			
		save(c);		
    }//editCriterion()
    
    
    public Criteria getCriterionById(Long id) throws Exception {
    	return (Criteria) getHibernateTemplate().load(Criteria.class, id);
    }//getCriterionById()
    
    
    private static final String hql_getAllCriterion = "from Criteria c order by c.id";
    
    
    public Collection getAllCriterion() throws Exception {    	
    	return getHibernateTemplate().find(hql_getAllCriterion);
    }
    
    
}//class CriteriaDAOImpl
