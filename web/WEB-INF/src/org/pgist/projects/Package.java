/**
 *
 */
package org.pgist.projects;

import java.util.Set;

/**
 * @author Guirong
 * @hibernate.class table="pgist_data_packages" lazy="true"
 */
public class Package {
    private Set projects;
    private String name;
    private String author;
    private String createDate;
    private double year1Cost;
    private double year2Cost;
    private double year3Cost;
    private double year4Cost;
    private double year5Cost;
    private Long id;

    /**
     *
     * @return Set
     *
     * @hibernate.set lazy="false" table="pgist_data_pack_proj" cascade="none"
     * @hibernate.collection-key column="package_id"
     * @hibernate.collection-many-to-many column="proj_id" class="org.pgist.projects.Project"
     */
    public Set getProjects(){
    return this.projects;
	}

        /**
         * @return
         *
         * @hibernate.property not-null="true"
         */
    public String getName() {
        return name;
    }

    /**
     * @return
     *
     * @hibernate.property
     */
    public String getAuthor() {
        return author;
    }

    public String getCreateDate() {
        return createDate;
    }

    /**
     * @return
     *
     * @hibernate.property
     */
    public double getYear1Cost() {
        //TODO: this shall not be persisted. it should come out from adding year 1 cost for all the projects

        return year1Cost;
    }

    /**
     * @return
     *
     * @hibernate.property
     */
    public double getYear2Cost() {
        return year2Cost;
    }

    /**
     * @return
     *
     * @hibernate.property
     */
    public double getYear3Cost() {

        return year3Cost;
    }

    /**
     * @return
     *
     * @hibernate.property
     */
    public double getYear4Cost() {
        return year4Cost;
    }

    /**
     * @return
     *
     * @hibernate.property
     */
    public double getYear5Cost() {
        return year5Cost;
    }

    /**
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }

    public double getTotalCost() {
        return (this.year1Cost + this.year2Cost + this.year3Cost
                + this.year4Cost + this.year5Cost);
    }

    public void setProjects(Set projects){
		this.projects = projects;
	}

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setYear1Cost(double year1Cost) {
        this.year1Cost = year1Cost;
    }

    public void setYear2Cost(double year2Cost) {
        this.year2Cost = year2Cost;
    }

    public void setYear3Cost(double year3Cost) {

        this.year3Cost = year3Cost;
    }

    public void setYear4Cost(double year4Cost) {
        this.year4Cost = year4Cost;
    }

    public void setYear5Cost(double year5Cost) {
        this.year5Cost = year5Cost;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**----------------------*/

}
