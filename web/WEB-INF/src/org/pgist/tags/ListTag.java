package org.pgist.tags;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.util.IteratorAdapter;


/**
 * 
 * @author kenny
 *
 */
public class ListTag extends TagSupport {

    
    private static final long serialVersionUID = -4358153734927746923L;

    private String id;
    
    private String var;
    
    private Object list;
    
    private boolean dragable = false;
    
    private String imagePath;
    
    private String dragHandler;
    
    private String stdImage;
    
    private String identity;
    
    private String title;
    
    private Iterator iterator;
    
    
    public void setId(String condition) {
        this.id = condition;
    }


    public void setVar(String var) {
        this.var = var;
    }


    public void setList(Object list) {
        this.list = list;
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


    public void setStdImage(String stdImage) {
        this.stdImage = stdImage;
    }


    public void setIdentity(String identity) {
        this.identity = identity;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }//doStartTag()
    
    
    public int doEndTag() throws JspException {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        String ctxPath = req.getContextPath();
        JspWriter writer = pageContext.getOut();
        try {
            // Acquire the collection we are going to iterate over
            Object collection = list;
            if (collection == null) {
                return EVAL_PAGE;
            }
            
            // Construct an iterator for this collection
            if (collection.getClass().isArray()) {
                try {
                    iterator = Arrays.asList((Object[]) collection).iterator();
                } catch (ClassCastException e) {
                    // Rats -- it is an array of primitives
                    int length = Array.getLength(collection);
                    ArrayList c = new ArrayList(length);
                    for (int i = 0; i < length; i++) {
                        c.add(Array.get(collection, i));
                    }
                    iterator = c.iterator();
                }
            } else if (collection instanceof Collection) {
                iterator = ((Collection) collection).iterator();
            } else if (collection instanceof Iterator) {
                iterator = (Iterator) collection;
            } else if (collection instanceof Map) {
                iterator = ((Map) collection).entrySet().iterator();
            } else if (collection instanceof Enumeration) {
                iterator = new IteratorAdapter((Enumeration) collection);
            }

            writer.write(var);
            writer.write("=new dhtmlXTreeObject('"+id+"',\"100%\",\"100%\",0);");
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
            if (stdImage!=null) {
                String[] images = stdImage.split(",");
                if (images.length<3) throw new Exception("standard images is invald!");
                writer.write(var);
                writer.write(".setStdImages('"+images[0].trim()+"','"+images[1].trim()+"','"+images[2].trim()+"');");
            }
            
            if (iterator==null) return EVAL_PAGE;
            
            while(iterator.hasNext()) {
                Object one = iterator.next();
                writer.write(var);
                writer.write(".insertNewItem(0,"+BeanUtils.getProperty(one, identity)+",'");
                writer.write(BeanUtils.getProperty(one, title));
                writer.write("',0,0,0,0,'');");
            }//while
        } catch(Exception e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }//doEndTag()


}//class DragableTreeTag
