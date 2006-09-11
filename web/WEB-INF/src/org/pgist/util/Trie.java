package org.pgist.util;

import java.util.ArrayList;
import java.util.List;


/**
 * Trie structure.<br>
 * 
 * Features:
 * <ul>
 *   <li>Paragraph can contain: 0-9 A-B a-b [blank] [punctuations]</li>
 *   <li>Phrase can contain: 0-9 A-B a-b ' [space]</li>
 *   <li>If "A B" and "B C D" are both phrases, and paragraph is "A B C D E", then "A B" is matched, and "B C D" is not.</li>
 *   <li>If "A" is a phrase and paragraph is "A'xyz", then "A" is matched</li>
 *   <li>"A  B" or "A    B" will be both matched as "A B".</li>
 *   <li>"A , B" or "A . B" will not be matched as "A B".</li>
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
        
        
        public char ch = 0;
        
        public int childSize = 0;
        
        public TrieNode[][] nexts = null;
        
        public Object object = null;
        
        
        public TrieNode(char ch) {
            this.ch = ch;
            childSize = 0;
            nexts = null;
            object = null;
        }
        
        
        public TrieNode match(char x) {
            if (childSize==0) return null;
            
            int mod = x % 10;
            
            int n = nexts[mod].length;
            
            for (int i=0; i<n; i++) {
                if (nexts[mod][i]!=null && nexts[mod][i].ch==x) return nexts[mod][i];
            }//for i
            
            return null;
        }//match()
        
        
        /**
         * Add a given char to chars array managed by current TrieNode.
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
            
            if (nexts[mod].length==0) nexts[mod] = new TrieNode[5];
            
            int bucket = -1;
            
            for (int i=0; i<nexts[mod].length; i++) {
                if (nexts[mod][i]==null) {
                    if (bucket==-1) bucket = i;
                } else {
                    if (nexts[mod][i].ch==x) return nexts[mod][i];
                }
            }//for i
            
            childSize++;
            
            TrieNode node = new TrieNode(x);
            
            if (bucket!=-1) {
                nexts[mod][bucket] = node;
                return node;
            }
            
            TrieNode[] array = new TrieNode[nexts[mod].length+2];
            
            for (int i=0; i<nexts[mod].length; i++) {
                array[i] = nexts[mod][i];
            }
            
            array[nexts[mod].length] = node;
            
            nexts[mod] = array;
            
            return node;
        }//add()
        
        
        synchronized public void remove(TrieNode node) {
            for (int i=0; i<10; i++) {
                for (int j=0; j<nexts[i].length; j++) {
                    if (nexts[i][j]==node) {
                        nexts[i][j] = null;
                        childSize--;
                        return;
                    }
                }//for j
            }//for i
        }//remove()
        
        
        public String toString() {
            if (ch==0) return "";
            else return ""+ch;
        }//toString()
        
        
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
     * Inner Class AbstractTrieScanner
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
    
    
    private TrieNode root;
    
    
    public Trie() {
        root = new TrieNode((char) 0);
        root.childSize = 0;
        root.ch = 0;
    }
    
    
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
    
    
    synchronized public void clear() {
        root = new TrieNode((char) 0);
        root.childSize = 0;
        root.ch = 0;
    }//clear()
    
    
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
    
    
    public TrieScanner highlight(String paragraph) {
        return new HighlighScanner(paragraph);
    }//highlight()
    
    
    public TrieScanner suggest(String paragraph) {
        return new SuggestScanner(paragraph);
    }//suggest()
    
    
}//class Trie
