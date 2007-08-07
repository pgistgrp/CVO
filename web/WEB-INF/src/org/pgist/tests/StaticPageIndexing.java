package org.pgist.tests;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.swing.text.html.HTMLDocument;

import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.pgist.search.SearchHelper;
import org.pgist.search.html.HTMLParser;


/**
 * 
 * @author kenny
 *
 */
public class StaticPageIndexing extends MatchingTask {
    
    
    private String indexPath;
    
    private File dataPath;
    
    private File sourcePath;
    
    
    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }
    
    
    public void setDataPath(String dataPath) {
        this.dataPath = new File(dataPath);
    }
    
    
    public void setSourcePath(File sourcePath) {
        this.sourcePath = sourcePath;
    }


    /*
     * ------------------------------------------------------------------------
     */


    /**
     * Execute the task
     */
    public void execute() throws BuildException {
        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BuildException(e);
        }
    }//execute()
    
    
    private void test() throws Exception {
        SearchHelper searchHelper = new SearchHelper();
        searchHelper.setContextPath("");
        searchHelper.setIndexPath(indexPath);
        
        IndexWriter writer = null;
        try {
            //delete all previous indexing
            IndexReader reader = null;
            try {
                reader = searchHelper.getIndexReader();
                reader.deleteDocuments(new Term("type", "staticpage"));
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            } finally {
                if (reader!=null) reader.close();
            }
            
            //now reindex with lucene
            writer = searchHelper.getIndexWriter();
            
            Document doc = new SAXReader().read(new File(dataPath, "static-pages.xml"));
            Element root = doc.getRootElement();
            
            for (Element one : (List<Element>) root.elements("page")) {
                String url = one.attributeValue("url");
                String path = one.getTextTrim();
                System.out.println(url+" ----> "+path);
                
                File file = new File(sourcePath, path);
                
                if (!file.exists()) {
                    System.out.println("    File not exists: "+ file.toString());
                    continue;
                }
                
                HTMLParser parser = new HTMLParser(new FileInputStream(file));
                
                org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();
                document.add(new Field("type", "staticpage", Field.Store.YES, Field.Index.UN_TOKENIZED));
                document.add(new Field("path", file.getPath(), Field.Store.YES, Field.Index.UN_TOKENIZED));
                document.add(new Field("url", url, Field.Store.YES, Field.Index.UN_TOKENIZED));
                document.add(new Field("contents", parser.getReader()));
                document.add(new Field("body", parser.getSummary(), Field.Store.YES, Field.Index.NO));
                document.add(new Field("title", parser.getTitle(), Field.Store.YES, Field.Index.TOKENIZED));
                writer.addDocument(document);
            }
            
            writer.optimize();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (writer!=null) writer.close();
        }
    }//test()
    
    
}//class StaticPageIndexing
