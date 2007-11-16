package org.pgist.projects;

/**
 * @author Guirong
 * @hibernate.class table="pgist_data_corridors" lazy="true"
 */
class Corridor {
    private Long id;
    private String name;
    private String description;
    private Long fpid;

    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }

    /**
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }

    /**
     * @hibernate.property
     */
    public String getDescription() {
        return description;
    }

    /**
     * @hibernate.property
     */
    public Long getFpid() {
        return fpid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFpid(Long fpid) {
        this.fpid = fpid;
    }
}
