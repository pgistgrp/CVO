package org.pgist.sarp.report;

import org.pgist.system.BaseDAOImpl;


/**
 * 
 * @author kenny
 *
 */
public class ReportDAOImpl extends BaseDAOImpl implements ReportDAO {

    
    @Override
    public Report getReportById(Long reportId) throws Exception {
        return (Report) load(Report.class, reportId);
    }
    
    
} //class ReportDAOImpl
