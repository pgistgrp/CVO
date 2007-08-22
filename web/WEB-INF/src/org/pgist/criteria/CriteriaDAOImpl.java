package org.pgist.criteria;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;
import org.pgist.system.BaseDAOImpl;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class CriteriaDAOImpl extends BaseDAOImpl implements CriteriaDAO {
	
    
    public Criteria addCriterion(Boolean bool_infoObjects, Boolean bool_objectives, String name, Set infoObjects, SortedSet objectives, String na) throws Exception {

    	Criteria c = new Criteria();

    	//set Criterion
    	c.setName(name);
    	c.setNa(na);

    	
    	if(bool_infoObjects) {
    		c.setInfoObjects(infoObjects);
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
		    	
		    	//set criteria association
		    	c.setSuite(cs);
		    	
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
    
    
    public void editCriterion(boolean bool_name, boolean bool_infoObjects, boolean bool_objectives, Criteria c, String name, Set infoObjects, SortedSet objectives, String na) throws Exception {
    	if(bool_name) {
    		c.setName(name);
    	} else {
    		c.setName(c.getName());
    	}
    	if(!(na.equals("NONE"))) {
    		c.setNa(na);
    	}
    	if(bool_infoObjects) {
    		c.getInfoObjects().clear();
    		save(c);
    		if(infoObjects.size() > 0) {
    			c.setInfoObjects(infoObjects);
    		}
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
    public SortedSet<Criteria> getAllCriterion(Long critSuiteId) throws Exception {    	
    	
    	CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, critSuiteId);
    	Set references = cs.getReferences();
    	
    	SortedSet<Criteria> cSet = new TreeSet();
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
    
    private static final String hql_getAllCriterion2 = "from Criteria c where c.deleted=? order by c.name";
    //private static final String hql_getAllCriterion2 = "from Criteria c";
    
    public Collection getAllCriterion() throws Exception {    	
    	return getHibernateTemplate().find(hql_getAllCriterion2, new Object[] {
                false,});
	
    } //getAllCriterion();
    

    private static final String hql_getAllCriterion3 = "from Criteria c where c.deleted=? and c.suite.id=? order by c.name";
    
    public Collection getAllCriterion(Long critSuiteId) throws Exception {    	
    	return getHibernateTemplate().find(hql_getAllCriterion3, new Object[] {
                false, critSuiteId});
    } //getAllCriterion();
    

    public Objective addObjective(Long critId, String description) throws Exception {
    	
		Objective o = new Objective();
		
		o.setDescription(description);
		
		save(o);
		
    	Criteria c = getCriterionById(critId);
    	SortedSet objectives = c.getObjectives();
    	objectives.add(o);
    	c.setObjectives(objectives);
    	save(c);
		
		return o;
    }//addObjectives()
    
    
    public Set<InfoObject> getInfoObjects(String[] infoObjectsIdList)throws Exception {
    	Set<InfoObject> InfoObjects = new HashSet();  
    	
    	for(int i=0; i<infoObjectsIdList.length; i++){
    		Long infoObjectId = Long.parseLong(infoObjectsIdList[i]);
    		InfoObjects.add((InfoObject)load(InfoObject.class, infoObjectId));
    	} //for  	
    	
    	return InfoObjects;
    } //getThemeObjects()
    
    
    public SortedSet getObjectiveObjects(String[] objectiveIdList) throws Exception {
    	SortedSet objectiveObjects = new TreeSet();
   
    	for(int i=0; i<objectiveIdList.length; i++){
    		Long objectiveId = Long.parseLong(objectiveIdList[i]);    		
    		objectiveObjects.add((Objective)load(Objective.class, objectiveId));
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
    
    
    private static final String hql_getCriteriaRefByCriteria = "from CriteriaRef cr where cr.criterion=?";
    
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
    	if(myWeight==null) {
    		return 0;
    	}
    	int myCritWeight = (int) myWeight;
    	return myCritWeight;
    }//getWeights();
    
    
    public CriteriaSuite getCriteriaSuiteById(Long id) throws Exception {
    	CriteriaSuite cs = (CriteriaSuite) load(CriteriaSuite.class, id);
    	
    	return cs;
    } //getCriteriaSuiteById();

    
    private static final String hql_getCriteriaSuites = "from CriteriaSuite cs order by cs.id";
    
    public Collection getCriteriaSuites() throws Exception {
    	return getHibernateTemplate().find(hql_getCriteriaSuites);
    } //getCriteriaSuiteById();
    
    
	private static final String hql_checkedPublished = "from InfoStructure infoS where type=? and cctId=?";
	
    public Long checkPublished(Long cctId) throws Exception {
    	
    	List list = getHibernateTemplate().find(hql_checkedPublished, new Object[] {
                "sdcrit", cctId,
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
    
    
    public void editObjective(Long objectiveId, String description) throws Exception {
    	Objective o = (Objective)load(Objective.class, objectiveId);
    	o.setDescription(description);
    	save(o);
    }
    
    
    private static final String hql_getOrphanInfoObjects = "from Criteria c where c.suite=?";
    
    public Collection getOrphanInfoObjects(Long suiteId, Collection infoObjects) throws Exception {
    	HashSet usedInfoObjects = new HashSet();
    	CriteriaSuite cs = getCriteriaSuiteById(suiteId);
    	
    	List list = getHibernateTemplate().find(hql_getOrphanInfoObjects, new Object[] {
                cs,
        });
    	
    	Iterator it = list.iterator();
    	while(it.hasNext()) {
    		Criteria c = (Criteria)it.next();
    		usedInfoObjects.addAll(c.getInfoObjects());
    	}
    	
    	Iterator utIt = usedInfoObjects.iterator();
    	while(utIt.hasNext()) {
    		InfoObject t = (InfoObject) utIt.next();
    		infoObjects.remove(t);
    	}
    	
    	return infoObjects;
    }


    public Set getInfoObjects(Long isid) throws Exception {
    	InfoStructure is = (InfoStructure) load(InfoStructure.class, isid);	
    	return is.getInfoObjects();
    }
    
    
    public void editCriterion(boolean bool_name, boolean bool_infoObjects, boolean bool_objectives, org.hibernate.Criteria c, String name, Set infoObjects, SortedSet objectives, String na) throws Exception {
        // TODO Auto-generated method stub
        
    }


    public void setWeight(Long suiteId, org.hibernate.Criteria criteria, int weight) throws Exception {
        // TODO Auto-generated method stub
        
    }


}//class CriteriaDAOImpl
