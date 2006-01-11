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
    
    private boolean deleted;
    
    
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
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isDeleted() {
        return deleted;
    }
    
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    
}
