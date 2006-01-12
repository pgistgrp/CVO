package org.pgist.cvo;

import org.pgist.model.Discussible;


/**
 * @author kenny
 *
 * @hibernate.joined-subclass name="CVO" table="pgist_cvo"
 * @hibernate.joined-subclass-key column="id"
 */
public class CVO extends Discussible {
    
    
    private String name;
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    
}
