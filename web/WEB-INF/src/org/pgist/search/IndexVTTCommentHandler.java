package org.pgist.search;

import org.apache.lucene.index.IndexWriter;
import org.pgist.sarp.vtt.VTTDAO;

public class IndexVTTCommentHandler implements IndexHandler {
    
    private VTTDAO vttDAO;
    
    public void setVttDAO(VTTDAO vttDAO) {
        this.vttDAO = vttDAO;
    }

    
    /*
     * ------------------------------------------------------------------------
     */
    

    @Override
    public void index(IndexWriter writer, IndexingTask task) throws Exception {
        
    } // indexConcern()
    
} //class IndexVTTCommentHandler
