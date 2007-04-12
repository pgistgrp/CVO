package org.pgist.ddl;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.funding.FundingSource;
import org.pgist.users.Role;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 */
public class DefaultFundingSourceHandler extends XMLHandler {
    
    
    public void doImports(Element root) throws Exception {
        List sources = root.elements("source");
        System.out.println("MATT sources = " + sources.size());
        for (int i=0,n=sources.size(); i<n; i++) {
            Element element = (Element) sources.get(i);

            String name = element.elementTextTrim("name");
            if (name==null || "".equals(name)) throw new Exception("name is required for Funding Source");
            FundingSource source = getFundingSourceByName(name);
            if(source == null) {
            	source = new FundingSource();
            	source.setName(name);
            }	
            source.setType(FundingSource.TYPE_TOLLS);
            
            saveFundingSource(source);
        }//for i
        
    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("fundingSources");
        
        List<FundingSource> sources = getFundingSources();
        for (FundingSource source: sources) {
            Element one = root.addElement("source");
            
            Element name = one.addElement("name");
            name.setText(source.getName());
            
        }//for user
    }//doExports()
    
    
}//class UserHandler
