package org.pgist.cvo;


/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods
 * <span style="color:red;">without</span> such a description
 * <span style="color:red;">ARE</span> AJAX service methods.<br>
 * 
 * @author kenny
 *
 */
public class SDCAgent {
    
    
    private CCTService cctService = null;
    
    private CSTService cstService = null;

    private SDCService sdcService = null;
    

    /**
     * This is not an AJAX service method.
     *
     * @param cctService
     */
    public void setCctService(CCTService cctService) {
        this.cctService = cctService;
    }


    /**
     * This is not an AJAX service method.
     *
     * @param cstService
     */
    public void setCstService(CSTService cstService) {
        this.cstService = cstService;
    }


    /**
     * This is not an AJAX service method.
     *
     * @param sdcService
     */
    public void setSdcService(SDCService sdcService) {
        this.sdcService = sdcService;
    }


    /*
     * ------------------------------------------------------------------------
     */


}//class SDCAgent
