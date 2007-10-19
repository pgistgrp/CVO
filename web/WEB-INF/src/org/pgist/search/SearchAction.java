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
import org.pgist.other.Experiment;
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
        
        if (queryStr==null || "".equals(queryStr)) return mapping.findForward("index");
        
        /*
         * process the query string, add * to each word
         */
        
        queryStr = searchHelper.prefixString(queryStr);
        
        if (queryStr==null || queryStr.length()==0) return mapping.findForward("index");
        
        String workflowId = request.getParameter("workflowId");
        
        IndexSearcher indexSearcher = null;
        PageSetting setting = null;
        
        try {
            indexSearcher = searchHelper.getIndexSearcher();
            
            String luceneQuery =
                //discussion and concern and project
                "(workflowid:"+workflowId+" AND ("+queryStr+"))"
                //user profile
                +" OR (type:userprofile AND ("+queryStr+"))"
                //static pages
                +" OR (type:staticpage AND ("+queryStr+"))";
            
            Query query = searchHelper.getParser().parse(luceneQuery);
            
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
                map.put("body", doc.get("body"));
                map.put("workflowid", doc.get("workflowid"));
                map.put("contextid", doc.get("contextid"));
                map.put("activityid", doc.get("activityid"));
                
                if ("post".equals(type)) {
                    map.put("isid", doc.get("isid"));
                    map.put("ioid", doc.get("ioid"));
                    map.put("postid", doc.get("postid"));
                    map.put("title", doc.get("title"));
                    map.put("tags", doc.get("tags"));
                } else if ("reply".equals(type)) {
                    map.put("postid", doc.get("postid"));
                    map.put("replyid", doc.get("replyid"));
                    map.put("isid", doc.get("isid"));
                    map.put("ioid", doc.get("ioid"));
                    map.put("title", doc.get("title"));
                    map.put("tags", doc.get("tags"));
                } else if ("concern".equals(type)) {
                    map.put("concernid", doc.get("concernid"));
                    map.put("title", doc.get("body").substring(0, 50));
                    map.put("tags", doc.get("tags"));
                } else if ("comment".equals(type)) {
                    map.put("concernid", doc.get("concernid"));
                    map.put("commentid", doc.get("commentid"));
                    map.put("title", doc.get("body").substring(0, 50));
                    map.put("tags", doc.get("tags"));
                } else if ("project".equals(type)) {
                    map.put("suiteid", doc.get("suiteid"));
                    map.put("projectid", doc.get("projectid"));
                    map.put("projectaltid", doc.get("projectaltid"));
                    map.put("projectaltname", doc.get("projectaltname"));
                } else if ("userprofile".equals(type)) {
                    map.put("userid", doc.get("userid"));
                    map.put("loginname", doc.get("loginname"));
                } else if ("staticpage".equals(type)) {
                    map.put("path", doc.get("path"));
                    map.put("url", doc.get("url"));
                    map.put("title", doc.get("title"));
                }
                
                list.add(map);
            }//for i
        } finally {
            indexSearcher.close();
        }
        
        Experiment experiment = importService.getExperimentByWorkflowId(new Long(workflowId));
        
        request.setAttribute("setting", setting);
        request.setAttribute("experiment", experiment);
        request.setAttribute("results", list);
        
        request.setAttribute("PGIST_SERVICE_SUCCESSFUL", true);
        
        return mapping.findForward("results");
    }//execute()
    
    
}//class SearchAction
