package org.pgist.sarp.vtt;

import org.pgist.sarp.cht.CHTService;
import org.pgist.search.SearchHelper;
import org.pgist.system.SystemService;


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
public class VTTAgent {
    
    
    private CHTService chtService = null;
    
    private VTTService vttService = null;
    
    private SystemService systemService = null;
    
    private SearchHelper searchHelper;
    
    
    /**
     * This is not an AJAX service method.
     *
     * @param vttService
     */
    public void setVttService(VTTService vttService) {
        this.vttService = vttService;
    }


    /**
     * This is not an AJAX service method.
     *
     * @param chtService
     */
    public void setChtService(org.pgist.sarp.cht.CHTService chtService) {
        this.chtService = chtService;
    }


    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }


    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }


    /*
     * ------------------------------------------------------------------------
     */


}//class VTTAgent
