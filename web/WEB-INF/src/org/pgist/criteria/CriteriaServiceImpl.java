package org.pgist.criteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;

import org.pgist.cvo.CCT;
import org.pgist.cvo.CCTDAO;
import org.pgist.cvo.CCTService;
import org.pgist.cvo.CategoryReference;
import org.pgist.cvo.Theme;


/**
 * 
 * @author kenny
 *
 */
public class CriteriaServiceImpl implements CriteriaService {
    
    
    private CriteriaDAO criteriaDAO;
    
    
    private CCTDAO cctDAO;
    

	public void setCriteriaDAO(CriteriaDAO criteriaDAO) {
        this.criteriaDAO = criteriaDAO;
    }


	public void setCctDAO(CCTDAO cctDAO) {
		this.cctDAO = cctDAO;
	}
    

	public Collection getCriterias() throws Exception {
        return null;
    }//getCriterias()
    
    
    public Criteria addCriterion(String name, Long cctId, Set themes,  Set objectives, String na) throws Exception {
    	CCT cct = criteriaDAO.getCct(cctId);  
    	
    	return criteriaDAO.addCriterion(name, cct, themes, objectives, na);
    }//addCriterion()
    
    
    public void deleteCriterion(Long id) throws Exception {
    	criteriaDAO.deleteCriterion(id);
    }//deleteCriterion
    
    
    public void editCriterion(Criteria c, String name, Long cctId, Set themes, Set objectives, String na) throws Exception {
    	CCT cct = criteriaDAO.getCct(cctId);  
    	
    	criteriaDAO.editCriterion(c, name, cct, themes, objectives, na);
    }//editCriterion()
    
    
    public Criteria getCriterionById(Long id) throws Exception {
    	return criteriaDAO.getCriterionById(id);
    }//getCriterionByID()
    
    
    public Collection getAllCriterion(Long cctId) throws Exception {
    	CCT cct = criteriaDAO.getCct(cctId);  
    	return criteriaDAO.getAllCriterion(cct);
    }//getAllCriterion()
    
    
    public Objective addObjective(String description) throws Exception {
    	return criteriaDAO.addObjective(description);
    }//addCriterion()
    
    
    public List getThemes(Long cctId) throws Exception {
    	CCT cct = criteriaDAO.getCct(cctId);            
        Set refs = cct.getRootCategory().getChildren();          
        List themes = new ArrayList(refs.size());           
        Map themesMap = new HashMap();
        
        for (CategoryReference ref : (Set<CategoryReference>) refs) {
            Theme theme = ref.getTheme();
            themesMap.put(ref.getId(), theme);
            themes.add(theme);
        }//for
        return themes;
    }//getThemes()
    
    
    public Set getThemeObjects(String[] themeIdList) throws Exception {
        return criteriaDAO.getThemeObjects(themeIdList);
    }//getThemesObjects()
    
    
    public Set getObjectiveObjects(String[] objectiveIdList) throws Exception {
        return criteriaDAO.getObjectiveObjects(objectiveIdList);
    }//getObjectiveObjects()
    
    
    public void deleteObjective(Long id) throws Exception {
    	criteriaDAO.deleteObjective(id);
    }//deleteObjective
    
   
    public Collection getObjectives() throws Exception {
    	return criteriaDAO.getObjectives();
    }//getObjectives()
    
    
    public void setWeight(Long cctId, Long critId, int weight) throws Exception {
    	CCT cct = criteriaDAO.getCct(cctId);
    	Criteria criteria = criteriaDAO.getCriterionById(critId);
    	
    	criteriaDAO.setWeight(cct, criteria, weight);
    }//setWeight()
    
    
    public Set getWeights(Long cctId) throws Exception {
    	CCT cct = criteriaDAO.getCct(cctId);
    	
    	return criteriaDAO.getWeights(cct);
    }//getWeights()
    
    
}//class CriteriaServiceImpl
