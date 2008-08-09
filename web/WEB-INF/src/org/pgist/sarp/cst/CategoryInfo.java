package org.pgist.sarp.cst;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author kenny
 *
 * @hibernate.class table="sarp_cst_category_info" lazy="true"
 */
public class CategoryInfo {
    
    
    private Long id;
    
    private CategoryReference catRef;
    
    private List<CategoryInfo> children = new ArrayList<CategoryInfo>();
    
    private Set<Integer> users = new HashSet<Integer>();
    
    private Map<String, Integer> alias = new HashMap<String, Integer>();
    
    private Set<String> tagsDiff = new HashSet<String>();
    
    private Set<Integer> jaccardScores = new HashSet<Integer>();
    
    private int freqSet;
    
    private int freqName;
    
    private int freqNameAndSet;
    
    
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
     * @hibernate.many-to-one column="catref_id" lazy="true" cascade="all"
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
     * @hibernate.list lazy="false" table="sarp_cst_cat_info_info_link" cascade="all"
     * @hibernate.collection-key column="parent_id"
     * @hibernate.collection-index column="index_order"
     * @hibernate.collection-one-to-many column="child_id" class="org.pgist.sarp.cst.CategoryInfo"
     */
    public List<CategoryInfo> getChildren() {
        return children;
    }
    public void setChildren(List<CategoryInfo> children) {
        this.children = children;
    }
    
    
    public void addChild(CategoryInfo child) {
        this.children.add(child);
        this.getCatRef().getChildren().add(child.getCatRef());
    }
    
    
    /**
     * @return
     * @hibernate.set table="sarp_cst_cat_info_user_link"
     * @hibernate.collection-key column="catinfo_id"
     * @hibernate.collection-element type="integer" column="user_id"
     */
    public Set<Integer> getUsers() {
        return users;
    }
    public void setUsers(Set<Integer> users) {
        this.users = users;
    }
    
    
    public void addUser(Object user) {
        if (user instanceof BigInteger) {
            users.add(((BigInteger) user).intValue());
        } else if (user instanceof Long) {
            users.add(((Long) user).intValue());
        } else if (user instanceof Integer) {
            users.add((Integer) user);
        }
    }
    
    
    /**
     * @return
     * @hibernate.map table="sarp_cst_cat_info_alias_link"
     * @hibernate.collection-key column="catinfo_id"
     * @hibernate.collection-index column="alias" type="string" length="255"
     * @hibernate.collection-element type="int" column="frequency"
     */
    public Map<String, Integer> getAlias() {
        return alias;
    }
    public void setAlias(Map<String, Integer> alias) {
        this.alias = alias;
    }
    
    
    /**
     * @return
     * @hibernate.set table="sarp_cst_cat_info_tagsdiff_link"
     * @hibernate.collection-key column="catinfo_id"
     * @hibernate.collection-element type="string" column="tagsdiff"
     */
    public Set<String> getTagsDiff() {
        return tagsDiff;
    }
    public void setTagsDiff(Set<String> tagsDiff) {
        this.tagsDiff = tagsDiff;
    }
    
    
    /**
     * @return
     * @hibernate.set table="sarp_cst_cat_info_score_link"
     * @hibernate.collection-key column="catinfo_id"
     * @hibernate.collection-element type="integer" column="jaccardscores"
     */
    public Set<Integer> getJaccardScores() {
        return jaccardScores;
    }
    public void setJaccardScores(Set<Integer> jaccardScores) {
        this.jaccardScores = jaccardScores;
    }
    
    
    public void addScore(Long score) {
        jaccardScores.add(score.intValue());
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public int getFreqSet() {
        return freqSet;
    }
    public void setFreqSet(int freqSet) {
        this.freqSet = freqSet;
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public int getFreqName() {
        return freqName;
    }
    public void setFreqName(int freqName) {
        this.freqName = freqName;
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public int getFreqNameAndSet() {
        return freqNameAndSet;
    }
    public void setFreqNameAndSet(int freqNameAndSet) {
        this.freqNameAndSet = freqNameAndSet;
    }
    
    
} //class CategoryInfo
