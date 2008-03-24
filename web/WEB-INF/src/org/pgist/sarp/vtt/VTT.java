package org.pgist.sarp.vtt;

import java.util.HashMap;
import java.util.Map;

import org.pgist.sarp.cht.CHT;
import org.pgist.sarp.cst.CategoryReference;
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
    
    /*
     * For published user value tree
     */
    private Map<Long, CategoryReference> categories = new HashMap<Long, CategoryReference>();
    
    /*
     * For unpublished user value tree
     */
    private Map<Long, CategoryReference> cats = new HashMap<Long, CategoryReference>();
    
    private Map<Long, CategoryReference> favorites = new HashMap<Long, CategoryReference>();
    
    private CHT cht;
    
    private User winner;
    
    private CategoryReference winnerCategory;
    
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
     * @hibernate.map table="sarp_vtt_user_category_map"
     * @hibernate.collection-key column="vtt_id"
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
     * @hibernate.map table="sarp_vtt_user_cat_map"
     * @hibernate.collection-key column="vtt_id"
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
     * @hibernate.map table="sarp_vtt_user_favorite_map"
     * @hibernate.collection-key column="vtt_id"
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
     * @hibernate.many-to-one column="cht_id" lazy="true" class="org.pgist.sarp.cht.CHT"
     */
    public CHT getCht() {
		return cht;
	}


	public void setCht(CHT cht) {
		this.cht = cht;
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
     * @hibernate.many-to-one column="winner_cat_id" lazy="true" class="org.pgist.sarp.cst.CategoryReference"
     */
    public CategoryReference getWinnerCategory() {
        return winnerCategory;
    }


    public void setWinnerCategory(CategoryReference winnerCategory) {
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


}//class VTT
