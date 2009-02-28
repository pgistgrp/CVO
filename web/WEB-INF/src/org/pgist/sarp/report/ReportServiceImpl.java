package org.pgist.sarp.report;

import org.pgist.sarp.drt.InfoObject;


/**
 * 
 * @author kenny
 *
 */
public class ReportServiceImpl implements ReportService {
    
    
    private ReportDAO reportDAO = null;
    
    
    public void setReportDAO(ReportDAO reportDAO) {
        this.reportDAO = reportDAO;
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


} //class ReportServiceImpl
