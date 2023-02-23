package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Course: SDEV-200
 * Class: Client Class
 */

public class Client extends User {
    /** Variables */
    private String phoneNumber;

    /** Constructors */
    Client() {

    }
    Client(String firstName, String lastName) {
        super(firstName, lastName);
    }

    /** Accessors & Mutators */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
