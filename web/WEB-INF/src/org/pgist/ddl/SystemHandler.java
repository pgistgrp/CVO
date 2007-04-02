package org.pgist.ddl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;


/**
 * 
 * @author kenny
 *
 */
public class SystemHandler {
    
    
    private List<Handler> handlers = new ArrayList<Handler>(20);
    
    
    public SystemHandler(ApplicationContext appContext, Session session, File dataPath, String file) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(dataPath, file));
        Element root = document.getRootElement();
        
        List elements = root.elements("handler");
        Handler handler = null;
        for (int i=0; i<elements.size(); i++) {
            Element element = (Element) elements.get(i);
            
            String type = element.attributeValue("type");
            
            if ("sql".equals(type)) {
                handler = new ScriptHandler();
                handler.setName(element.getTextTrim());
            } else {
                String name = element.attributeValue("name");
                if (name==null || "".equals(name)) throw new Exception("Attribute name is required for handler!");
                
                String className = element.getTextTrim();
                if (className==null || "".equals(className)) throw new Exception("Element handler can not be empty.!");
                
                Class klass = Class.forName(className);
                XMLHandler xmlHandler = (XMLHandler) klass.newInstance();
                xmlHandler.setAppContext(appContext);
                
                handler = xmlHandler;
                
                handler.setName(name);
            }
            
            handler.setSession(session);
            handler.setDataPath(dataPath);
            
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
