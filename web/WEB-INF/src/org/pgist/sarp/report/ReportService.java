package org.pgist.sarp.report;

import org.pgist.sarp.drt.InfoObject;

/**
 * @author kenny
 *
 */
public interface ReportService {

    /**
     * @param reportId
     * @param property
     * @return
     */
    InfoObject publish(Long reportId, String title) throws Exception;

    /**
     * @param id
     * @param content
     */
    void saveReportContent(Long id, String content) throws Exception;
    
}
