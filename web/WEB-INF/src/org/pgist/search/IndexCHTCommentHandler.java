package org.pgist.search;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.pgist.sarp.cht.CHTDAO;

public class IndexCHTCommentHandler extends IndexHandler {
    
    private CHTDAO chtDAO;
    
    public void setChtDAO(CHTDAO chtDAO) {
        this.chtDAO = chtDAO;
    }


    /*
     * ------------------------------------------------------------------------
     */

    
    @Override
    public void index(IndexReader reader, IndexWriter writer, IndexSearcher searcher, QueryParser parser, IndexingTask task) throws Exception {
        
    } // indexConcern()
    
} //class IndexCHTCommentHandler
