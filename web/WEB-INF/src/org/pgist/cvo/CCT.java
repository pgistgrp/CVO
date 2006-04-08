package org.pgist.cvo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.pgist.model.PGame;


/**
 * 
 * @author kenny
 *
 * @hibernate.joined-subclass name="CCT" table="pgist_cvo_cct"
 * @hibernate.joined-subclass-key column="id"
 */
public class CCT extends PGame {
    
    
    private int maxConcernPerPerson = Integer.MAX_VALUE;
    
    private Set concerns = new HashSet();
    
    private Map tags = new HashMap();
    
    private static Map locks = new HashMap();
    
    
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public int getMaxConcernPerPerson() {
        return maxConcernPerPerson;
    }
    
    
    public void setMaxConcernPerPerson(int maxConcernPerPerson) {
        this.maxConcernPerPerson = maxConcernPerPerson;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" table="pgist_cvo_concerns" order-by="createTime desc"
     * @hibernate.collection-key column="cct_id"
     * @hibernate.collection-one-to-many class="org.pgist.cvo.Concern"
     */
    public Set getConcerns() {
        return concerns;
    }


    public void setConcerns(Set concerns) {
        this.concerns = concerns;
    }


    /**
     * 
     * @return
     * 
     * @hibernate.map table="pgist_cvo_cct_tags" lazy="true"
     * @hibernate.collection-key column="cct_id"
     * @hibernate.index-many-to-many column="tag_id" class="org.pgist.cvo.Tag"
     * @hibernate.collection-element column="times" type="int" not-null="true"
     */
    public Map getTags() {
        return tags;
    }


    public void setTags(Map tags) {
        this.tags = tags;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    /**
     * Add the given concern and tags to CCT. All tags with be checked if exists in CCT, if
     * true then the reference times of this tag will be adjusted; if false then a new tag
     * will be created and the reference times will be set to 1.
     * 
     * @param concern A Concern object.
     * @param tags A collection of Tag objects which are related to the given concern.
     */
    public void addConcern(Concern concern, Collection tags) {
        Long lock = (Long) locks.get(getId().toString());
        if (lock==null) {
            synchronized (locks) {
                lock = (Long) locks.get(id.toString());
                if (lock==null) {
                    lock = new Long(1);
                    locks.put(id.toString(), lock);
                }
            }//synchronized
        }
        synchronized(lock) {
            Map cctTags = getTags();
            for (Iterator iter=tags.iterator(); iter.hasNext(); ) {
                Tag tag = (Tag) iter.next();
                Integer times = (Integer) cctTags.get(tag);
                if (times==null) {
                    cctTags.put(tag, new Integer(1));
                } else {
                    cctTags.put(tag, new Integer((times.intValue())+1));
                }
            }//for iter
        }//synchronized
        
        for (Iterator iter=tags.iterator(); iter.hasNext(); ) {
            Tag tag = (Tag) iter.next();
            concern.getTags().add(tag);
        }//for iter
        
        concerns.add(concern);
    }//addConcern()
    
    
}//class CCT
