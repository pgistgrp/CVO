package org.pgist.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/**
 * 
 * @author kenny
 *
 */
public class FakeResponseWrapper extends HttpServletResponseWrapper {
    
    
    private PrintWriter tpWriter; 
    
    private FakeOutputStream tpStream;
    
    
    public FakeResponseWrapper(ServletResponse response) throws java.io.IOException { 
        super((HttpServletResponse) response);
        tpStream = new FakeOutputStream();
        tpWriter = new PrintWriter(tpStream);
    }
    
    
    public ServletOutputStream getOutputStream() throws IOException {
        return tpStream;
    }//getOutputStream()
    
    
    public PrintWriter getWriter() throws IOException {
        return tpWriter;
    }//getWriter()
    
    
}//class FakeResponseWrapper
