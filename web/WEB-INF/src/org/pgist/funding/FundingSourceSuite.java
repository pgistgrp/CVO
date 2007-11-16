package org.pgist.funding;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.pgist.users.User;


/**
 * A funding source suite consists of a set of funding source references.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_funding_suite" lazy="true"
 */
public class FundingSourceSuite {
    
    
    private Long id;
    
    private Set<FundingSourceRef> references = new HashSet<FundingSourceRef>();
    
    private Map<User, UserCommute> userCommutes = new HashMap<User, UserCommute>();
    
    
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
     * @hibernate.set inverse="true" lazy="true"
     * @hibernate.collection-key column="suite_id"
     * @hibernate.collection-one-to-many class="org.pgist.funding.FundingSourceRef"
     */
    public Set<FundingSourceRef> getReferences() {
        return references;
    }


    public void setReferences(Set<FundingSourceRef> references) {
        this.references = references;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.map table="pgist_funding_user_commute_map"
     * @hibernate.collection-key column="fundsuite_id"
     * @hibernate.index-many-to-many column="user_id" class="org.pgist.users.User"
     * @hibernate.collection-many-to-many class="org.pgist.funding.UserCommute" column="usercommute_id"
     */
    public Map<User, UserCommute> getUserCommutes() {
        return userCommutes;
    }


    public void setUserCommutes(Map<User, UserCommute> userCommutes) {
        this.userCommutes = userCommutes;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * TODO: Check if the suite contains a reference to the given funding source alternative.
     * 
     * @param alt a given funding source alternative
     * @return
     */
    public boolean containsAlts(FundingSourceAlternative alt) {
        return false;
    }//contains()


}//class FundingSourceSuite
