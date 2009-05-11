package org.pgist.search;

import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.pgist.sarp.bct.BCTDAO;
import org.pgist.sarp.bct.ConcernComment;

public class IndexConcernCommentHandler extends IndexHandler {
    
    
    private BCTDAO bctDAO;
    
    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */

    
    private void doIndex(IndexWriter writer, Date date, String title, String content, String objectId, String concernId, String workflowId, String link) throws Exception {
        Document doc = new Document();
        doc.add( new Field("type", "concern-comment", Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("date", date.toString(), Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("body", content, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("title", title, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("contents", title+" "+content, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("objectId", objectId, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("concernId", concernId, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("workflowId", workflowId, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        doc.add( new Field("link", link, Field.Store.YES, Field.Index.NOT_ANALYZED) );
        writer.addDocument(doc);
    }
    
    
    @Override
    public void index(IndexReader reader, IndexWriter writer, IndexSearcher searcher, QueryParser parser, IndexingTask task) throws Exception {
        ConcernComment comment = bctDAO.getConcernCommentById(new Long(task.getObjectId()));
        Query query = null;
        
        if ("indexing".equals(task.getAction())) {
            doIndex(writer, comment.getCreateTime(), comment.getTitle(), comment.getContent(), task.getObjectId(), comment.getConcern().getId().toString(), task.getWorkflowId(), task.getLink());
            
            System.out.println("---- Done indexing concern comment "+task.getId());
        } else if ("removing".equals(task.getAction())) {
            query = parser.parse("objectId:"+comment.getId() + " AND type:concern-comment");
            doRemove(writer, query);
            
            System.out.println("---- Done removing concern comment "+task.getId());
        } else if ("reindexing".equals(task.getAction())) {
            query = parser.parse("objectId:"+comment.getId() + " AND type:concernn-comment");
            Document doc = getDocument(searcher, query);
            doRemove(writer, query);
            doIndex(writer, comment.getCreateTime(), comment.getTitle(), comment.getContent(), doc.get("objectId"), comment.getConcern().getId().toString(), doc.get("workflowId"), doc.get("link"));
            
            System.out.println("---- Done reindexing concern comment "+task.getId());
        }
    } // index()
    
    
} //class IndexConcernCommentHandler
