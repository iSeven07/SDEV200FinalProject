package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Date: 2-22-2023
 * Course: SDEV-200
 * Last Update: 2-23-2023
 * Description: This application is the start of a program that will allow a user
 *              to create appointments at a Massage Therapy Business. Currently
 *              the application will create our various class objects and then
 *              test their functionality. The application will create a client,
 *              therapist, massage, scrub, and then use those objects to create an
 *              appointment object. They will then be sent to a print() function to
 *              print their details to the console for verification that the classes
 *              are working.
 */

import java.util.ArrayList;
import java.util.Arrays;


/** Test Application */
public class TestClasses {
    public static void main(String[] args) throws Exception {

        // Create Class Objects
        System.out.println("\nTesting Classes...\n");

        /** User Creation */
        Client client1 = new Client("John", "Smith");
        client1.setPhoneNumber("123-456-7890");
        Therapist therapist1 = new Therapist("Jane", "Doe");
        therapist1.setLicenseNumber("LNO1234567890");

        /** Service Creation */
        Massage massage1 = new Massage("Swedish Massage", 90, 80.00);
        Scrub scrub1 = new Scrub("Sugar Scrub", 30.00);

        /** Appointment Creation */
        Appointment appointment1 = new Appointment(client1, therapist1, new ArrayList<Service>(Arrays.asList(massage1, scrub1)), new java.util.Date());

        /** Print Objects */
        print(client1);
        print(therapist1);
        print(massage1);
        print(scrub1);
        print(appointment1);

        /** Test MongoDB Connection */
        // NOTE: Connection should be allowed from anywhere in the world; however, a connection denied
        //       message may still happen. If so, a fix will be found or alternatively the instructor's IP
        //       address can be added to the allowed connections list. Upon successful connection, the list
        //       will populate test collection's held within the MongoDB Atlas cluster.

        try{
            System.out.println("Testing MongoDB Atlas connection... Please wait.");
            MongoConnector dbConnection = new MongoConnector("mongodb+srv://admin:test123@cluster0.lmb2nuu.mongodb.net/?retryWrites=true&w=majority");
            System.out.println("\nListing Databases:");
            dbConnection.listDatabases();
            System.out.println("\nListing Collections:");
            dbConnection.listCollections();
            System.out.println();
        }catch(InstantiationException error){
            System.out.println(error.getMessage());
        }

    }

    /** Print Objects */
    public static void print(Object obj) {

        System.out.println("getClass(): " + obj.getClass().getSimpleName());
        /** Print User Related Information if User */
        if (obj instanceof User) {
            System.out.println("Name: " + ((User) obj).getName());
            System.out.println("User ID: " + ((User) obj).getUserID());
            System.out.println("Creation Date: " + ((User) obj).getCreationDate());
            if (obj instanceof Client) {
                System.out.println("Phone No.: " + ((Client) obj).getPhoneNumber());
            } else {
                System.out.println("License No.: " + ((Therapist) obj).getLicenseNumber());
            }
        } 
        /** Print Service Relalated Information if Service */
        else if (obj instanceof Service) {
            if (obj instanceof Massage) {
                System.out.println("Service: " + ((Massage)obj).getStyle());
                System.out.println("Length: " + ((Massage)obj).getMinutes());  
            }
            else {
                System.out.println("Service: " + ((Scrub)obj).getProductType());
                System.out.println("Length: " + Scrub.getMinutes());
            }
            System.out.println("Service ID: " + ((Service)obj).getServiceID());
            System.out.printf("Price: $%.2f%n", ((Service)obj).getPrice());
        }
        /** Print Appointment Related Information if Appointment */
        else if (obj instanceof Appointment) {
            System.out.println("Appointment Date/Time: " + ((Appointment)obj).getDateTime());
            System.out.println("AppointmentID: " + ((Appointment)obj).getAppointmentID());
            System.out.println("Client Name: " + (((Appointment)obj).getClient()).getName());
            System.out.println("Therapist Name: " + (((Appointment)obj).getTherapist()).getName());
            System.out.print("Services:");
            for (Service service : ((Appointment)obj).getServices()) {
                if (service instanceof Massage)
                    System.out.print(" " + ((Massage)service).getStyle());
                else
                    System.out.print(", " + ((Scrub)service).getProductType());
            }
            System.out.printf("\nAppointment Cost: $%.2f%n", ((Appointment)obj).getCost());
        }
        System.out.println("\n------------------\n");
    }
}
