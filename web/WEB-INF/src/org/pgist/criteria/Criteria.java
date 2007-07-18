package org.pgist.criteria;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pgist.cvo.Theme;


/**
 * <span style="color:red;">POJO</span>: PGIST Criteria Class<br>
 * <span style="color:red;">TABLE</span>: pgist_crit
 * 
 * <p>Criteria class contains information about criteria objects
 * 
 * @author John
 * 
 * @hibernate.class table="pgist_crit" lazy="true"
 */
public class Criteria implements Serializable {
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * id of the criteria. Unique id number used to identify each criteria.
     */
    private Long id;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Name of a criteria. 
     */
    private String name;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     *  CriteriaSuite, CriteriaSuite object this criteria is associated with. 
     */
    private CriteriaSuite suite;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * na. na is a string description of the criteria and is optional.
     */
    private String na;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * themes. A Set of themes associated with this criterion.
     */
    private Set<Theme> themes = new HashSet();
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * objectives. A Set of objectives for this criterion.
     */
    private SortedSet<Objective> objectives = new TreeSet<Objective>(new ObjectiveComparator());
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * Object. Object holder, not currently used.
     */
    private Object object;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * deleted. Determines if the crtieria is deleted so it won't be used.
     */
    private boolean deleted = false;
    
    /**
     * <span style="color:blue;">(Column.)</span>
     * tempWeight. Temporary holder for weight value when passed to the front end. 
     */
    private int tempWeight;
    
    
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
     * @hibernate.many-to-one column="suite_id" cascade="all"
     */
    public CriteriaSuite getSuite() {
        return suite;
    }


    public void setSuite(CriteriaSuite suite) {
        this.suite = suite;
    }
    
    
    /**
     * @hibernate.property length="2000" comment="if a score for this project and criterion is not applicapable"
     */
    public String getNa() {
        return na;
    }
    
    
    public void setNa(String na) {
        this.na = na;
    }
    
    
    public void setThemes(Set<Theme> themes) {
        this.themes = themes;
    }
    
    /**
     * @hibernate.set lazy="false" table="pgist_criteria_theme_link" cascade="none"
     * @hibernate.collection-key column="criterion_id"
     * @hibernate.collection-many-to-many column="theme_id" class="org.pgist.cvo.Theme"
     */   
    public Set<Theme> getThemes() {
        return themes;
    }
    
    
    public void setObjectives(SortedSet<Objective> objectives) {
        this.objectives = objectives;
    }
    
    /**
     * @hibernate.set lazy="false" table="pgist_criteria_objective_link" cascade="all" sort="org.pgist.criteria.ObjectiveComparator"
     * @hibernate.collection-key column="criterion_id"
     * @hibernate.collection-one-to-many column="objective_id" class="org.pgist.criteria.Objective"
     */   
    public SortedSet<Objective> getObjectives() {
        return objectives;
    }
    
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    /**
     * @hibernate.property not-null="true"
     */
    public boolean getDeleted() {
        return deleted;
    }

    
    public void setTempWeight(int tempWeight) {
        this.tempWeight = tempWeight;
    }
    
    /**
     * @hibernate.property not-null="false"
     */
    public int getTempWeight() {
        return tempWeight;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    

    public Object getObject() {
        return object;
    }


    public void setObject(Object object) {
        this.object = object;
    }
    
    
}//class Criteria
