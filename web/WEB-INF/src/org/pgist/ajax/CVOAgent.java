package org.pgist.ajax;

import org.pgist.cvo.CVODAO;


/**
 * 
 * @author kenny
 *
 */
public class CVOAgent {

    
    private CVODAO cvoDAO;
    
    
    public void setCvoDAO(CVODAO cvoDAO) {
        this.cvoDAO = cvoDAO;
    }
    
    
    public boolean createCVO(String name) {
        return true;
    }
    
    
}
