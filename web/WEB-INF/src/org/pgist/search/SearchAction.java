package org.pgist.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author kenny
 *
 */
public class SearchAction extends Action {
    
    
    private SearchHelper searchHelper;
    
    
    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }


    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        SearchForm sform = (SearchForm) form;
        
        List list = new ArrayList();

        String queryStr = sform.getQueryStr();
        
        if (queryStr==null || "".equals(queryStr)) return mapping.findForward("index");
        
        IndexSearcher indexSearcher = null;
        
        try {
            indexSearcher = searchHelper.getIndexSearcher();
            
            Query query = searchHelper.getParser().parse("workflowid:"+sform.getWorkflowId()+" AND "+queryStr);
            
            Hits hits = indexSearcher.search(query);
            
            sform.setTotal(hits.length());
            
            final int HITS_PER_PAGE = 10;
            
            int end = Math.min(hits.length(), HITS_PER_PAGE);
            
            for (int i=0; i<end; i++) {
                Document doc = hits.doc(i);
                
                String type = doc.get("type");
                
                Map map = new HashMap();
                map.put("type", type);
                map.put("doc", doc);
                
                if ("post".equals(type)) {
                    map.put("isid", doc.get("isid"));
                    map.put("ioid", doc.get("ioid"));
                } else if ("reply".equals(type)) {
                    map.put("pid", doc.get("postid"));
                    map.put("rid", doc.get("replyid"));
                    map.put("isid", doc.get("isid"));
                    map.put("ioid", doc.get("ioid"));
                } else if ("concern".equals(type)) {
                    map.put("concernid", doc.get("concernid"));
                } else if ("comment".equals(type)) {
                    map.put("commentid", doc.get("commentid"));
                }
                
                list.add(map);
            }//for i
        } finally {
            indexSearcher.close();
        }
        
        sform.setResults(list);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("results");
    }//execute()
    
    
}//class SearchAction
