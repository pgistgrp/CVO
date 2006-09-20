package org.pgist.voting;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_vote_package"
 */
public class VotePackage implements Serializable {
    
    
    private Long id;
    
    private Set sheets = new HashSet();
    
    
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
     * 
     * @hibernate.set lazy="true" table="pgist_vote_sheet" cascade="all" order-by="id"
     * @hibernate.collection-key column="pkg_id"
     * @hibernate.collection-one-to-many class="org.pgist.voting.VoteSheet"
     */
    public Set getSheets() {
        return sheets;
    }


    public void setSheets(Set sheets) {
        this.sheets = sheets;
    }
    
    
}//class VotePackage
