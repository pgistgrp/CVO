package org.pgist.cvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_cvo_summary" lazy="true"
 */
public class Summary implements Serializable {
    
    
    private Long id;
    
    private Long cctId;
    
    private List revisions = new ArrayList();
    
    
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
    public Long getCctId() {
        return cctId;
    }


    public void setCctId(Long cctId) {
        this.cctId = cctId;
    }


    /**
     * @return
     * 
     * @hibernate.list table="pgist_cvo_summary_revision" lazy="true" cascade="all"
     * @hibernate.collection-key column="summary_id"
     * @hibernate.collection-index column="revision_order"
     * @hibernate.collection-one-to-many class="org.pgist.cvo.SummaryRevision"
     * 
     */
    public List getRevisions() {
        return revisions;
    }


    public void setRevisions(List revisions) {
        this.revisions = revisions;
    }


}//class Summary
