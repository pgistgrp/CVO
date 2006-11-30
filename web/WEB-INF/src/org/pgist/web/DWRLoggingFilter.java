package org.pgist.web;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.AjaxFilter;
import org.directwebremoting.AjaxFilterChain;
import org.directwebremoting.WebContextFactory;


/**
 * 
 * @author kenny
 *
 */
public class DWRLoggingFilter implements AjaxFilter {
    
    
    public Object doFilter(Object obj, Method method, Object[] params, AjaxFilterChain chain) throws Exception {
        Object result = null;
        try {
            result = chain.doFilter(obj, method, params);
        } finally {
            HttpServletRequest request = WebContextFactory.get().getHttpServletRequest();
            if (result instanceof Map) {
                Map map = (Map) result;
                Object success = map.get("successful");
                if ("true".equals(success) || (new Boolean(true)).equals(success)) {
                    request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
                } else {
                    request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
                }
            } else if (result==null) {
                request.setAttribute("PGIST_SERVICE_SUCCESSFUL", false);
            } else {
                request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
            }
        }
        return result;
    }//doFilter()
    
    
}//class DWRLoggingFilter
