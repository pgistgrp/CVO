package org.pgist.search;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;


/**
 * 
 * @author kenny
 *
 */
public class SearchHelper {
    
    
    private String contextPath;
    
    private String indexPath;
    
    private String absolutePath;
    
    
    public SearchHelper() {
    }


    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }//setIndexPath()
    
    
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    
    
    public IndexSearcher getIndexSearcher() throws IOException {
        if (absolutePath==null) {
            absolutePath = contextPath + indexPath;
        }
        return new IndexSearcher(absolutePath);
    }//getIndexSearcher()


    public IndexWriter getIndexWriter() throws IOException   {
        if (absolutePath==null) {
            absolutePath = contextPath + indexPath;
        }
        return new IndexWriter(absolutePath, new StandardAnalyzer(), false);
    }//getIndexWriter()
    
    
}//class SearchHelper
