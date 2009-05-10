package org.pgist.search;

import org.apache.lucene.index.IndexWriter;
import org.pgist.sarp.drt.DRTDAO;

public class IndexDRTCommentHandler implements IndexHandler {
    
    private DRTDAO drtDAO;
    
    public void setDrtDAO(DRTDAO drtDAO) {
        this.drtDAO = drtDAO;
    }

    
    /*
     * ------------------------------------------------------------------------
     */
    

    @Override
    public void index(IndexWriter writer, IndexingTask task) throws Exception {
        
    } // indexConcern()
    
} //class IndexDRTCommentHandler
