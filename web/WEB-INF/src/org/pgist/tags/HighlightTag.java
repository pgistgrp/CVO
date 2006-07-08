package org.pgist.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 * 
 * @author kenny
 *
 */
public class HighlightTag extends BodyTagSupport {

    
    private static final long serialVersionUID = -7932209094743723372L;

    private String text;
    
    private String style;
    
    private String styleClass;
    
    
    private void clear() {
        text = null;
        style = null;
        styleClass = null;
    }
    
    
    public HighlightTag() {
        clear();
    }
    
    
    public void setText(String text) {
        this.text = text.trim();
    }


    public void setStyle(String style) {
        this.style = style.trim();
    }


    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass.trim();
    }


    /*
     * ------------------------------------------------------------------------
     */


    public int doAfterBody() throws JspException {
        if ((style==null || "".equals(style)) && (styleClass==null || "".equals(styleClass))) throw new JspException("at least one attribute of style and styleClass should be defined");
        
        String attribute = "style";
        String value = style;
        if (style==null) {
            attribute = "class";
            value = styleClass;
        }
        
        BodyContent body = getBodyContent();
        
        if (body==null) return SKIP_BODY;
        
        String content = body.getString();
        
        if (content==null || "".equals(content)) return SKIP_BODY;
        
        StringBuffer sb = new StringBuffer();
        
        int start = 0;
        int index = -1;
        int length = text.length();
        int contentLen = content.length();
        
        while((index = content.indexOf(text, start)) >= 0) {
            sb.append(content.substring(start, index));
            sb.append("<span ").append(attribute).append("=\"").append(value).append("\">");
            sb.append(content.substring(index, index+length));
            sb.append("</span>");
            start = index + length;
            if (start>=contentLen) break;
        }//while
        
        if (index==-1) {
            sb.append(content.substring(start));
        }
        
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
    
    
}//class HighlightTag
