package org.pgist.funding;

import java.io.Serializable;


/**
 * A zip code factor object used to provide access to the different types of zip code factors
 * 
 * @author Matt Paulin
 * @hibernate.class table="pgist_zip_code_factor" lazy="true"
 */
public class ZipCodeFactor implements Serializable {
    
	private String zipcode;
	private int SR99;
	private int SR520;
	private int SR167;
	private int parking;
    private int I90;
    private int I405S;
	private int I405N;
	private int I5N;
	private int I5S;

    private Long id;
	
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
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public int getI405N() {
		return I405N;
	}
	public void setI405N(int i405N) {
		I405N = i405N;
	}
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public int getI405S() {
		return I405S;
	}
	public void setI405S(int i405s) {
		I405S = i405s;
	}
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public int getI90() {
		return I90;
	}
	public void setI90(int i90) {
		I90 = i90;
	}
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public int getParking() {
		return parking;
	}
	public void setParking(int parking) {
		this.parking = parking;
	}
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public int getSR167() {
		return SR167;
	}
	public void setSR167(int sr167) {
		SR167 = sr167;
	}
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public int getSR520() {
		return SR520;
	}
	public void setSR520(int sr520) {
		SR520 = sr520;
	}
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public int getSR99() {
		return SR99;
	}
	public void setSR99(int sr99) {
		SR99 = sr99;
	}
	
	
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public int getI5N() {
        return I5N;
    }
    public void setI5N(int i5n) {
        I5N = i5n;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
    public int getI5S() {
        return I5S;
    }
    public void setI5S(int i5s) {
        I5S = i5s;
    }
    
    
    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}//class UserCommute
