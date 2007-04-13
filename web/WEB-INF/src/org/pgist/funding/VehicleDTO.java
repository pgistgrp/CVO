package org.pgist.funding;

import java.io.Serializable;

import org.pgist.users.Vehicle;


/**
 * @author Matt Paulin
 * 
 */
public class VehicleDTO implements Serializable, Comparable<VehicleDTO> {
    
    
    private Long id;
    
    private float milesPerGallon;
    
    private float milesPerYear;
    
    private float approxValue;
    
    
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
     * @return the milesPerGallon
     */
    public float getMilesPerGallon() {
        return milesPerGallon;
    }
    
    
    public void setMilesPerGallon(float milesPerGallon) {
        this.milesPerGallon = milesPerGallon;
    }
    
    
    /**
     * @return the milesPerYear
     */
    public float getMilesPerYear() {
        return milesPerYear;
    }
    
    
    public void setMilesPerYear(float milesPerYear) {
        this.milesPerYear = milesPerYear;
    }


    /**
     * @return
     */
    public float getApproxValue() {
        return approxValue;
    }


    public void setApproxValue(float approxValue) {
        this.approxValue = approxValue;
    }

	public void loadVehicleFromThis(Vehicle vehicle) {
		vehicle.setId(this.getId());
		vehicle.setApproxValue(this.getApproxValue());
		vehicle.setMilesPerGallon(this.getMilesPerGallon());
		vehicle.setMilesPerYear(this.getMilesPerYear());
	}	

	
	public void loadFromVehicle(Vehicle vehicle) {
		this.setId(vehicle.getId());
		this.setApproxValue(vehicle.getApproxValue());
		this.setMilesPerGallon(vehicle.getMilesPerGallon());
		this.setMilesPerYear(vehicle.getMilesPerYear());
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(VehicleDTO o) {
		if(o == null) return 0;
		
		if(this.getId() > o.getId()) return 1;
		if(this.getId() == o.getId()) return 0;
		return -1;
	}    
    
}//class Vehicle
