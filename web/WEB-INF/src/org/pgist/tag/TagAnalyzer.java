package org.pgist.tag;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.pgist.util.ScanResult;
import org.pgist.util.Trie;
import org.pgist.util.TrieScanner;


/**
 * Tag Analyzer
 * 
 * @author kenny
 */
public class TagAnalyzer {
    
    
    private Trie trie = new Trie();
    
	private TagDAO tagDAO = null;
    
	
	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}
    

	/*
	 * ------------------------------------------------------------------------
	 */
    
	
    public void reload() throws Exception {
        Collection<Tag> tags = tagDAO.getAllTags();
        
        for (Tag tag : tags) {
            trie.add(tag.getName(), tag);
        }//for tag
    }//reload()
    
    
    public String[][] suggest(String text) {
        String[][] phrases = new String[2][];
        
        TrieScanner scanner = trie.suggest(text);
        
        Set set0 = new HashSet();
        Set set1 = new HashSet();
        
        ScanResult result = null;
        Tag tag = null;
        
        while (!scanner.eop()) {
            result = (ScanResult) scanner.scan();
            if (result!=null) {
                if (result.isMatched()) {
                    tag = (Tag) result.getObject();
                    if (tag.getType()==Tag.TYPE_INCLUDED) {
                        set0.add(tag.getName());
                    }
                } else {
                    set1.add(text.substring(result.getFrom(), result.getTail()).toLowerCase());
                }
            }
        }//while
        
        phrases[0] = (String[]) set0.toArray();
        phrases[1] = (String[]) set1.toArray();
        
        return phrases;
    }//suggest()
    
    
}//class TagAnalyzer
