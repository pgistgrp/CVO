package org.pgist.sarp.vtt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.pgist.sarp.cht.CHT;
import org.pgist.sarp.cht.CategoryPath;
import org.pgist.users.User;


/**
 * @author kenny
 *
 * @hibernate.class table="sarp_vtt" lazy="true"
 */
public class VTT {
	
	
	private Long id;
	
    private String name = "";
    
    private String purpose = "";
    
    private String instruction = "";
    
    private List<CategoryPath> paths = new ArrayList<CategoryPath>();
    
    private CHT cht;
    
    private Set<User> users = new HashSet<User>();
    
    private boolean closed;
    
	
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
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return
     * 
     * @hibernate.property type="text" not-null="true"
     */
    public String getInstruction() {
        return instruction;
    }


    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }


    /**
     * @return
     * 
     * @hibernate.property type="text" not-null="true"
     */
    public String getPurpose() {
        return purpose;
    }


    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }


    /**
     * @return
     * 
     * @hibernate.list table="sarp_vtt_path_list"
     * @hibernate.collection-key column="vtt_id"
     * @hibernate.collection-index column="index_order"
     * @hibernate.collection-many-to-many column="root_catref_id" class="org.pgist.sarp.cht.CategoryPath"
     */
	public List<CategoryPath> getPaths() {
		return paths;
	}


	public void setPaths(List<CategoryPath> paths) {
		this.paths = paths;
	}


    /**
     * @return
     * 
     * @hibernate.many-to-one column="cht_id" lazy="true" class="org.pgist.sarp.cht.CHT"
     */
    public CHT getCht() {
		return cht;
	}


	public void setCht(CHT cht) {
		this.cht = cht;
	}


    /**
     * @hibernate.set lazy="false" table="pgist_vtt_user_link" cascade="none"
     * @hibernate.collection-key column="vtt_id"
     * @hibernate.collection-one-to-many column="user_id" class="org.pgist.users.User"
     */   
	public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public boolean isClosed() {
		return closed;
	}


	public void setClosed(boolean closed) {
		this.closed = closed;
	}


}//class VTT
