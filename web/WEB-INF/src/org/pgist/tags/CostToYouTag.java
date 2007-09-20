package org.pgist.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.packages.PackageService;
import org.pgist.packages.UserPackage;
import org.pgist.util.WebUtils;
import org.springframework.web.context.WebApplicationContext;


/**
 * 
 * @author kenny
 *
 */
public class CostToYouTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String suiteId = "";
    
    private String var = "";
    
    
    public void setSuiteId(String suiteId) {
        this.suiteId = suiteId;
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
        
        PackageService packageService = (PackageService) appContext.getBean("packageService");
        
        UserPackage uPkg = new UserPackage();
        
        try {
            packageService.calcUserValues(uPkg, WebUtils.currentUser(), new Long(suiteId));
        } catch (Exception e) {
            throw new JspException(e);
        }
        
        getJspContext().setAttribute(var, uPkg);
    }//doTag()
    
    
}//class CostToYouTag
