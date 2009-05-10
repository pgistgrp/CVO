package org.pgist.search;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.pgist.sarp.drt.DRTDAO;

public class IndexDRTCommentHandler extends IndexHandler {
    
    private DRTDAO drtDAO;
    
    public void setDrtDAO(DRTDAO drtDAO) {
        this.drtDAO = drtDAO;
    }

    
    /*
     * ------------------------------------------------------------------------
     */
    

    @Override
    public void index(IndexReader reader, IndexWriter writer, IndexSearcher searcher, QueryParser parser, IndexingTask task) throws Exception {
        
    } // indexConcern()
    
} //class IndexDRTCommentHandler
