package org.pgist.tags;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.pgist.model.Node;


/**
 * 
 * @author kenny
 *
 */
public class TreeXMLTag extends TagSupport {

    
    private Node root;
    

    public void setRoot(Node root) {
        this.root = root;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */


    public int doStartTag() throws JspException {
        if (root==null) throw new JspException("attribute root can't be null.");
        
        return EVAL_BODY_INCLUDE;
    }//doStartTag()
    
    
    public int doEndTag() throws JspException {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        JspWriter writer = pageContext.getOut();
        
        try {
            writer.println("<tree id=\"0\">");
            
            for (Node node : (Collection<Node>) root.getChildren()) {
                processNode(writer, node);
            }//for
            
            writer.println("</tree>");
        } catch(Exception e) {
            throw new JspException(e);
        }
        
        return SKIP_BODY;
    }//doEndTag()


    private void processNode(JspWriter writer, Node node) throws IOException {
        writer.println("<item text=\""+node.getCaption()+"\" id=\""+node.getId()+"\" open=\"1\">");
        
        for (Node node1 : (Collection<Node>) node.getChildren()) {
            processNode(writer, node1);
        }//for node1
        
        writer.println("</item>");
    }//processNode()


}//class DragableTreeTag
