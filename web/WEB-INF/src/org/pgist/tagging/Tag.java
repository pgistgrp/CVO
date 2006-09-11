package org.pgist.tagging;


/**
 *
 * @author kenny
 *
 * @hibernate.class table="pgist_tags" lazy="true"
 */
public class Tag {
    
    
    public static final int TYPE_INCLUDED = 0;
    
    public static final int TYPE_EXCLUDED = 1;
    
    public static final int STATUS_OFFICIAL  = 1;
    
    public static final int STATUS_CANDIDATE = 2;
    
    public static final int STATUS_REJECTED  = 3;
    
    
    protected Long id;
    
    protected String name;
    
    private int type = TYPE_INCLUDED;
    
    private int status = STATUS_CANDIDATE;
    
    private int count = 0;
    
    
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
     * @hibernate.property not-null="true"
     */
    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
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
    public int getCount() {
        return count;
    }


    public void setCount(int count) {
        this.count = count;
    }
    
    
    /*
     * ------------------------------------------------------------------------
     */
    
    
    public String toString() {
        return name;
    }


}//class Tag
