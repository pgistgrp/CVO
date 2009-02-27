package org.pgist.glossary;

import java.util.Collection;
import java.util.Date;

import org.pgist.util.PageSetting;
import org.pgist.util.WebUtils;


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


    public Collection getTerms(int[] status) throws Exception {
        return glossaryDAO.getTermsByName(null, true, status);
    }//getTerms()


    public Collection getTerms(PageSetting setting, boolean prefixed) throws Exception {
        Collection terms = glossaryDAO.getTerms(setting, prefixed);
        
        return terms;
    }//getTerms()


    public Collection getTerms(String filter, String sort, String direction, int[] status) throws Exception {
        Collection terms = null;
        if (sort==null || "".equalsIgnoreCase(sort.trim()) || "name".equalsIgnoreCase(sort.trim())) {
            terms = glossaryDAO.getTermsByName(filter, !"desc".equals(direction), status);
        } else if ("views".equalsIgnoreCase(sort)) {
            terms = glossaryDAO.getTermsByViews(filter, !"desc".equals(direction), status);
        } else if ("comments".equalsIgnoreCase(sort)) {
            terms = glossaryDAO.getTermsByComments(filter, !"desc".equals(direction), status);
        } else if ("createtime".equalsIgnoreCase(sort)) {
            terms = glossaryDAO.getTermsByCreateTime(filter, !"desc".equals(direction), status);
        }
        
        return terms;
    }//getTerms()
    
    
    public Term getTermById(Long id) throws Exception {
        Term term = glossaryDAO.getTermById(id);
        return term;
    }//getTermById()


    public void updateTerm(Term term, String[] relatedTerms, String[] links, String[][] sources, String[] categories) throws Exception {
        term.getRelatedTerms().clear();
        term.getLinks().clear();
        term.getSources().clear();
        term.getCategories().clear();
        
        if (relatedTerms!=null) {
            for (int i=0; i<relatedTerms.length; i++) {
                if (relatedTerms[i]==null || "".equals(relatedTerms[i].trim())) continue;
                
                Term one = glossaryDAO.getTermByName(relatedTerms[i].trim());
                glossaryDAO.saveTerm(one);
                
                if (one==null) throw new Exception("Term "+relatedTerms[i]+" is not found!");
                term.getRelatedTerms().add(one);
            }//for i
        }
        
        if (links!=null) {
            for (int i=0; i<links.length; i++) {
                if (links[i]==null || "".equals(links[i].trim())) continue;
                
                TermLink link = new TermLink();
                link.setLink(links[i]);
                glossaryDAO.saveTermLink(link);
                
                term.getLinks().add(link);
            }//for i
        }
        
        if (sources!=null) {
            for (int i=0; i<sources.length; i++) {
                if (sources[i][0]==null || "".equals(sources[i][0].trim())) continue;
                
                TermSource source = new TermSource();
                source.setCitation(sources[i][0]);
                source.setUrl(sources[i][1]);
                glossaryDAO.saveTermSource(source);
                
                term.getSources().add(source);
            }//for i
        }
        
        if (categories!=null) {
            for (int i=0; i<categories.length; i++) {
                if (categories[i]==null || "".equals(categories[i].trim())) continue;
                
                TermCategory one = glossaryDAO.getCategoryByName(categories[i].trim());
                if (one==null) throw new Exception("Category "+categories[i]+" is not found!");
                term.getCategories().add(one);
            }//for i
        }
        
        glossaryDAO.saveTerm(term);
    }//updateTerm()


    public void createTerm(Term term, String[] relatedTerms, String[] links, String[][] sources, String[] categories, int status) throws Exception {
        term.getRelatedTerms().clear();
        term.getLinks().clear();
        term.getSources().clear();
        term.getCategories().clear();
        term.setStatus(status);
        term.setCreateTime(new Date());
        term.setCreator(glossaryDAO.getUserById(WebUtils.currentUserId()));
        
        String name = Character.toUpperCase(term.getName().charAt(0))+term.getName().toLowerCase().substring(1);
        term.setName(name);
        term.setInitial(name.charAt(0));
        
        if (relatedTerms!=null) {
            for (int i=0; i<relatedTerms.length; i++) {
                if (relatedTerms[i]==null || "".equals(relatedTerms[i].trim())) continue;
                
                Term one = glossaryDAO.getTermByName(relatedTerms[i].trim());
                glossaryDAO.saveTerm(one);
                
                if (one==null) throw new Exception("Term "+relatedTerms[i]+" is not found!");
                term.getRelatedTerms().add(one);
            }//for i
        }
        
        if (links!=null) {
            for (int i=0; i<links.length; i++) {
                if (links[i]==null || "".equals(links[i].trim())) continue;
                
                TermLink link = new TermLink();
                link.setLink(links[i]);
                glossaryDAO.saveTermLink(link);
                
                term.getLinks().add(link);
            }//for i
        }
        
        if (sources!=null) {
            for (int i=0; i<sources.length; i++) {
                if (sources[i][0]==null || "".equals(sources[i][0].trim())) continue;
                
                TermSource source = new TermSource();
                source.setCitation(sources[i][0]);
                source.setUrl(sources[i][1]);
                glossaryDAO.saveTermSource(source);
                
                term.getSources().add(source);
            }//for i
        }
        
        if (categories!=null) {
            for (int i=0; i<categories.length; i++) {
                if (categories[i]==null || "".equals(categories[i].trim())) continue;
                
                TermCategory one = glossaryDAO.getCategoryByName(categories[i].trim());
                if (one==null) throw new Exception("Category "+categories[i]+" is not found!");
                term.getCategories().add(one);
            }//for i
        }
        
        glossaryDAO.saveTerm(term);
    }//createTerm()


    public void deleteTerm(Term term) throws Exception {
        term.setDeleted(true);
        glossaryDAO.saveTerm(term);
    }//deleteTerm()


    public Collection getComments(Term term) throws Exception {
        return null;
    }//getComments()


    public TermComment getCommentById(Long id) throws Exception {
        return glossaryDAO.getCommentById(id);
    }//getCommentById()


    synchronized public TermComment createComment(Long id, String comment) throws Exception {
        Term term = glossaryDAO.getTermById(id);
        if (term==null) throw new Exception("term with id "+id+" is not found!");
        
        TermComment termComment = new TermComment();
        termComment.setComment(comment);
        
        term.getComments().add(termComment);
        
        glossaryDAO.increaseCommentCount(term);
        
        return termComment;
    }//createComment


    synchronized public void deleteComment(Term term, TermComment comment) throws Exception {
        glossaryDAO.decreaseCommentCount(term);
        term.getComments().remove(comment);
    }//deleteComment()


    public void setFlag(Long id) throws Exception {
        Term term = glossaryDAO.getTermById(id);
        if (term==null) throw new Exception("term with id "+id+" is not found!");
        
        if (!term.isFlaged()) term.setFlaged(true);
        
        glossaryDAO.saveTerm(term);
    }//setFlag()


    public void increaseViewCount(Term term) throws Exception {
        glossaryDAO.increaseViewCount(term);
        
        //set view count for current user
        glossaryDAO.setViewedByCurrentUser(term);
    }//increaseViewCount()


    public void increaseHighlightCount(Term term) throws Exception {
        glossaryDAO.increaseHighlightCount(term);
    }//increaseViewCount()


    public void acceptTerm(Term term) throws Exception {
        if (term.getStatus()==Term.STATUS_PENDING) {
            term.setStatus(Term.STATUS_OFFICIAL);
            glossaryDAO.saveTerm(term);
        }
    }//acceptTerm()


    public void rejectTerm(Term term, String reason) throws Exception {
        if (term.getStatus()==Term.STATUS_PENDING) {
            term.setDeleted(true);
            glossaryDAO.saveTerm(term);
        }
    }//rejectTerm()


}//class GlossaryServiceImpl
