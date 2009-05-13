package org.pgist.search;

import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
    
    
    protected void doIndex(IndexWriter writer, String type, Date date, String title, String content, String objectId, String workflowId, String link) throws Exception {
        Document doc = new Document();
        doc.add( new Field("type", type, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("date", date.toString(), Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("body", content, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("contents", title+" "+content, Field.Store.YES, Field.Index.ANALYZED) );
        doc.add( new Field("objectId", objectId, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("workflowId", workflowId, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("link", link, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        writer.addDocument(doc);
    }
    
    
    abstract public void index(IndexReader reader, IndexWriter writer, IndexSearcher searcher, QueryParser parser, IndexingTask task) throws Exception;
    
}
