package org.pgist.report;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.criteria.CriteriaService;
import org.pgist.criteria.CriteriaSuite;
import org.pgist.cvo.CCT;
import org.pgist.cvo.CCTService;
import org.pgist.cvo.CSTService;
import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.PackageService;
import org.pgist.packages.PackageSuite;
import org.pgist.packages.PackageVoteSuite;
import org.pgist.packages.VoteSuiteStat;
import org.pgist.projects.ProjectService;
import org.pgist.projects.ProjectSuite;
import org.pgist.system.SystemService;
import org.pgist.users.User;
import org.pgist.util.WebUtils;


/**
 * An action for step 5 reports<br> 
 * 
 * This action accepts such parameters:<br>
 * <ul>
 *   <li>cct_id - long, Id of the CCT object</li>
 *   <li>critSuiteId - long, id of the criteria suite</li>
 *   <li>pkgSuiteId - long, id of the package suite</li>
 *   <li>repoSuiteId - long, id of the report suite</li>
 * </ul>
 * 
 * The control will be forwarded to page with the mapping name of "reports", with the following variables available:<br>
 * <ul>
 *   <li>summaries - a list of themes</li>
 *   <li>cr - a set or criterias</li>
 *   <li>up - a set of user packages</li>
 *   <li>cp - a set of clustered packages</li>
 *   <li>pp - the preferred package</li> 
 *   <li>vss - a set of voteSuiteStat objects</li>
 * </ul>
 * 
 * @author kenny
 *
 */

public class ReportAction extends Action {

	private ReportService reportService;
	private CCTService cctService;
	private CSTService cstService;
	private CriteriaService criteriaService;
	private PackageService packageService;
	private ProjectService projectService;
	private SystemService systemService;
	

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public void setCstService(CSTService cstService) {
		this.cstService = cstService;
	}
	
	public void setCctService(CCTService cctService) {
		this.cctService = cctService;
	}
	
	public void setCriteriaService(CriteriaService criteriaService) {
		this.criteriaService = criteriaService;
	}
	
