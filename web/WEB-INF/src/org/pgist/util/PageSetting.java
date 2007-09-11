package org.pgist.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author kenny
 *
 */
public class PageSetting {

    
    private int page = 1;
    
    private int rowSize = 0;
    
    private int pageSize = 0;
    
    private int rowOfPage = 10;
    
    private int pageOfScreen = 10;
    
    private int head = 1;
    
    private int tail = 1;
    
    private String filter = null;
    
    private Map parameters = new HashMap();
    
    private int[] options = {
        10,
        15,
        20,
        25,
        30,
        50,
        80,
        100,
        200
    };
    
    
    public PageSetting() {
    }


    public PageSetting(int rowOfPage) {
        this.rowOfPage = rowOfPage;
    }

    
    public int getPage() {
        return page;
    }


    public void setPage(int page) {
        this.page = page;
    }


    public void setPage(String page) {
        try {
            this.page = Integer.parseInt(page);
            if (this.page<1) this.page = 1;
        } catch (Exception e) {
            this.page = 1;
        }
    }


    public int getPageSize() {
        return pageSize;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public Map getParameters() {
        return parameters;
    }


    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }


    public int getRowOfPage() {
        return rowOfPage;
    }


    public void setRowOfPage(int rowOfPage) {
        this.rowOfPage = rowOfPage;
        if (this.rowOfPage<=0) this.rowOfPage = 20;
    }


    public void setRowOfPage(String rowOfPage) {
        try {
            this.rowOfPage = Integer.parseInt(rowOfPage);
            if (this.rowOfPage<=0) this.rowOfPage = 20;
        } catch(Exception e) {
            this.rowOfPage = 20;
        }
    }


    public int getPageOfScreen() {
        return pageOfScreen;
    }


    public void setPageOfScreen(int pageOfScreen) {
        this.pageOfScreen = pageOfScreen;
    }


    public int getRowSize() {
        return rowSize;
    }
    
    
    public int getHead() {
        return head;
    }


    public int getTail() {
        return tail;
    }
    
    
    public String getFilter() {
        return filter;
    }


    public void setFilter(String filter) {
        this.filter = filter;
    }


    public int[] getOptions() {
        return options;
    }


    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
        
        if (rowOfPage==-1) {
            head = 1;
            tail = rowOfPage;
            return;
        }
        
        pageSize = (int)Math.ceil(((float)rowSize)/rowOfPage);
        
        if (page<=0) {
            page = 1;
        } else if (page>pageSize) {
            page = pageSize;
        }
        
        if (pageSize<=pageOfScreen) {//we have only a few pages
            head = 1;
        } else {//too many pages
            head = (page / pageOfScreen);
            if (page % pageOfScreen==0) head--;
            head = head * pageOfScreen + 1;
        }
        tail = head + pageOfScreen - 1;
        if (tail > pageSize) tail = pageSize;
    }//setRowSize()
    
    
    public int getFirstRow() {
        int firstRow = (page-1)*rowOfPage;
        if (firstRow<0) {
            firstRow = 0;
        }
        return firstRow;
    }
    
    
    public int getLastRow() {
        int lastRow = getFirstRow()+rowOfPage-1;
        if (lastRow>rowSize) {
            lastRow = rowSize-1;
        }
        return lastRow;
    }
    
    
    public Object get(String name) {
        return parameters.get(name);
    }
    
    
    public void set(String name, Object value) {
        parameters.put(name, value);
    }


}
