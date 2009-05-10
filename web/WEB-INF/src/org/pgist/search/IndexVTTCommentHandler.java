package org.pgist.search;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.pgist.sarp.vtt.VTTDAO;

public class IndexVTTCommentHandler extends IndexHandler {
    
    private VTTDAO vttDAO;
    
    public void setVttDAO(VTTDAO vttDAO) {
        this.vttDAO = vttDAO;
    }

    
    /*
     * ------------------------------------------------------------------------
     */
    

    @Override
    public void index(IndexReader reader, IndexWriter writer, IndexSearcher searcher, QueryParser parser, IndexingTask task) throws Exception {
        
    } // indexConcern()
    
} //class IndexVTTCommentHandler
