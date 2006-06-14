package org.pgist.ddl;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pgist.cvo.StopWord;


/**
 * 
 * @author kenny
 *
 */
public class StopWordHandler extends Handler {
    
    
    public void doImports(Element root) throws Exception {
        List stopwords = root.elements("stopword");
        for (int i=0,n=stopwords.size(); i<n; i++) {
            Element element = (Element) stopwords.get(i);
            
            StopWord stopword = new StopWord();
            
            String name = element.getTextTrim();
            if (name==null || "".equals(name)) throw new Exception("name is required for stopword");
            stopword.setName(name);
            
            saveStopWord(stopword);
        }//for i
    }//imports()
    
    
    public void doExports(Document document) throws Exception {
        Element root = document.addElement("stopwords");
        
        List<StopWord> stopWords = getStopWords();
        for (StopWord stopWord : stopWords) {
            Element one = root.addElement("stopword");
            one.setText(stopWord.getName());
        }//for user
    }//doExports()
    
    
}//class StopWordHandler
