package org.pgist.other;

import org.pgist.criteria.CriteriaSuite;
import org.pgist.cvo.CCT;
import org.pgist.funding.FundingSourceSuite;
import org.pgist.packages.PackageSuite;
import org.pgist.projects.ProjectSuite;
import org.pgist.report.ReportSuite;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_experiment"
 */
public class Experiment {
    
    
    private Long id;
    
    private Long workflowId;
    
    private CCT cct;
    
    private ProjectSuite projectSuite;
    
    private FundingSourceSuite fundingSuite;
    
    private PackageSuite pkgSuite;
    
    private CriteriaSuite critSuite;
    
    private ReportSuite reportSuite;
    
    
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
     * @hibernate.many-to-one column="cct_id"
     */
    public CCT getCct() {
        return cct;
    }


    public void setCct(CCT cct) {
        this.cct = cct;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="proj_suite_id"
     */
    public ProjectSuite getProjectSuite() {
        return projectSuite;
    }


    public void setProjectSuite(ProjectSuite projectSuite) {
        this.projectSuite = projectSuite;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="fund_suite_id"
     */
    public FundingSourceSuite getFundingSuite() {
        return fundingSuite;
    }


    public void setFundingSuite(FundingSourceSuite fundingSuite) {
        this.fundingSuite = fundingSuite;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="pkg_suite_id"
     */
    public PackageSuite getPkgSuite() {
        return pkgSuite;
    }


    public void setPkgSuite(PackageSuite pkgSuite) {
        this.pkgSuite = pkgSuite;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.many-to-one column="crit_suite_id"
     */
    public CriteriaSuite getCritSuite() {
        return critSuite;
    }


    public void setCritSuite(CriteriaSuite critSuite) {
        this.critSuite = critSuite;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="report_suite_id"
     */
    public ReportSuite getReportSuite() {
        return reportSuite;
    }


    public void setReportSuite(ReportSuite reportSuite) {
        this.reportSuite = reportSuite;
    }


}//class Experiment
