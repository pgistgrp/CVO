package org.pgist.cvo;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * DWR AJAX Agent class.<br>
 * Provide AJAX services to client programs.<br>
 * In this document, all the NON-AJAX methods are marked out. So all methods without such
 * a description <span style="color:red;">ARE</span> AJAX service methods.<br>
 * 
 * @author kenny
 *
 */
public class CCTAgent {


    CCTService cctService = null;


    /**
     * This is not an AJAX service method.
     * 
     * @param cctService
     */
    public void setCctService(CCTService cctService) {
        this.cctService = cctService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * Get all the CCT instances from the database.
     * 
     * @param params An empty map.
     * @return A map contains:<br>
     *         <ul>
     *           <li>result - a boolean value to denote if the operation succeeds</li>
     *           <li>ccts   - a list of CCT objects</li>
     *         </ul>
     * @throws Exception
     */
    public Map getCCTs(Map params)  throws Exception {
        Map map = new HashMap();
        
        Collection list = cctService.getCCTs();
        map.put("result", new Boolean(true));
        map.put("ccts", list);
        
        return map;
    }//getCCTs()
    
    
    /**
     * Create a new CCT instance with the given parameters.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>name : name of the new CCT instance</li>
     *           <li>purpose : purpose of the new CCT instance</li>
     *           <li>instruction : instruction of the new CCT instance</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>result - a boolean value to denote if the operation succeeds</li>
     *         </ul>
     * @throws Exception
     */
    public Map createCCT(Map params) throws Exception {
        Map map = new HashMap();
        
        CCT cct = new CCT();
        cct.setName((String) params.get("name"));
        cct.setPurpose((String) params.get("purpose"));
        cct.setInstruction((String) params.get("instruction"));
        cct.setCreateTime(new Date());
        
        cctService.save(cct);
        
        map.put("result", "true");
        
        return map;
    }//createCCT()
    
    
    /**
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Map prepareConcern(Map params) throws Exception {
        Map map = new HashMap();
        
        map.put("result", "true");
        
        return map;
    }//prepareConcern()
    
    
    /**
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Map saveConcern(Map params) throws Exception {
        Map map = new HashMap();
        
        map.put("result", "true");
        
        return map;
    }//saveConcern()
    
    
    /**
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Map getConcerns(Map params) throws Exception {
        Map map = new HashMap();
        
        map.put("result", "true");
        
        return map;
    }//getConcerns()
    
    
    /**
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Map getTagCloud(Map params) throws Exception {
        Map map = new HashMap();
        
        map.put("result", "true");
        
        return map;
    }//getTagCloud()
    
    
}//class CCTAgent
