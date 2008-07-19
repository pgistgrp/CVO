package org.pgist.sarp.vtt;

import java.util.HashMap;
import java.util.Map;

import org.pgist.sarp.cst.CategoryReference;


/**
 * Measurement Unit Set for one CategoryReference (path).
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_vtt_munit_set" lazy="true"
 */
public class MUnitSet {
    
    
    private Long id;
    
    private CategoryReference catRef;
    
    // map: unit --> frequency
    private Map<String, Integer> freqs = new HashMap<String, Integer>();
    
    // map: expert id --> expert unit
    private Map<Long, EUnitSet> expUnits = new HashMap<Long, EUnitSet>();
    
    // map: expert id --> comment
    private Map<Long, String> expComments = new HashMap<Long, String>();
    
    
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
     * @hibernate.many-to-one column="catref_id" lazy="true" class="org.pgist.sarp.cst.CategoryReference"
     */
    public CategoryReference getCatRef() {
        return catRef;
    }
    
    
    public void setCatRef(CategoryReference catRef) {
        this.catRef = catRef;
    }


    /**
     * @return
     * 
     * @hibernate.map table="sarp_munitset_unit_freq_map"
     * @hibernate.collection-key column="munitset_id"
     * @hibernate.collection-index column="unit" type="string" length="32"
     * @hibernate.collection-element type="int" column="freq"
     */
    public Map<String, Integer> getFreqs() {
        return freqs;
    }
    public void setFreqs(Map<String, Integer> freqs) {
        this.freqs = freqs;
    }


    /**
     * @return
     * 
     * @hibernate.map table="sarp_munitset_user_eunitset_map"
     * @hibernate.collection-key column="munitset_id"
     * @hibernate.collection-index column="user_id" type="long"
     * @hibernate.collection-many-to-many column="eunitset_id" class="org.pgist.sarp.vtt.EUnitSet"
     */
    public Map<Long, EUnitSet> getExpUnits() {
        return expUnits;
    }
    public void setExpUnits(Map<Long, EUnitSet> expUnits) {
        this.expUnits = expUnits;
    }


    /**
     * @return
     * 
     * @hibernate.map table="sarp_munitset_user_comment_map"
     * @hibernate.collection-key column="munitset_id"
     * @hibernate.collection-index column="user_id" type="long"
     * @hibernate.collection-element type="string" column="comment" length="256"
     */
    public Map<Long, String> getExpComments() {
        return expComments;
    }
    public void setExpComments(Map<Long, String> expComments) {
        this.expComments = expComments;
    }
    
    
} //class MUnitSet
