package org.pgist.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.pgist.glossary.Term;
import org.pgist.glossary.TermAnalyzer;
import org.pgist.util.ScanResult;
import org.springframework.web.context.WebApplicationContext;


/**
 * 
 * @author kenny
 *
 */
public class TermHighlightTag extends BodyTagSupport {

    
    private static final long serialVersionUID = -7932209094743723372L;
    
    private String strategy = "default";
    
    private int count = 0;
    
    private String style;
    
    private String styleClass;
    
    private String url = "/term.do?id=";
    
    
    private void clear() {
        strategy = null;
        style = null;
        styleClass = null;
    }
    
    
    public TermHighlightTag() {
        clear();
    }
    
    
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }


    public void setCount(int count) {
        this.count = count;
    }


    public void setStyle(String style) {
        this.style = style.trim();
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass.trim();
    }


    public void setUrl(String url) {
        this.url = url;
    }


    /*
     * ------------------------------------------------------------------------
     */


    public int doAfterBody() throws JspException {
        String attribute = null;
        String value = null;
        if (style!=null) {
            attribute = "style";
            value = style;
        } else if (styleClass!=null) {
            attribute = "class";
            value = styleClass;
        }
        
        BodyContent body = getBodyContent();
        
        if (body==null) return SKIP_BODY;
        
        String content = body.getString();
        
        if (content==null || "".equals(content)) return SKIP_BODY;
        
        /*
         * Get the TermAnalyzer from spring
         */
        WebApplicationContext context = (WebApplicationContext) pageContext.getServletContext()
            .getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        
        TermAnalyzer analyzer = (TermAnalyzer) context.getBean("termAnalyzer");
        
        StringBuffer sb = new StringBuffer();
        
        List list = analyzer.highlight(content, count);
        
        int start = 0;
        int from, tail;
        for (int i=0; i<list.size(); i++) {
            ScanResult result = (ScanResult) list.get(i);
            Term term = (Term) result.getObject();
            
            from = result.getFrom();
            tail = result.getTail();
            
            if (from>start) {
                /*
                 * output normal text
                 */
                sb.append(content.substring(start, from));
                
                /*
                 * output term URL
                 */
                sb.append("<a href=\"").append(url).append(term.getId()).append("\"");
                sb.append(" title=\"").append(term.getShortDefinition().replace("\"", "&quot;")).append("\" ");
                
                if (attribute!=null) {
                    sb.append(attribute).append("=\"").append(value);
                }
                
                sb.append("\">").append(content.substring(from, tail)).append("</a>");
                
                start = tail;
            }
        }//for i
        
        sb.append(content.substring(start));
        
        try {
            body.getEnclosingWriter().print(sb.toString());
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }
        
        return SKIP_BODY;
    }//doAfterBody()
    
    
    public int doEndTag() throws JspException {
        clear();
        return EVAL_PAGE;
    }//doEndTag()
    
    
    public void release() {
        clear();
    }//release()
    
    
}//class TermHighlightTag
