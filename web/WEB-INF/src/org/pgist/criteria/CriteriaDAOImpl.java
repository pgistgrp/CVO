package org.pgist.criteria;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pgist.cvo.Theme;
import org.pgist.system.BaseDAOImpl;
import org.pgist.users.User;
import org.pgist.util.WebUtils;
import org.pgist.discussion.InfoStructure;


/**
 * 
 * @author kenny
 *
 */
public class CriteriaDAOImpl extends BaseDAOImpl implements CriteriaDAO {

	
	private static final String hql_addCriterion = "from Criteria c where lower(c.name)=?";
	
    
    public Criteria addCriterion(Boolean bool_themes, Boolean bool_objectives, String name, Set themes, Set objectives, String na) throws Exception {
    	
    	List list = getHibernateTemplate().find(hql_addCriterion, new Object[] {
                name.toLowerCase(),
        });
    	
    	//Check for existing criteria
    	if(list.size()>0) {
    		throw new Exception("Criteria already exist.");
    	}  		
    	
    	//create all classes
    	Criteria c = new Criteria();

    	//set Criterion
    	c.setName(name);
    	c.setNa(na);

    	
    	if(bool_themes) {
    		c.setThemes(themes);
    	}
    	if(bool_objectives) {
    		c.setObjectives(objectives);		
    	}
    	
		save(c);		
		return c;
    } //addCriterion
    
    
    public void addAssocCriterion(Long critId, Long critSuiteId, boolean checked) throws Exception {
    	
    	//create/load all classes
    	Criteria c = (Criteria) load(Criteria.class, critId);
    	CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, critSuiteId);
    	
