package org.pgist.glossary;

import java.util.Collection;

import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface GlossaryDAO {
    
    
    Collection getAllTerms() throws Exception;

    Collection getTerms(PageSetting setting) throws Exception;

    Collection getTermsByName(String filter, boolean ascending, int[] status) throws Exception;
    
    Collection getTermsByViews(String filter, boolean ascending, int[] status) throws Exception;
    
    Collection getTermsByComments(String filter, boolean ascending, int[] status) throws Exception;
    
    Collection getTermsByCreateTime(String filter, boolean ascending, int[] status) throws Exception;
    
    Term getTermById(Long id) throws Exception;

    Term getTermByName(String name) throws Exception;
    
    void saveTerm(Term term) throws Exception;

    TermCategory getCategoryByName(String name) throws Exception;


}//interface GlossaryDAO
