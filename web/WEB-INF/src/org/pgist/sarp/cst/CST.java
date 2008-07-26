package org.pgist.sarp.cst;

import java.util.HashMap;
import java.util.Map;

import org.pgist.sarp.bct.BCT;
import org.pgist.users.User;


/**
 * @author kenny
 *
 * @hibernate.class table="sarp_cst" lazy="true"
 */
public class CST {
	
	
	private Long id;
	
    private String name = "";
    
    private String purpose = "";
    
    private String instruction = "";
    
    /*
     * For published user hierarchy
     */
    private Map<Long, CategoryReference> categories = new HashMap<Long, CategoryReference>();
    
    /*
     * For unpublished user hierarchy
     */
    private Map<Long, CategoryReference> cats = new HashMap<Long, CategoryReference>();
    
    private Map<Long, CategoryReference> favorites = new HashMap<Long, CategoryReference>();
    
    private BCT bct;
    
    private User winner;
    
    private CategoryInfo winnerCategory;
    
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
     * @hibernate.map table="sarp_cst_user_category_map"
     * @hibernate.collection-key column="cst_id"
     * @hibernate.collection-index column="user_id" type="long"
     * @hibernate.collection-many-to-many column="root_catref_id" class="org.pgist.sarp.cst.CategoryReference"
     */
	public Map<Long, CategoryReference> getCategories() {
		return categories;
	}


	public void setCategories(Map<Long, CategoryReference> categories) {
		this.categories = categories;
	}


    /**
     * @return
     * 
     * @hibernate.map table="sarp_cst_user_favorite_map"
     * @hibernate.collection-key column="cst_id"
     * @hibernate.collection-index column="user_id" type="long"
     * @hibernate.collection-many-to-many column="favorite_catref_id" class="org.pgist.sarp.cst.CategoryReference"
     */
	public Map<Long, CategoryReference> getFavorites() {
		return favorites;
	}


	public void setFavorites(Map<Long, CategoryReference> favorites) {
		this.favorites = favorites;
	}


    /**
     * @return
     * 
     * @hibernate.map table="sarp_cst_user_cat_map"
     * @hibernate.collection-key column="cst_id"
     * @hibernate.collection-index column="user_id" type="long"
     * @hibernate.collection-many-to-many column="root_catref_id" class="org.pgist.sarp.cst.CategoryReference"
     */
    public Map<Long, CategoryReference> getCats() {
        return cats;
    }


    public void setCats(Map<Long, CategoryReference> cats) {
        this.cats = cats;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="bct_id" lazy="true" class="org.pgist.sarp.bct.BCT"
     */
    public BCT getBct() {
		return bct;
	}


	public void setBct(BCT bct) {
		this.bct = bct;
	}


    /**
     * @return
     * 
     * @hibernate.many-to-one column="winner_id" lazy="true" class="org.pgist.users.User"
     */
	public User getWinner() {
        return winner;
    }


    public void setWinner(User winner) {
        this.winner = winner;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="winner_cat_id" lazy="true" class="org.pgist.sarp.cst.CategoryInfo"
     */
    public CategoryInfo getWinnerCategory() {
        return winnerCategory;
    }


    public void setWinnerCategory(CategoryInfo winnerCategory) {
        this.winnerCategory = winnerCategory;
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


}//class CST
