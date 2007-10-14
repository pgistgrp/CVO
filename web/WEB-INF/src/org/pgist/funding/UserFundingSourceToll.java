package org.pgist.funding;

/**
 * Used to match the data from Mike's spreadsheet with funding options that have tolls
 * 
 * @author Matt Paulin
 * @hibernate.class table="pgist_user_funding_source_toll" lazy="true"
 */
public class UserFundingSourceToll {

    public static final String PARKING_DOWNTOWN = "Commercial parking tax";
    public static final String ALASKA_WAY_VIADUCT = "Toll on Alaska Way Viaduct";
    public static final String I405N = "Toll on I-405, north of I-90";
    public static final String I405S = "Toll on I-405, south of I-90";
    public static final String I5N = "Toll on I-5, north of I-90";
    public static final String I5S = "Toll on I-5, south of I-90";
    public static final String SR520 = "Toll on SR 520 bridge";
    public static final String I90 = "Toll on I-90";
    public static final String SR167 = "Toll on SR 167";

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
     * @hibernate.many-to-one column="source_id" cascade="all"
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
