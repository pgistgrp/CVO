package org.pgist.util;


/**
 * 
 * @author kenny
 *
 */
public abstract class TrieScanner {
    
    
    protected String para;
    
    protected char ch;
    
    protected int index;
    
    protected int point = 0;
    
    protected int length;
    
    
    /**
     * skip to the next beginning
     */
    protected void skip() {
        if (ch>='a' && ch<='z') {
            while (index<length-1) {
                index++;
                ch = para.charAt(index);
                if (ch<'a' || ch>'z') break;
            }//while
            if (index==length) return;
        }
        
        while (index<length-1) {
            index++;
            ch = para.charAt(index);
            if (ch>='a' || ch<='z') break;
        }//while
    }//skip()
    
    
    public boolean eop() {
        return index>=length-1;
    }//eop()
    
    
    abstract public ScanResult scan();
    
    
}//interface TrieScanner
