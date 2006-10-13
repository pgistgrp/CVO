package org.pgist.criteria;

import java.util.Collection;


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
    
    
}//class CriteriaServiceImpl
