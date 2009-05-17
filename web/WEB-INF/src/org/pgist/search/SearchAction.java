package org.pgist.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.other.ImportService;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class SearchAction extends Action {
    
    
    private SearchHelper searchHelper;
    
    private ImportService importService;
    
    private static final Map<String, String> TYPES = new HashMap<String, String>();
    
    
    static {
        TYPES.put("concern", "Concern");
        TYPES.put("concern-comment", "Concern Comment");
        TYPES.put("drt-comment", "DRT Comment");
        TYPES.put("cst-comment", "CST Comment");
        TYPES.put("cht-comment", "CHT Comment");
        TYPES.put("vtt-comment", "ICT Comment");
    } //static
    
    
    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }


    public void setImportService(ImportService importService) {
        this.importService = importService;
    }


    /*
     * ------------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws java.lang.Exception {
        List list = new ArrayList();
        
        String queryStr = request.getParameter("queryStr");
        if (queryStr==null) queryStr = "";
        else queryStr = queryStr.trim();
        
        /*
         * process the query string, add * to each word
         */
        
        queryStr = searchHelper.prefixString(queryStr);
        
        PageSetting setting = new PageSetting(20);
        request.setAttribute("setting", setting);
        if (queryStr==null || queryStr.length()==0) return mapping.findForward("index");
        
        String workflowId = request.getParameter("workflowId");
        
        IndexSearcher indexSearcher = null;
        
        try {
            indexSearcher = searchHelper.getIndexSearcher();
            
            String luceneQuery = "(workflowId:"+workflowId+" AND ("+queryStr+"))";            
            Query query = searchHelper.getParser().parse(luceneQuery);
            
            Hits hits = indexSearcher.search(query);
            
            setting.setPage(request.getParameter("page"));
            
            setting.setRowSize(hits.length());
            
            int start = setting.getFirstRow();
            int end = Math.min(hits.length(), start+setting.getRowOfPage());
            
            for (int i=start; i<end; i++) {
                Document doc = hits.doc(i);
                
                String type = TYPES.get(doc.get("type"));
                if (type==null) {
                    type = "Unknown";
                }
                
                Map map = new HashMap();
                map.put("type", type);
                map.put("title", doc.get("title"));
                map.put("doc", doc);
                map.put("body", doc.get("body"));
                map.put("workflowId", doc.get("workflowId"));
                map.put("link", doc.get("link"));
                
                list.add(map);
            }//for i
        } finally {
            indexSearcher.close();
        }
        
        request.setAttribute("queryStr", queryStr);
        request.setAttribute("results", list);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("results");
    }//execute()
    
    
}//class SearchAction
