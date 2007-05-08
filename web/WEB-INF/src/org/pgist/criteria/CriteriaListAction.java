package org.pgist.criteria;

import java.util.Collection;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.criteria.CriteriaService;
import org.pgist.cvo.CCTForm;

public class CriteriaListAction extends Action {
	
	  private CriteriaService criteriaService = null;
	    
	    
	    public CriteriaListAction() {
	    }
	    
	    
	    public void setCriteriaService(CriteriaService criteriaService) {
	        this.criteriaService = criteriaService;
	    }

	    
	    public ActionForward execute(
	            ActionMapping mapping,
	            ActionForm form,
	            javax.servlet.http.HttpServletRequest request,
	            javax.servlet.http.HttpServletResponse response
	    ) throws java.lang.Exception {

	    	Collection ccts = criteriaService.getCCTs();
	    	
	    	request.setAttribute("ccts", ccts);
	    	
	    	
	        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
	        
	        return mapping.findForward("list");
	    }//execute()
	
	
} //CriteriaListAction();
