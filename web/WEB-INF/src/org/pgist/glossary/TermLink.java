package org.pgist.glossary;


/**
 * Glossary Links
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_link"
 */
public class TermLink {


    private Long id;
    
    private String link;

    
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
    public String getLink() {
        return link;
    }


    public void setLink(String link) {
        this.link = link;
    }


    public String toString() {
        return link;
    }


}//class TermLink
