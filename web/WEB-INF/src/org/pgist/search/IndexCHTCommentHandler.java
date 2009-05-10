package org.pgist.search;

import org.apache.lucene.index.IndexWriter;
import org.pgist.sarp.cht.CHTDAO;

public class IndexCHTCommentHandler implements IndexHandler {
    
    private CHTDAO chtDAO;
    
    public void setChtDAO(CHTDAO chtDAO) {
        this.chtDAO = chtDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */

    
    @Override
    public void index(IndexWriter writer, IndexingTask task) throws Exception {
        
    } // indexConcern()
    
} //class IndexCHTCommentHandler
