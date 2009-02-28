package org.pgist.sarp.bct;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="sarp_bct" lazy="true"
 */
public class BCT {
    
    
    private Long id;
    
    private String name = "";
    
    private String purpose = "";
    
    private String instruction = "";
    
    private Date createTime;
    
    private boolean closed = false;
    
    private int maxConcernPerPerson = Integer.MAX_VALUE;
    
    private Set concerns = new HashSet();
    
    private Set tagRefs = new HashSet();
    
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
     * @hibernate.set lazy="true" table="sarp_bct_concern_link" cascade="all" order-by="createTime desc"
     * @hibernate.collection-key column="bct_id"
     * @hibernate.collection-many-to-many class="org.pgist.sarp.bct.Concern"
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
     * @hibernate.set lazy="true" cascade="all" table="sarp_bct_tag_refs" order-by="id"
     * @hibernate.collection-key column="cct_id"
     * @hibernate.collection-many-to-many class="org.pgist.sarp.bct.TagReference"
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
    public boolean isClosed() {
		return closed;
	}
    
    
	public void setClosed(boolean closed) {
		this.closed = closed;
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

    
}//class BCT
