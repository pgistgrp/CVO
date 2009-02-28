package org.pgist.sarp.report;

import org.pgist.system.BaseDAO;

/**
 * @author kenny
 *
 */
public interface ReportDAO extends BaseDAO {

    Report getReportById(Long reportId) throws Exception;
    
}// interface ReportDAO
