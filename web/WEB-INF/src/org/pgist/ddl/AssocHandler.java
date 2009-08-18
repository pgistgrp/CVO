package org.pgist.ddl;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.users.Assoc;


/**
 * 
 * @author michalis
 *
 */
public class AssocHandler extends XMLHandler {
    
    
    public void doImports(Element root) throws Exception {
        List assocs = root.elements("assoc");
        for (int i=0,n=assocs.size(); i<n; i++) {
            Element element = (Element) assocs.get(i);
            
            String name = element.getTextTrim();
            if (name==null || "".equals(name)) throw new Exception("name is required for association");
            
            Assoc assoc = getAssocByName(name);
            if (assoc==null) {
                assoc = new Assoc();
                assoc.setName(name);
                assoc.setDescription(name);
                assoc.setDeleted(false);
                assoc.setInternal(true);
                saveAssoc(assoc);
            }
        }//for i
    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("assocs");
        
        List<Assoc> assocs = getAssocs();
        
        for (Assoc assoc : assocs) {
            Element one = root.addElement("assoc");
            one.setText(assoc.getName());
        }//for
    }//doExports()


}//class AssocHandler
