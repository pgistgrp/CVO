package org.pgist.packages.knapsack;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.util.WebUtils;


/**
 * 
 * @author kenny
 *
 */
public class GATestingAction extends Action {
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws Exception {
        String save = request.getParameter("save");
        String type = request.getParameter("type");
        
        String scriptPath = "";
        if ("funding".equalsIgnoreCase(type)) {
            request.setAttribute("type", "Funding");
            scriptPath = WebUtils.getContextPath()+"/WEB-INF/config/knapsack-funding.bsh";
        } else if ("project".equalsIgnoreCase(type)) {
            request.setAttribute("type", "Project");
            scriptPath = WebUtils.getContextPath()+"/WEB-INF/config/knapsack-project.bsh";
        } else {
            return mapping.findForward("error");
        }
        
        if ("true".equalsIgnoreCase(save)) {
            String beanshell = request.getParameter("beanshell");
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new BufferedWriter(new FileWriter(scriptPath)));
                writer.write(beanshell);
                request.setAttribute("succeed", true);
            } catch (Exception e) {
                request.setAttribute("succeed", false);
            } finally {
                if (writer!=null) writer.close();
            }
        }
        
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(scriptPath);
            int size = stream.available();
            byte[] array = new byte[size];
            stream.read(array);
            request.setAttribute("beanshell", new String(array, "utf-8"));
        } finally {
            if (stream!=null) stream.close();
        }
        
        return mapping.findForward("view");
    }//execute()
    
    
}//class GATestingAction
