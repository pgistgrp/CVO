package org.pgist.util;

import java.util.ArrayList;
import java.util.List;


/**
 * Trie structure. Trie is used in PGIST as a fast phrase matching tool against a predefined
 * dictionary. The dictionary contains two types of phrases, inclusive and exclusive.<br>
 * 
 * For generality, a Trie node can be associated with any object. In PGIST, it will be
 * org.pgist.tagging.Tag class, which has a boolean value to indicate if it's inclusive
 * or exclusive.<br>
 * 
 * Features:
 * <ul>
 *   <li>Paragraph can contain: 0-9 A-B a-b [blank] [punctuations]</li>
 *   <li>Phrase can contain: 0-9 A-B a-b ' [space]</li>
 *   <li>If "A B" and "B C D" are both phrases, and paragraph is "A B C D E", then "A B" is matched, and "B C D" is not.</li>
 *   <li>If "A B" and "A B C" are both phrases, and paragraph is "A B C D E", then "A B C" is matched, and "A B" is not.
 *       That is, always match the longest phrase starting from the same position.</li>
 *   <li>If "A" is a phrase and paragraph is "A'xyz", then "A" is matched</li>
 *   <li>"A  B" or "A    B" will be both matched as "A B".</li>
 *   <li>"A , B" or "A . B" will not be matched as "A B".</li>
 * </ul>
 * 
 * Functions:
 * <ul>
 *   <li>Test if a specific phrase is in trie.</li>
 *   <li>Suggest phrases from a given paragrah.</li>
 *   <li>Highlight phrases from a given paragrah.</li>
 * </ul>
 * 
 * @author kenny
 */
public class Trie {
    
    
    /**
     * Inner Class TrieNode
     */
    private class TrieNode {
        
        
        /* char of this node */
        public char ch = 0;
        
        /* how many children this node has */
        public int childSize = 0;
        
        /*
         * Child nodes. Two-dimension array, the first dimension acts as a hash bucket.
         * The second dimension stores the child nodes.
         */
        public TrieNode[][] nexts = null;
        
        /* associated object to this node */
        public Object object = null;
        
        
        /* constructor */
        public TrieNode(char ch) {
            this.ch = ch;
            childSize = 0;
            nexts = null;
            object = null;
        }
        
        
        /**
         * Match one char from child nodes of this node.
         * 
         * @param x the char to be matched
         * @return a child TrieNode of this node, which is matched 
         */
        public TrieNode match(char x) {
            if (childSize==0) return null;
            
            /* calculate bucket position */
            int mod = x % 10;
            
            int n = nexts[mod].length;
            
            /* search the char to be matched */
            for (int i=0; i<n; i++) {
                if (nexts[mod][i]!=null && nexts[mod][i].ch==x) return nexts[mod][i];
            }//for i
            
            return null;
        }//match()
        
        
        /**
         * Add a given char to chars array managed by current TrieNode.
         * Note that the given char maybe already in the array. We need to check it
         * before a new TrieNode is created.
         * 
         * @param x the character to be added.
         * @return A TrieNode object of x
         */
        synchronized public TrieNode add(char x) {
            if (childSize==0) {
                /*
                 * Lazily create the char array
                 */
                nexts = new TrieNode[10][0];
            }
            
            /*
             * Here the char array implements a Hash structure.
             * x % 10 obtains the position where x should be placed.
             */
            int mod = x % 10;
            
            /* lazily create the char array */
            if (nexts[mod].length==0) nexts[mod] = new TrieNode[5];
            
            int bucket = -1;
            
            /*
             * find the place where the given char should be inserted,
             *  or return if it's already in trie
             */
            for (int i=0; i<nexts[mod].length; i++) {
                if (nexts[mod][i]==null) {
                    /*
                     * find an empty place, but we still don't know if the char already
                     * exists in the array, so we just mark the place
                     */
                    if (bucket==-1) bucket = i;
                } else {
                    /*
                     * Now we are sure the char already exists in the array, we return its node
                     */
                    if (nexts[mod][i].ch==x) return nexts[mod][i];
                }
            }//for i
            
            /*
             * Now we are sure a new TrieNode need to be created and inserted
             */
            
            childSize++;
            
            TrieNode node = new TrieNode(x);
            
            if (bucket!=-1) {
                /* we already marked where we can insert the new node */
                nexts[mod][bucket] = node;
                return node;
            }
            
            /*
             * Now we have to expand the array for the new node.
             * We'd like to expand it with two more buckets, chances are we
             * will have another new node to insert.
             */
            
            TrieNode[] array = new TrieNode[nexts[mod].length+2];
            
            /* copy content to the new array */
            for (int i=0; i<nexts[mod].length; i++) {
                array[i] = nexts[mod][i];
            }
            
            /* now we insert the new node */
            array[nexts[mod].length] = node;
            
            nexts[mod] = array;
            
            return node;
        }//add()
        
        
        /**
         * Remove a node from the Trie.
         * 
         * @param node the node to be removed
         */
        synchronized public void remove(TrieNode node) {
            /*
             * First we have to find it
             */
            
            /* calculate the bucket position */
            int mod = node.ch % 10;
            
            /* not found */
            if (nexts[mod].length==0) return;
            
            /* search the node */
            for (int i=0; i<nexts[mod].length; i++) {
                if (nexts[mod][i]==node) {
                    nexts[mod][i] = null;
                    childSize--;
                    return;
                }
            }//for i
        }//remove()
        
        
        /**
         * Help to debug.
         */
        public String toString() {
            if (ch==0) return "";
            else return ""+ch;
        }//toString()
        
        
        /**
         * Help to debug.
         * 
         * @param x
         */
        public void print(char x) {
            if (ch!=0) System.out.print(x+" --> "+ch);
            else System.out.print(x+" --> ");
            int mod = x % 10;
            if (childSize>0 && nexts[mod]!=null) {
                for (int i=0; i<nexts[mod].length; i++) {
                    if (nexts[mod][i]!=null) System.out.print(","+nexts[mod][i].ch);
                }
            }
            System.out.println();
        }//print()
        
        
        /**
         * Help to debug.
         * 
         * @param prefix
         */
        public void printAll(String prefix) {
            if (ch!=0) prefix = prefix + ch;
            
            System.out.println("I am "+prefix);
            
            if (nexts!=null) {
                for (int i=0; i<nexts.length; i++) {
                    for (int j=0; j<nexts[i].length; j++) {
                        TrieNode node = nexts[i][j];
                        if (node!=null) {
                            node.printAll(prefix);
                        }
                    }
                }
            }
        }//printAll()
        
        
    }//class TrieNode
    
    
    /**
     * Inner Class AbstractTrieScanner.
     * One TrieScanner works something like an Iterator pattern.
     */
    private abstract class AbstractTrieScanner implements TrieScanner {
        
        
        protected TrieNode parent;
        
