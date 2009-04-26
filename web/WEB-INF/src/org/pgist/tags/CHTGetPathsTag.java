package org.pgist.tags;

import java.io.IOException;
import java.util.ArrayList;
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
            boolean myvote = false;
            
            if ("myvote".equalsIgnoreCase(orderby)) {
                orderby = null;
                myvote = true;
            }
            
            List<CategoryPath> list = chtService.getPathsByChtId(chtId, orderby);
            
            if (myvote) {
                List<CategoryPath> list1 = new ArrayList<CategoryPath>();
                List<CategoryPath> list2 = new ArrayList<CategoryPath>();
                
                for (CategoryPath cp : list) {
                    if (PgistELFunctions.voted(context, cp)) {
                        list2.add(cp);
                    } else {
                        list1.add(cp);
                    }
                }
                
                list.clear();
                list.addAll(list1);
                list.addAll(list2);
            }
            
            context.setAttribute(var, list);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }//doTag()
    
    
} //class CHTGetPathsTag
