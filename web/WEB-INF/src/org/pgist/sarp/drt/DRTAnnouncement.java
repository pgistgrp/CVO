package org.pgist.sarp.drt;


/**
 * @author kenny
 * 
 * @hibernate.class table="sarp_drt_announcement" lazy="true"
 */
public class DRTAnnouncement {
    
    
    private Long id;
    
    private String title;
    
    private String description;
    
    private int numAgree;
    
    private int numVote;
    
    private boolean done;
    
    
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
     * @hibernate.property
     */
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * @return
     * @hibernate.property type="text"
     */
    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getNumAgree() {
        return numAgree;
    }


    public void setNumAgree(int numAgree) {
        this.numAgree = numAgree;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getNumVote() {
        return numVote;
    }


    public void setNumVote(int numVote) {
        this.numVote = numVote;
    }
    
    


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isDone() {
        return done;
    }
    
    


    public void setDone(boolean done) {
        this.done = done;
    }
    
    
} //class Announcement
