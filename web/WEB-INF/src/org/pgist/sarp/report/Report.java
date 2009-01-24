package org.pgist.sarp.report;

import org.pgist.sarp.bct.BCT;
import org.pgist.sarp.cht.CHT;
import org.pgist.sarp.cst.CST;
import org.pgist.sarp.drt.InfoObject;
import org.pgist.sarp.vtt.VTT;

/**
 * @author kenny
 *
 * @hibernate.class table="sarp_report" lazy="true"
 */
public class Report {
    
    
    private Long id;
    
    private Long workflowId;
    
    private BCT bct;
    
    private CST cst;
    
    private CHT cht;
    
    private VTT vtt;
    
    private InfoObject bctDrt;
    
    private InfoObject cstDrt;
    
    private InfoObject chtDrt;
    
    private InfoObject vttDrt;
    
    
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
    public Long getWorkflowId() {
        return workflowId;
    }
    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="bct_id"
     */
    public BCT getBct() {
        return bct;
    }
    public void setBct(BCT bct) {
        this.bct = bct;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="cst_id"
     */
    public CST getCst() {
        return cst;
    }
    public void setCst(CST cst) {
        this.cst = cst;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="cht_id"
     */
    public CHT getCht() {
        return cht;
    }
    public void setCht(CHT cht) {
        this.cht = cht;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="vtt_id"
     */
    public VTT getVtt() {
        return vtt;
    }
    public void setVtt(VTT vtt) {
        this.vtt = vtt;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="bct_drt_id"
     */
    public InfoObject getBctDrt() {
        return bctDrt;
    }
    public void setBctDrt(InfoObject bctDrt) {
        this.bctDrt = bctDrt;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="cst_drt_id"
     */
    public InfoObject getCstDrt() {
        return cstDrt;
    }
    public void setCstDrt(InfoObject cstDrt) {
        this.cstDrt = cstDrt;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="cht_drt_id"
     */
    public InfoObject getChtDrt() {
        return chtDrt;
    }
    public void setChtDrt(InfoObject chtDrt) {
        this.chtDrt = chtDrt;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="vtt_drt_id"
     */
    public InfoObject getVttDrt() {
        return vttDrt;
    }
    public void setVttDrt(InfoObject vttDrt) {
        this.vttDrt = vttDrt;
    }

    
} //class Report
