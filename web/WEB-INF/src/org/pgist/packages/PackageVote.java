package org.pgist.packages;

import org.pgist.cvo.CCT;
import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_package_vote" lazy="true"
 */
public class PackageVote {
    
    
    /**
     * participant has not voted on this package yet
     * value: 0<br>
     */
    public static int VOTING_UNKNOWN = 0;
    
    /**
     * value: 1<br>
     */
    public static int VOTING_HIGH    = 1;
    
    /**
     * value: 2<br>
     */
    public static int VOTING_MEDIUM  = 2;
    
    /**
     * value: 3<br>
     */
    public static int VOTING_LOW     = 3;
    
    
    private Long id;
    
    private CCT cct;
    
    private ClusteredPackage clusteredPackage;
    
    private int result;
    
    private User author;
    
    
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
     * @hibernate.property
     */
    public int getResult() {
        return result;
    }


    public void setResult(int result) {
        this.result = result;
    }


}//class PackageVote
