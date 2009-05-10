package org.pgist.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.pgist.sarp.bct.Concern;
import org.pgist.sarp.bct.BCTDAO;

public class IndexConcernHandler implements IndexHandler {
    
    private BCTDAO bctDAO;
    
    public void setBctDAO(BCTDAO bctDAO) {
        this.bctDAO = bctDAO;
    }

    /*
     * ------------------------------------------------------------------------
     */

    @Override
    public void index(IndexWriter writer, IndexingTask task) throws Exception {
        Concern concern = bctDAO.getConcernById(task.getObjectId());
        
        if ("indexing".equals(task.getAction())) {
            Document doc = new Document();
            doc.add( new Field("type", "concern", Field.Store.YES, Field.Index.UN_TOKENIZED) );
            doc.add( new Field("date", concern.getCreateTime().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
            doc.add( new Field("body", concern.getContent(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
            doc.add( new Field("contents", concern.getContent(), Field.Store.YES, Field.Index.TOKENIZED) );
            doc.add( new Field("workflowId", task.getWorkflowId().toString(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
            doc.add( new Field("link", task.getLink(), Field.Store.YES, Field.Index.UN_TOKENIZED) );
            writer.addDocument(doc);
            
            System.out.println("---- Done indexing concern "+task.getId());
        } else if ("removing".equals(task.getAction())) {
            
        } else if ("reindexing".equals(task.getAction())) {
            
        }
    } // indexConcern()
    
} //class IndexConcernHandler
