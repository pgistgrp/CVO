package org.pgist.glossary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.pgist.discussion.Discussion;
import org.pgist.discussion.DiscussionDAO;
import org.pgist.discussion.DiscussionPost;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class GlossaryServiceImpl implements GlossaryService {
    
    
    private GlossaryDAO glossaryDAO;
    
    private DiscussionDAO discussionDAO;
    
    
    public void setGlossaryDAO(GlossaryDAO glossaryDAO) {
        this.glossaryDAO = glossaryDAO;
    }
    
    
    public void setDiscussionDAO(DiscussionDAO discussionDAO) {
        this.discussionDAO = discussionDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */


    public Collection getTerms(PageSetting setting, boolean prefixed) throws Exception {
        return glossaryDAO.getTerms(setting, prefixed);
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
        return glossaryDAO.getTermById(id);
    }//getTermById()


    public void updateTerm(Term term, String[] relatedTerms, String[] links, String[] sources, String[] categories) throws Exception {
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
    }//updateTerm()


    public void createTerm(Term term, String[] relatedTerms, String[] links, String[] sources, String[] categories, int status) throws Exception {
        term.getRelatedTerms().clear();
        term.getLinks().clear();
        term.getSources().clear();
        term.getCategories().clear();
        term.setStatus(status);
        term.setCreateTime(new Date());
        
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
                if (sources[i]==null || "".equals(sources[i].trim())) continue;
                
                TermSource source = new TermSource();
                source.setSource(sources[i]);
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


    public Collection getComments(Term term) throws Exception {
        //get discussion
        Discussion discussion = discussionDAO.getDiscussion(Term.class.getName(), term.getId());
        if (discussion==null) {
            return new ArrayList(0);
        } else {
            //get comments
            Collection comments = discussionDAO.getPosts(discussion);
            return comments;
        }
    }//getComments()


    public DiscussionPost createComment(Long id, Long quoteId, String comment) throws Exception {
        Term term = glossaryDAO.getTermById(id);
        if (term==null) throw new Exception("term with id "+id+" is not found!");
        
        Discussion discussion = discussionDAO.getDiscussion(Term.class.getName(), term.getId());
        if (discussion==null) {
            discussion = discussionDAO.createDiscussion(Term.class.getName(), term.getId());
        }
        
        DiscussionPost quote = null;
        if (quoteId!=null) {
            quote = discussionDAO.getPostById(quoteId);
            if (quote==null) throw new Exception("quoted post with id "+quoteId+" is not found!");
        }
        
        return discussionDAO.createPost(discussion, quote, comment);
    }//createComment


}//class GlossaryServiceImpl
