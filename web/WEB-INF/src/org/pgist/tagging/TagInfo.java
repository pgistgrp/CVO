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


    public int getFontSize() {
        int myTimes = getTimes();
        if (myTimes>=1 && myTimes<=2) {
            return 1;
        } else if (myTimes>=3 && myTimes <=5) {
            return 2;
        } else if (myTimes>=7 && myTimes <=10) {
            return 3;
        } else if (myTimes>10) {
            return 4;
        } else {
            //invalid state
            return 0;
        }
    }


}//class TagInfo
