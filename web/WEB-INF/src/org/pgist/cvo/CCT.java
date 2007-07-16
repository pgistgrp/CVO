package org.pgist.cvo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_cvo_cct" lazy="true"
 */
public class CCT {
    
    
    private Long id;
    
    private String name = "";
    
    private String purpose = "";
    
    private String instruction = "";
    
    private Date createTime;
    
    private boolean deleted = false;
    
    private int maxConcernPerPerson = Integer.MAX_VALUE;
    
    private Set concerns = new HashSet();
    
    private Set tagRefs = new HashSet();
    
    //private Set criteria = new HashSet();
    
    private CategoryReference rootCategory = new CategoryReference();

    private Long workflowId;
    
    
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
     * @hibernate.set lazy="true" cascade="all" order-by="createTime desc"
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
     * @hibernate.set lazy="true" cascade="all" table="pgist_cvo_criteria_link" order-by="id"
     * @hibernate.collection-key column="cct_id"
     * @hibernate.collection-one-to-many class="org.pgist.criteria.Criteria"
     */
    /*public Set getCriteria() {
		return criteria;
	}


	public void setCriteria(Set criteria) {
		this.criteria = criteria;
	}
*/

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
     * @hibernate.property not-null="true"
     */
    public boolean isDeleted() {
        return deleted;
    }
    
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
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
     * @hibernate.property type="text" not-null="true"
     */
    public String getInstruction() {
        return instruction;
    }


    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }


    /**
     * @return
     * 
     * @hibernate.property type="text" not-null="true"
     */
    public String getPurpose() {
        return purpose;
    }


    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public Long getWorkflowId() {
        return workflowId;
    }


    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    
}//class CCT
