package org.pgist.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.pgist.sarp.vtt.VTTComment;
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
        VTTComment comment = vttDAO.getCommentById(new Long(task.getObjectId()));
        Query query = null;
        
        if ("indexing".equals(task.getAction())) {
            doIndex(writer, "vtt-comment", comment.getCreateTime(), comment.getTitle(), comment.getContent(), task.getObjectId(), task.getWorkflowId(), task.getLink());
            
            System.out.println("---- Done indexing vtt comment "+task.getId());
        } else if ("removing".equals(task.getAction())) {
            query = parser.parse("objectId:"+comment.getId() +" AND type:vtt-comment");
            doRemove(writer, query);
            
            System.out.println("---- Done removing vtt comment "+task.getId());
        } else if ("reindexing".equals(task.getAction())) {
            query = parser.parse("objectId:"+comment.getId() +" AND type:vtt-comment");
            Document doc = getDocument(searcher, query);
            doRemove(writer, query);
            doIndex(writer, "vtt-comment", comment.getCreateTime(), comment.getTitle(), comment.getContent(), doc.get("objectId"), doc.get("workflowId"), doc.get("link"));
            
            System.out.println("---- Done reindexing vtt comment "+task.getId());
        }
    } // index()
    
    
} //class IndexVTTCommentHandler
