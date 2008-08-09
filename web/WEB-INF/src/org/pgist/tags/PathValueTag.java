package org.pgist.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.sarp.cht.CategoryPath;
import org.pgist.sarp.vtt.VTTService;
import org.pgist.users.User;
import org.pgist.util.WebUtils;
import org.pgist.wfengine.util.Utils;
import org.springframework.web.context.WebApplicationContext;


/**
 * Get the corresponding CategoryPathValue object for the current user and current vttId.
 * 
 * @author kenny
 *
 */
public class PathValueTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String var;
    
    private CategoryPath path;
    
    private Long userId;
    
    public void setVar(String var) {
        this.var = var;
    }


    public void setPath(CategoryPath category) {
        this.path = category;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        
        /*
         * Get the Service from spring
         */
        WebApplicationContext appContext = (WebApplicationContext) context.getServletContext()
            .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        
        VTTService vttService = (VTTService) appContext.getBean("vttService");
        
        try {
            Object object = Utils.narrow(vttService.getCategoryPathValueByPathId(userId, path.getId()));
            context.setAttribute(var, object);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }//doTag()
    
    
}//class PathValueTag
