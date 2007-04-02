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
    
    
    public Criteria addCriterion(Boolean bool_themes, Boolean bool_objectives, String name, Long critSuite, Set themes,  Set objectives, String na) throws Exception {
    		
    	return criteriaDAO.addCriterion(bool_themes, bool_objectives, name, critSuite, themes, objectives, na);
    }//addCriterion()
    
    
    public void deleteCriterion(Long id) throws Exception {
    	criteriaDAO.deleteCriterion(id);
    }//deleteCriterion
    
    
    public void editCriterion(Boolean bool_themes, Boolean bool_objectives, Criteria c, String name, Set themes, Set objectives, String na) throws Exception {	
    	criteriaDAO.editCriterion(bool_themes, bool_objectives, c, name, themes, objectives, na);
    }//editCriterion()
    
    
    public Criteria getCriterionById(Long id) throws Exception {
    	return criteriaDAO.getCriterionById(id);
    }//getCriterionById()
    
    
    public Set getAllCriterion(Long critSuiteId) throws Exception {  
    	return criteriaDAO.getAllCriterion(critSuiteId);
    }//getAllCriterion()

    
    public Collection getAllCriterion() throws Exception { 
    	return criteriaDAO.getAllCriterion();
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
    
    
    public Map getWeights(Long critSuiteId) throws Exception {
    	return criteriaDAO.getWeights(critSuiteId);
    }//getWeights()
    
    
    public void publish(Long cctId) throws Exception {
    	
        Date date = new Date();
        
        InfoStructure structure = new InfoStructure();
        structure.setType("sdcrit");
        structure.setTitle("Step 2: SD Criteria");
        structure.setRespTime(date);
        //structure.setCctId(cctId);
        criteriaDAO.save(structure);
        
        for (Criteria crit : (Collection<Criteria>) criteriaDAO.getAllCriterion()) {
        	//crit.getCct();
        	crit.getId();
        	crit.getClass();
        	crit.getNa();
        	crit.getName();
        	crit.getObjectives();
        	crit.getThemes();
        	
            
            InfoObject obj = new InfoObject();
            obj.setObject(crit);
            obj.setRespTime(date);
            criteriaDAO.save(obj);
            
            structure.getInfoObjects().add(obj);
            
        }//for ref
        
        criteriaDAO.save(structure);
        
    }//publish()
    
    
    public CCT getCCTById(Long cctId) throws Exception {
    	CCT cct = cctDAO.getCCTById(cctId);
    	return cct;
    } //getCctById();
    
    
    public Collection getCCTs() throws Exception {
    	Collection ccts = cctDAO.getCCTs();
    	return ccts;
    }


    public void setWeight(Long suiteId, Long critId, int weight) throws Exception {
    	Criteria criteria = criteriaDAO.getCriterionById(critId);
    	criteriaDAO.setWeight(suiteId, criteria, weight);
    }
    
    
    /**
     * TODO: get a CriteriaSuite object by the given id
     */
    public CriteriaSuite getCriteriaSuiteById(Long id) throws Exception {
    	return criteriaDAO.getCriteriaSuiteById(id);
    }//getCriteriaSuiteById()
    
    
}//class CriteriaServiceImpl
