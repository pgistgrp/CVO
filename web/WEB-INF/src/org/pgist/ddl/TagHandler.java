package org.pgist.ddl;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.tag.Tag;


/**
 * 
 * @author kenny
 *
 */
public class TagHandler extends Handler {
    
    
    public void doImports(Element root) throws Exception {
        List tags = root.elements("tag");
        for (int i=0,n=tags.size(); i<n; i++) {
            Element element = (Element) tags.get(i);
            
            String name = element.getTextTrim();
            if (name==null || "".equals(name)) throw new Exception("name is required for tag");
            
            Tag tag = getTagByName(name);
            if (tag==null) {
                tag = new Tag();
                tag.setName(name);
                tag.setStatus(parseTagStatus(element.attributeValue("status")));
                tag.setCount(0);
                saveTag(tag);
            }
        }//for i
    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("tags");
        
        List<Tag> tags = getTags();
        
        for (Tag tag : tags) {
            Element one = root.addElement("tag");
            one.setText(tag.getName());
        }//for
    }//doExports()
    
    
}//class TagHandler
