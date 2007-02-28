package org.pgist.cvo;

import java.util.HashSet;
import java.util.Set;

import org.pgist.model.PGame;
import org.pgist.packages.PackageStat;


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
    
    private Set criteria = new HashSet();
    
    private Set projects = new HashSet();
    
    private Set sources = new HashSet();
    
    private PackageStat pkgStat = new PackageStat();
    
    private CategoryReference rootCategory = new CategoryReference();
    
    
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
     * @hibernate.set lazy="true" cascade="all" order-by="createTime desc"
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

    
    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" table="pgist_cvo_criteria_link" order-by="id"
     * @hibernate.collection-key column="cct_id"
     * @hibernate.collection-one-to-many class="org.pgist.criteria.Criteria"
     */
    public Set getCriteria() {
		return criteria;
	}


	public void setCriteria(Set criteria) {
		this.criteria = criteria;
	}


    /**
     * @return
     * 
     * @hibernate.set inverse="true" lazy="true" table="pgist_cvo_cct_proj_link"
     * @hibernate.collection-key column="cct_id"
     * @hibernate.collection-many-to-many column="project_id" class="org.pgist.projects.Project"
     */
	public Set getProjects() {
        return projects;
    }


    public void setProjects(Set projects) {
        this.projects = projects;
    }


    /**
     * @return
     * 
     * @hibernate.set inverse="true" lazy="true" table="pgist_cvo_cct_source_link"
     * @hibernate.collection-key column="cct_id"
     * @hibernate.collection-many-to-many column="source_id" class="org.pgist.funding.FundingSource"
     */
    public Set getSources() {
        return sources;
    }


    public void setSources(Set sources) {
        this.sources = sources;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="pkg_stat_id" cascade="all" lazy="true"
     */
    public PackageStat getPkgStat() {
        return pkgStat;
    }


    public void setPkgStat(PackageStat pkgStat) {
        this.pkgStat = pkgStat;
    }


    /**
     * @return
     * 
     * @hibernate.many-to-one column="root_cat_ref_id" cascade="all" lazy="true"
     */
    public CategoryReference getRootCategory() {
        return rootCategory;
    }


    public void setRootCategory(CategoryReference rootCategory) {
        this.rootCategory = rootCategory;
    }


}//class CCT
