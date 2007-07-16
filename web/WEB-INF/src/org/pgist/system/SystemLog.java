package org.pgist.system;

import java.util.Date;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_system_logs" lazy="true"
 */
public class SystemLog {
    
    
    private Long id;
    
    private Long userId;
    
    private Date time;
    
    private String url;
    
    private String query;
    
    private String method;
    
    private String postData;
    
    private String referer;
    
    private boolean successful;
    
    
    /**
     * @return
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Date getTime() {
        return time;
    }
    
    
    public void setTime(Date time) {
        this.time = time;
    }
    
    
    /**
     * @return
     * @hibernate.property column="user_id"
     */
    public Long getUserId() {
        return userId;
    }
    
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }


    /**
     * @return
     * @hibernate.property type="text"
     */
    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * @return
     * @hibernate.property type="text"
     */
    public String getQuery() {
        return query;
    }


    public void setQuery(String query) {
        this.query = query;
    }


    /**
     * @return
     * @hibernate.property
     */
    public String getMethod() {
        return method;
    }


    public void setMethod(String method) {
        this.method = method;
    }


    /**
     * @return
     * @hibernate.property type="text"
     */
    public String getPostData() {
        return postData;
    }


    public void setPostData(String postData) {
        this.postData = postData;
    }


    /**
     * @return
     * @hibernate.property length="1024"
     */
    public String getReferer() {
        return referer;
    }


    public void setReferer(String referer) {
        this.referer = referer;
    }


    /**
     * @return
     * @hibernate.property
     */
    public boolean isSuccessful() {
        return successful;
    }


    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
    
    
}//class SystemLog
