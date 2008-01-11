package org.pgist.discussion;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * @author kenny
 *
 * @hibernate.class table="pgist_info_structure" lazy="true"
 */
public class InfoStructure extends GenericInfo {
    
    
    /*
     * sd for concern summary
     */
    public static final String TYPE_SDC = "sdc";
    
    /*
     * sd for criteria
     */
    public static final String TYPE_SDCRIT = "sdcrit";
    
    /*
     * sd for projects
     */
    public static final String TYPE_SDP = "sdp";
    
    /*
     * sd for funding sources
     */
    public static final String TYPE_SDF = "sdf";
    
    /*
     * sd for packages
     */
    public static final String TYPE_SDPKG = "sdpkg";
    
    /*
     * sd for create package, maybe deleted later
     */
    public static final String TYPE_SDCP = "sdcp";
    
    /*
     * sd for review report
     */
    public static final String TYPE_SDRR = "sdrr";
    
    /*
     * sd for sarp concern summary
     */
    public static final String TYPE_SARP_SDC = "sarp_sdc";
    
    
    private Long cctId;
    
    private Long suiteId;
    
    private String type;
    
    /*
     * the title for the html page
     */
    private String title;
    
    private Set infoObjects = new HashSet();
    
    
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public Long getCctId() {
        return cctId;
    }


    public void setCctId(Long cctId) {
        this.cctId = cctId;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="false"
     */
    public Long getSuiteId() {
        return suiteId;
    }


    public void setSuiteId(Long suiteId) {
        this.suiteId = suiteId;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public String getType() {
        return type;
    }
    
    
    public void setType(String type) {
        this.type = type;
    }


    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * @return
     * 
     * @hibernate.set lazy="true" cascade="all" order-by="response_time desc"
     * @hibernate.collection-one-to-many class="org.pgist.discussion.InfoObject"
     * @hibernate.collection-key column="structure_id"
     */
    public Set getInfoObjects() {
        return infoObjects;
    }


    public void setInfoObjects(Set infoObjects) {
        this.infoObjects = infoObjects;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public String getLevel() {
        return "structure";
    }//getLevel()
    
    
    public void addObject(Object object) {
        InfoObject one = new InfoObject();
        one.setObject(object);
        getInfoObjects().add(object);
    }//addObject()
    
    
    public void addObjects(Collection objects) {
        for (Object obj : objects) {
            InfoObject one = new InfoObject();
            one.setObject(obj);
            getInfoObjects().add(obj);
        }
    }//addObjects()

    
    public void deleteInfoObjects() {
    	infoObjects.clear();
    }
    
}//class InfoStructure
