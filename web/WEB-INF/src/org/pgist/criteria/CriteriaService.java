package org.pgist.criteria;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.List;

import org.pgist.cvo.CCT;
import org.pgist.discussion.InfoStructure;
import org.pgist.cvo.Theme;

/**
 * 
 * @author kenny
 *
 */
public interface CriteriaService {
    
    
    Collection getCriterias() throws Exception;
    
    
    Criteria addCriterion(Boolean bool_themes, Boolean bool_objectives, String name, Set themes, SortedSet objectives, String na) throws Exception;
    
    
    void addAssocCriterion(Long critId, Long critSuiteId, boolean checked) throws Exception;
    
    
    void deleteCriterion(Long id) throws Exception;
    
    
    boolean getContainsCriteria(Long critId, Long critSuiteId) throws Exception; 
    
    
    void editCriterion(boolean bool_name, boolean bool_themes, boolean bool_objectives, Criteria c, String name, Set themes, SortedSet objectives, String na) throws Exception;
    
    
    Criteria getCriterionById(Long id) throws Exception;
    
    
    Collection getAllCriterion(Long critSuiteId) throws Exception;
   
    
    Collection getAllCriterion() throws Exception;
    
    
    Objective addObjective(Long critId, String description) throws Exception;
    
    
    List getThemes(Long cctId) throws Exception;
    
    
    Set<Theme> getThemeObjects(String[] themeIdList)throws Exception;
    
    
    SortedSet getObjectiveObjects(String[] objectivesIdList)throws Exception;
    
    
    void deleteObjective(Long id) throws Exception;
    
    
    Collection getObjectives() throws Exception;
    
    
    int getWeight(Long critSuiteId, Long critId) throws Exception;
    
    
    void publish(Long cctId) throws Exception;
    
    
    void setWeight(Long suiteId, Long critId, int weight) throws Exception;
    
    
    CCT getCCTById(Long cctId) throws Exception;
    
    
    Collection getCCTs() throws Exception;
    
    
    CriteriaSuite getCriteriaSuiteById(Long id) throws Exception;
    
    
    Collection getCriteriaSuites() throws Exception;
    
    
    CriteriaSuite createCriteriaSuite() throws Exception;


    InfoStructure publish(Long cctId, Long suiteId, String title) throws Exception;
    
    
    void editObjective(Long objectiveId, String description) throws Exception;
    
    
    Collection getOrphanThemes(Long suiteId, Long cctId) throws Exception;
    
    
}//interface CriteriaService
