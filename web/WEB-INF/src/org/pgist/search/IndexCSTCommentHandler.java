package org.pgist.search;

import org.apache.lucene.index.IndexWriter;
import org.pgist.sarp.cst.CSTDAO;

public class IndexCSTCommentHandler implements IndexHandler {
    
    private CSTDAO cstDAO;
    
    public void setCstDAO(CSTDAO cstDAO) {
        this.cstDAO = cstDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */

    @Override
    public void index(IndexWriter writer, IndexingTask task) throws Exception {
        
    } // indexConcern()
    
} //class IndexCSTCommentHandler
