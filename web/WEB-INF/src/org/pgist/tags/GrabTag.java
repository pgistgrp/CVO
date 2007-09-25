package org.pgist.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.system.SystemService;
import org.pgist.wfengine.util.Utils;
import org.springframework.web.context.WebApplicationContext;


/**
 * 
 * @author kenny
 *
 */
public class GrabTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private Long id;
    
    private String name;
    
    private String var = "";
    
    
    public void setId(Long id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setVar(String var) {
        this.var = var;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        
        /*
         * Get the PackageService from spring
         */
        WebApplicationContext appContext = (WebApplicationContext) context.getServletContext()
            .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        
        SystemService systemService = (SystemService) appContext.getBean("systemService");
        
        try {
            Object object = Utils.narrow(systemService.getObject(name, id));
            context.setAttribute(var, object);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }//doTag()
    
    
}//class GrabTag
