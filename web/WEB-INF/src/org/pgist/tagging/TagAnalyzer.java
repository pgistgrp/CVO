package org.pgist.tagging;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    
    
    private Map tagsMap = new HashMap();
    
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
        
        trie.clear();
        tagsMap.clear();
        
        for (Tag tag : tags) {
            addTag(tag);
        }//for tag
    }//reload()
    
    
    public Tag getTag(String tagName) {
        return (Tag) tagsMap.get(tagName);
    }//getTag()
    
    
    public void addTag(Tag tag) {
        tagsMap.put(tag.getName(), tag);
        trie.add(tag.getName(), tag);
    }//addTag()
    
    
    public Tag deleteTag(String tagName) {
        trie.remove(tagName);
        return (Tag) tagsMap.remove(tagName);
    }//deleteTag()
    
    
    /**
     * From the given text, give some suggested tags according to the following processes:<br>
     * <ul>
     *   <li>accept matched included tags (official tags)</li>
     *   <li>reject excluded tags (stopwords)</li>
     *   <li>suggest unrecognized words (potential tags)</li>
     * </ul> 
     * 
     * @param text the text string to be parsed
     * @return an 2D array of string, array[0] is the matched tags, array[1] is the suggested words
     */
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
        
        phrases[0] = (String[]) set0.toArray(new String[0]);
        phrases[1] = (String[]) set1.toArray(new String[0]);
        
        return phrases;
    }//suggest()
    
    
}//class TagAnalyzer
