package org.pgist.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

public abstract class IndexHandler {
    
    
    protected void doRemove(IndexWriter writer, Query query) throws Exception {
        writer.deleteDocuments(query);
    } //doRemove()
    
    
    protected Document getDocument(IndexSearcher searcher, Query query) throws Exception {
        ScoreDoc[] scoreDocs =searcher.search(query, null, 1).scoreDocs;
        
        if (scoreDocs!=null && scoreDocs.length>0) {
            return searcher.doc(scoreDocs[0].doc);
        }
        
        return null;
    }
    
    
    abstract public void index(IndexReader reader, IndexWriter writer, IndexSearcher searcher, QueryParser parser, IndexingTask task) throws Exception;
    
}
