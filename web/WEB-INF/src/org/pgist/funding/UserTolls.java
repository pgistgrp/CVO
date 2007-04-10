package org.pgist.funding;

import java.io.Serializable;

/**
 * Used to pass the user tolls from the javascript
 * 
 * @author Matt Paulin
 */
public class UserTolls implements Serializable {

    /**
     * meaning: Sales Tax<br>
     * value: 1<br>
     * calculation: annual cost = (tax rate) * (estimated annual consumption)
     */
    public static final int TYPE_SALES_TAX = 1;
    
    /**
     * meaning: Annual Vehicle License Fee<br>
     * value: 2<br>
     * calculation: annual cost = (tax rate) * (number of vehicles)
     */
    public static final int TYPE_LICENSE = 2;
    
    /**
     * meaning: Annual Motor Vehicle Excise Tax<br>
     * value: 3<br>
     * calculation: annual cost = sum( (tax rate) * (vehicle value) )
     */
    public static final int TYPE_MOTOR_TAX = 3;
    
    /**
     * meaning: Gas Tax<br>
     * value: 4<br>
     * calculation: annual cost = sum( (tax rate) / (miles per galon) * (miles driven per year) )
     */
    public static final int TYPE_GAS_TAX = 4;
    
    /**
     * meaning: Sales Tax on Gas<br>
     * value: 5<br>
     * calculation: annual cost = sum( (tax rate) / (miles per galon) * (miles driven per year) )
     */
    public static final int TYPE_SALES_GAS_TAX = 5;
    
    /**
     * meaning: Employer Excise Tax<br>
     * value: 6<br>
     * calculation: No direct cost calculated
     */
    public static final int TYPE_EMPLOYER_EXCISE_TAX = 6;
    
    /**
     * meaning: Commercial Parking Tax<br>
     * value: 7<br>
     * calculation: annual cost = (tax rate) * (parkings per year)
     */
    public static final int TYPE_PARKING_TAX = 7;
    
    /**
     * meaning: 1.Tolls<br>
     * value: 8<br>
     * calculation: annual cost = (tax rate) * (trips per year)
     */
    public static final int TYPE_TOLLS = 8;	
	
	private boolean SR99;
	private boolean I405S;
	private boolean SR520;
	private boolean I90;
	private boolean SR167;
	private boolean parking;
	private boolean I405N;
	
	public boolean isI405N() {
		return I405N;
	}
	public void setI405N(boolean i405n) {
		I405N = i405n;
	}
	public boolean isI405S() {
		return I405S;
	}
	public void setI405S(boolean i405s) {
		I405S = i405s;
	}
	public boolean isI90() {
		return I90;
	}
	public void setI90(boolean i90) {
		I90 = i90;
	}
	public boolean isParking() {
		return parking;
	}
	public void setParking(boolean parking) {
		this.parking = parking;
	}
	public boolean isSR167() {
		return SR167;
	}
	public void setSR167(boolean sr167) {
		SR167 = sr167;
	}
	public boolean isSR520() {
		return SR520;
	}
	public void setSR520(boolean sr520) {
		SR520 = sr520;
	}
	public boolean isSR99() {
		return SR99;
	}
	public void setSR99(boolean sr99) {
		SR99 = sr99;
	}	
}
