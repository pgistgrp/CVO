package org.pgist.glossary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.pgist.util.ScanResult;
import org.pgist.util.Trie;
import org.pgist.util.TrieScanner;


/**
 * 
 * @author kenny
 *
 */
public class TermAnalyzer {
    
    
    private Trie trie = new Trie();
    
    private GlossaryDAO glossaryDAO;
    
    
    public TermAnalyzer() {
    }//TermAnalyzer()


    public void setGlossaryDAO(GlossaryDAO glossaryDAO) throws Exception {
        this.glossaryDAO = glossaryDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void reload() throws Exception {
        Collection<Term> terms = glossaryDAO.getAllTerms();
        
        for (Term term : terms) {
            trie.add(term.getName(), term);
            
            TermAcronym acronym = term.getAcronym();
            if (acronym!=null) trie.add(acronym.getName(), term);
            
            for (Iterator<TermVariation> iter = term.getVariations().iterator(); iter.hasNext(); ) {
                TermVariation variation = iter.next();
                trie.add(variation.getName(), term);
            }//for iter
        }//for term
    }//reload()
    
    
    public List highlight(String text, int count) {
        TrieScanner scanner = trie.highlight(text);
        
        List list = new ArrayList(10);
        Set set = new HashSet();
        
        ScanResult result = null;
        Term term = null;
        
        while (!scanner.eop()) {
            result = (ScanResult) scanner.scan();
            if (result!=null) {
                term = (Term) result.getObject();
                if (!set.contains(term)) {
                    list.add(result);
                    set.add(term);
                }
                
                if (count>0 && list.size()>=count) break;
            }
        }//while
        
        return list;
    }//highlight()
    
    
}//class TermAnalyzer
