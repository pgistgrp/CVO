package org.pgist.ws;


/**
 * 
 * @author kenny
 *
 */
public interface WebServiceProvider {
    
    
    public Object call(String method, Object... args) throws Exception;
    
    
}//interface WebServiceProvider
