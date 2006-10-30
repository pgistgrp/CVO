package org.pgist.criteria;

import java.util.Collection;
import java.util.Map;


/**
 * 
 * @author kenny
 *
 */
public interface CriteriaService {
    
    
    Collection getCriterias() throws Exception;
    
    
    Criteria addCriterion(String name, String low, String medium, String high, String na) throws Exception;
    
    
    void deleteCriterion(Long id) throws Exception;
    
    
    void editCriterion(Criteria c, String name, String low, String medium, String high, String na) throws Exception;
    
    
    Criteria getCriterionById(Long id) throws Exception;
    
    
    Collection getAllCriterion() throws Exception;
    
}//interface CriteriaService
