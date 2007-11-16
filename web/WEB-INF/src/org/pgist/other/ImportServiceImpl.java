package org.pgist.other;

import java.io.File;
import java.util.Collection;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.pgist.criteria.CriteriaDAO;
import org.pgist.cvo.CCTDAO;
import org.pgist.packages.PackageDAO;
import org.pgist.projects.ProjectDAO;
import org.pgist.util.WebUtils;


/**
 * The service class for importing situation template.
 * 
 * @author kenny
 */
public class ImportServiceImpl implements ImportService {
    
    
    private CCTDAO cctDAO;
    
    private CriteriaDAO criteriaDAO;
    
    private ProjectDAO projectDAO;
    
    private PackageDAO packageDAO;
    
    private ImportDAO importDAO;
    
    
    public CCTDAO getCctDAO() {
        return cctDAO;
    }
    
    
    public void setCctDAO(CCTDAO cctDAO) {
        this.cctDAO = cctDAO;
    }


    public CriteriaDAO getCriteriaDAO() {
        return criteriaDAO;
    }


    public void setCriteriaDAO(CriteriaDAO criteriaDAO) {
        this.criteriaDAO = criteriaDAO;
    }


    public PackageDAO getPackageDAO() {
        return packageDAO;
    }


    public void setPackageDAO(PackageDAO packageDAO) {
        this.packageDAO = packageDAO;
    }


    public ProjectDAO getProjectDAO() {
        return projectDAO;
    }


    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }


    public void setImportDAO(ImportDAO importDAO) {
        this.importDAO = importDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public Collection getTemplates() throws Exception {
        return importDAO.getTemplates();
    }//getTemplates()
    
    
    public void importTemplate(Long templateId) throws Exception {
        SituationTemplate template = importDAO.getTemplateById(templateId);
        
        if (template==null) throw new Exception("can't find template with id: "+templateId);
        
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(WebUtils.getContextPath(), template.getPath()));
        Element root = document.getRootElement();
        
        
    }//importTemplate()


}//ImportService
