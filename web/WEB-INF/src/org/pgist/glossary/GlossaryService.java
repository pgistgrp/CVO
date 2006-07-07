package org.pgist.glossary;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface GlossaryService {
    
    
    Collection getTerms(PageSetting setting) throws Exception;

    Collection getTerms(String filter, String sort, String direction) throws Exception;
    
    Term getTermById(Long id) throws Exception;

    void saveTerm(Term term, String[] relatedTerms, String[] links, String[] sources, String[] categories) throws Exception;

    
}//interface GlossaryService
