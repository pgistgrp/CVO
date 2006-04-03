package org.pgist.cvo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * Title: Tag analysis for text information
 * </p>
 * 
 * <p>
 * Description: This class provides the function of extracting tags based on tag
 * database and suggesting new tags based on nautral language processing
 * </p>
 * 
 * @author Guirong
 * @version 1.0
 */
public class TagAnalyzer {
    
    
    private static List all_terms = new ArrayList();

    /**
     * tt is a "trie" data structure to promote efficient retrieval of existing
     * tags. tt[i][0] - the char tt[i][1] - index of the left child tt[i][2] -
     * index of the right child tt[i][3] - ID of the tag, or -1 if it's not the
     * end of a tag
     */
    private long[][] tt = null;

    /**
     * term_id_count is a useful data structure to index the found results: id
     * as the array index allows fast retrieval of a term, and count records the
     * number of appearance.
     */
    private long[][] term_id_count;
    

    public static void main(String args[]) {
        TagAnalyzer g = new TagAnalyzer();

        System.out.println("my agenda is to find a bus oooo");
    }
    

    /**
     * Extract existing tags for the given text.
     * 
     * @param statement
     *            String
     * @return List
     */
    public List parseText(String statement) {
        return null;
    }
    

    /**
     * This method recursively finds the longest matched part of a string
     * against the tree, starting from a given position in the tree.
     * 
     * @param tree
     *            long[][]
     * @param start
     *            int
     * @param s
     *            String
     * @param settings
     *            long[]
     * @return int
     */
    public int matchString(long[][] tree, int start, String s, long[] settings) {
        return 0;
    }
    

    /**
     * Add a given node to the tree.
     * 
     * @param tree
     *            long[][]
     * @param tag
     *            Tag
     */
    public void addTag(long[][] tree, Tag tag) {

    }
    

    /**
     * Get data form the tag database, and build or rebuild the tree. It loops
     * through all the tags and call addNode() to add all tags into the tree.
     * 
     * @return long[][]
     */
    public long[][] rebuildTree() {

        return null;
    }
    

}
