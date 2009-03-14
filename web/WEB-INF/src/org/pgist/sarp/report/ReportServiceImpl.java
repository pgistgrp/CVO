package org.pgist.sarp.report;

import java.io.FileWriter;

import org.pgist.sarp.drt.InfoObject;
import org.pgist.util.JythonAPI;


/**
 * 
 * @author kenny
 *
 */
public class ReportServiceImpl implements ReportService {
    
    
    private ReportDAO reportDAO = null;
    
    private JythonAPI jythonAPI;
    
    
    public void setReportDAO(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
    }


    public void setJythonAPI(JythonAPI jythonAPI) {
        this.jythonAPI = jythonAPI;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    @Override
    public InfoObject publish(Long reportId, String title) throws Exception {
        Report report = reportDAO.getReportById(reportId);
        report.getBct();
        report.getBctDrt();
        report.getCht();
        report.getChtDrt();
        report.getCst();
        report.getCstDrt();
        report.getCht();
        report.getChtDrt();
        report.getVtt();
        report.getVttDrt();
        
        InfoObject infoObject = new InfoObject();
        infoObject.setTitle(title);
        infoObject.setTarget(report);
        
        reportDAO.save(infoObject);
        
        return infoObject;
    } //publish()


    @Override
    public void saveReportContent(Long id, String content) throws Exception {
        Report report = reportDAO.getReportById(id);
        
        String output = jythonAPI.getContextPath()+"/WEB-INF/jsp/sarp/report/report_"+report.getWorkflowId()+".html";
        FileWriter writer = new FileWriter(output);
        writer.write(content);
        writer.close();
    } //saveReportContent()


} //class ReportServiceImpl
