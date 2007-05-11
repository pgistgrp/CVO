package org.pgist.criteria;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pgist.projects.Project;
import org.pgist.projects.ProjectRef;
import org.pgist.users.User;
import org.pgist.cvo.Theme;


/**
 * A CriteriaSuite is a suite of CriteriaRef objects.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_crit_suite" lazy="true"
 */
public class CriteriaSuite {
    
    
    private Long id;
    
    private Set<CriteriaRef> references = new HashSet<CriteriaRef>();
    
    private Map<CriteriaRef, CriteriaUserWeight> weights = new HashMap<CriteriaRef, CriteriaUserWeight>();
    
    
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
     * @hibernate.set inverse="true" lazy="true" table="pgist_suite_crit_link"
     * @hibernate.collection-key column="suite_id"
     * @hibernate.collection-one-to-many class="org.pgist.criteria.CriteriaRef"
     */
    public Set<CriteriaRef> getReferences() {
        return references;
    }


    public void setReferences(Set<CriteriaRef> references) {
        this.references = references;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_crit_suite_weight_map"
     * @hibernate.collection-key column="critsuite_id"
     * @hibernate.index-many-to-many column="crit_id" class="org.pgist.criteria.CriteriaRef"
     * @hibernate.collection-many-to-many class="org.pgist.criteria.CriteriaUserWeight" column="weight_id"
     */
    public Map<CriteriaRef, CriteriaUserWeight> getWeights() {
        return weights;
    }


    public void setWeights(Map<CriteriaRef, CriteriaUserWeight> weights) {
        this.weights = weights;
    }
    
    
    /**
     * Put to weight map
     * @param User user, Integer weight
     */
    public void addWeight(CriteriaRef cr, CriteriaUserWeight cuw) {	
    	weights.put(cr, cuw); 	
    } //addWeight();
    
    
    /**
     * Put to weight map
     * @param User user, Integer weight
     */
    public void addReference(CriteriaRef cr) {	
    	references.add(cr); 	
    } //addReference();
    
    
    public CriteriaRef getCriteriaReference(Criteria criteria) {
    	if(criteria == null) return null;
    	for (CriteriaRef cr : getReferences()) {    		
    		if(cr.getCriterion().getId().equals(criteria.getId())) {
    			return cr;
    		}
    	}    	
    	return null;
    } //getProjectReference
    
    /*
    public CriteriaRef getCriteriaReference(Theme theme) {
    	if(theme == null) return null;
    	for (CriteriaRef cr : getReferences()) {
    		for(Theme t : cr.getCriterion().getThemes()) {
    			if(t.getId().equals(theme.getId())) {
    				return cr;
    			}
    		}
    	}    	
    	return null;
    } //getProjectReference
    */
}//class CriteriaSuite
