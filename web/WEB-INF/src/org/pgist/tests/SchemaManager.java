package org.pgist.tests;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 
 * @author kenny
 *
 */
public class SchemaManager {
    
    
    private File file = null;
    
    
    public void setFile(File file) {
        this.file = file;
    }//setFile()
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public void executeDrop(Connection connection) throws Exception {
        Statement stmt = connection.createStatement();
        
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();

        List elements = root.element("drops").elements("statement");
        for (int i=0; i<elements.size(); i++) {
            Element element = (Element) elements.get(i);
            
            String exception = element.attributeValue("exception");
            String sql = element.elementTextTrim("script");
            
            try {
                stmt.executeUpdate(sql);
            } catch (Exception e) {
                e.printStackTrace();
                if ("throw".equalsIgnoreCase(exception)) throw e;
            }
        }//for i
    }//executeDrop()
    
    
    public void executeCreate(Connection connection) throws Exception {
        Statement stmt = connection.createStatement();
        
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element root = document.getRootElement();
        
        List elements = root.element("creates").elements("statement");
        for (int i=0; i<elements.size(); i++) {
            Element element = (Element) elements.get(i);
            
            String exception = element.attributeValue("exception");
            String sql = element.elementTextTrim("script");
            
            try {
                stmt.executeUpdate(sql);
            } catch (Exception e) {
                e.printStackTrace();
                if ("throw".equalsIgnoreCase(exception)) throw e;
            }
        }//for i
    }//executeCreate()
    
    
}//class XMLSchemaManager
