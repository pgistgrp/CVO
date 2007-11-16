package org.pgist.system;


/**
 * Email Template class.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_email_template"
 */
public class EmailTemplate {


    private Long id;
    
    private String name;
    
    private String description;
    
    private String subject;
    
    private String notes;
    
    private String content;
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getDescription() {
        return description;
    }
    
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    
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
     * @hibernate.property unique="true" not-null="true"
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
    public String getSubject() {
        return subject;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }


    /**
     * @return
     * @hibernate.property type="text" not-null="true"
     */
    public String getNotes() {
        return notes;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }


    /**
     * @return
     * @hibernate.property type="text" not-null="true"
     */
    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


}//class EmailTemplate
