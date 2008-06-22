package org.pgist.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.sarp.cht.CHTService;
import org.pgist.sarp.cht.CategoryPath;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author kenny
 *
 */
public class CHTGetPathsTag extends SimpleTagSupport {
    
    
    private String var = "";
    
    private Long chtId;
    
    private String orderby;
    
    
    public void setVar(String var) {
        this.var = var;
    }


    public void setChtId(Long chtId) {
        this.chtId = chtId;
    }
    
    
    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }


    public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        
        /*
         * Get the CHTService from spring
         */
        WebApplicationContext appContext = (WebApplicationContext) context.getServletContext()
            .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        
        CHTService chtService = (CHTService) appContext.getBean("chtService");
        
        try {
            List<CategoryPath> list = chtService.getPathsByChtId(chtId, orderby);
            context.setAttribute(var, list);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }//doTag()
    
    
} //class CHTGetPathsTag
