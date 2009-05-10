package org.pgist.search;

import org.apache.lucene.index.IndexWriter;

public interface IndexHandler {
    
    void index(IndexWriter writer, IndexingTask task) throws Exception;
    
}
