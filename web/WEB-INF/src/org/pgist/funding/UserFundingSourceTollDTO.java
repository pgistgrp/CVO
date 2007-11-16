package org.pgist.funding;

/**
 * Used to transfer information about the user funding source
 * 
 * @author Matt Paulin
 */
public class UserFundingSourceTollDTO implements Comparable<UserFundingSourceTollDTO> {

	private Long id;
    
	/**
	 * The name of the funding source toll
	 */
	private String name;
	
	/**
	 * Records if the user uses this funding source at all
	 */
	private boolean used;
	
	/**
	 * The number of peak times the user would use this funding source
	 */
	private int peakTrips;

	/**
	 * Records the number of times this user would use this funding source in off peak times
	 */
	private int offPeakTrips;
	
    /**
     * @return
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the offPeakTrips
   	 */
	public int getOffPeakTrips() {
		return offPeakTrips;
	}

	/**
	 * @param offPeakTrips the offPeakTrips to set
	 */
	public void setOffPeakTrips(int offPeakTrips) {
		this.offPeakTrips = offPeakTrips;
	}

	/**
	 * @return the peakTrips
     * @hibernate.property
   	 */
	public int getPeakTrips() {
		return peakTrips;
	}

	/**
	 * @param peakTrips the peakTrips to set
	 */
	public void setPeakTrips(int peakTrips) {
		this.peakTrips = peakTrips;
	}

	/**
	 * @return the used
     * @hibernate.property
	 */
	public boolean isUsed() {
		return used;
	}

	/**
	 * @param used the used to set
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}

	public void loadTollFromThis(UserFundingSourceToll tempToll) {
		tempToll.setName(this.getName());
		tempToll.setId(this.getId());
		tempToll.setUsed(this.isUsed());
		tempToll.setPeakTrips(this.getPeakTrips());
		tempToll.setOffPeakTrips(this.getOffPeakTrips());		
	}	

	
	public void loadFromToll(UserFundingSourceToll tempToll) {
		this.setName(tempToll.getName());
		this.setId(tempToll.getId());
		this.setUsed(tempToll.isUsed());
		this.setPeakTrips(tempToll.getPeakTrips());
		this.setOffPeakTrips(tempToll.getOffPeakTrips());		
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(UserFundingSourceTollDTO o) {
		if(o == null) return 0;
		
		return this.getName().compareTo(o.getName());
	}		
}
