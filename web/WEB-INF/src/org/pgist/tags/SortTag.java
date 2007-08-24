package org.pgist.tags;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.pgist.wfengine.util.Utils;


/**
 * 
 * @author kenny
 *
 */
public class SortTag extends SimpleTagSupport {
    
    
    private static final long serialVersionUID = 1L;
    
    
    private String name = null;
    
    private Collection items = null;
    
    private String key = null;
    
    
    public void setName(String name) {
        this.name = name;
    }


    public void setItems(Collection items) {
        this.items = items;
    }


    public void setKey(String key) {
        this.key = key;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void doTag() throws JspException, IOException {
        Collection list = new TreeSet(
            new Comparator() {
                public int compare(Object o1, Object o2) {
                    try {
                        String value1 = BeanUtils.getProperty(o1, key);
                        String value2 = BeanUtils.getProperty(o2, key);
                        
                        if (value1==null || value2==null) throw new Exception("null values is compared");
                        
                        return value1.compareTo(value2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new ClassCastException(e.getMessage());
                    }
                }//compare()
            }
        );
        
        getJspContext().setAttribute(name, list);
        
        if (items==null) return;
        
        for (Object one : items) {
            Object object = Utils.narrow(one);
            list.add(one);
        }
    }//doTag()
    
    
}//class SortTag
