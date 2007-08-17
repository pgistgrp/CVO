package org.pgist.system;

import java.io.Serializable;

/**
 * <span style="color:red;">POJO</span>: PGIST register object Class<br>
 * <span style="color:red;">TABLE</span>: pgist_register_object class
 * 
 * <p>Register object for webq id's and income values
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_register_object" lazy="true"
 */
public class RegisterObject implements Serializable {

	private Long id;
	
	private String type = "";
	
	private String value = "";
	
	private boolean used = false;
	
	

	/**
     * @return
     * 
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
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


    /**
     * @return
     * @hibernate.property unique="true" not-null="true"
     */
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
     * @hibernate.property not-null="true"
     */
	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
