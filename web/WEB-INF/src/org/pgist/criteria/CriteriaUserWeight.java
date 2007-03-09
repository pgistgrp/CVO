package org.pgist.criteria;

import java.util.HashMap;
import java.util.Map;

import org.pgist.users.User;


/**
 * A CriteriaUserWeight contains a map of weights for each user.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_crit_user_weight" lazy="true"
 */
public class CriteriaUserWeight {
    
    
    private Long id;
    
    private CriteriaSuite suite;
    
    private Map<User, Integer> weights = new HashMap<User, Integer>();
    
    
    /**
     * @return
     * 
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
     * @hibernate.many-to-one column="critsuite_id" cascade="none"
     */
    public CriteriaSuite getSuite() {
        return suite;
    }


    public void setSuite(CriteriaSuite suite) {
        this.suite = suite;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_crit_user_weight_map"
     * @hibernate.collection-key column="userweight_id"
     * @hibernate.index-many-to-many column="user_id" class="org.pgist.users.User"
     * @hibernate.collection-element type="integer" column="weight"
     */
    public Map<User, Integer> getWeights() {
        return weights;
    }


    public void setWeights(Map<User, Integer> weights) {
        this.weights = weights;
    }
    
    
}//class CriteriaUserWeight
