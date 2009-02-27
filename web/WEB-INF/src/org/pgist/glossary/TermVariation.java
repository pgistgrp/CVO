package org.pgist.glossary;

import java.io.Serializable;


/**
 * Variation of a Glossary Term
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_variations"
 */
public class TermVariation implements Serializable {


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


    /*
     * ------------------------------------------------------------------------
     */


    public String toString() {
        return name;
    }


}//class TermVariation
