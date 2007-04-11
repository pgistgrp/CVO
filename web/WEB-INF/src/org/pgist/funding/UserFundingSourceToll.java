package org.pgist.funding;

/**
 * Used to match the data from Mike's spreadsheet with funding options that have tolls
 * 
 * @author Matt Paulin
 * @hibernate.class table="pgist_user_funding_source_toll" lazy="true"
 */
public class UserFundingSourceToll {

    public static final String PARKING_DOWNTOWN = "Parking Downtown";
    public static final String ALASKA_WAY_VIADUCT = "Alaska Way Viaduct";
    public static final String I405N = "I-405 North";
    public static final String I405S = "I-405 South";
    public static final String SR520 = "SR 520 Floating Bridge";
    public static final String I90 = "I-90";
    public static final String SR167 = "SR 167";

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
	 * Funding Source associated with this toll
	 */
	private FundingSource fundingSource;
	
    /**
     * @return
     * 
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }	
	
	/**
	 * @return the name
     * @hibernate.property
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
	 * @return the fundingSource
     * @hibernate.many-to-one column="user_id" cascade="none"
   	 */
	public FundingSource getFundingSource() {
		return fundingSource;
	}

	/**
	 * @param fundingSource the fundingSource to set
	 */
	public void setFundingSource(FundingSource fundingSource) {
		this.fundingSource = fundingSource;
	}

	/**
	 * @return the offPeakTrips
     * @hibernate.property
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
}
