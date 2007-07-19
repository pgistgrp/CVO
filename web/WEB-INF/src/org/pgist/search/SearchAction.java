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
import org.pgist.util.PageSetting;


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
        List list = new ArrayList();
        
        String queryStr = request.getParameter("queryStr");
        
        if (queryStr==null || "".equals(queryStr)) return mapping.findForward("index");
        
        IndexSearcher indexSearcher = null;
        PageSetting setting = null;
        
        try {
            indexSearcher = searchHelper.getIndexSearcher();
            
            Query query = searchHelper.getParser().parse("workflowid:"+request.getParameter("workflowId")+" AND "+queryStr);
            
            Hits hits = indexSearcher.search(query);
            
            setting = new PageSetting(20);
            setting.setPage(request.getParameter("page"));
            
            setting.setRowSize(hits.length());
            
            int start = setting.getFirstRow();
            int end = Math.min(hits.length(), start+setting.getRowOfPage());
            
            for (int i=start; i<end; i++) {
                Document doc = hits.doc(i);
                
                String type = doc.get("type");
                
                Map map = new HashMap();
                map.put("type", type);
                map.put("doc", doc);
                map.put("title", doc.get("title"));
                map.put("body", doc.get("body"));
                map.put("tags", doc.get("tags"));
                
                if ("post".equals(type)) {
                    map.put("isid", doc.get("isid"));
                    map.put("ioid", doc.get("ioid"));
                    map.put("postid", doc.get("postid"));
                } else if ("reply".equals(type)) {
                    map.put("postid", doc.get("postid"));
                    map.put("replyid", doc.get("replyid"));
                    map.put("isid", doc.get("isid"));
                    map.put("ioid", doc.get("ioid"));
                } else if ("concern".equals(type)) {
                    map.put("concernid", doc.get("concernid"));
                } else if ("comment".equals(type)) {
                    map.put("concernid", doc.get("concernid"));
                    map.put("commentid", doc.get("commentid"));
                }
                
                list.add(map);
            }//for i
        } finally {
            indexSearcher.close();
        }
        
        request.setAttribute("setting", setting);
        request.setAttribute("results", list);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("results");
    }//execute()
    
    
}//class SearchAction
