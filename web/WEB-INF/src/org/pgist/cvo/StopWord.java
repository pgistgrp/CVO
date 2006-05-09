package org.pgist.cvo;


/**
 * @author Jie
 * @version 1.0
 * @hibernate.class table="pgist_cvo_stopwords" lazy="true"
 */
public class StopWord {
    
    
    protected Long id;

    protected String name;


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
    
    
}//class StopWord
