package org.pgist.glossary;

import org.pgist.util.Trie;


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
        //trie.build(glossaryDAO.getAllTerms());
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    //public int[][] mark(String text) {
    //    return trie.mark(text);
    //}//mark()
    
    
}//class TermAnalyzer
