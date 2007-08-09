package org.pgist.criteria;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

import org.pgist.discussion.InfoObject;
import org.pgist.system.BaseDAO;

/**
 * 
 * @author kenny
 *
 */
public interface CriteriaDAO extends BaseDAO {
    
	
	Criteria addCriterion(Boolean bool_infoObjects, Boolean bool_objectives, String name, Set infoObjects,  SortedSet objectives, String na) throws Exception;
    
	
	void addAssocCriterion(Long critId, Long critSuiteId, boolean checked) throws Exception;
	
	
	void deleteCriterion(Long id) throws Exception;
    
	
	boolean getContainsCriteria(Long critId, Long critSuiteId) throws Exception; 
	
	
	void editCriterion(boolean bool_name, boolean bool_infoObjects, boolean bool_objectives, Criteria c, String name, Set infoObjects, SortedSet objectives, String na) throws Exception;
	
	
	Criteria getCriterionById(Long id) throws Exception;
	
	
	Set getCriterions(String[] criteriaIdList) throws Exception;
	
	
	Collection getAllCriterion(Long critSuiteId) throws Exception;
	
	
	Collection getAllCriterion() throws Exception;
	
	
	Objective addObjective(Long critId, String description) throws Exception;
	
	
	Set<InfoObject> getInfoObjects(String[] infoObjectsIdList)throws Exception;
	
	
	SortedSet getObjectiveObjects(String[] objectivesIdList)throws Exception;
	
	
	void deleteObjective(Long id) throws Exception;
	
	
	Collection getObjectives() throws Exception;
	
	
	int getWeight(Long critSuiteId, Long critId) throws Exception;
	
	
	void setWeight(Long suiteId, Criteria criteria, int weight) throws Exception;
	
	
	CriteriaSuite getCriteriaSuiteById(Long id) throws Exception;
	
	
	Collection getCriteriaSuites() throws Exception;

	
	Long checkPublished(Long cctId) throws Exception;
	
	
	void editObjective(Long objectiveId, String description) throws Exception;

	
	Collection getOrphanInfoObjects(Long suiteId, Collection infoObjects) throws Exception;

	
	Set getInfoObjects(Long isid) throws Exception;

}//interface CriteriaDAO
