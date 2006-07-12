package org.pgist.glossary;

import org.pgist.users.User;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_term_participant"
 */
public class TermParticipantRecord {
    
    
    private Long id;
    
    private Term term;
    
    private User user;
    
    
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
     * @hibernate.many-to-one column="term_id" class="org.pgist.glossary.Term" casecad="all"
     */
    public Term getTerm() {
        return term;
    }


    public void setTerm(Term term) {
        this.term = term;
    }


    /**
     * @return
     * @hibernate.many-to-one column="creator_id" class="org.pgist.users.User" casecad="all"
     */
    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }
    
    
}//class TermParticipantRecord
