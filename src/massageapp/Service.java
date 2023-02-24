package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Course: SDEV-200
 * Class: Abstract Service Class
 */

public abstract class Service {

    /** Variables */
    private int serviceID;
    private double price;
    static int serviceCount;

    /** Constructors */
    protected Service() {
        setServiceID();
    }
    protected Service(double price) {
        this.price = price;
        setServiceID();
    }

    /** Accessors & Mutators */
    public int getServiceID() {
        return this.serviceID;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    /** Set Unique Service ID */
    private void setServiceID() {
        serviceCount++;
        this.serviceID = serviceCount;
    }
    
}
