package org.pgist.tags;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.pgist.util.FastByteArrayOutputStream;
import org.pgist.wfengine.EnvironmentInOuts;


/**
 * 
 * @author kenny
 *
 */
public class WorkflowIncludeTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    private String property;
    
    
    public void setProperty(String property) {
		this.property = property;
	}


    /*
     * ------------------------------------------------------------------------
     */
    
    
	public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        
        EnvironmentInOuts inouts = (EnvironmentInOuts) request.getAttribute("org.pgist.wfengine.INOUTS");
        
        if (inouts!=null) {
        	String page = inouts.getProperty(property);
        	if (page!=null) {
        		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
    			PageResponse pageResponse = new PageResponse((HttpServletResponse) context.getResponse());
        		try {
        			dispatcher.include(request, pageResponse);
        			
    	            pageResponse.getContent().writeTo(context.getOut(), null);
        		} catch (ServletException se) {
        			throw new JspException(se);
				}
        	}
        }
    }//doTag()
    
    
    /**
     * Copied from struts 2.0.11 code base.
     * 
     * Implementation of ServletOutputStream that stores all data written
     * to it in a temporary buffer accessible from {@link #getBuffer()} .
     *
     * @author <a href="joe@truemesh.com">Joe Walnes</a>
     * @author <a href="mailto:scott@atlassian.com">Scott Farquhar</a>
     */
    static final class PageOutputStream extends ServletOutputStream {

        private FastByteArrayOutputStream buffer;


        public PageOutputStream() {
            buffer = new FastByteArrayOutputStream();
        }

        /**
         * Return all data that has been written to this OutputStream.
         */
        public FastByteArrayOutputStream getBuffer() throws IOException {
            flush();

            return buffer;
        }

        public void close() throws IOException {
            buffer.close();
        }

        public void flush() throws IOException {
            buffer.flush();
        }

        public void write(byte[] b, int o, int l) throws IOException {
            buffer.write(b, o, l);
        }

        public void write(int i) throws IOException {
            buffer.write(i);
        }

        public void write(byte[] b) throws IOException {
            buffer.write(b);
        }
    }
    
    
    /**
     * Copied from struts 2.0.11 code base.
     * 
     * Simple wrapper to HTTPServletResponse that will allow getWriter()
     * and getResponse() to be called as many times as needed without
     * causing conflicts.
     * <p/>
     * The underlying outputStream is a wrapper around
     * {@link PageOutputStream} which will store
     * the written content to a buffer.
     * <p/>
     * This buffer can later be retrieved by calling {@link #getContent}.
     *
     * @author <a href="mailto:joe@truemesh.com">Joe Walnes</a>
     * @author <a href="mailto:scott@atlassian.com">Scott Farquhar</a>
     */
    static final class PageResponse extends HttpServletResponseWrapper {

        protected PrintWriter pagePrintWriter;
        protected ServletOutputStream outputStream;
        private PageOutputStream pageOutputStream = null;


        /**
         * Create PageResponse wrapped around an existing HttpServletResponse.
         */
        public PageResponse(HttpServletResponse response) {
            super(response);
        }


        /**
         * Return the content buffered inside the {@link PageOutputStream}.
         *
         * @return
         * @throws IOException
         */
        public FastByteArrayOutputStream getContent() throws IOException {
            //if we are using a writer, we need to flush the
            //data to the underlying outputstream.
            //most containers do this - but it seems Jetty 4.0.5 doesn't
            if (pagePrintWriter != null) {
                pagePrintWriter.flush();
            }

            return ((PageOutputStream) getOutputStream()).getBuffer();
        }

        /**
         * Return instance of {@link PageOutputStream}
         * allowing all data written to stream to be stored in temporary buffer.
         */
        public ServletOutputStream getOutputStream() throws IOException {
            if (pageOutputStream == null) {
                pageOutputStream = new PageOutputStream();
            }

            return pageOutputStream;
        }

        /**
         * Return PrintWriter wrapper around PageOutputStream.
         */
        public PrintWriter getWriter() throws IOException {
            if (pagePrintWriter == null) {
                pagePrintWriter = new PrintWriter(new OutputStreamWriter(getOutputStream(), getCharacterEncoding()));
            }

            return pagePrintWriter;
        }
    }
    
    
}//class WorkflowInfoTag
