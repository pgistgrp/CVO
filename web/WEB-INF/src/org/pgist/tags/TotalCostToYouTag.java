package org.pgist.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.funding.FundingSourceAltRef;
import org.pgist.packages.ClusteredPackage;
import org.pgist.packages.FundingSourceAlternativeDTO;
import org.pgist.packages.FundingSourceDTO;
import org.pgist.packages.PackageService;
import org.springframework.web.context.WebApplicationContext;


/**
 * 
 * @author kenny
 *
 */
public class TotalCostToYouTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String var = "";
    
    private ClusteredPackage pkg = null;
    
    
    public void setPkg(ClusteredPackage pkg) {
        this.pkg = pkg;
    }


    public void setVar(String var) {
        this.var = var;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        
        /*
         * Get the PackageService from spring
         */
        WebApplicationContext appContext = (WebApplicationContext) context.getServletContext()
            .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        
        PackageService packageService = (PackageService) appContext.getBean("packageService");
        
        try {
            float total = 0.0f;
            
            Long suiteId = null;
            
            for (FundingSourceAltRef fsRef : pkg.getFundAltRefs()) {
                suiteId = fsRef.getSourceRef().getSuite().getId();
                if (suiteId!=null) break;
            }
            
            System.out.println("@ suiteId ----> "+suiteId);
            
            if (suiteId!=null) {
                List<FundingSourceDTO> dtos = packageService.createPackageFundingDTOs(pkg, new Long(suiteId));
                System.out.println("@ dtos.size() ----> "+dtos.size());
                for (FundingSourceDTO dto : dtos) {
                    System.out.println("@====> "+dto);
                    for (FundingSourceAlternativeDTO alt : dto.getFundingSourceAlternatives()) {
                        System.out.println("@----> "+alt.getYourCost());
                        total += alt.getYourCost();
                    }
                }
            }
            
            context.setAttribute(var, total);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }//doTag()
    
    
}//class CostToYouTag
