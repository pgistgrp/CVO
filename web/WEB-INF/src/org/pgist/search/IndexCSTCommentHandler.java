package org.pgist.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.pgist.sarp.cst.CSTComment;
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
        CSTComment comment = cstDAO.getCommentById(new Long(task.getObjectId()));
        Query query = null;
        
        if ("indexing".equals(task.getAction())) {
            doIndex(writer, "cst-comment", comment.getCreateTime(), comment.getTitle(), comment.getContent(), task.getObjectId(), task.getWorkflowId(), task.getLink());
            
            System.out.println("---- Done indexing cst comment "+task.getId());
        } else if ("removing".equals(task.getAction())) {
            query = parser.parse("objectId:"+comment.getId() +" AND type:cst-comment");
            doRemove(writer, query);
            
            System.out.println("---- Done removing cst comment "+task.getId());
        } else if ("reindexing".equals(task.getAction())) {
            query = parser.parse("objectId:"+comment.getId() +" AND type:cst-comment");
            Document doc = getDocument(searcher, query);
            doRemove(writer, query);
            doIndex(writer, "cst-comment", comment.getCreateTime(), comment.getTitle(), comment.getContent(), doc.get("objectId"), doc.get("workflowId"), doc.get("link"));
            
            System.out.println("---- Done reindexing cst comment "+task.getId());
        }
    } // index()
    
    
} //class IndexCSTCommentHandler
