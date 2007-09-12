package org.pgist.discussion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pgist.search.SearchHelper;
import org.pgist.util.PageSetting;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;


/**
 * @author kenny
 */
public class SDSearchAction extends Action {
    
    
    /**
     * Spring injected service
     */
    private SDService sdService;
    
    private SearchHelper searchHelper;
    
    
    public void setSdService(SDService sdService) {
        this.sdService = sdService;
    }
    
    
    public void setSearchHelper(SearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response
    ) throws Exception {
        String isid = request.getParameter("isid");
        if (isid==null) {
            isid = "";
        }
        
        String ioid = request.getParameter("ioid");
        if (ioid==null) {
            ioid = "";
        }
        
        String queryStr = request.getParameter("queryStr");
        if (queryStr==null) {
            queryStr = "";
        }
        
        PageSetting setting = new PageSetting();
        setting.setRowOfPage(request.getParameter("count"));
        setting.setPage(request.getParameter("page"));
        request.setAttribute("setting", setting);
        
        List results = new ArrayList();
        request.setAttribute("results", results);
        
        queryStr = searchHelper.prefixString(queryStr);
        
        if (queryStr.length()==0) {
            return mapping.findForward("view");
        }
        
        IndexSearcher searcher = null;
        try {
            searcher = searchHelper.getIndexSearcher();
            
            Hits hits = null;
            
            if (ioid.length() > 0) { 
                hits = searcher.search(searchHelper.getParser().parse(
                    "ioid:" + ioid + " AND (type:post OR type:reply) AND " + queryStr
                ));
            } else if (isid.length() > 0) {
                hits = searcher.search(searchHelper.getParser().parse(
                    "isid:" + isid + " AND (type:post OR type:reply) AND " + queryStr
                ));
            } else {
                request.setAttribute("reason", "Either ioid or isid is required!");
            }
            
            System.out.println("----- "+hits);
            
            setting.setRowSize(hits.length());
            
            int start = setting.getFirstRow();
            int end = Math.min(hits.length(), start+setting.getRowOfPage());
            
            for (int i=start; i<end; i++) {
                Document doc = hits.doc(i);
                
                String type = doc.get("type");
                
                Map result = new HashMap();
                result.put("type", type);
                result.put("doc", doc);
                result.put("body", doc.get("body"));
                result.put("workflowid", doc.get("workflowid"));
                result.put("contextid", doc.get("contextid"));
                result.put("activityid", doc.get("activityid"));
                result.put("isid", doc.get("isid"));
                result.put("ioid", doc.get("ioid"));
                result.put("postid", doc.get("postid"));
                result.put("title", doc.get("title"));
                result.put("tags", doc.get("tags"));
                
                if ("reply".equals(type)) {
                    result.put("replyid", doc.get("replyid"));
                }
                
                results.add(result);
            }//for i
            
            return mapping.findForward("view");
        } catch (Exception e) {
            e.printStackTrace();
            return mapping.findForward("error");
        }
    }//execute()
    
    
}//class SDSearchAction
