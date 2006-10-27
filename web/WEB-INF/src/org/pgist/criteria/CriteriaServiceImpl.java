package org.pgist.criteria;

import java.util.Collection;
import java.util.Map;


/**
 * 
 * @author kenny
 *
 */
public class CriteriaServiceImpl implements CriteriaService {
    
    
    private CriteriaDAO criteriaDAO;
    
    
    public void setCriteriaDAO(CriteriaDAO criteriaDAO) {
        this.criteriaDAO = criteriaDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public Collection getCriterias() throws Exception {
        return null;
    }//getCriterias()
    
    
    public Criteria addCriterion(String name, String low, String medium, String high, String na) throws Exception {
    	return criteriaDAO.addCriterion(name, low, medium, high, na);
    }//addCriterion()
    
    
    public void deleteCriterion(Long id) throws Exception {
    	criteriaDAO.deleteCriterion(id);
    }//deleteCriterion
    
    
    public void editCriterion(Criteria c, String name, String low, String medium, String high, String na) throws Exception {
    	criteriaDAO.editCriterion(c, name, low, medium, high, na);
    }//editCriterion()
    
    
    public Criteria getCriterionById(Long id) throws Exception {
    	return criteriaDAO.getCriterionById(id);
    }//getCriterionByID()
    
    
}//class CriteriaServiceImpl
