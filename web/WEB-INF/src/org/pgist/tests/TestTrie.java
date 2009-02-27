package org.pgist.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.pgist.util.ScanResult;
import org.pgist.util.Trie;
import org.pgist.util.TrieScanner;


/**
 * 
 * @author kenny
 *
 */
public class TestTrie {
    
    
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
    
    
    @Test
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
    @Test
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
    @Test
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
    @Test
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
    @Test
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
    @Test
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
    @Test
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
    @Test
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
    
    
    /**
     * Phrase contains digits
     */
    @Test
    public void testHighlight8() {
        String[] dict = {
                "I520",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "We prefer to use I520 as a test base.";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 17);
                assertEquals("incorrect tail index.", result.getTail(), 21);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight8()
    
    
    /**
     * Phrase starts with digits
     */
    @Test
    public void testHighlight9() {
        String[] dict = {
                "520 Road",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "We prefer to use 520 Road as a test base.";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 17);
                assertEquals("incorrect tail index.", result.getTail(), 25);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight9()
    
    
    /**
     * Phrase ends with digits
     */
    @Test
    public void testHighlight10() {
        String[] dict = {
                "Road 520",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "We prefer to use Road 520 as a test base.";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 17);
                assertEquals("incorrect tail index.", result.getTail(), 25);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight10()
    
    
    /**
     * Phrase starts with digits, paragraph starts with phrase
     */
    @Test
    public void testHighlight11() {
        String[] dict = {
                "520 Road",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "520 Road! The correct road.";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 0);
                assertEquals("incorrect tail index.", result.getTail(), 8);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight11()
    
    
    /**
     * Phrase ends with digits, paragraph ends with phrase
     */
    @Test
    public void testHighlight12() {
        String[] dict = {
                "Road 520",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "The correct road: Road 520";
        
        TrieScanner scanner = trie.highlight(para);
        
        boolean found = false;
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                found = true;
                assertEquals("Phrase '"+dict[0]+"' is not matched.", dict[0], result.getObject());
                assertEquals("incorrect from index.", result.getFrom(), 18);
                assertEquals("incorrect tail index.", result.getTail(), 26);
            }
        }//while
        
        if (!found) assertTrue("No phrase is matched.", false);
    }//testHighlight12()
    
    
    /**
     * 
     */
    @Test
    public void testSuggest1() {
        String[] dict = {
                "right of way",
                "on",
                "the",
        };
        
        Trie trie = createTrie(dict);
        
        String para = "I have the right of way on this road. That's correct.";
        Set set = new HashSet();
        set.add("i");
        set.add("have");
        set.add("the");
        set.add("right of way");
        set.add("on");
        set.add("this");
        set.add("road");
        set.add("that's");
        set.add("correct");
        
        TrieScanner scanner = trie.suggest(para);
        
        while (!scanner.eop()) {
            ScanResult result = scanner.scan();
            if (result!=null) {
                String matched = null;
                if (result.isMatched()) {
                    matched = result.getObject().toString();
                } else {
                    matched = para.substring(result.getFrom(), result.getTail()).toLowerCase();
                    if (set.contains(matched)) set.remove(matched);
                }
            }
        }//while
        
        assertEquals("There's some pharses which are not suggested!", set.size(), dict.length);
        for (int i=0; i<dict.length; i++) {
            assertTrue("There's some pharses which are not matched!", set.contains(dict[i]));
        }
    }//testSuggest1()
    
    
    @Test
    public void testRemove1() {
        String[] dict = {
                "abcda",
                "abcdg",
                "abcdeg",
                "abcdefg",
                "abcdefghij",
        };
        
        Trie trie;
        
        for (int i=0; i<dict.length; i++) {
            trie = createTrie(dict);
            trie.remove(dict[i]);
            for (int j=0; j<dict.length; j++) {
                if (i==j) {
                    assertEquals("Failed remove "+dict[i], null, trie.find(dict[j]));
                } else {
                    assertEquals("Failed remove "+dict[i], dict[j], trie.find(dict[j]));
                }
            }//for j
        }//for i
    }//testRemove1()
    
    
}//class TagMatcherTest
