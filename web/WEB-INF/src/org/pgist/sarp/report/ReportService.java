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
    
}
