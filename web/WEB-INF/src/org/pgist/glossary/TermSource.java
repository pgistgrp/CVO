package org.pgist.glossary;


/**
 * Glossary Sources
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_source"
 */
public class TermSource {


    private Long id;
    
    private String source;

    
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
    public String getSource() {
        return source;
    }


    public void setSource(String source) {
        this.source = source;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */


    public String toString() {
        return source;
    }
    
    
}//class TermSource
