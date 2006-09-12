package org.pgist.projects;

public class Vehicle {
    private Long id;
    private float milesPerGallon;
    private float milesPerYear;
    public Long getId() {
        return id;
    }

    public float getMilesPerGallon() {

        return milesPerGallon;
    }

    public float getMilesPerYear() {
        return milesPerYear;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMilesPerGallon(float milesPerGallon) {

        this.milesPerGallon = milesPerGallon;
    }

    public void setMilesPerYear(float milesPerYear) {
        this.milesPerYear = milesPerYear;
    }
}
