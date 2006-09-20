package org.pgist.glossary;

import java.io.Serializable;


/**
 * Glossary Sources
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_source"
 */
public class TermSource implements Serializable {


    private Long id;
    
    private String citation;
    
    private String url;

    
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
    public String getCitation() {
        return citation;
    }


    public void setCitation(String citation) {
        this.citation = citation;
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    /*
     * ------------------------------------------------------------------------
     */


    public String toString() {
        return citation;
    }
    
    
}//class TermSource
