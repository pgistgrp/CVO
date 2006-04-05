package org.pgist.cvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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


    private CCTService cctService = null;
    
    private TagAnalyzer analyzer = null;


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
     * @param analyzer
     */
    public void setAnalyzer(TagAnalyzer analyzer) {
        this.analyzer = analyzer;
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
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>ccts - a list of CCT objects</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map getCCTs(Map params)  throws Exception {
        Map map = new HashMap();
        
        Collection list = cctService.getCCTs();
        map.put("successful", new Boolean(true));
        map.put("ccts", list);
        
        return map;
    }//getCCTs()
    
    
    /**
     * Create a new CCT instance with the given parameters.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>name - String, name of the new CCT instance</li>
     *           <li>purpose - String, purpose of the new CCT instance</li>
     *           <li>instruction - String, instruction of the new CCT instance</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
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
        
        map.put("successful", new Boolean(true));
        
        return map;
    }//createCCT()
    
    
    /**
     * Analyze the given concern, extract and return the recognized tags from it.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>cctId - long int, the current CCT instance id</li>
     *           <li>concern - String, the new concern which user entered</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>tags - a list of Tag objects</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map prepareConcern(Map params) throws Exception {
        Map map = new HashMap();
        
        //Temp for test
        List tags = new ArrayList();
        Tag tag = new Tag();
        tag.setDescription("tag1");
        tag.setName("tag1");
        tag.setStatus(Tag.STATUS_OFFICIAL);
        tags.add(tag);
        tag = new Tag();
        tag.setDescription("tag2");
        tag.setName("tag2");
        tag.setStatus(Tag.STATUS_OFFICIAL);
        tags.add(tag);
        
        map.put("tags", tags);
        map.put("successful", new Boolean(true));
        
        return map;
    }//prepareConcern()
    
    
    /**
     * Save the given concern and its tags to the system.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>cctId - long int, the current CCT instance id</li>
     *           <li>concern - String, the new concern which user entered</li>
     *           <li>tags - String array, tags provided by current user</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>concern - A newly created Concern object</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map saveConcern(Map params) throws Exception {
        Map map = new HashMap();
        
        Long cctId = new Long((String) params.get("cctId"));
        String concern = (String) params.get("concern");
        String[] tags = (String[]) params.get("tags");
        CCT cct = cctService.getCCTById(cctId);
        if (cct!=null) {
            Concern concernObj = cctService.createConcern(cct, concern, tags);
            map.put("concern", concernObj);
        }
        map.put("successful", new Boolean(true));
        
        return map;
    }//saveConcern()
    
    
    /**
     * Get concerns conform to given conditions.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>cctId - long int, the current CCT instance id</li>
     *           <li>count - int, number of concerns to be extracted</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>concerns - a list of Concern objects</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map getConcerns(Map params) throws Exception {
        Map map = new HashMap();
        
        map.put("successful", new Boolean(true));
        
        return map;
    }//getConcerns()
    
    
    /**
     * Get the tag cloud of the current CCT instance.
     * 
     * @param params A map contains:<br>
     *         <ul>
     *           <li>cctId - long int, the current CCT instance id</li>
     *           <li>type - int
     *             <ul>
     *               <li>type==0, search top count tags which are the hotest</li>
     *               <li>type==1, search tags which are over a specific threshhold</li>
     *             </ul>
     *           </li>
     *           <li>count - valid when type==0</li>
     *           <li>threshhold - valid when type==1</li>
     *         </ul>
     * @return A map contains:<br>
     *         <ul>
     *           <li>successful - a boolean value denoting if the operation succeeds</li>
     *           <li>tags - a list of Tag objects</li>
     *           <li>reason - reason why operation failed (valid when successful==false)</li>
     *         </ul>
     * @throws Exception
     */
    public Map getTagCloud(Map params) throws Exception {
        Map map = new HashMap();
        
        map.put("successful", new Boolean(true));
        
        return map;
    }//getTagCloud()
    
    
}//class CCTAgent
