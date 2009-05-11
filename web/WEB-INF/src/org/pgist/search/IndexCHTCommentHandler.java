package org.pgist.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.pgist.sarp.cht.CHTComment;
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
        CHTComment comment = chtDAO.getCommentById(new Long(task.getObjectId()));
        Query query = null;
        
        if ("indexing".equals(task.getAction())) {
            doIndex(writer, "cht-comment", comment.getCreateTime(), comment.getTitle(), comment.getContent(), task.getObjectId(), task.getWorkflowId(), task.getLink());
            
            System.out.println("---- Done indexing cht comment "+task.getId());
        } else if ("removing".equals(task.getAction())) {
            query = parser.parse("objectId:"+comment.getId() +" AND type:cht-comment");
            doRemove(writer, query);
            
            System.out.println("---- Done removing cht comment "+task.getId());
        } else if ("reindexing".equals(task.getAction())) {
            query = parser.parse("objectId:"+comment.getId() +" AND type:cht-comment");
            Document doc = getDocument(searcher, query);
            doRemove(writer, query);
            doIndex(writer, "cht-comment", comment.getCreateTime(), comment.getTitle(), comment.getContent(), doc.get("objectId"), doc.get("workflowId"), doc.get("link"));
            
            System.out.println("---- Done reindexing cht comment "+task.getId());
        }
    } // index()
    
} //class IndexCHTCommentHandler
