package org.pgist.criteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;

import org.pgist.cvo.CCT;
import org.pgist.cvo.CCTDAO;
import org.pgist.cvo.CCTService;
import org.pgist.cvo.CategoryReference;
import org.pgist.cvo.Theme;
import org.pgist.discussion.InfoObject;
import org.pgist.discussion.InfoStructure;


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
    	CCT cct = cctDAO.getCCTById(cctId);  
    	
    	return criteriaDAO.addCriterion(name, cct, themes, objectives, na);
    }//addCriterion()
    
    
    public void deleteCriterion(Long id) throws Exception {
    	criteriaDAO.deleteCriterion(id);
    }//deleteCriterion
    
    
    public void editCriterion(Criteria c, String name, Long cctId, Set themes, Set objectives, String na) throws Exception {
    	CCT cct = cctDAO.getCCTById(cctId);  
    	
    	criteriaDAO.editCriterion(c, name, cct, themes, objectives, na);
    }//editCriterion()
    
    
    public Criteria getCriterionById(Long id) throws Exception {
    	return criteriaDAO.getCriterionById(id);
    }//getCriterionByID()
    
    
    public Collection getAllCriterion(Long cctId) throws Exception {
    	CCT cct = cctDAO.getCCTById(cctId);  
    	return criteriaDAO.getAllCriterion(cct);
    }//getAllCriterion()
    
    
    public Objective addObjective(String description) throws Exception {
    	return criteriaDAO.addObjective(description);
    }//addCriterion()
    
    
    public List getThemes(Long cctId) throws Exception {
    	CCT cct = cctDAO.getCCTById(cctId);           
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
    	CCT cct = cctDAO.getCCTById(cctId);
    	Criteria criteria = criteriaDAO.getCriterionById(critId);
    	
    	criteriaDAO.setWeight(cct, criteria, weight);
    }//setWeight()
    
    
    public Set getWeights(Long cctId) throws Exception {
    	CCT cct = cctDAO.getCCTById(cctId);
    	
    	return criteriaDAO.getWeights(cct);
    }//getWeights()
    
    
    public void publish(Long cctId, String[] criteriaIdList) throws Exception {

        Date date = new Date();
        
        InfoStructure structure = new InfoStructure();
        structure.setType("sdcrit");
        structure.setRespTime(date);
        structure.setCctId(cctId);
        criteriaDAO.save(structure);
        
        
        for (Criteria crit : (Set<Criteria>) criteriaDAO.getCriterions(criteriaIdList)) {
        	crit.getCct();
        	crit.getId();
        	crit.getClass();
        	crit.getMoes();
        	crit.getNa();
        	crit.getName();
        	crit.getObjectives();
        	crit.getThemes();
        	
            
            InfoObject obj = new InfoObject();
            obj.setObject(crit);
            obj.setRespTime(date);
            criteriaDAO.save(obj);
            
            structure.getInfoObjects().add(obj);
            
            //cstDAO.publish(structure, obj, ref);
        }//for ref
        
        criteriaDAO.save(structure);
    }//publish()
    
    
}//class CriteriaServiceImpl
