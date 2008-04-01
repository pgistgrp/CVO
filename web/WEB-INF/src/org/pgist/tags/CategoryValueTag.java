package org.pgist.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.sarp.cst.CategoryReference;
import org.pgist.sarp.vtt.VTTService;
import org.pgist.wfengine.util.Utils;
import org.springframework.web.context.WebApplicationContext;


/**
 * 
 * @author kenny
 *
 */
public class CategoryValueTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String var;
    
    private CategoryReference category;
    
    
    public void setVar(String var) {
        this.var = var;
    }


    public void setCategory(CategoryReference category) {
        this.category = category;
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
            Object object = Utils.narrow(vttService.getCategoryValueById(category.getId()));
            context.setAttribute(var, object);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }//doTag()
    
    
}//class CategoryValueTag
