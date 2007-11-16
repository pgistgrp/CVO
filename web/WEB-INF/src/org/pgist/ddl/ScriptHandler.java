package org.pgist.ddl;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import org.hibernate.Session;

import workbench.sql.IteratingScriptParser;
import workbench.sql.ScriptCommandDefinition;


/**
 * 
 * @author kenny
 *
 */
public class ScriptHandler implements Handler {
    
    
    private String name;
    
    private File dataPath;
    
    private Session session;
    
    
    public void setName(String name) {
        this.name = name;
    }//setName()
    
    
    public void setDataPath(File dataPath) {
        this.dataPath = dataPath;
    }//setDataPath()
    
    
    public void setSession(Session session) {
        this.session = session;
    }//setSession
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void imports(String suffix) throws Exception {
        Connection connection = session.connection();
        Statement stmt = connection.createStatement();
        
        IteratingScriptParser parser = new IteratingScriptParser(new File(dataPath, name), "UTF-8");
        
        do {
            ScriptCommandDefinition definition = parser.getNextCommand();
            if (definition==null) break;
            
            String sql = definition.getSQL();
            stmt.execute(sql);
        } while (true);
    }//imports()
    
    
    public void exports(String suffix) throws Exception {
    }//exports()


}//class ScriptHandler
