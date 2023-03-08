package massageapp;

//import org.bson.codecs.pojo.annotations.BsonId;
//import org.bson.codecs.pojo.annotations.BsonProperty;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Course: SDEV-200
 * Class: Abstract Service Class
 */

public abstract class Service {

    /** Variables */
    private int serviceId;
    private double price;
    static int serviceCount;

    /** Constructors */
    protected Service() {
        //setServiceID();
        //this.serviceId = 1001;
        // only used by MongoDB when creating objects. For some reason ID doesn't want to be passed back correctly.
        // it can be fixed with this method... not preferred, but works for now.
        //this.serviceID = serviceCount - (Store.getMassages().size() + Store.getScrubs().size());
        ++serviceCount;
    }
    protected Service(double price) {
        this.price = price;
        //setServiceID();
        this.serviceId = ++serviceCount;
    }

    /** Accessors & Mutators */
    public int getServiceId() {
        return this.serviceId;
    }

    // Has to be public for MongoDB to set
    public void setServiceId(int serviceID) {
        this.serviceId = serviceID;
    }

    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    /** Set Unique Service ID */
    // private void setServiceID() {
    //     serviceCount++;
    //     this.serviceID = serviceCount;
    // }

    @Override
    abstract public String toString();
    
}
