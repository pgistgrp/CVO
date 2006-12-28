package org.pgist.criteria;

import org.pgist.cvo.CCT;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_criteria_weight" lazy="true"
 */
public class CriteriaWeight {
    
    
    private Long id;
    
    private CCT cct;
    
    private Criteria criteria;
    
    private User owner;
    
    private int weight = 0;
    
    
    /**
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
     * 
     * @hibernate.many-to-one lazy="true"
     */
    public Criteria getCriteria() {
        return criteria;
    }


    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one lazy="true"
     */
    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }


    /**
     * @hibernate.property not-null="true"
     */
    public int getWeight() {
        return weight;
    }


    public void setWeight(int weight) {
        this.weight = weight;
    }


	public CCT getCct() {
		return cct;
	}


	public void setCct(CCT cct) {
		this.cct = cct;
	}


}//class CriteriaWeight
