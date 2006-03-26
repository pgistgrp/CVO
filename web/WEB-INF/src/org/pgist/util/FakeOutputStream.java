package org.pgist.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;


/**
 * 
 * @author kenny
 *
 */
public class FakeOutputStream extends ServletOutputStream {
    
    
    private ByteArrayOutputStream baStream;
    
    private boolean closed = false;
    
    
    public FakeOutputStream() {
        baStream = new ByteArrayOutputStream();
    }
    
    
    public void write(int i) throws java.io.IOException {
        baStream.write(i);
    }//write()
    
    
    public void close() throws java.io.IOException {
        if (!closed) {
          closed = true;
        }
    }//close()
    
    
    public String getContent() {
        return baStream.toString();
    }//getContent()
    
    
}//class FakeOutputStrea
