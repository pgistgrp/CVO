package org.pgist.workflow.modeling;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.WebContextFactory;


/**
 * 
 * @author kenny
 *
 */
public class WFModelingAgent {
    
    
    public static final int TEMPLATE_TYPE_SITUATION = 0;
    
    public static final int TEMPLATE_TYPE_MEETING   = 1;
    
    public static final int TEMPLATE_TYPE_PMETHOD   = 2;
    
    public static final int CONNECTOR_BRANCH        = 0;
    
    public static final int CONNECTOR_JOIN          = 1;
    
    public static final int CONNECTOR_SWITCH        = 2;
    
    public static final int CONNECTOR_ENDSWITCH     = 3;
    
    
    /**
     * Generate a XML string to express template of the given type and given id as a tree.
     * This tree should be displayed at the left panel of Modeling Screen.
     * 
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    public Map getDefinitionAsTree(HttpServletRequest request, HttpServletResponse response, int type, Long id) throws Exception {
        Map map = new HashMap();
        
        map.put("html", WebContextFactory.get().forwardToString("/test.jsp"));
        map.put("successful", new Boolean(true));
        return map;
    }//getDefinitionAsTree()
    
    
    /**
     * Generate a HTML string to express the visual definition of the give template.
     * This definition should be displayed at the bottom-right panel of Modeling Screen.
     * 
     * @param type
     * @param id
     * @return
     * @throws Exception
     */
    public Map getDefinitionSheet(HttpServletRequest request, HttpServletResponse response, int type, Long id) throws Exception {
        Map map = new HashMap();
        
        map.put("html", WebContextFactory.get().forwardToString("/test.jsp"));
        map.put("successful", new Boolean(true));
        return map;
    }//getDefinitionSheet()
    
    
    /**
     * Generate a HTML string to express all the templates of the given type.
     * This list should be displayed at the top panel of Modeling Screen.
     * 
     * @param type
     * @return
     * @throws Exception
     */
    public Map getTemplateList(HttpServletRequest request, HttpServletResponse response, int type) throws Exception {
        Map map = new HashMap();
        
        map.put("html", WebContextFactory.get().forwardToString("/test.jsp"));
        map.put("successful", new Boolean(true));
        return map;
    }//getTemplateList()
    
    
    /**
     * Create a new template.
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Map createTemplate(HttpServletRequest request, HttpServletResponse response, Map params) throws Exception {
       Map map = new HashMap();
       
       String type = (String) params.get("type");
       
       try {
           if ((""+TEMPLATE_TYPE_SITUATION).equals(type)) {//situation template
               
           } else if ((""+TEMPLATE_TYPE_MEETING).equals(type)) {//meeting template
               
           } else if ((""+TEMPLATE_TYPE_PMETHOD).equals(type)) {//pmethod template
               
           } else {
               throw new Exception("Type must be valid when creating template!");
           }
       } catch(Exception e) {
           map.put("successful", new Boolean(false));
           map.put("reason", e.getMessage());
       }
       
       map.put("successful", new Boolean(true));
       return map;
    }//createTemplate()
    
    
    /**
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Map insertEntity(HttpServletRequest request, HttpServletResponse response, Map params) throws Exception {
        Map map = new HashMap();
        
        map.put("html", WebContextFactory.get().forwardToString("/test.jsp"));
        map.put("successful", new Boolean(true));
        return map;
    }//insertEntity()
    
    
    /**
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Map insertConnector(HttpServletRequest request, HttpServletResponse response, Map params) throws Exception {
        Map map = new HashMap();
        
        map.put("html", WebContextFactory.get().forwardToString("/test.jsp"));
        map.put("successful", new Boolean(true));
        return map;
    }//insertConnector()
    
    
}//class WFModelingAgent
