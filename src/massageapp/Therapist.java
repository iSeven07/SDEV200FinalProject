package massageapp;

public class Therapist extends User {
    /** Variables */
    private String licenseNumber;

    /** Constructors */
    Therapist() {

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
