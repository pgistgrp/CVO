package org.pgist.search;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.pgist.sarp.cst.CSTDAO;

public class IndexCSTCommentHandler extends IndexHandler {
    
    private CSTDAO cstDAO;
    
    public void setCstDAO(CSTDAO cstDAO) {
        this.cstDAO = cstDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */

    @Override
    public void index(IndexReader reader, IndexWriter writer, IndexSearcher searcher, QueryParser parser, IndexingTask task) throws Exception {
        
    } // indexConcern()
    
} //class IndexCSTCommentHandler
