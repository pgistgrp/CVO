package org.pgist.criteria;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


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
    
    
}//class CriteriaSuite
