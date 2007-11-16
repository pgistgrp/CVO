package org.pgist.tagging;


/**
 * This is an object for transferring Tag information to presentation level.
 * 
 * @author kenny
 *
 */
public class TagInfo {
    
    
    private Long id;
    
    private String name;
    
    private int times;
    
    
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getTimes() {
        return times;
    }


    public void setTimes(int times) {
        this.times = times;
    }


}//class TagInfo
