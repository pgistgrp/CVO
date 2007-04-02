package org.pgist.criteria;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

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
	
    
    public Criteria addCriterion(Boolean bool_themes, Boolean bool_objectives, String name, Long critSuite, Set themes, Set objectives, String na) throws Exception {
    	
    	List list = getHibernateTemplate().find(hql_addCriterion, new Object[] {
                name.toLowerCase(),
        });
    	
    	//Check for existing criteria
    	if(list.size()>0) {
    		throw new Exception("Criteria already exist.");
    	}  		
    	
    	//create all classes
    	Criteria c = new Criteria();
    	CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, critSuite);
    	CriteriaRef cr = new CriteriaRef();
    	CriteriaUserWeight cuw = new CriteriaUserWeight();
    	
    	//set CriteriaRef 
    	cr.setCriterion(c);
    	cr.setSuite(cs);
    	
    	//set CriteriaUserWeight
    	cuw.setSuite(cs);
    	
    	//set CriteriaSuite
    	cs.addWeight(cr, cuw);
    	cs.addReference(cr);
    	
    	//set Criterion
    	c.setName(name);
    	c.setNa(na);
    	c.setCritRef(cr);
    	
    	if(bool_themes) {
    		c.setThemes(themes);
    	}
    	if(bool_objectives) {
    		c.setObjectives(objectives);		
    	}
    	
    	save(cs);
    	save(cuw);
    	save(cr);
		save(c);		
		return c;
    } //addCriterion
    
    
    public void deleteCriterion(Long id) throws Exception {
        Criteria criteria = (Criteria) getHibernateTemplate().load(Criteria.class, id);
        criteria.setDeleted(true);
    }//deleteCriteria()
    
    
    public void editCriterion(Boolean bool_themes, Boolean bool_objectives, Criteria c, String name, Set themes, Set objectives, String na) throws Exception {
    	c.setName(name);
	
		c.setNa(na);
		
    	if(bool_themes) {
    		c.setThemes(themes);
    	}
    	if(bool_objectives) {
    		c.setObjectives(objectives);		
    	}
    
		save(c);		
    }//editCriterion()
    
    
    public Criteria getCriterionById(Long id) throws Exception {
    	return (Criteria) getHibernateTemplate().load(Criteria.class, id);
    }//getCriterionById()
    
    
    //Maybe delete if Jordan doesn't need it later.
    public Set getCriterions(String[] criteriaIdList) throws Exception {
    	Set criteriaObjects = new HashSet();
    	   
    	for(int i=0; i<criteriaIdList.length; i++){
    		Long criteriaId = Long.parseLong(criteriaIdList[i]);
    		criteriaObjects.add(load(Criteria.class, criteriaId));
    	} //for
    	
    	return criteriaObjects;
    }//getCriterionById()
    
    
    public Set getAllCriterion(Long critSuiteId) throws Exception {    	
    	
    	CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, critSuiteId);
    	Set references = cs.getReferences();
    	
    	Set cSet = new HashSet();
    	Iterator r = references.iterator();
    	while(r.hasNext()) {
    		CriteriaRef tempCR= (CriteriaRef) r.next();
    		Criteria c = tempCR.getCriterion();
    		if(!c.getDeleted()) {
    			cSet.add(c);
    		}
    	}
    	return cSet;
    } //getAllCriterion(Long critSuiteId);
    
    
    private static final String hql_getAllCriterion2 = "from Criteria c where c.deleted=? order by c.id";
    
    public Collection getAllCriterion() throws Exception {    	
    	return getHibernateTemplate().find(hql_getAllCriterion2, new Object[] {
                false,});
    } //getAllCriterion();
    
    
    private static final String hql_addObjective = "from Objective o where lower(o.description)=?";
    
    public Objective addObjective(String description) throws Exception {
    	
		Objective o = new Objective();
		
		o.setDescription(description);
		
    	List list = getHibernateTemplate().find(hql_addObjective, new Object[] {
    			description.toLowerCase(),
        });
    	
    	if(list.size()>0) {
    		throw new Exception("Objective already exist.");
    	}  	
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
    
    
    public void deleteObjective(Long id) throws Exception {
        Objective objective = (Objective) getHibernateTemplate().load(Objective.class, id);
        //try catch doesn't work to handle error yet
        try {
        	if (objective != null) getHibernateTemplate().delete(objective);
        } catch(Exception e) {
        	throw new Exception("You cannot delete a Objective that has been associated with a Criteria.");
        }
    }//deleteObjective()
    
    
    
    private static final String hql_getObjectives = "from Objective o order by o.id";
    
    public Collection getObjectives() {
    	return getHibernateTemplate().find(hql_getObjectives);
    } //getObjectives
    
    
    public void setWeight(Long suiteId, Criteria criteria, int weight) throws Exception {
    	
    	CriteriaSuite cs = (CriteriaSuite)load(CriteriaSuite.class, suiteId);
    	CriteriaRef cr = criteria.getCritRef();
    	Map csWeights = cs.getWeights();    	
    	CriteriaUserWeight cuw = (CriteriaUserWeight) csWeights.get(cr); 	
    	Integer iWeight = new Integer(weight);    
    	cuw.addWeight(getUserById(WebUtils.currentUserId()), iWeight);	
	    
    	save(cuw);
    	
    }//setUserWeight()
    
    
    public Map getWeights(Long critSuiteId) throws Exception {
    	CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, critSuiteId);
    	
    	return cs.getWeights();
    }//getWeights();
    
    
    public CriteriaSuite getCriteriaSuiteById(Long id) throws Exception {
    	CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, id);
    	return cs;
    } //getCriteriaSuiteById();
    
    
}//class CriteriaDAOImpl