    	if(checked) {
	    	CriteriaRef checkCr = getCriteriaRefByCriteria(c, cs);
	    	if(checkCr==null) {
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
		    	
		    	//Save
		    	save(cs);
		    	save(cuw);
		    	save(cr);
	    	}
    	} else {
    		CriteriaRef cr = getCriteriaRefByCriteria(c, cs);
    		Map weightsMap = cs.getWeights();
    		CriteriaUserWeight cuw = (CriteriaUserWeight) weightsMap.get(cr);
    		weightsMap.remove(cr);
    		cs.setWeights(weightsMap);
    		save(cs);
    		//delete
    		getHibernateTemplate().delete(cuw);
    		getHibernateTemplate().delete(cr);
    	}
    } //assocCriterion()
    
    
    public void deleteCriterion(Long id) throws Exception {
        Criteria criteria = (Criteria) getHibernateTemplate().load(Criteria.class, id);
        criteria.setDeleted(true);
    }//deleteCriteria()
    
    
    public void editCriterion(boolean bool_name, boolean bool_themes, boolean bool_objectives, Criteria c, String name, Set themes, Set objectives, String na) throws Exception {
    	if(bool_name) {
    		c.setName(name);
    	} else {
    		c.setName(c.getName());
    	}
    	if(!(na.equals("NONE"))) {
    		c.setNa(na);
    	}
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
    
    /*
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
    */
    
    
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
    
    
    
    private static final String hql_getAllCriterion2 = "from Criteria c where c.deleted=? order by c.name";
    
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
    
    
    public Set<Theme> getThemeObjects(String[] themeIdList) throws Exception {
    	Set<Theme> themeObjects = new HashSet();  
    	
    	for(int i=0; i<themeIdList.length; i++){
    		Long themeId = Long.parseLong(themeIdList[i]);
    		themeObjects.add((Theme)load(Theme.class, themeId));
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

    	CriteriaRef cr = getCriteriaRefByCriteria(criteria, cs);
    	Map csWeights = cs.getWeights();    	
    	CriteriaUserWeight cuw = (CriteriaUserWeight) csWeights.get(cr); 	
    	Integer iWeight = new Integer(weight);    
    	cuw.addWeight(getUserById(WebUtils.currentUserId()), iWeight);	
	    
    	save(cuw);
    	
    }//setUserWeight()
    
    
    private static final String hql_getCriteriaRefByCriteria = "from CriteriaRef cr where criterion=?";
    
    public CriteriaRef getCriteriaRefByCriteria(Criteria criteria, CriteriaSuite cs) throws Exception {
    	
    	List list = getHibernateTemplate().find(hql_getCriteriaRefByCriteria, new Object[] {
    			criteria,
        });
    	if(list.size()>0) {
	    	Iterator it = list.iterator();
	    	while(it.hasNext()) {
		    	CriteriaRef cr = (CriteriaRef) it.next();
		    	CriteriaSuite tempCS = cr.getSuite();
		    	if(tempCS==cs) {
		    		return cr;
		    	}
	    	}
    	}
    	return null;
    }
    
    
    public boolean getContainsCriteria(Long critId, Long critSuiteId) throws Exception {
    	Criteria c = (Criteria) load(Criteria.class, critId);
    	if(c.getDeleted()) {
    		return false;
    	}
    	CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, critSuiteId);
    	
    	CriteriaRef cr = getCriteriaRefByCriteria(c, cs);
    	if(cr==null) {
    		return false;
    	}
    	return true;
    }
  
    
    public int getWeight(Long critSuiteId, Long critId) throws Exception {
    	User user = (User) load(User.class, WebUtils.currentUserId());
    	Criteria criteria = (Criteria) load(Criteria.class, critId);
    	
    	CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, critSuiteId);
    	
    	CriteriaRef cr = getCriteriaRefByCriteria(criteria, cs);
    	
    	Map weights = cs.getWeights();
    	CriteriaUserWeight cuw = (CriteriaUserWeight) weights.get(cr);
    	Map userWeights = cuw.getWeights();
    	Integer myWeight = (Integer) userWeights.get(user);
    	int myCritWeight = (int) myWeight;
    	return myCritWeight;
    }//getWeights();
    
    
    public CriteriaSuite getCriteriaSuiteById(Long id) throws Exception {
    	CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, id);
    	
    	return cs;
    } //getCriteriaSuiteById();

    
    private static final String hql_getCriteriaSuites = "from CriteriaSuite cs order by o.id";
    
    public Collection getCriteriaSuites() throws Exception {
    	return getHibernateTemplate().find(hql_getCriteriaSuites);
    } //getCriteriaSuiteById();
    
    
	private static final String hql_checkedPublished = "from InfoStructure infoS where cctId=?";
	
    public Long checkPublished(Long cctId) throws Exception {
    	
    	List list = getHibernateTemplate().find(hql_checkedPublished, new Object[] {
                cctId,
        });
    	
    	Long id = null;
    	//Check for existing criteria
    	if(list.size()>0) {
    		Iterator it = list.iterator();
    		InfoStructure is = (InfoStructure) it.next();
    		id = is.getId();
    	}  		
    	
		return id;
    } //checkPublished()
    
    
    /* Sort alogrithm that is currently not used
    public void sortHashSet(Set references) throws Exception {
    	Iterator itRef = references.iterator();
    	Set sortedSet = new HashSet();

    	while(itRef.hasNext()) {
    		CriteriaRef cr = (CriteriaRef) itRef.next();
    		Criteria c = cr.getCriterion();
    		String critName = c.getName();
    		
    	}
    	
    }
    
    
    public CriteriaRef getSmallRef(Set references) {
    	Iterator itRef = references.iterator();
    	CriteriaRef lowest = null;
    	boolean firstpass = true;
    	
    	while(itRef.hasNext()) {
    		CriteriaRef cr = (CriteriaRef) itRef.next();
    		if(firstpass) {
    			firstpass = false;
    			lowest = cr;
    		} else {
    			Criteria lowestCrif = lowest.getCriterion();
    			String lowestName = lowestCrif.getName();
    			
        		Criteria c = cr.getCriterion();
    			String critName = c.getName();
    			
    			if(lowestName.compareTo(critName) > 0) {
    				lowest = cr;
    			}
    			
    		}
    	}
    	return null;
    }
    */
    
}//class CriteriaDAOImpl