        protected TrieNode candidate;
        
        protected String para;
        
        protected char ch;
        
        protected int index;
        
        protected int point = 0;
        
        protected int length;
        
        
        protected AbstractTrieScanner(String paragraph) {
            para = paragraph.toLowerCase();
            index = 0;
            length = para.length();
            ch = para.charAt(index);
            candidate = null;
            
            if (ch<'0' || ( ch>'9' && ch<'a' ) || ch>'z') skip();
        }
        
        
        /**
         * skip to the next beginning
         * 
         * @return tail of the skipped unit
         */
        protected int skip() {
            int tail = -1;
            
            if ( ch=='\'' || (ch>='0' && ch<='9') || (ch>='a' && ch<='z') ) {
                while (index<length-1) {
                    index++;
                    ch = para.charAt(index);
                    if (ch!='\'' && ( ch<'0' || (ch>'9' && ch<'a') || ch>'z' )) {
                        tail = index;
                        break;
                    }
                }//while
                if (index==length) return index;
            }
            
            while (index<length-1) {
                index++;
                ch = para.charAt(index);
                if ( (ch>='0' && ch<='9') || (ch>='a' && ch<='z') ) break;
            }//while
            
            return tail;
        }//skip()
        
        
        /**
         * Test if it's end of paragraph.
         * 
         * @return
         */
        public boolean eop() {
            return index>=length-1;
        }//eop()
        
        
        /**
         * Scan for next unit.
         * 
         * @param ext if true, the scanner will return the unmatched unit as the ScanResult.
         *            else, only the matched unit will be returned (The match field of ScanResult will reflect this).
         * @return A ScanResult object
         */
        protected ScanResult scan(boolean ext) {
            if (index>=length) return null;
            
            ScanResult result = new ScanResult(index);
            
            parent = root;
            candidate = null;
            point = 0;
            int tail = -1;
            
            TrieNode node = null;
            
            while (index<length) {
                node = parent.match(ch);
                
                if (node==null) {
                    if (candidate!=null) {
                        result.setObject(candidate.object);
                        candidate = null;
                        index = point;
                        ch = para.charAt(index);
                        skip();
                        return result;
                    } else {
                        if (ext) {
                            if ( (ch>='0' && ch<='9') || (ch>='a' && ch<='z')) {
                                tail = skip();
                            } else {
                                tail = index;
                                skip();
                            }
                            if (tail==-1) return null;
                            result.setTail(tail);
                            result.setMatched(false);
                            return result;
                        } else {
                            skip();
                            return null;
                        }
                    }
                } else {
                    if (node.object!=null) {
                        if (index==length-1) {
                            result.setTail(length);
                            result.setObject(node.object);
                            
                            return result;
                        }
                        
                        char chr = para.charAt(index+1);
                        if (chr<'0' || (chr>'9' && chr<'a') || chr>'z') {
                            candidate = node;
                            point = index;
                            result.setTail(index+1);
                        }
                    }
                    
                    if (index==length-1) {
                        /*
                         * if now it's the last char, return
                         */
                        if (candidate!=null) {
                            result.setObject(candidate.object);
                            candidate = null;
                            return result;
                        } else {
                            return null;
                        }
                    }
                    
                    /*
                     * now we need to go ahead of some chars
                     */
                    
                    parent = node;
                    index++;
                    ch = para.charAt(index);
                    
                    char chr;
                    do {
                        if (ch=='\t') ch = ' ';
                        
                        if (ch==' ' && index<length-1) {
                            /*
                             * for white space, we need to go ahead one further char, and scan again
                             */
                            chr = para.charAt(index+1);
                            if (chr=='\t') chr = ' ';
                            if (chr==' ') {
                                index++;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    } while (index<length-1);
                    
                }
            }//while
            
            return null;
        }//scan()
        
        
    }//class AbstractTrieScanner
    

    private class HighlighScanner extends AbstractTrieScanner {
        
        
        public HighlighScanner(String paragraph) {
            super(paragraph);
        }
        
        
        public ScanResult scan() {
            return scan(false);
        }//scan()
        
        
    }//class HighlighScanner
    
    
    private class SuggestScanner extends AbstractTrieScanner {
        
        
        public SuggestScanner(String paragraph) {
            super(paragraph);
        }
        
        
        public ScanResult scan() {
            return scan(true);
        }//scan()
        
        
    }//class SuggestScanner
    
    
    /**
     * The root of the whole Trie structure.
     */
    private TrieNode root;
    
    
    /**
     * Constructor.
     */
    public Trie() {
        root = new TrieNode((char) 0);
        root.childSize = 0;
        root.ch = 0;
    }
    
    
    /**
     * Add a phrase and its associated object into the Trie structure.
     * 
     * @param phrase
     * @param object
     */
    public void add(String phrase, Object object) {
        if (phrase==null || phrase.length()==0) return;
        
        phrase = phrase.toLowerCase();
        int n = phrase.length();
        
        TrieNode parent = root;
        TrieNode node = null;
        
        for (int i=0; i<n; i++) {
            char ch = phrase.charAt(i);
            node = parent.match(ch);
            
            if (node==null) {
                parent = parent.add(ch);
            } else {
                parent = node;
            }
        }//for i
        
        parent.object = object;
    }//add()
    
    
    synchronized public Object remove(String word) {
        if (word==null || word.length()==0) return null;
        
        word = word.toLowerCase();
        
        int n = word.length();
        
        List parents = new ArrayList(n-1);
        
        TrieNode parent = root;
        TrieNode node = null;
        
        for (int i=0; i<n; i++) {
            parents.add(parent);
            node = parent.match(word.charAt(i));
            if (node==null) {
                parents.clear();
                return null;
            }
            parent = node;
        }//for i
        
        if (node!=null) {
            Object obj = node.object;
            
            if (node.childSize>0) {
                node.object = null;
            } else {
                for (int i=n-1; i>=0; i--) {
                    parent = (TrieNode) parents.get(i);
                    parent.remove(node);
                    if (parent.childSize>0 || parent.object!=null) break;
                    node = parent;
                }//for i
            }
            
            parents.clear();
            return obj;
        }
        
        parents.clear();
        return null;
    }//remove()
    
    
    /**
     * Clear the whole Trie structure.
     */
    synchronized public void clear() {
        root = new TrieNode((char) 0);
        root.childSize = 0;
        root.ch = 0;
    }//clear()
    
    
    /**
     * Search a given word/phrase in Trie, return its associated object
     * @param word
     * @return
     */
    public Object find(String word) {
        if (word==null || word.length()==0) return null;
        
        word = word.toLowerCase();
        
        int n = word.length();
        
        TrieNode parent = root;
        TrieNode node = null;
        
        for (int i=0; i<n; i++) {
            node = parent.match(word.charAt(i));
            if (node==null) return null;
            parent = node;
        }//for i
        
        if (node!=null) return node.object;
        
        return null;
    }//find()
    
    
    /**
     * Highlight the given paragraph against Trie structure.
     * 
     * @param paragraph
     * @return a TrieScanner
     */
    public TrieScanner highlight(String paragraph) {
        return new HighlighScanner(paragraph);
    }//highlight()
    
    
    /**
     * For a given paragraph, split it to small chunks, each chunk is either matched to the Trie,
     * or not matched.<br>
     * 
     * In practice, an associated Tag (inclusive or exclusive) object can be got with the
     * matched phrase, and the exclusive Tag can be filtered out, the inclusive Tag can be
     * strongly suggested. And unmatched phrase is always used as suggested phrase.<br>
     * 
     * @param paragraph
     * @return a TrieScanner
     */
    public TrieScanner suggest(String paragraph) {
        return new SuggestScanner(paragraph);
    }//suggest()
    
    
}//class Trie
