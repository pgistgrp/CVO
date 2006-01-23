package org.pgist.tags;

import java.io.IOException;
import java.util.Iterator;

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
public class TreeTag extends TagSupport {

    
    private static final long serialVersionUID = -4358153734927746923L;

    private String id;
    
    private String var;
    
    private Node node;
    
    private boolean dragable = false;
    
    private String imagePath;
    
    private String dragHandler;
    
    private String clickHandler;
    
    private String stdImage;
    
    
    public void setId(String condition) {
        this.id = condition;
    }


    public void setVar(String var) {
        this.var = var;
    }


    public void setNode(Node node) {
        this.node = node;
    }


    public void setDragable(boolean dragable) {
        this.dragable = dragable;
    }


    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public void setDragHandler(String dragHandler) {
        this.dragHandler = dragHandler;
    }


    public void setClickHandler(String clickHandler) {
        this.clickHandler = clickHandler;
    }


    public void setStdImage(String stdImage) {
        this.stdImage = stdImage;
    }


    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }//doStartTag()
    
    
    public int doEndTag() throws JspException {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        String ctxPath = req.getContextPath();
        JspWriter writer = pageContext.getOut();
        try {
            writer.write(var);
            writer.write("=new dhtmlXTreeObject('"+id+"',\"100%\",\"100%\","+node.getId()+");");
            if (imagePath!=null) {
                writer.write(var);
                writer.write(".setImagePath('"+ctxPath+imagePath+"');");
            }
            if (dragable) {
                writer.write(var);
                writer.write(".enableDragAndDrop(true);");
            }
            if (dragHandler!=null) {
                writer.write(var);
                writer.write(".setDragHandler("+dragHandler+");");
            }
            if (clickHandler!=null) {
                writer.write(var);
                writer.write(".setOnClickHandler("+clickHandler+");");
            }
            if (stdImage!=null) {
                String[] images = stdImage.split(",");
                if (images.length<3) throw new Exception("standard images is invald!");
                writer.write(var);
                writer.write(".setStdImages('"+images[0].trim()+"','"+images[1].trim()+"','"+images[2].trim()+"');");
            }
            for (Iterator iter=node.getChildren().iterator(); iter.hasNext(); ) {
                Node one = (Node) iter.next();
                getJavascript(one, writer, node.getId());
            }//for iter
        } catch(Exception e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }//doEndTag()


    private void getJavascript(Node node, JspWriter writer, Long parentId) throws IOException {
        writer.write(var);
        writer.write(".insertNewItem("+parentId+","+node.getId()+",'");
        writer.write(node.getCaption());
        writer.write("',0,0,0,0,'');");
        
        for (Iterator iter=node.getChildren().iterator(); iter.hasNext(); ) {
            Node one = (Node) iter.next();
            getJavascript(one, writer, node.getId());
        }//for iter
    }//getJavascript()


}//class DragableTreeTag
