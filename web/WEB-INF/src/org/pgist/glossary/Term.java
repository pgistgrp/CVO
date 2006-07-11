package org.pgist.glossary;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.pgist.users.User;


/**
 * Glossary POJO
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_term"
 */
public class Term {

    
    public static final int STATUS_PENDING  = 0;
    
    public static final int STATUS_OFFICIAL = 1;
    
    
    private Long id;
    
    private String name;
    
    private String abbreviation;
    
    private String shortDefinition = "";
    
    private String extDefinition = "";
    
    private Set relatedTerms = new HashSet();
    
    private Set links = new HashSet();
    
    private Set sources = new HashSet();
    
    private int status;
    
    private boolean deleted = false;
    
    private User creator;
    
    private Date createTime;
    
    private Date modifyTime;
    
    private int refCount;
    
    private int hitCount;
    
    private int commentCount;
    
    private boolean flaged = false;
    
    private char initial;
    
    private Set categories = new HashSet();
    
    
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
     * @hibernate.property
     */
    public String getAbbreviation() {
        return abbreviation;
    }


    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getShortDefinition() {
        return shortDefinition;
    }
    
    
    public void setShortDefinition(String shortDefinition) {
        this.shortDefinition = shortDefinition;
    }


    /**
     * @return
     * @hibernate.property type="text" not-null="true"
     */
    public String getExtDefinition() {
        return extDefinition;
    }
    
    
    public void setExtDefinition(String extDefinition) {
        this.extDefinition = extDefinition;
    }
    
    
    /**
     * @return
     * @hibernate.set lazy="false" table="pgist_glossary_term_link_link" cascade="all" order-by="link_id"
     * @hibernate.collection-key column="term_id"
     * @hibernate.collection-many-to-many column="link_id" class="org.pgist.glossary.TermLink"
     */
    public Set getLinks() {
        return links;
    }


    public void setLinks(Set links) {
        this.links = links;
    }


    /**
     * @return
     * @hibernate.set lazy="false" table="pgist_glossary_term_term_link" cascade="all" order-by="term_id"
     * @hibernate.collection-key column="related_term_id"
     * @hibernate.collection-many-to-many column="term_id" class="org.pgist.glossary.Term"
     */
    public Set getRelatedTerms() {
        return relatedTerms;
    }


    public void setRelatedTerms(Set relatedTerms) {
        this.relatedTerms = relatedTerms;
    }


    /**
     * @return
     * @hibernate.set lazy="false" table="pgist_glossary_term_source_link" cascade="all" order-by="source_id"
     * @hibernate.collection-key column="term_id"
     * @hibernate.collection-many-to-many column="source_id" class="org.pgist.glossary.TermSource"
     */
    public Set getSources() {
        return sources;
    }


    public void setSources(Set sources) {
        this.sources = sources;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    /**
     * @return
     * @hibernate.many-to-one column="creator_id" class="org.pgist.users.User" casecad="all"
     */
    public User getCreator() {
        return creator;
    }


    public void setCreator(User creator) {
        this.creator = creator;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    /**
     * @return
     * @hibernate.property
     */
    public Date getModifyTime() {
        return modifyTime;
    }


    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getCommentCount() {
        return commentCount;
    }


    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isFlaged() {
        return flaged;
    }


    public void setFlaged(boolean flaged) {
        this.flaged = flaged;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getHitCount() {
        return hitCount;
    }


    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public char getInitial() {
        return initial;
    }


    public void setInitial(char initial) {
        this.initial = initial;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getRefCount() {
        return refCount;
    }


    public void setRefCount(int refCount) {
        this.refCount = refCount;
    }


    /**
     * @return
     * @hibernate.set lazy="true" table="pgist_glossary_term_categ_link" cascade="none" order-by="category_id"
     * @hibernate.collection-key column="term_id"
     * @hibernate.collection-many-to-many column="category_id" class="org.pgist.glossary.TermCategory"
     */
    public Set getCategories() {
        return categories;
    }


    public void setCategories(Set categories) {
        this.categories = categories;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public String toString() {
        return this.name;
    }//toString()
    
    
    public String getCategoryList() {
        StringBuffer sb = new StringBuffer();
        
        if (categories!=null) {
            for (Iterator iter=categories.iterator(); iter.hasNext(); ) {
                TermCategory category = (TermCategory) iter.next();
                sb.append(category.getName());
                if (iter.hasNext()) sb.append(", ");
            }//for iter
        }
        
        return sb.toString();
    }//getCategoryList()
    
    
}//class Term
