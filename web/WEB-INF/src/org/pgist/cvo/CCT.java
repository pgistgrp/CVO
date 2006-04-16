package org.pgist.cvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

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
    
    private Set tagRefs = new HashSet();
    
    private static Cache lockCache;
    
    
    static {
        try {
            CacheManager manager = CacheManager.getInstance();
            lockCache = manager.getCache("cctTagMaps");
            if (lockCache==null) {
                lockCache = new Cache("cctTagMaps", 50, true, false, 2*3600, 20*60);
                manager.addCache(lockCache);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }//static
    
    
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
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" table="pgist_cvo_tag_refs" order-by="id"
     * @hibernate.collection-key column="cct_id"
     * @hibernate.collection-one-to-many class="org.pgist.cvo.TagReference"
     */
    public Set getTagRefs() {
        return tagRefs;
    }


    public void setTagRefs(Set tagRefs) {
        this.tagRefs = tagRefs;
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
     * @return A collection of new tags.
     */
    public Collection addConcern(Concern concern, Collection tags) throws Exception {
        List list = new ArrayList(5);
        
        HashMap tagMap = null;
        Element element =  lockCache.get(id);
        if (element==null) {
            synchronized (lockCache) {
                element = lockCache.get(id);
                if (element==null) {
                    tagMap = new HashMap();
                    for (Iterator iter=tagRefs.iterator(); iter.hasNext(); ) {
                        TagReference tagRef = (TagReference) iter.next();
                        Tag tag = tagRef.getTag();
                        tagMap.put(tag.getId(), tagRef);
                    }//for iter
                    element = new Element(id, tagMap);
                    lockCache.put(element);
                } else {
                    tagMap = (HashMap) element.getValue();
                }
            }//synchronized
        } else {
            tagMap = (HashMap) element.getValue();
        }
        
        synchronized(tagMap) {
            Set cctTags = getTagRefs();
            for (Iterator iter=tags.iterator(); iter.hasNext(); ) {
                Tag tag = (Tag) iter.next();
                TagReference ref = (TagReference) tagMap.get(tag.getId());
                if (ref==null) {
                    ref = new TagReference();
                    ref.setCct(this);
                    ref.setTag(tag);
                    ref.setTimes(1);
                    cctTags.add(ref);
                    list.add(tag);
                } else {
                    ref.setTimes(ref.getTimes()+1);
                }
                concern.getTags().add(ref);
            }//for iter
        }//synchronized
        
        concerns.add(concern);
        
        return list;
    }//addConcern()


    public void removeConcern(Concern concern) {
        //TODO
    }//removeConcern()


    public void editConcern(Concern concern, String[] tags) {
        // TODO Auto-generated method stub
    }//editConcern()
    
    
}//class CCT
