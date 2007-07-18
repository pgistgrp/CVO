package org.pgist.report;

import java.util.Collection;
import java.util.Iterator;

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
import org.pgist.packages.UserPackage;
import org.pgist.system.SystemService;
import org.pgist.users.UserInfo;
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
	private SystemService systemService;
	private PackageService packageService;
	

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
        if (strReportSuiteId==null || "".equals(strReportSuiteId.trim())) {
        	errors += "repoSuiteId cannot be empty <br/>";
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
    	Collection userPkgs = pkgSuite.getUserPkgs();
    	UserInfo userInfo = WebUtils.currentUser();
    	Iterator it = userPkgs.iterator();
    	UserPackage up = new UserPackage();
    	
    	while(it.hasNext()) {
    		UserPackage userPackage = (UserPackage) it.next();
    		if(userPackage.getAuthor().getId().equals(userInfo.getId())) {
    			up = userPackage;
    			break;
    		}
    	}
    	
    	// get cluster packages
    	Collection cp = pkgSuite.getClusteredPkgs();
    	
    	// get preferred package
    	ClusteredPackage pp = reportService.getPreferredClusteredPackage(packSuiteId);
    	
    	//get all vote suite stats
    	Collection vss = reportService.getVoteSuiteStats(packSuiteId);
    	
    	//Sets the Criteria References which contain criteria and grades.
    	request.setAttribute("summaries", summaries);
    	request.setAttribute("cr", cr);
    	request.setAttribute("up", up);
    	request.setAttribute("cp", cp);
    	request.setAttribute("pp", pp);
    	request.setAttribute("vss", vss);
    	request.setAttribute("concernSummary", repoSummary.getConcernSummary());
    	request.setAttribute("criteriaSummary", repoSummary.getCriteriaSummary());
    	request.setAttribute("executiveSummary", repoSummary.getExecutiveSummary());
    	request.setAttribute("packageSummary", repoSummary.getPackageSummary());
    	request.setAttribute("participantsSummary", repoSummary.getParticipantsSummary());
    	request.setAttribute("projectSummary", repoSummary.getProjectSummary());
    	request.setAttribute("critSuiteId", critSuiteId);
    	request.setAttribute("cctId", cctId);
    	request.setAttribute("packSuiteId", packSuiteId);
    	request.setAttribute("finalized", repoSummary.isFinalized());
    	request.setAttribute("concernStats", rs.getReportStatsConcerns());
    	request.setAttribute("evalStats", rs.getReportStatsEval());
    	request.setAttribute("counties", systemService.getAllCounties());
    	
        return mapping.findForward("report");
    }//execute()

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}



} //ReportAction
