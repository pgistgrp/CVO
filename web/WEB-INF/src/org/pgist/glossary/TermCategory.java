package org.pgist.glossary;

import java.io.Serializable;


/**
 * Category for Glossary Term
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_category"
 */
public class TermCategory implements Serializable {

    
    private Long id;
    
    private String name;
    
    
    /**
     * @return
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }


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


}//class TermCategory