	public void setPackageService(PackageService packageService) {
		this.packageService = packageService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
    	
    	String strCctId = request.getParameter("cct_id");
    	String strCritSuiteId = request.getParameter("critSuiteId");
    	String strPackSuiteId = request.getParameter("pkgSuiteId");
    	String strReportSuiteId = request.getParameter("repoSuiteId");
    	String strFundingSuiteId = request.getParameter("fundSuiteId");
    	String strProjectSuiteId = request.getParameter("projSuiteId");
    	String strVoteSuiteId = request.getParameter("voteSuiteId");
    	String errors = "";
    	
    	boolean error = false;
    	
        if (strCctId==null || "".equals(strCctId.trim())) {
            errors += "cct_id cannot be empty <br/>";
            error = true;
        }     
        if (strCritSuiteId==null || "".equals(strCritSuiteId.trim())) {
        	errors += "critSuiteId cannot be empty <br/>";
            error = true;
        }
        if (strPackSuiteId==null || "".equals(strPackSuiteId.trim())) {
        	errors += "pkgSuiteId cannot be empty <br/>";
            error = true;
        }
        if (strFundingSuiteId==null || "".equals(strFundingSuiteId.trim())) {
        	errors += "fundingSuiteId cannot be empty <br/>";
            error = true;
        }
        if (strProjectSuiteId==null || "".equals(strProjectSuiteId.trim())) {
        	errors += "projectSuiteId cannot be empty <br/>";
            error = true;
        }
        if (strReportSuiteId==null || "".equals(strReportSuiteId.trim())) {
        	errors += "repoSuiteId cannot be empty <br/>";
            error = true;
        }
        if (strVoteSuiteId==null || "".equals(strVoteSuiteId.trim())) {
        	errors += "voteSuiteId cannot be empty <br/>";
            error = true;
        }
        if(error) {
        	errors += " cctId:" + strCctId + " critSuiteId:" + strCritSuiteId;
        	request.setAttribute("error", errors);
            return mapping.findForward("report");	
        }
        
        Long cctId = Long.parseLong(strCctId);
        Long critSuiteId = Long.parseLong(strCritSuiteId);

        Long packSuiteId = Long.parseLong(strPackSuiteId);
        Long repoSuiteId = Long.parseLong(strReportSuiteId);
        Long fundSuiteId = Long.parseLong(strFundingSuiteId);
        Long projSuiteId = Long.parseLong(strProjectSuiteId);
        Long voteSuiteId = Long.parseLong(strVoteSuiteId);
        
        // get Concern Summaries
        CCT cct = cctService.getCCTById(cctId);
        Collection summaries = cstService.getThemes(cct);
        
        // get Criteria
    	CriteriaSuite cs = criteriaService.getCriteriaSuiteById(critSuiteId);
    	Collection cr = cs.getReferences();
    	
    	//get Report Summary
    	ReportSuite rs = reportService.getReportSuiteById(repoSuiteId);
    	ReportSummary repoSummary = rs.getReportSummary();
    	
    	// get Packages
    	PackageSuite pkgSuite = packageService.getPackageSuite(packSuiteId);
    	
    	// get cluster packages
    	Collection cp = pkgSuite.getClusteredPkgs();
    	
    	// get preferred package
    	VoteSuiteStat ppvss = pkgSuite.getPrefPkgVoteSuiteStat();
    	ClusteredPackage pp = ppvss.getClusteredPackage();
    	
    	//get all vote suite stats
    	Collection vss = reportService.getVoteSuiteStats(packSuiteId);
    	
    	//get Funding refs
    	Set fundRefs = reportService.getFundRefbySuiteId(fundSuiteId);
    	
    	//get Project refs
    	Set projRefs = reportService.getProjRefbySuiteId(projSuiteId);
    	ProjectSuite ps = projectService.getProjectSuite(projSuiteId);
    	
    	//Get clustered packages vote suite stats
    	PackageVoteSuite vSuite = packageService.getPackageVoteSuite(voteSuiteId);
    	
    	//get DTO mess
    	User u = reportService.getUserById(WebUtils.currentUserId()); 
    	List dto = packageService.createPackageRoadProjectDTOs(pp, critSuiteId, projSuiteId, u);
    	System.out.println("***ReportAction PPID" + pp.getId() + " crit " + critSuiteId + " proj " + projSuiteId + " user " + u.getLoginname() + "dto = " + dto);
    	request.setAttribute("packageRoadProjects", dto);
 	
    	
    	//Sets the Criteria References which contain criteria and grades.
    	request.setAttribute("voteSuite", vSuite);  
    	request.setAttribute("summaries", summaries);
    	request.setAttribute("cr", cr);
    	request.setAttribute("fundRefs", fundRefs);
    	request.setAttribute("projRefs", projRefs);
    	request.setAttribute("cp", cp);
    	request.setAttribute("pp", pp);
    	request.setAttribute("vss", vss);
    	//request.setAttribute("pkgSuite", pkgSuite);
    	
    	request.setAttribute("executiveSummary", repoSummary.getExecutiveSummary());
    	request.setAttribute("part1a", repoSummary.getPart1a());
    	request.setAttribute("part1b", repoSummary.getPart1b());
    	request.setAttribute("part2a", repoSummary.getPart2a());
    	request.setAttribute("part3a", repoSummary.getPart3a());
    	request.setAttribute("part4a", repoSummary.getPart4a());
    	
    	request.setAttribute("critSuiteId", critSuiteId);
    	request.setAttribute("cctId", cctId);
    	request.setAttribute("packSuiteId", packSuiteId);
    	request.setAttribute("finalized", repoSummary.isFinalized());
    	
    	request.setAttribute("statsES", rs.getStatsES());
    	request.setAttribute("statsPart1", rs.getStatsPart1());
    	request.setAttribute("statsPart2", rs.getStatsPart2());
    	request.setAttribute("statsPart3", rs.getStatsPart3());
    	request.setAttribute("statsPart4", rs.getStatsPart4());
    	
    	request.setAttribute("finalReportDate", repoSummary.getFinalReportDate());
    	request.setAttribute("reportVoteDate", repoSummary.getFinalVoteDate());
    	request.setAttribute("repoSuiteId", repoSuiteId);
    	request.setAttribute("counties", systemService.getAllCounties());
    	
        return mapping.findForward("report");
    }//execute()



} //ReportAction
