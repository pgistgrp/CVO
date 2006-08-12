package org.pgist.util;

public class ScanResult {

        
    private int from;
    
    private int tail;
    
    private int length;
    
    private Object object;
    
    
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
        length = tail - from + 1;
    }
    
    
    public int getLength() {
        return length;
    }


    public int length() {
        return length;
    }


    public Object getObject() {
        return object;
    }
    
    
    public void setObject(Object object) {
        this.object = object;
    }
    

}//class ScanResult
