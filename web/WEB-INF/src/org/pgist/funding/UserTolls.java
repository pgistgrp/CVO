package org.pgist.funding;

import java.io.Serializable;

/**
 * Used to pass the user tolls from the javascript
 * 
 * @author Matt Paulin
 */
public class UserTolls implements Serializable {

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
