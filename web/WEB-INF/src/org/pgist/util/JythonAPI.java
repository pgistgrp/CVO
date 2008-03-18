package org.pgist.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.python.util.PythonInterpreter;


/**
 * @author kenny
 *
 */
public class JythonAPI {
    
    
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
        return new PythonInterpreter();
    }
    
    
    public void run(PythonInterpreter interpreter, String file) throws FileNotFoundException {
        interpreter.execfile(new FileInputStream(contextPath+scriptPath+"/"+file));
    }
    
    
} //class JythonAPI
