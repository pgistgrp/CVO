package org.pgist.projects;

/**
 * @author Mike and Guirong
 * @hibernate.class table="pgist_ag_proj_crit" lazy="true"
 */
class ProjectCriteria {
    private Long id;
    private Project project;
    private Criteria criterion;
    private Double value;

    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public Project getProject() {
        return project;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public Criteria getCriterion() {
        return criterion;
    }

    /**
     * @hibernate.property
     */
    public Double getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setCriterion(Criteria criterion) {
        this.criterion = criterion;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
