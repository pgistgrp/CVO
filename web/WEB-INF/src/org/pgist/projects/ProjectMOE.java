package org.pgist.projects;

/**
 * @author Mike and Guirong
 * @hibernate.class table="pgist_ag_prjmoe" lazy="true"
 */
class ProjectMOE {
    private Project project;
    private MOE moe;
    private Double value;
    private Long id;

    /**
     * @hibernate.property not-null="true"
     */
    public Project getProject() {
        return project;
    }

    /**
     * @hibernate.property
     */
    public Double getValue() {
        return value;
    }

    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public MOE getMoe() {

        return moe;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMoe(MOE moe) {

        this.moe = moe;
    }
}
