package org.pgist.cvo;

import java.util.Collection;


/**
 * 
 * @author kenny
 *
 */
public interface CCTDAO {
    
    
    Collection getCCTs() throws Exception;

    void save(CCT cct) throws Exception;

    CCT getCCTById(Long cctId) throws Exception;
    
    
}//interface CCTDAO
