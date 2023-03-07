package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Course: SDEV-200
 * Class: Therapist Class
 */

public class Therapist extends User {
    /** Variables */
    private String licenseNumber;

    /** Constructors */
    public Therapist() {

    }
    Therapist(String firstName, String lastName) {
        super(firstName, lastName);
    }

    //** Accessors & Mutators */
    public String getLicenseNumber() {
        return this.licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
