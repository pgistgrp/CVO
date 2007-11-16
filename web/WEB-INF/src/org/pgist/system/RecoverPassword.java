package org.pgist.system;

import java.io.Serializable;
import java.util.Date;

import org.pgist.util.MD5;

/**
 * <span style="color:red;">POJO</span>: PGIST RecoverPassword Class<br>
 * <span style="color:red;">TABLE</span>: pgist_recoverpassword
 * 
 * <p>recoverpassword contains temporary information used for password recovery
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_recover_password" lazy="true"
 */
public class RecoverPassword implements Serializable{
	
    /**
     * <span style="color:blue;">(Column.)</span>
     * id of the announcement. Unique id number used to identify each announcement.
     */
	private Long id;
	
    /**
     * <span style="color:blue;">(Column.)</span>
     * date of the Announcement. Date of the announcement post.
     */
	private Long userId;
	
    /**
     * <span style="color:blue;">(Column.)</span>
     * date of the Announcement. Date of the announcement post.
     */
	private Date date;
	
    /**
     * <span style="color:blue;">(Column.)</span>
     * code. code used for email recovery.
     */
	private String code;
	
	
    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
	
    
    /**
     * @hibernate.property not-null="true"
     */
    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    
    /**
     * @hibernate.property not-null="true"
     */
    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }
    
    
    /**
     * @return
     * @hibernate.property type="text" not-null="true"
     */
    public String getCode() {
        return code;
    }

    
    public void setCode(String code) {
        this.code = code;
    }
  
    
    public void encode() {
        code = MD5.getDigest(code);
    }  
    
    
} //RecoverPassword()
