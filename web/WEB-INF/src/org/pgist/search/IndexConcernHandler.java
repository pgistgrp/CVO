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
import org.pgist.sarp.bct.Concern;

public class IndexConcernHandler extends IndexHandler {
    
    
    private BCTDAO bctDAO;
    
    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }

    /*
     * ------------------------------------------------------------------------
     */

    
    @Override
    public void index(IndexReader reader, IndexWriter writer, IndexSearcher searcher, QueryParser parser, IndexingTask task) throws Exception {
        Concern concern = bctDAO.getConcernById(new Long(task.getObjectId()));
        Query query = null;
        
        if ("indexing".equals(task.getAction())) {
            doIndex(writer, "concern", concern.getCreateTime(), "", concern.getContent(), task.getObjectId(), task.getWorkflowId(), task.getLink());
            
            System.out.println("---- Done indexing concern "+task.getId());
        } else if ("removing".equals(task.getAction())) {
            query = parser.parse("objectId:"+concern.getId() +" AND type:concern");
            doRemove(writer, query);
            query = parser.parse("concernId:"+concern.getId() +" AND type:concern-comment");
            doRemove(writer, query);
            
            System.out.println("---- Done removing concern "+task.getId());
        } else if ("reindexing".equals(task.getAction())) {
            query = parser.parse("objectId:"+concern.getId() +" AND type:concern");
            Document doc = getDocument(searcher, query);
            doRemove(writer, query);
            doIndex(writer, "concern", concern.getCreateTime(), "", concern.getContent(), doc.get("objectId"), doc.get("workflowId"), doc.get("link"));
            
            System.out.println("---- Done reindexing concern "+task.getId());
        }
    } // index()
    
    
} //class IndexConcernHandler
