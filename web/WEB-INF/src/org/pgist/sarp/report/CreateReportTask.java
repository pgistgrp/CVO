package org.pgist.sarp.report;

import org.pgist.sarp.bct.BCTService;
import org.pgist.sarp.cht.CHTService;
import org.pgist.sarp.cst.CSTService;
import org.pgist.sarp.drt.DRTService;
import org.pgist.sarp.vtt.VTTService;
import org.pgist.util.JythonAPI;
import org.pgist.wfengine.EnvironmentInOuts;
import org.pgist.wfengine.WorkflowInfo;
import org.pgist.wfengine.WorkflowTask;
import org.python.util.PythonInterpreter;


/**
 * Automatic workflow task for creating report.
 * 
 * @author kenny
 */
public class CreateReportTask implements WorkflowTask {
    
    
    public static final String IN_BCT_ID = "bct_id";
    
    public static final String IN_CST_ID = "cst_id";
    
    public static final String IN_CHT_ID = "cht_id";
    
	public static final String IN_VTT_ID = "vtt_id";
	
    public static final String IN_BCT_DRT_ID = "bct_oid";
    
    public static final String IN_CST_DRT_ID = "cst_oid";
    
    public static final String IN_CHT_DRT_ID = "cht_oid";
    
    public static final String IN_VTT_DRT_ID = "vtt_oid";
    
    public static final String OUT_REPORT_ID = "report_id";
    
    
    private JythonAPI jythonAPI;
    
    private BCTService bctService;
    
    private CSTService cstService;
    
    private CHTService chtService;
    
    private VTTService vttService;
    
    private DRTService drtService;
    
    
    public void setBctService(BCTService bctService) {
        this.bctService = bctService;
    }
    
    
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
    }
    
    
    public void setChtService(CHTService chtService) {
        this.chtService = chtService;
    }
    
    
    public void setVttService(VTTService vttService) {
        this.vttService = vttService;
    }
    
    
    public void setDrtService(DRTService drtService) {
        this.drtService = drtService;
    }
    
    
    public void setJythonAPI(JythonAPI jythonAPI) {
        this.jythonAPI = jythonAPI;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void execute(WorkflowInfo info, EnvironmentInOuts inouts) throws Exception {
        System.out.println("\n@ CreateReportTask.execute()\n");
        
        Long bctId = new Long(inouts.getIntValue(IN_BCT_ID));
        Long cstId = new Long(inouts.getIntValue(IN_CST_ID));
        Long chtId = new Long(inouts.getIntValue(IN_CHT_ID));
        Long vttId = new Long(inouts.getIntValue(IN_VTT_ID));
        Long bctDrtId = new Long(inouts.getIntValue(IN_BCT_DRT_ID));
        Long cstDrtId = new Long(inouts.getIntValue(IN_CST_DRT_ID));
        Long chtDrtId = new Long(inouts.getIntValue(IN_CHT_DRT_ID));
        Long vttDrtId = new Long(inouts.getIntValue(IN_VTT_DRT_ID));
        
        Report report = new Report();
        
        report.setWorkflowId(info.getWorkflow().getId());
        report.setBct(bctService.getBCTById(bctId));
        report.setCst(cstService.getCSTById(cstId));
        report.setCht(chtService.getCHTById(chtId));
        report.setVtt(vttService.getVTTById(vttId));
        report.setBctDrt(drtService.getInfoObjectByTargetId(bctDrtId));
        report.setCstDrt(drtService.getInfoObjectByTargetId(cstDrtId));
        report.setChtDrt(drtService.getInfoObjectByTargetId(chtDrtId));
        report.setVttDrt(drtService.getInfoObjectByTargetId(vttDrtId));
        
        drtService.save(report);
        
        try {
            PythonInterpreter interpreter = jythonAPI.getInterpreter();
            interpreter.set("bctService", bctService);
            interpreter.set("cstService", cstService);
            interpreter.set("chtService", chtService);
            interpreter.set("vttService", vttService);
            interpreter.set("drtService", drtService);
            interpreter.set("output", jythonAPI.getContextPath()+"/WEB-INF/jsp/sarp/report/report_"+report.getWorkflowId()+".html");
            jythonAPI.run(interpreter, "Report.py");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        inouts.setIntValue(OUT_REPORT_ID, report.getId().intValue());
    }//execute()
    
    
}//class CreateReportTask
