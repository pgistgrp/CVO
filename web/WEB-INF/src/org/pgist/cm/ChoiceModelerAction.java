package org.pgist.cm;

import java.util.List;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author kenny
 *
 */
public class ChoiceModelerAction extends Action {
    
    
    private ChoiceModelerService choiceModelerService;
    
    
    public void setChoiceModelerService(ChoiceModelerService choiceModelerService) {
        this.choiceModelerService = choiceModelerService;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        String name = request.getParameter("name");
        
        if (name==null || name.length()==0) {
            return mapping.findForward("main");
        }
        
        String greeting = choiceModelerService.echo(name);
        
        request.setAttribute("name", name);
        request.setAttribute("greeting", greeting);
        
        List list = choiceModelerService.getList();
        
        request.setAttribute("list", list);
        
        int[] x = new int[] { 10, 11, 12 };
        
        int[] y = choiceModelerService.getArray(x);
        
        for (int i=0; i<y.length; i++) {
            System.out.println("----> "+y[i]);
        }//for i
        
        choiceModelerService.test();
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("main");
    }//execute()
    
    
}//class ChoiceModelerAction
