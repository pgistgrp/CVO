package org.pgist.search;

import java.io.IOException;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

public abstract class IndexHandler {
    
    protected static void doRemove(IndexWriter writer, Query query) throws Exception {
        writer.deleteDocuments(query);
    } //doRemove()
    
    
    protected static Document getDocument(IndexSearcher searcher, Query query) throws Exception {
        ScoreDoc[] scoreDocs =searcher.search(query, null, 1).scoreDocs;
        
        if (scoreDocs!=null && scoreDocs.length>0) {
            return searcher.doc(scoreDocs[0].doc);
        }
        
        return null;
    }
    
    
    protected static String extractHTML(String html) throws IOException, ParserException {
        PlainTextVisitor visitor = new PlainTextVisitor();
        
        Parser parser = Parser.createParser(html, "utf-8");
        parser.visitAllNodesWith(visitor);
        
        return visitor.getText();
    }
    
    
    protected void doIndex(IndexWriter writer, String type, Date date, String title, String content, String objectId, String workflowId, String link) throws Exception {
        content = extractHTML(content);
        
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
    
    
    public static void main(String[] args) throws Exception {
        String s = "<script>var x=100000;</script><style>.kdsfdsf {sdfsdfsdf sfds fsdfds}</style><!-- ajdsfasdf asdfjas dfsa dfas df --><a rel=\"nofollow\" href=\"http://networkworld.com/\">coondoggie</a> writes <em>&quot;Robocalls are a scourge, and the Federal Trade Commission today took action against one outfit by asking a federal court to <a href=\"http://www.networkworld.com/community/node/41859\">shut down companies that have been bombarding consumers</a> with hundreds of millions of allegedly deceptive robocalls in an effort to sell vehicle service contracts. According to the FTC, the robocalls have prompted tens of thousands of complaints from consumers who are either on the Do Not Call Registry or asked not to be called. Five telephone numbers associated with the defendants have generated a total of 30,000 Do Not Call complaints. Consumers received the robocalls at home, work, and on their cell phones, sometimes several times in one day. Businesses, government offices and even 911 dispatchers also have been subjected to the calls, the FTC said.&quot;</em> Reader powerlord points out that another such company, not named in the FTC filing, raised the ire of thousands of internet-goers, who struck back by <a href=\"http://online.wsj.com/article/SB124234497033421649.html\">rickrolling the company&#39;s voice mail</a> and digging up personal information on the company&#39;s president.";
        String output = IndexHandler.extractHTML(s);
        System.out.println("----> "+output);
    }
}
