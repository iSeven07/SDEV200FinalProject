package massageapp;

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

    /** Set Unique Service ID */
    private void setServiceID() {
        serviceCount++;
        this.serviceID = serviceCount;
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
    
}
