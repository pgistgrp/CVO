package org.pgist.criteria;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.List;

import org.pgist.cvo.CCT;

/**
 * 
 * @author kenny
 *
 */
public interface CriteriaService {
    
    
    Collection getCriterias() throws Exception;
    
    
    Criteria addCriterion(String name, Long cctId, Set themes, Set objectives, String na) throws Exception;
    
    
    void deleteCriterion(Long id) throws Exception;
    
    
    void editCriterion(Criteria c, String name, Long cctId, Set themes, Set objectives, String na) throws Exception;
    
    
    Criteria getCriterionById(Long id) throws Exception;
    
    
    Collection getAllCriterion(Long cctId) throws Exception;
    
    
    Collection getAllCriterion() throws Exception;
    
    
    Objective addObjective(String description) throws Exception;
    
    
    List getThemes(Long cctId) throws Exception;
    
    
    Set getThemeObjects(String[] themeIdList)throws Exception;
    
    
    Set getObjectiveObjects(String[] objectivesIdList)throws Exception;
    
    
    void deleteObjective(Long id) throws Exception;
    
    
    Collection getObjectives() throws Exception;
    
    
    void setWeight(Long cctId, Long critId, int weight) throws Exception;
    
    
    Set getWeights(Long cctId) throws Exception;
    
    
    void publish(Long cctId, String[] criteriaIdList) throws Exception;
    
    
    public CCT getCCTById(Long cctId) throws Exception;
    
    
    public Collection getCCTs() throws Exception;
    
    
}//interface CriteriaService
