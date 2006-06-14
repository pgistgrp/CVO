package org.pgist.ddl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;


/**
 * 
 * @author kenny
 *
 */
public class SystemHandler {
    
    
    private List<Handler> handlers = new ArrayList<Handler>(20);
    
    
    public SystemHandler(Session session, File dataPath, String file) throws Exception {
        Handler.setSession(session);
        Handler.dataPath = dataPath;
        
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(dataPath, file));
        Element root = document.getRootElement();
        
        List elements = root.elements("handler");
        for (int i=0; i<elements.size(); i++) {
            Element element = (Element) elements.get(i);
            
            String name = element.attributeValue("name");
            if (name==null || "".equals(name)) throw new Exception("Attribute name is required for handler!");
            
            String className = element.getTextTrim();
            if (className==null || "".equals(className)) throw new Exception("Element handler can not be empty.!");
            
            Class klass = Class.forName(className);
            Handler handler = (Handler) klass.newInstance();
            handler.setName(name);
            
            handlers.add(handler);
        }//for i
    }//SystemHandler()
    
    
    public void imports(String suffix) throws Exception {
        for (Handler handler : handlers) {
            handler.imports(suffix);
        }//for
    }//imports()
    
    
    public void exports(String suffix) throws Exception {
        for (Handler handler : handlers) {
            handler.exports(suffix);
        }//for
    }//exports()
    
    
}//class SystemHandler
