package org.pgist.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.cvo.CVO;
import org.pgist.cvo.CVODAO;
import org.pgist.model.Post;


/**
 * 
 * @author kenny
 *
 */
public class SearchAction extends Action {
    
    
    private SearchHelper searchHelper;
    
    private CVODAO cvoDAO;
    
    
    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }


    public void setCvoDAO(CVODAO cvoDAO) {
        this.cvoDAO = cvoDAO;
    }


    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        SearchForm sform = (SearchForm) form;
        
        String queryStr = sform.getQueryStr();
        
        if (queryStr==null || "".equals(queryStr)) return mapping.findForward("index");
        
        IndexSearcher indexSearcher = searchHelper.getIndexSearcher();
        Analyzer analyzer = new StandardAnalyzer();
        Query query = QueryParser.parse(queryStr, "contents", analyzer);
        
        List list = new ArrayList();
        
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
            if ("concern".equals(type) || "comment".equals(type)) {
                String id = doc.get("id");
                Post post = cvoDAO.getPostById(new Long(id));
                String cvoId = doc.get("cvoId");
                CVO cvo = cvoDAO.getCVOById(new Long(cvoId));
                map.put("cvo", cvo);
                map.put("post", post);
            }
            list.add(map);
        }//for i
        
        indexSearcher.close();
        
        sform.setResults(list);
        
        return mapping.findForward("results");
    }//execute()
    
    
}//class SearchAction
