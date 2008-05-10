package org.pgist.sarp.drt;


/**
 * @author kenny
 * 
 * @hibernate.class table="sarp_drt_announcement" lazy="true"
 */
public class DRTAnnouncement {
    
    
    private int id;
    
    private String title;
    
    private String description;
    
    private int numAgree;
    
    private int numVote;
    
    
    /**
     * @return
     * @hibernate.id generator-class="native"
     */
    public int getId() {
        return id;
    }
    
    
    public void setId(int id) {
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
    
    
} //class Announcement
