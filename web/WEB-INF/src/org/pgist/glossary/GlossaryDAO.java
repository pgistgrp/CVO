package org.pgist.glossary;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface GlossaryDAO {
    
    
    Collection getTerms(PageSetting setting) throws Exception;

    Term getTermById(Long id) throws Exception;

    Term getTermByName(String name) throws Exception;
    
    void saveTerm(Term term) throws Exception;

    TermCategory getCategoryByName(String name) throws Exception;

    
}//interface GlossaryDAO
