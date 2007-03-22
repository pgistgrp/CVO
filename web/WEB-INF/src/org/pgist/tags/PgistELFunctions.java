package org.pgist.tags;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;


/**
 * PGIST JSP Expression Language functions.<br>
 * 
 * Function list:
 * <ul>
 *   <li>contains(collection, object) - return true if object contained in collection</li>
 * </ul>
 * 
 * @author kenny
 *
 */
public class PgistELFunctions extends SimpleTagSupport {
    
    private Collection collection;
    private Object object;
    
    public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public static boolean contains(Collection collection, Object object) {
        if (collection==null) return false;
        return collection.contains(object);
    }//contains()
    
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();
        if(contains(this.collection, this.object)) {
            writer.write("true");        	        	
        } else {
            writer.write("false");        	
        }
    	
    }
}//class PgistELFunctions
