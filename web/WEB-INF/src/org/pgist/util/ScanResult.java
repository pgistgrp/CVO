package org.pgist.util;


/**
 * The scan result for Trie.<br>
 * 
 * The boolean field "matched" indicates if the scan matched a phrase or not.<br>
 * 
 * If one phrase is matched, the field "object" will be a valid domain object.<br>
 * 
 * If not matched, the field "object" will be null. In this situation, the unmatched word can be
 * extracted by using field "from" and "tail".<br>
 * 
 * @author kenny
 */
public class ScanResult {

        
    private int from;
    
    private int tail;
    
    private Object object;
    
    private boolean matched = true;
    
    
    public ScanResult(int from) {
        this.from = from;
    }
    
    
    public int getFrom() {
        return from;
    }
    
    
    public void setFrom(int from) {
        this.from = from;
    }
    
    
    public int getTail() {
        return tail;
    }
    
    
    public void setTail(int tail) {
        this.tail = tail;
    }
    
    
    public boolean isMatched() {
        return matched;
    }


    public void setMatched(boolean matched) {
        this.matched = matched;
    }


    public Object getObject() {
        return object;
    }
    
    
    public void setObject(Object object) {
        this.object = object;
    }
    

}//class ScanResult
