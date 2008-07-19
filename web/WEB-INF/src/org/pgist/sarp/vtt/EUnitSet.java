package org.pgist.sarp.vtt;

import java.util.HashMap;
import java.util.Map;


/**
 * Expert Unit Set.
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_vtt_eunit_set" lazy="true"
 */
public class EUnitSet {
    
    
    private Long id;
    
    // map: unit --> Appropriate
    private Map<String, Boolean> apprs = new HashMap<String, Boolean>();
    
    // map: unit --> Available
    private Map<String, Boolean> avails = new HashMap<String, Boolean>();
    
    // map: unit --> Duplicate
    private Map<String, Boolean> dups = new HashMap<String, Boolean>();
    
    // map: unit --> Recommend
    private Map<String, Boolean> recs = new HashMap<String, Boolean>();
    
    
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
     * @hibernate.map table="sarp_eunitset_unit_appr_map"
     * @hibernate.collection-key column="eunitset_id"
     * @hibernate.collection-index column="unit" type="string" length="32"
     * @hibernate.collection-element type="boolean" column="appr"
     */
    public Map<String, Boolean> getApprs() {
        return apprs;
    }
    public void setApprs(Map<String, Boolean> apprs) {
        this.apprs = apprs;
    }

    
    /**
     * @return
     * 
     * @hibernate.map table="sarp_eunitset_unit_avail_map"
     * @hibernate.collection-key column="eunitset_id"
     * @hibernate.collection-index column="unit" type="string" length="32"
     * @hibernate.collection-element type="boolean" column="avail"
     */
    public Map<String, Boolean> getAvails() {
        return avails;
    }
    public void setAvails(Map<String, Boolean> avails) {
        this.avails = avails;
    }

    
    /**
     * @return
     * 
     * @hibernate.map table="sarp_eunitset_unit_dup_map"
     * @hibernate.collection-key column="eunitset_id"
     * @hibernate.collection-index column="unit" type="string" length="32"
     * @hibernate.collection-element type="boolean" column="dup"
     */
    public Map<String, Boolean> getDups() {
        return dups;
    }
    public void setDups(Map<String, Boolean> dups) {
        this.dups = dups;
    }

    
    /**
     * @return
     * 
     * @hibernate.map table="sarp_eunitset_unit_rec_map"
     * @hibernate.collection-key column="eunitset_id"
     * @hibernate.collection-index column="unit" type="string" length="32"
     * @hibernate.collection-element type="boolean" column="rec"
     */
    public Map<String, Boolean> getRecs() {
        return recs;
    }
    public void setRecs(Map<String, Boolean> recs) {
        this.recs = recs;
    }
    
    
} //class EUnitSet
