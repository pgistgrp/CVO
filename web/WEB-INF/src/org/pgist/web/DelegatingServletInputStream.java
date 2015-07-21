package org.pgist.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;


/**
 * 
 * @author kenny
 *
 */
public class DelegatingServletInputStream extends ServletInputStream {
    
    
    private final InputStream proxy;
    
    private StringBuilder sb = new StringBuilder();
    
    
    public DelegatingServletInputStream(InputStream proxy) {
        this.proxy = proxy;
    }

    
    public InputStream getTargetStream() {
        return proxy;
    }
    
    
    public InputStream getSourceStream() {
        return proxy;
    }
    
    
    public int read() throws IOException {
        int one = proxy.read();
        if (one!=-1) {
            char ch = (char) one;
            sb.append(ch);
        }
        return one;
    }
    
    
    public void close() throws IOException {
        super.close();
        proxy.close();
    }
    
    
    public String getContent() {
        return sb.toString();
    }


	public boolean isFinished() {
		try {
			return proxy.available() == 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
	}

	public boolean isReady() {
		return true;
	}


	public void setReadListener(ReadListener arg0) {
		// TODO Auto-generated method stub
		
	}
    
    
}//class DelegatingServletInputStream
