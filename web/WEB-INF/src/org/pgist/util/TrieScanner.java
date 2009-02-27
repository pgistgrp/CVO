package org.pgist.util;


/**
 * 
 * @author kenny
 *
 */
public interface TrieScanner {
    
    
    /**
     * Check if the current scanner already scanned to the end of paragraph
     * @return
     */
    public boolean eop();
    
    
    public ScanResult scan();
    
    
}//interface TrieScanner
