package org.pgist.criteria;

import java.util.Map;

import org.pgist.system.BaseDAO;


/**
 * 
 * @author kenny
 *
 */
public interface CriteriaDAO extends BaseDAO {
    
	
	Criteria addCriterion(String name, String low, String medium, String high, String na) throws Exception;
    
	
	void deleteCriterion(Long id) throws Exception;
    
	
	void editCriterion(Criteria c, String name, String low, String medium, String high, String na) throws Exception;
	
	
	Criteria getCriterionById(Long id) throws Exception;
	
	
}//interface CriteriaDAO
