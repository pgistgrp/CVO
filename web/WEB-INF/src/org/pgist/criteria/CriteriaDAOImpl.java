package org.pgist.criteria;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

import org.pgist.criteria.Objective;
import org.pgist.cvo.CCT;
import org.pgist.cvo.Theme;
import org.pgist.system.BaseDAOImpl;
import java.util.List;
import org.pgist.util.WebUtils;

/**
 * 
 * @author kenny
 *
 */
public class CriteriaDAOImpl extends BaseDAOImpl implements CriteriaDAO {

	
	private static final String hql_addCriterion = "from Criteria c where lower(c.name)=?";
	
    public Criteria addCriterion(String name, CCT cct, Set themes, Set objectives, String na) throws Exception {
    	//check to see if criteria already exist
    	
		Criteria c = new Criteria();
		c.setName(name);
		c.setThemes(themes);
		c.setObjectives(objectives);
		c.setCct(cct);
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
    
    
    public void editCriterion(Criteria c, String name, CCT cct, Set themes, Set objectives, String na) throws Exception {
    	c.setName(name);
		c.setThemes(themes);
		c.setObjectives(objectives);
		c.setCct(cct);
		c.setNa(na);
			
		save(c);		
    }//editCriterion()
    
    
    public Criteria getCriterionById(Long id) throws Exception {
    	return (Criteria) getHibernateTemplate().load(Criteria.class, id);
    }//getCriterionById()
    
    
    private static final String hql_getAllCriterion = "from Criteria c where c.cct=? order by c.id";
    
    public Collection getAllCriterion(CCT cct) throws Exception {    	
    	
    	return getHibernateTemplate().find(hql_getAllCriterion, cct);
    }
    
    
    private static final String hql_addObjective = "from Objective o where lower(o.description)=?";
    
    public Objective addObjective(String description) throws Exception {
    	
		Objective o = new Objective();
		
		o.setDescription(description);
		
    	/*List list = getHibernateTemplate().find(hql_addObjective, new Object[] {
    			description.toLowerCase(),
        });
    	
    	if(list.size()>0) {
    		throw new Exception("Objective already exist.");
    	}  	*/
		save(o);
			
		return o;
    }//addObjectives()
    
    
    public Set getThemeObjects(String[] themeIdList) throws Exception {
    	Set themeObjects = new HashSet();  
    	
    	for(int i=0; i<themeIdList.length; i++){
    		Long themeId = Long.parseLong(themeIdList[i]);
    		themeObjects.add(load(Theme.class, themeId));
    	} //for  	
    	
    	return themeObjects;
    } //getThemeObjects()
    
    
    public Set getObjectiveObjects(String[] objectiveIdList) throws Exception {
    	Set objectiveObjects = new HashSet();
   
    	for(int i=0; i<objectiveIdList.length; i++){
    		Long objectiveId = Long.parseLong(objectiveIdList[i]);
    		objectiveObjects.add(load(Objective.class, objectiveId));
    	} //for
    	
    	return objectiveObjects;
    } //getThemeObjects()
    
    
    public CCT getCct(Long cctId){
    	CCT cct = (CCT) getHibernateTemplate().load(CCT.class, cctId);
    	return cct;
    }//getCct()
    
    
    public void deleteObjective(Long id) throws Exception {
        Objective objective = (Objective) getHibernateTemplate().load(Objective.class, id);
        if (objective != null) getHibernateTemplate().delete(objective);
    }//deleteObjective()
    
    
    
    private static final String hql_getObjectives = "from Objective o order by o.id";
    
    public Collection getObjectives() {
    	return getHibernateTemplate().find(hql_getObjectives);
    } //getObjectives
    
    
    
}//class CriteriaDAOImpl
