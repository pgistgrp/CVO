package org.pgist.packages;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.funding.FundingSource;
import org.pgist.projects.Project;


/**
 * @author Guirong
 * 
 * @hibernate.class table="pgist_packages" lazy="true"
 */
public class Package implements Serializable {
    
    
    public static int PGIST_PACKAGE_TYPE_PPP = 0;//preliminary personal package
    
    public static int PGIST_PACKAGE_TYPE_SPP = 1;//submitted personal package
    
    public static int PGIST_PACKAGE_TYPE_CPP = 2;//clustered personal package
    
    
    private Set<Project> projects = new HashSet<Project>();
    
    private String name;
    
    private String author;
    
    private Date createDate;
    
    private double year1Cost;
    
    private double year2Cost;
    
    private double year3Cost;
    
    private double year4Cost;
    
    private double year5Cost;
    
    private Long id;
    
    private int type;
    
    private Set<FundingSource> fundingSources = new HashSet<FundingSource>();
    
    
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
     * @hibernate.set lazy="true" table="pgist_pack_proj_link" cascade="none"
     * @hibernate.collection-key column="package_id"
     * @hibernate.collection-many-to-many  column="proj_id" class="org.pgist.projects.Project"
     */
    public Set<Project> getProjects(){
        return projects;
	}
    
    
    public void setProjects(Set<Project> projects){
        this.projects = projects;
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
    public String getAuthor() {
        return author;
    }
    
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public Date getCreateDate() {
        return createDate;
    }
    
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public double getYear1Cost() {
        //TODO: this shall not be persisted. it should come out from adding year 1 cost for all the projects
        return year1Cost;
    }
    
    
    public void setYear1Cost(double year1Cost) {
        this.year1Cost = year1Cost;
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public double getYear2Cost() {
        return year2Cost;
    }
    
    
    public void setYear2Cost(double year2Cost) {
        this.year2Cost = year2Cost;
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public double getYear3Cost() {

        return year3Cost;
    }
    
    
    public void setYear3Cost(double year3Cost) {
        this.year3Cost = year3Cost;
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public double getYear4Cost() {
        return year4Cost;
    }
    
    
    public void setYear4Cost(double year4Cost) {
        this.year4Cost = year4Cost;
    }
    
    
    /**
     * @return
     * @hibernate.property
     */
    public double getYear5Cost() {
        return year5Cost;
    }
    
    
    public void setYear5Cost(double year5Cost) {
        this.year5Cost = year5Cost;
    }
    
    
    /**
     * @hibernate.property
     */
    public int getType() {
        return type;
    }
    
    
    public void setType(int type) {
        this.type = type;
    }
    
    
    /**
     * @hibernate.set lazy="true" table="pgist_ag_pack_fund" cascade="none"
     * @hibernate.collection-key column="package_id"
     * @hibernate.collection-many-to-many  column="fund_id" class="org.pgist.funding.FundingSource"
     */
    public Set<FundingSource> getFundingSources() {
        return fundingSources;
    }
    
    
    public void setFundingSources(Set<FundingSource> fundingSources) {
        this.fundingSources = fundingSources;
    }
    

    /*
     * ------------------------------------------------------------------------
     */
    
    
    public double getTotalCost() {
        return (year1Cost + year2Cost + year3Cost + year4Cost + year5Cost);
    }//getTotalCost()
    
    
}//class Package
