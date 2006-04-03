package org.pgist.cvo;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 *
 * @author kenny
 *
 */
public class CCTAgent {


    CCTService cctService = null;


    public void setCctService(CCTService cctService) {
        this.cctService = cctService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Map getCCTs(Map params)  throws Exception {
        Map map = new HashMap();
        
        Collection list = cctService.getCCTs();
        map.put("ccts", list);
        
        return map;
    }//getCCTs()
    
    
    /**
     * 
     * @param params
     * @return
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
    }//createCCT
    
    
}//class CCTAgent
