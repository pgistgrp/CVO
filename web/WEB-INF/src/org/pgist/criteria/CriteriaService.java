package org.pgist.criteria;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

import org.pgist.cvo.CCT;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;


/**
 * 
 * @author kenny
 *
 */
public interface CriteriaService {
    
    
    Collection getCriterias() throws Exception;
    
    
    Criteria addCriterion(Boolean bool_infoObjects, Boolean bool_objectives, String name, Set infoObjects, SortedSet objectives, String na) throws Exception;
    
    
    void addAssocCriterion(Long critId, Long critSuiteId, boolean checked) throws Exception;
    
    
    void deleteCriterion(Long id) throws Exception;
    
    
    boolean getContainsCriteria(Long critId, Long critSuiteId) throws Exception; 
    
    
    void editCriterion(boolean bool_name, boolean bool_infoObjects, boolean bool_objectives, Criteria c, String name, Set infoObjects, SortedSet objectives, String na) throws Exception;
    
    
    Criteria getCriterionById(Long id) throws Exception;
    
    
    Collection getAllCriterion(Long critSuiteId) throws Exception;
   
    
    Collection getAllCriterion() throws Exception;
    
    
    Objective addObjective(Long critId, String description) throws Exception;
    
    
    Set<InfoObject> getInfoObjects(String[] infoObjectsIdList)throws Exception;
    
    
    SortedSet getObjectiveObjects(String[] objectivesIdList)throws Exception;
    
    
    void deleteObjective(Long id) throws Exception;
    
    
    Collection getObjectives() throws Exception;
    
    
    int getWeight(Long critSuiteId, Long critId) throws Exception;
    
    
    void setWeight(Long suiteId, Long critId, int weight) throws Exception;
    
    
    CCT getCCTById(Long cctId) throws Exception;
    
    
    Collection getCCTs() throws Exception;
    
    
    CriteriaSuite getCriteriaSuiteById(Long id) throws Exception;
    
    
    Collection getCriteriaSuites() throws Exception;
    
    
    CriteriaSuite createCriteriaSuite() throws Exception;


    InfoStructure publish(Long workflowId, Long cctId, Long suiteId, String title) throws Exception;
    
    
    void editObjective(Long objectiveId, String description) throws Exception;
    
    
    Collection getOrphanInfoObjects(Long suiteId, Long isid) throws Exception;
    
    
    Set getInfoObjects(Long isid) throws Exception;
    
    
}//interface CriteriaService
