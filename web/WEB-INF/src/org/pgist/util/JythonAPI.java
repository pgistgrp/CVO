package org.pgist.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.python.util.PythonInterpreter;


/**
 * @author kenny
 *
 */
public class JythonAPI {
    
    
    private static boolean initialized = false;
    
    private String contextPath;
    
    private String scriptPath = "/WEB-INF/jython";
    
    
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    
    
    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public PythonInterpreter getInterpreter() {
        synchronized (this) {
            if (!initialized) {
                initialized = true;
                Properties postProperties = new Properties();
                postProperties.setProperty("python.home", contextPath+"/WEB-INF/lib/jython");
                PythonInterpreter.initialize(new Properties(), postProperties, null);
            }
        }
        return new PythonInterpreter();
    }
    
    
    public void run(PythonInterpreter interpreter, String file) throws FileNotFoundException {
        interpreter.execfile(new FileInputStream(contextPath+scriptPath+"/"+file));
    }
    
    
} //class JythonAPI
