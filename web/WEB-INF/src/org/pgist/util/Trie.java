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
    
    public void build(List objects){
        
    }
    
    public String[][] parse(String text){
        String inclusion[] = {""};
        String exclusion[] = {""};
        String output[][] = {inclusion, exclusion};
    	return output;
    }
    
    public String mark(String text){
        return "";
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

