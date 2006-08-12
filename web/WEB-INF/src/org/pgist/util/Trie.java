package org.pgist.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;


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
        
        public boolean hasNext = false;
        
        public TrieNode[][] nexts = null;
        
        public Object object = null;
        
        
        public TrieNode(char ch) {
            this.ch = ch;
            hasNext = false;
            nexts = null;
            object = null;
        }
        
        
        public TrieNode match(char x) {
            if (!hasNext) return null;
            
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
            if (!hasNext) {
                /*
                 * Lazily create the char array
                 */
                nexts = new TrieNode[10][0];
                hasNext = true;
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
        
        
        public String toString() {
            if (ch==0) return "";
            else return ""+ch;
        }//toString()
        
        
        public void print(char x) {
            if (ch!=0) System.out.print(x+" --> "+ch);
            else System.out.print(x+" --> ");
            int mod = x % 10;
            if (hasNext && nexts[mod]!=null) {
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
    
    
    private class HighlighScanner extends TrieScanner {
        
        
        private TrieNode parent;
        
        private TrieNode candidate;
        
        
        public HighlighScanner(String paragraph, TrieNode root) {
            para = paragraph.toLowerCase();
            index = 0;
            length = para.length();
            ch = para.charAt(index);
            candidate = null;
            
            if (ch<'a' && ch>'z') skip();
        }
        
        
        /**
         * Scan for next unit.
         */
        public ScanResult scan() {
            if (index>=length) return null;
            
            ScanResult result = new ScanResult(index);
            
            parent = root;
            candidate = null;
            point = 0;
            
            TrieNode node = null;
            
            while (index<length) {
                node = parent.match(ch);
                //parent.print(ch);
                if (node==null) {
                    if (candidate!=null) {
                        result.setObject(candidate.object);
                        candidate = null;
                        index = point;
                        ch = para.charAt(index);
                        skip();
                        return result;
                    } else {
                        skip();
                        return null;
                    }
                } else {
                    if (node.object!=null) {
                        if (index==length-1) {
                            result.setTail(length);
                            result.setObject(node.object);
                            
                            return result;
                        }
                        
                        char chr = para.charAt(index+1);
                        if (chr<'a' || chr>'z') {
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
        
        
    }//class HighlighScanner
    
    
    private TrieNode root;
    
    
    public Trie() {
        root = new TrieNode((char) 0);
        root.hasNext = false;
        root.ch = 0;
    }
    
    
    public void add(Object object) {
        String word = object.toString().trim();
        
        if (word==null || word.length()==0) return;
        
        word = word.toLowerCase();
        int n = word.length();
        
        TrieNode parent = root;
        TrieNode node = null;
        
        for (int i=0; i<n; i++) {
            char ch = word.charAt(i);
            node = parent.match(ch);
            
            if (node==null) {
                parent = parent.add(ch);
            } else {
                parent = node;
            }
        }//for i
        
        parent.object = object;
    }//add()
    
    
    synchronized public void remove(String word) {
        //TODO
    }//remove()
    
    
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
        return new HighlighScanner(paragraph, root);
    }//highlight()
    
    
    public TrieScanner suggest(String paragraph) {
        return null;
    }//suggest()
    
    
    /*
     * Temp
     */
    public static void main(String[] args) {
        System.out.println("---> begin");
        
        String para = "I have my right \t of way on this road. multiplication. That's a brawl haha bulldoze, facelift's, frivolitiii. furnace, furniture! municipali. little-understood!";
        //String para = "Operators of Alaska's giant Prudhoe Bay oil field scramble to inspect pipelines for signs of corrosion, as they try to decide whether it's safe to keep pumping oil from parts of the field."
        //    +" British Petroleum has already shut down about half of Prudhoe Bay, after discovering a small oil spill last weekend, when about 630 gallons of crude oil leaked."
        //    +" Alaska Gov. Frank Murkowski and other officials toured the Prudhoe Bay oil pipelines, which have been crippled by corrosion problems discovered this week. The threat of a stoppage also endangers Alaska's budget: Oil taxes account for more than 90 percent of its revenues.";
        
        Trie trie = new Trie();
        Set set = new HashSet();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/home/kenny/temp/words.txt"));
            String s = null;
            while ((s=reader.readLine())!=null) {
                s = s.trim();
                if (s.length()==0) continue;
                trie.add(s);
                set.add(s);
            }
            trie.add("human right");
            trie.add("right of way");
            trie.add("right");
            set.add("human right");
            set.add("right of way");
            set.add("right");
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String hit = null;
        
        long begin = Calendar.getInstance().getTimeInMillis();
        
        //for (int i=0; i<1000000; i++) {
            TrieScanner scanner = trie.highlight(para);
            
            while (!scanner.eop()) {
                ScanResult result = scanner.scan();
                if (result!=null) {
                    hit = result.toString();
                    System.out.println("------------> "+result.getFrom()+","+result.getTail()+","+result.length()+"  "+result.getObject()+", "+para.substring(result.getFrom(), result.getTail()));
                }
            }//while
        //}//for i
        
        long end = Calendar.getInstance().getTimeInMillis();
        
        System.out.println("---> time used: "+(end-begin));
        
        //trie.root.printAll("");
        
        /*
        begin = Calendar.getInstance().getTimeInMillis();
        
        for (int i=0; i<1000000; i++) {
            String[] ss = para.split(" ");
            for (int j=0; j<ss.length-5; j++) {
                String s = "";
                for (int k=0; k<5; k++) {
                    s += ss[j+k];
                    if (set.contains(s)) {
                        hit = s;
                    }
                }
            }
        }//for i
        
        end = Calendar.getInstance().getTimeInMillis();
        
        System.out.println("---> time used: "+(end-begin));
        */
    }//main()
    
    
}//class Trie
