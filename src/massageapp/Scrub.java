package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Course: SDEV-200
 * Class: Scrub Class
 */

public class Scrub extends Service {
    /** Variables */
    private String productType;
    private static int minutes = 30;

    /** Constructors */
    public Scrub() {
    }

    Scrub(String productType, double price) {
        super(price);
        this.productType = productType;
    }

    /** Accessors & Mutators */
    public String getProductType() {
        return this.productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public static int getMinutes() {
        return minutes;
    }

    @Override
    public String toString() {
        return this.getProductType();
    }

}
