package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Course: SDEV-200
 * Class: Massage Class
 */

public class Massage extends Service {
    /** Variables */
    private String style;
    private int minutes;

    /** Constructors */
    Massage() {
    }
    Massage(String style, int minutes, double price) {
        super(price);
        this.style = style;
        this.minutes = minutes;
    }

    /** Accessors & Mutators */
    public String getStyle() {
        return this.style;
    }
    public void setStyle(String style) {
        this.style = style;
    }
    public int getMinutes() {
        return this.minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return this.getStyle();
    }

}
