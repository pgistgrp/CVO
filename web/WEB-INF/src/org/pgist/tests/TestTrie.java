package org.pgist.tests;

import org.pgist.util.ScanResult;
import org.pgist.util.Trie;
import org.pgist.util.TrieScanner;

import junit.framework.TestCase;


/**
 * 
 * @author kenny
 *
 */
public class TestTrie extends TestCase {
    
    
    private Trie createTrie(String[] dict) {
        Trie trie = new Trie();
        
        /*
         * Create dictionary
         */
        for (int i=0; i<dict.length; i++) {
            trie.add(dict[i], dict[i]);
        }//for i
        
        return trie;
    }//createTrie()
    
    
    public void testFind() {
        String[] dict = {
            "right",
            "right of way",
            "way",
        };
        
        Trie trie = createTrie(dict);
        
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
    
    
    /**
     * Phrase in the middle, exact match.
     */
    public void testHighlight1() {
        String[] dict = {
                "right of way",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "I have my right of way on this road.";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 10);
                assertEquals("incorrect tail index.", result.getTail(), 22);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight1()
    
    
    /**
     * Phrase in the middle, multi space
     */
    public void testHighlight2() {
        String[] dict = {
                "right of way",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "I have my right  of  way  on this road.";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 10);
                assertEquals("incorrect tail index.", result.getTail(), 24);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight2()
    
    
    /**
     * Phrase in the end, multi space
     */
    public void testHighlight3() {
        String[] dict = {
                "right of way",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "I have my right  of  way";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 10);
                assertEquals("incorrect tail index.", result.getTail(), 24);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight3()
    
    
    /**
     * Phrase in the beginning, multi space
     */
    public void testHighlight4() {
        String[] dict = {
                "right of way",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "Right  of  way. I have.";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 0);
                assertEquals("incorrect tail index.", result.getTail(), 14);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight4()
    
    
    /**
     * Phrase in the middle, multi space and tab
     */
    public void testHighlight5() {
        String[] dict = {
                "right of way",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "Right  of \t way. I have.";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 0);
                assertEquals("incorrect tail index.", result.getTail(), 15);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight5()
    
    
    /**
     * Phrase contains "'" and multi space
     */
    public void testHighlight6() {
        String[] dict = {
                "Newton's First Law",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "According to Newton's  First Law, an object tends to keep its speed and direction.";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 13);
                assertEquals("incorrect tail index.", result.getTail(), 32);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight6()
    
    
    /**
     * Phrase contains "'" and multi space and tab
     */
    public void testHighlight7() {
        String[] dict = {
                "Newton's First Law",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "According to Newton's  First \t\t Law, an object tends to keep its speed and direction.";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 13);
                assertEquals("incorrect tail index.", result.getTail(), 35);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight7()
    
    
}//class TagMatcherTest
