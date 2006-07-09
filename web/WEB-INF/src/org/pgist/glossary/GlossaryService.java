package org.pgist.glossary;

import java.util.Collection;

import org.pgist.discussion.DiscussionPost;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface GlossaryService {
    
    
    Collection getTerms(PageSetting setting) throws Exception;
    

    Collection getTerms(String filter, String sort, String direction, int[] status) throws Exception;
    
    
    Term getTermById(Long id) throws Exception;
    

    void updateTerm(Term term, String[] relatedTerms, String[] links, String[] sources, String[] categories) throws Exception;
    

    void createTerm(Term term, String[] relatedTerms, String[] links, String[] sources, String[] categories, int status_pending) throws Exception;
    

    Collection getComments(Term term) throws Exception;
    
    
    DiscussionPost createComment(Long id, Long quoteId, String comment) throws Exception;

    
}//interface GlossaryService
