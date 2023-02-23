package massageapp;

public class Scrub extends Service {
    /** Variables */
    private String productType;
    private static int minutes = 30;

    /** Constructors */
    Scrub() {
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

}
