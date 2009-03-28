package org.pgist.sarp.vtt;

import java.util.HashMap;
import java.util.Map;

import org.pgist.sarp.cht.CategoryPath;


/**
 * Measurement Unit Set for one CategoryPath.
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_vtt_munit_set" lazy="true"
 */
public class MUnitSet implements Comparable<MUnitSet> {
    
    
    private Long id;
    
    private CategoryPath path;
    
    private String name;
    
    // map: unit --> frequency
    private Map<String, Integer> freqs = new HashMap<String, Integer>();
    
    // map: expert id --> expert unit
    private Map<Long, EUnitSet> expUnits = new HashMap<Long, EUnitSet>();
    
    // map: unit --> appropriate frequency
    private Map<String, Integer> apprFreqs = new HashMap<String, Integer>();
    
    // map: unit --> available frequency
    private Map<String, Integer> availFreqs = new HashMap<String, Integer>();
    
    // map: unit --> duplicate frequency
    private Map<String, Integer> dupFreqs = new HashMap<String, Integer>();
    
    // map: unit --> recommended frequency
    private Map<String, Integer> recoFreqs = new HashMap<String, Integer>();
    
    // map: user id --> unit selected by this user
    private Map<Long, String> userSelections= new HashMap<Long, String>();
    
    
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
     * @hibernate.many-to-one column="path_id" lazy="true"
     */
    public CategoryPath getPath() {
        return path;
    }
    
    
    public void setPath(CategoryPath path) {
        this.path = path;
    }


    /**
     * @return
     * 
     * @hibernate.property
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
     * @hibernate.map table="sarp_munitset_unit_apprfreq_map"
     * @hibernate.collection-key column="munitset_id"
     * @hibernate.collection-index column="unit" type="string" length="32"
     * @hibernate.collection-element type="int" column="freq"
     */
    public Map<String, Integer> getApprFreqs() {
        return apprFreqs;
    }


    public void setApprFreqs(Map<String, Integer> apprFreqs) {
        this.apprFreqs = apprFreqs;
    }


    /**
     * @return
     * 
     * @hibernate.map table="sarp_munitset_unit_availfreq_map"
     * @hibernate.collection-key column="munitset_id"
     * @hibernate.collection-index column="unit" type="string" length="32"
     * @hibernate.collection-element type="int" column="freq"
     */
    public Map<String, Integer> getAvailFreqs() {
        return availFreqs;
    }


    public void setAvailFreqs(Map<String, Integer> availFreqs) {
        this.availFreqs = availFreqs;
    }


    /**
     * @return
     * 
     * @hibernate.map table="sarp_munitset_unit_dupfreq_map"
     * @hibernate.collection-key column="munitset_id"
     * @hibernate.collection-index column="unit" type="string" length="32"
     * @hibernate.collection-element type="int" column="freq"
     */
    public Map<String, Integer> getDupFreqs() {
        return dupFreqs;
    }


    public void setDupFreqs(Map<String, Integer> dupFreqs) {
        this.dupFreqs = dupFreqs;
    }


    /**
     * @return
     * 
     * @hibernate.map table="sarp_munitset_unit_recofreq_map"
     * @hibernate.collection-key column="munitset_id"
     * @hibernate.collection-index column="unit" type="string" length="32"
     * @hibernate.collection-element type="int" column="freq"
     */
    public Map<String, Integer> getRecoFreqs() {
        return recoFreqs;
    }


    public void setRecoFreqs(Map<String, Integer> recoFreqs) {
        this.recoFreqs = recoFreqs;
    }


    /**
     * @return
     * 
     * @hibernate.map table="sarp_munitset_exp_eunitset_map"
     * @hibernate.collection-key column="munitset_id"
     * @hibernate.collection-index column="exp_id" type="long"
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
     * @hibernate.map table="sarp_munitset_user_eunitset_map"
     * @hibernate.collection-key column="munitset_id"
     * @hibernate.collection-index column="user_id" type="long"
     * @hibernate.collection-element type="string" column="selected_unit" length="256"
     */
    public Map<Long, String> getUserSelections() {
        return userSelections;
    }


    public void setUserSelections(Map<Long, String> userSelections) {
        this.userSelections = userSelections;
    }


    @Override
    public int compareTo(MUnitSet o) {
        return this.name.compareTo(o.getName());
    }
    
    
} //class MUnitSet
