package org.pgist.search;

import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.pgist.sarp.drt.Comment;
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
        Comment comment = drtDAO.getCommentById(new Long(task.getObjectId()));
        
        Query query = null;
        
        if ("indexing".equals(task.getAction())) {
            doIndex(writer, "drt-comment", comment.getCreateTime(), comment.getTitle(), comment.getContent(), task.getObjectId(), task.getWorkflowId(), task.getLink());
            
            System.out.println("---- Done indexing drt comment "+task.getId());
        } else if ("removing".equals(task.getAction())) {
            query = parser.parse("objectId:"+comment.getId() +" AND type:drt-comment");
            doRemove(writer, query);
            
            System.out.println("---- Done removing drt comment "+task.getId());
        } else if ("reindexing".equals(task.getAction())) {
            query = parser.parse("objectId:"+comment.getId() +" AND type:drt-comment");
            Document doc = getDocument(searcher, query);
            doRemove(writer, query);
            doIndex(writer, "drt-comment", comment.getCreateTime(), comment.getTitle(), comment.getContent(), doc.get("objectId"), doc.get("workflowId"), doc.get("link"));
            
            System.out.println("---- Done reindexing drt comment "+task.getId());
        }
    } // index()
    
    
} //class IndexDRTCommentHandler
