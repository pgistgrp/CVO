package org.pgist.search;

import java.util.Collection;

import org.apache.struts.action.ActionForm;


/**
 * 
 * @author kenny
 *
 */
public class SearchForm extends ActionForm {
    
    
    private static final long serialVersionUID = 659187588279342675L;
    
    private String queryStr;
    
    private Collection results;
    
    private int workflowId;
    
    private int total;
    
    
    public String getQueryStr() {
        return queryStr;
    }
    
    
    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }


    public Collection getResults() {
        return results;
    }


    public void setResults(Collection results) {
        this.results = results;
    }


    public int getWorkflowId() {
        return workflowId;
    }


    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }


    public int getTotal() {
        return total;
    }


    public void setTotal(int total) {
        this.total = total;
    }
    
    
}//class SearchForm
