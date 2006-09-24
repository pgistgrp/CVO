package org.pgist.ddl;

import java.io.File;

import org.hibernate.Session;


/**
 * 
 * @author kenny
 *
 */
public interface Handler {
    
    
    void setName(String name);
    
    void setDataPath(File dataPath);
    
    void setSession(Session session);
    
    void imports(String suffix) throws Exception;
    
    void exports(String suffix) throws Exception;

    
}//interface Handler
