package massageapp;

import java.util.ArrayList;

/* Purpose is to maintain a "Data Store" for the application */
public class Store {
        // Store of Collections
        private static ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        private static ArrayList<Client> clients = new ArrayList<Client>();
        private static ArrayList<Therapist> therapists = new ArrayList<Therapist>();
        private static ArrayList<Scrub> scrubs = new ArrayList<Scrub>();
        private static ArrayList<Massage> massages = new ArrayList<Massage>();

    // Store() {

    // }

    public static ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    public static ArrayList<Client> getClients() {
        return clients;
    }
    public static ArrayList<Therapist> getTherapists() {
        return therapists;
    }
    public static ArrayList<Scrub> getScrubs() {
        return scrubs;
    }
    public static ArrayList<Massage> getMassages() {
        return massages;
    }

    public static void setAppointments(ArrayList<Appointment> appointmentList) {
        appointments = appointmentList;
    }
    public static void setClients(ArrayList<Client> clientList) {
        clients = clientList;
    }
    public static void setTherapists(ArrayList<Therapist> therapistList) {
        therapists = therapistList;
    }
    public static void setScrubs(ArrayList<Scrub> scrubList) {
        scrubs = scrubList;
    }
    public static void setMassages(ArrayList<Massage> massageList) {
        massages = massageList;
    }

    public static void addClient(Client client) {
        clients.add(client);
    }
    public static void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
    

}
