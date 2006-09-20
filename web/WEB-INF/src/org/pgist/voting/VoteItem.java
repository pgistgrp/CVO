package org.pgist.voting;

import java.io.Serializable;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_vote_item"
 */
public class VoteItem implements Serializable {
    
    
    private Long id;
    
    private String title;
    
    private int type;
    
    private int score;
    
    
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
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getScore() {
        return score;
    }


    public void setScore(int score) {
        this.score = score;
    }
    
    
}//class VoteItem
