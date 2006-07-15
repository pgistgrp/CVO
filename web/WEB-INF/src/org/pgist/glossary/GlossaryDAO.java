package org.pgist.glossary;

import java.util.Collection;

import org.pgist.system.BaseDAO;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public interface GlossaryDAO extends BaseDAO {
    
    
    Collection getAllTerms() throws Exception;

    Collection getTerms(PageSetting setting, boolean prefixed) throws Exception;

    Collection getTermsByName(String filter, boolean ascending, int[] status) throws Exception;
    
    Collection getTermsByViews(String filter, boolean ascending, int[] status) throws Exception;
    
    Collection getTermsByComments(String filter, boolean ascending, int[] status) throws Exception;
    
    Collection getTermsByCreateTime(String filter, boolean ascending, int[] status) throws Exception;
    
    Term getTermById(Long id) throws Exception;

    Term getTermByName(String name) throws Exception;
    
    void saveTerm(Term term) throws Exception;

    TermCategory getCategoryByName(String name) throws Exception;

    void saveTermLink(TermLink link) throws Exception;

    void saveTermSource(TermSource source) throws Exception;
    
    
    /*
     * viewed count on the user basis
     */
    void setViewedByCurrentUser(Term term) throws Exception;
    

}//interface GlossaryDAO
