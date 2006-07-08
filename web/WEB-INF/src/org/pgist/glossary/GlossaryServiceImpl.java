package org.pgist.glossary;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class GlossaryServiceImpl implements GlossaryService {
    
    
    private GlossaryDAO glossaryDAO;
    
    
    public void setGlossaryDAO(GlossaryDAO glossaryDAO) {
        this.glossaryDAO = glossaryDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */


    public Collection getTerms(PageSetting setting) throws Exception {
        return glossaryDAO.getTerms(setting);
    }//getTerms()


    public Collection getTerms(String filter, String sort, String direction) throws Exception {
        Collection terms = null;
        if (sort==null || "".equalsIgnoreCase(sort.trim()) || "name".equalsIgnoreCase(sort.trim())) {
            terms = glossaryDAO.getTermsByName(filter, !"desc".equals(direction));
        } else if ("views".equalsIgnoreCase(sort)) {
            terms = glossaryDAO.getTermsByViews(filter, !"desc".equals(direction));
        } else if ("comments".equalsIgnoreCase(sort)) {
            terms = glossaryDAO.getTermsByComments(filter, !"desc".equals(direction));
        } else if ("createtime".equalsIgnoreCase(sort)) {
            terms = glossaryDAO.getTermsByCreateTime(filter, !"desc".equals(direction));
        }
        return terms;
    }//getTerms()
    
    
    public Term getTermById(Long id) throws Exception {
        return glossaryDAO.getTermById(id);
    }//getTermById()


    public void saveTerm(Term term, String[] relatedTerms, String[] links, String[] sources, String[] categories) throws Exception {
        term.getRelatedTerms().clear();
        term.getLinks().clear();
        term.getSources().clear();
        term.getCategories().clear();
        
        for (int i=0; i<relatedTerms.length; i++) {
            if (relatedTerms[i]==null || "".equals(relatedTerms[i].trim())) continue;
            
            Term one = glossaryDAO.getTermByName(relatedTerms[i].trim());
            if (one==null) throw new Exception("Term "+relatedTerms[i]+" is not found!");
            term.getRelatedTerms().add(one);
        }//for i
        
        for (int i=0; i<links.length; i++) {
            if (links[i]==null || "".equals(links[i].trim())) continue;
            
            term.getLinks().add(links[i]);
        }//for i
        
        for (int i=0; i<sources.length; i++) {
            if (sources[i]==null || "".equals(sources[i].trim())) continue;
            
            term.getSources().add(sources[i]);
        }//for i
        
        for (int i=0; i<categories.length; i++) {
            if (categories[i]==null || "".equals(categories[i].trim())) continue;
            
            TermCategory one = glossaryDAO.getCategoryByName(categories[i].trim());
            if (one==null) throw new Exception("Category "+categories[i]+" is not found!");
            term.getCategories().add(one);
        }//for i
        
        glossaryDAO.saveTerm(term);
    }//saveTerm()


}//class GlossaryServiceImpl
