package org.pgist.search;

import org.apache.lucene.index.IndexWriter;
import org.pgist.sarp.bct.BCTDAO;

public class IndexConcernCommentHandler implements IndexHandler {
    
    private BCTDAO bctDAO;
    
    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */

    
    @Override
    public void index(IndexWriter writer, IndexingTask task) throws Exception {
        
    } // indexConcern()
    
} //class IndexConcernCommentHandler
