package org.pgist.discussion;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.PageSetting;


/**
 * SDAction is the entry for a specific thread of Structured Discussion instance.<br>
 * 
 * SDAction accepts paramters from the request:
 *   <ul>
 *     <li>isid - int, the id of a InfoStructure object</li>
 *     <li>ioid - int, the id of a InfoObject object. Optional, can be empty if the discussion is on the whole structure.</li>
 *     <li>pid - int, the id of a DiscussionPost object</li>
 *     <li>page - int, current page number. Optional, default is 1.</li>
 *     <li>count - int, the number of reply posts to be showned in one page. Optional, default is -1, means show all posts.</li>
 *   </ul>
 *   
 * When executing, SDAction forwards to the jsp file mapped to name "main" in
 * struts-config.xml to render a HTML page. In this page, the following variables are
 * available (in request/attribute):
 *   <ul>
 *     <li>structure - an InfoStructure object</li>
 *     <li>object - an InfoObject object. If the discussion is on the whole structure, object is null.</li>
 *     <li>post - a DiscussionPost object</li>
 *     <li>setting - a PageSetting object</li>
 *     <li>replies - A list of DiscussionPost objects.</li>
 *   </ul>
 * 
 * @author kenny
 */
public class SDThreadAction extends Action {
    
    
    /**
     * Spring injected service
     */
    private SDService sdService;
    
    
    public void setSdService(SDService sdService) {
        this.sdService = sdService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws Exception {
        /*
         * isid of a InfoStructure object
         */
        Long isid = new Long(request.getParameter("isid"));
        
        /*
         * Load the specified InfoStructure object from database.
         */
        InfoStructure structure = sdService.getInfoStructureById(isid);
        
        if (structure==null) throw new Exception("InfoStructure with id "+isid+" is not found.");
        
        request.setAttribute("structure", structure);
        
        /*
         * ioid of a InfoObject object
         */
        String ioidStr = request.getParameter("ioid");
        if (ioidStr!=null && !"".equals(ioidStr.trim())) {
            Long ioid = new Long(ioidStr);
            
            /*
             * Load the specified InfoStructure object from database.
             */
            InfoObject infoObj = sdService.getInfoObjectById(ioid);
            
            if (infoObj==null) throw new Exception("InfoObject with id "+ioid+" is not found.");
            
            request.setAttribute("object", infoObj);
        } else {
            request.setAttribute("object", null);
        }
        
        Long pid = new Long(request.getParameter("pid"));
        DiscussionPost post = sdService.getPostById(pid);
        
        if (post!=null) {
            request.setAttribute("post", post);
            
            /*
             * increase the view times
             */
            sdService.increaseViews(post);
            
            PageSetting setting = new PageSetting();
            setting.setPage(request.getParameter("page"));
            setting.setRowOfPage(request.getParameter("count"));
            
            Collection replies = sdService.getReplies(post, setting);
            request.setAttribute("replies", replies);
            request.setAttribute("setting", setting);
            
            return mapping.findForward("main");
        }
        
        return mapping.findForward("error");
    }//execute()
    
    
}//class SDThreadAction
