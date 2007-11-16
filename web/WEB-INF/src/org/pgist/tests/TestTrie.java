package org.pgist.tests;

import org.pgist.util.Trie;

import junit.framework.TestCase;


/**
 * 
 * @author kenny
 *
 */
public class TestTrie extends TestCase {
    
    
    public void testFind() {
        String[] dict = {
            "right",
            "right of way",
            "way",
        };
        
        Trie trie = new Trie();
        
        /*
         * Create dictionary
         */
        for (int i=0; i<dict.length; i++) {
            trie.add(dict[i]);
        }//for i
        
        for (int i=0; i<dict.length; i++) {
            /*
             * Test lowercase
             */
            String s = (String) trie.find(dict[i]);
            assertEquals("Phrase '"+dict[i]+"' is not matched.", dict[i], s);
            
            /*
             * Test uppercase
             */
            s = (String) trie.find(dict[i].toUpperCase());
            assertEquals("Phrase '"+dict[i].toUpperCase()+"' is not matched.", dict[i], s);
        }//for i
    }//testFind()
    
    
}//class TagMatcherTest
