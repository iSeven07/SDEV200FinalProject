package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Course: SDEV-200
 * Class: Appointment Class
 */

import java.util.ArrayList;
import java.util.Date;

public class Appointment {
    /** Variables */
    private int appointmentID;
    private Client client;
    private Therapist therapist;
    private ArrayList<Service> services = new ArrayList<Service>();
    private Date dateTime;
    private static int appointmentCount;

    /** Constructors */
    Appointment() {
        setAppointmentID();
    }
    Appointment(Client client, Therapist therapist, ArrayList<Service> services, Date dateTime) {
        this.client = client;
        this.therapist = therapist;
        this.services = services;
        this.dateTime = dateTime;
        setAppointmentID();
    }

    /** Accessors & Mutators */
    public int getAppointmentID() {
        return this.appointmentID;
    }
    public Client getClient() {
        return this.client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Therapist getTherapist() {
        return this.therapist;
    }
    public void setTherapist(Therapist therapist) {
        this.therapist = therapist;
    }
    public ArrayList<Service> getServices() {
        return this.services;
    }
    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
    public double getCost() {
        double totalCost = 0.00;

        for (Service service : services) {
            totalCost += service.getPrice();
        }

        return totalCost;
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /** Set AppointmentID */
    private void setAppointmentID() {
        appointmentCount++;
        this.appointmentID = appointmentCount;
    }
}
