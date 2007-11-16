package org.pgist.packages;

import java.util.HashMap;
import java.util.Map;

import org.pgist.users.User;


/**
 * A PackageUserVote object contains the user votes for one package.
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_pkg_user_vote" lazy="true"
 */
public class PackageUserVote {
    
    
    /**
     * vote for UNKNOWN.<br>
     * value: 0.
     */
    public static int VOTE_UNKNOWN = 0;
    
    /**
     * vote for VOTE_HIGH.<br>
     * value: 1.
     */
    public static int VOTE_HIGH    = 1;
    
    /**
     * vote for VOTE_MEDIUM.<br>
     * value: 2.
     */
    public static int VOTE_MEDIUM  = 2;
    
    /**
     * vote for VOTE_LOW.<br>
     * value: 3.
     */
    public static int VOTE_LOW    = 3;
    
    private Long id;
    
    private PackageVoteSuite voteSuite;
    
    private Map<User, Integer> votes = new HashMap<User, Integer>();
    
    
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
     * @hibernate.many-to-one column="votesuite_id" cascade="none"
     */
    public PackageVoteSuite getVoteSuite() {
        return voteSuite;
    }


    public void setVoteSuite(PackageVoteSuite voteSuite) {
        this.voteSuite = voteSuite;
    }


    /**
     * @return
     * 
     * @hibernate.map table="pgist_pkg_vote_map"
     * @hibernate.collection-key column="uservote_id"
     * @hibernate.index-many-to-many column="user_id" class="org.pgist.users.User"
     * @hibernate.collection-element type="integer" column="vote"
     */
    public Map<User, Integer> getVotes() {
        return votes;
    }


    public void setVotes(Map<User, Integer> votes) {
        this.votes = votes;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public boolean equals(Object object) {
        if (this == object) return true;
        
        if (!(object instanceof PackageUserVote)) return false;
        
        final PackageUserVote vote = (PackageUserVote) object;
        
        if ( (id!=null) ? (!id.equals(vote.id)) : (vote.id!=null) ) {
            return false;
        }
        
        return true;
    }//equals()
    
    
    public int hashCode() {
        return (id!=null) ? (id.hashCode()) : 0;
    }//hashCode()
    
    
}//class PackageUserVote
