package org.pgist.cvo;

import org.pgist.model.PGame;


/**
 * 
 * @author kenny
 *
 * @hibernate.joined-subclass name="CST" table="pgist_cvo_cst"
 * @hibernate.joined-subclass-key column="id"
 */
public class CST extends PGame {
    
    
    private CCT cct;
    
    
    public CCT getCct() {
        return cct;
    }
    
    
    public void setCct(CCT cct) {
        this.cct = cct;
    }
    
    
}//class CST
