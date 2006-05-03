package org.pgist.cvo;

import java.util.HashSet;
import java.util.Set;

import org.pgist.model.PGame;


/**
 * 
 * @author kenny
 *
 * @hibernate.joined-subclass name="CCT" table="pgist_cvo_cct"
 * @hibernate.joined-subclass-key column="id"
 */
public class CCT extends PGame {
    
    
    private int maxConcernPerPerson = Integer.MAX_VALUE;
    
    private Set concerns = new HashSet();
    
    private Set tagRefs = new HashSet();
    
    private CategoryReference rootCategory = new CategoryReference();
    
    
    public CCT() {
        rootCategory.setCctId(this.getId());
    }
    
    
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public int getMaxConcernPerPerson() {
        return maxConcernPerPerson;
    }
    
    
    public void setMaxConcernPerPerson(int maxConcernPerPerson) {
        this.maxConcernPerPerson = maxConcernPerPerson;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" table="pgist_cvo_concerns" order-by="createTime desc"
     * @hibernate.collection-key column="cct_id"
     * @hibernate.collection-one-to-many class="org.pgist.cvo.Concern"
     */
    public Set getConcerns() {
        return concerns;
    }


    public void setConcerns(Set concerns) {
        this.concerns = concerns;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" table="pgist_cvo_tag_refs" order-by="id"
     * @hibernate.collection-key column="cct_id"
     * @hibernate.collection-one-to-many class="org.pgist.cvo.TagReference"
     */
    public Set getTagRefs() {
        return tagRefs;
    }


    public void setTagRefs(Set tagRefs) {
        this.tagRefs = tagRefs;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="root_cat_ref_id" cascade="all" lazy="true"
     */
    public CategoryReference getRootCategory() {
        return rootCategory;
    }


    public void setRootCategory(CategoryReference rootCategory) {
        this.rootCategory = rootCategory;
    }


}//class CCT
