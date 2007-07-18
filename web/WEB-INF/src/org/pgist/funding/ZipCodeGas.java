package org.pgist.funding;

import java.io.Serializable;


/**
 * A zip code object that corrilates gas tax with the zip code
 * 
 * @author Matt Paulin
 * @hibernate.class table="pgist_zip_code_gas" lazy="true"
 */
public class ZipCodeGas implements Serializable {
    
	private String zipcode;
	private Float avgGas;
	
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
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

    /**
     * @return
     * 
     * @hibernate.property not-null="true"
     */
	public Float getAvgGas() {
		return avgGas;
	}
	public void setAvgGas(Float avgGas) {
		this.avgGas = avgGas;
	}

}//class UserCommute
