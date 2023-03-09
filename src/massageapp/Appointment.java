package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Course: SDEV-200
 * Class: Appointment Class
 */

import java.util.ArrayList;
import java.util.Date;
//import java.util.List;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class Appointment {
    /** Variables */
    @BsonProperty("appointmentID")
    private int appointmentID;
    //private Client client;
    //private Therapist therapist;
    //private ArrayList<Service> services = new ArrayList<Service>();
    //private List<Service> services = new ArrayList<Service>();
    private int client;
    private int therapist;
    private ArrayList<Integer> services = new ArrayList<Integer>();
    private Date dateTime;
    private static int appointmentCount;

    /** Constructors */
    protected Appointment() {
        //setAppointmentID();
        ++appointmentCount;
    }
    Appointment(int client, int therapist, ArrayList<Integer> services, Date dateTime) {
        this.client = client;
        this.therapist = therapist;
        this.services = services;
        this.dateTime = dateTime;
        //setAppointmentID();
        this.appointmentID = ++appointmentCount;
    }

    /** Accessors & Mutators */
    public int getAppointmentID() {
        return this.appointmentID;
    }
    public int getClient() {
        return this.client;
    }
    public void setClient(int client) {
        this.client = client;
    }
    public int getTherapist() {
        return this.therapist;
    }
    public void setTherapist(int therapist) {
        this.therapist = therapist;
    }
    public ArrayList<Integer> getServices() {
        return this.services;
    }
    public void setServices(ArrayList<Integer> services) {
        this.services = services;
    }
    @BsonIgnore
    public double getCost() {
        double totalCost = 0.00;

        // for (Service service : services) {
        //     totalCost += service.getPrice();
        // }

        for (Massage massage : Store.getMassages()) {
            if(massage.getServiceId() == this.services.get(0)) {
                totalCost += massage.getPrice();
                System.out.println("getCost() Massage Found!");
            }
            else {
                System.out.println(massage.getStyle() + ": " + massage.getServiceId() + " != " + this.services.get(0));
            }
        }
        for (Scrub scrub : Store.getScrubs()) {
            if(scrub.getServiceId() == this.services.get(1)) {
                totalCost += scrub.getPrice();
                System.out.println("getCost() Scrub Found!");
            }
        }

        return totalCost;
    }

    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    // Used for Displaying on Data Table
    @BsonIgnore
    public String getClientName() {
        //return this.getClient().getName();
        for(Client client : Store.getClients()) {
            if(client.getUserID() == this.client) {
                return client.getName();
            }
        }
        return "Client not found";
    }

    @BsonIgnore
    public String getTherapistName() {
        //return this.getTherapist().getName();
        for(Therapist therapist : Store.getTherapists()) {
            if(therapist.getUserID() == this.therapist) {
                return therapist.getName();
            }
        }
        return "Therapist not found";
    }

    @BsonIgnore
    public String getServicesString() {
        String services = "";
        // for (Service service : this.services) {
        //     if (service instanceof Massage) {
        //         services += ((Massage)service).getStyle();
        //     }
        //     else {
        //         services += ", " + ((Scrub)service).getProductType();
        //     }
        // }

        for(Massage massage : Store.getMassages()) {
            if(massage.getServiceId() == this.services.get(0)) {
                services += massage.getStyle();
            }
        }
        for(Scrub scrub : Store.getScrubs()) {
            if(this.services.size() > 1 && scrub.getServiceId() == this.services.get(1)) {
                services += ", " + scrub.getProductType();
            }
        }
        return services;
    }

    @BsonIgnore
    public String getClientPhoneNumber() {
        // return this.getClient().getPhoneNumber();
        for(Client client : Store.getClients()) {
            if(client.getUserID() == this.client) {
                return client.getPhoneNumber();
            }
        }
        return "Client not found";
    }

    public void setAppointmentID(int appointmentID) {
        // appointmentCount++;
        // this.appointmentID = appointmentCount;
        this.appointmentID = appointmentID;
    }

    @Override
    public String toString() {
        return "" + this.getDateTime();
    }
}
