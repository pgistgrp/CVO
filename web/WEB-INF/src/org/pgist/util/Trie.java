package org.pgist.util;

import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author guirong
 */
public class Trie {
    Node root = new Node('$');
    
    /** Creates a new instance of Trie */
    public Trie() {
        
    }
    
    public Trie(List objects){
        build(objects);
    }
    
    public Pair longestPrefix(String s, Node node){
        Pair pair = new Pair();
        int n = s.length();
        Node p, q = node;
        int i = 0;
        while(q != null){
        	p = q;
        	q = q.getChild(s.charAt(i));
        	
        }
        return pair;
    }
    
    public boolean exists(Object object){
        Pair pair = longestPrefix(object.toString(), root);
        if(pair.length == object.toString().length() 
        		&& pair.node.objects.contains(object))
            return true;
        else
            return false;
    }
    
    public void add(Object object){
        String s = object.toString();
        
    }
    
    public void remove(Object object){
        
    }
    
    public void build(Collection objects){
        
    }
    
    public String[][] parse(String text){
        String inclusion[] = {""};
        String exclusion[] = {""};
        String output[][] = {inclusion, exclusion};
    	return output;
    }
    
    public int[][] mark(String text){
        return null;
    }
    
    class Pair{
        int length;
        Node node;
    }

    class Node{
        char c;
        boolean hasObject;
        HashMap children = null;
        Collection objects = null;
        
        /** Creates a new instance of Node */
        public Node(char ch) {
            c = ch;
        }
        
        public Node getChild(char ch){
        	if(children == null)
        		return null;
        	
        	return (Node)children.get("" + ch);
        }
        
        public Node addChild(char letter){
            if(children == null)
                children = new HashMap();
            
            Node n = new Node(letter);
            
            children.put("" + letter, n);
            return n;
        }
        
        public void removeChild(char letter){
            if(children == null)
                return;
            
            children.remove("" + letter);
        }
        
        public void addObject(Object object){
            if(objects == null)
                objects = new HashSet();
            
            objects.add(object);
        }

        public void removeObject(Object object){
            if(objects == null)
                objects = new HashSet();
            
            objects.remove(object);
        }

        public Collection getObjects(){
            return objects;
        }


    }

}

