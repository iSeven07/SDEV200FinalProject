package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Course: SDEV-200
 * Class: MongoDB Connection Class
 * Description: This class allows the program to communicate with our MongoDB Atlas Cluster. Drivers files
 *              are required and are located in the lib\ folder. If you see any syntax or runtime errors related
 *              to MongoDB, it is most likely your classpath is not properly configured. 
 */

//import java.net.UnknownHostException;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
//import com.mongodb.client.model.FindOneAndReplaceOptions;
//import com.mongodb.client.model.ReturnDocument;
//import com.mongodb.client.model.FindOneAndReplaceOptions;
//import com.mongodb.client.model.ReturnDocument;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
//import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import static com.mongodb.client.model.Filters.eq;
//import static java.util.Collections.singletonList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDB {

    private static MongoClient instance = null;
    private static MongoDatabase db; 



    /** Create Database Connection */
    public MongoDB() throws InstantiationException {
        if (instance == null) {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:test123@cluster0.bw3dt7h.mongodb.net/?retryWrites=true&w=majority");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                                                                .applyConnectionString(connectionString)
                                                                .codecRegistry(codecRegistry)
                                                                .build();
        instance = MongoClients.create(clientSettings);
        db = instance.getDatabase("pseudo_massage");
        }
        else {
            throw new InstantiationException("Database Instance Already Exists");
        }
    }

    /** Lists Databases in the MongoDB Cluster */
    public void listDatabases() {
        try {
            List<Document> databases = instance.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.get("name")));
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    /** Lists Collections within our specified Database: pseudo_massage */
    public void listCollections() {
        try {
            // MongoCollection<Document> collection = database.getCollection("clients");

            for (String name : db.listCollectionNames()) {
                System.out.println(name);
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    /** Lists Documents within a given collection */
    public void listDocuments(String collection) {
        try {
            MongoCollection<Document> collections = db.getCollection(collection);
            FindIterable<Document> iterDoc = collections.find();
            Iterator<Document> it = iterDoc.iterator();
            System.out.println("\nPrinting out the " + collection + " collection:");
            while (it.hasNext()) {
                System.out.println(it.next());
            }

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    /** Inserts an object into the database */
    public void insertObject(Object o) {
        try {
            if (o instanceof Client) {
                MongoCollection<Client> clients = db.getCollection("clients", Client.class);
                clients.insertOne((Client)o);
                System.out.println("Client inserted.");
            }
            else if (o instanceof Therapist) {
                MongoCollection<Therapist> therapists = db.getCollection("therapists", Therapist.class);
                therapists.insertOne((Therapist)o);
                System.out.println("Therapist inserted.");
            }
            else if (o instanceof Massage) {
                MongoCollection<Massage> massages = db.getCollection("massages", Massage.class);
                massages.insertOne((Massage)o);
                System.out.println("Massage inserted.");
            }
            else if (o instanceof Scrub) {
                MongoCollection<Scrub> scrubs = db.getCollection("scrubs", Scrub.class);
                scrubs.insertOne((Scrub)o);
                System.out.println("Scrub inserted.");
            }
            else if (o instanceof Appointment) {
                MongoCollection<Appointment> scrubs = db.getCollection("appointments", Appointment.class);
                scrubs.insertOne((Appointment)o);
                System.out.println("Appointment inserted.");
            }
        } catch (Exception err) {
            System.out.println("Insert was not successful. Info:\n" + err.getMessage());
        }
    }

    /** Deletes an object from the database */
    public void deleteObject(Object o) {
        //System.out.println("Grade deleted:\t" + grades.deleteOne(filterByGradeId));
        try {
            if (o instanceof Client) {
                MongoCollection<Client> clients = db.getCollection("clients", Client.class);
                int userID = ((Client)o).getUserID();
                clients.deleteOne(Filters.eq("userID", userID));
                System.out.println("\nClient " + ((Client)o).getName() + " with ID " + ((Client)o).getUserID() + " has been removed from the database.");
            }
            else if (o instanceof Therapist) {
                MongoCollection<Therapist> therapists = db.getCollection("therapists", Therapist.class);
                int userID = ((Therapist)o).getUserID();
                therapists.deleteOne(Filters.eq("userID", userID));
                System.out.println("\nTherapist " + ((Therapist)o).getName() + " with ID " + ((Therapist)o).getUserID() + " has been removed from the database.");
            }
            else if (o instanceof Massage) {
                MongoCollection<Massage> massages = db.getCollection("massages", Massage.class);
                int serviceId = ((Massage)o).getServiceId();
                massages.deleteOne(Filters.eq("serviceId", serviceId));
                System.out.println("\nMassage " + ((Massage)o).getStyle() + " with ID " + ((Massage)o).getServiceId() + " has been removed from the database.");
            }
            else if (o instanceof Scrub) {
                MongoCollection<Scrub> scrubs = db.getCollection("scrubs", Scrub.class);
                int serviceId = ((Scrub)o).getServiceId();
                scrubs.deleteOne(Filters.eq("serviceId", serviceId));
                System.out.println("\nScrub " + ((Scrub)o).getProductType() + " with ID " + ((Scrub)o).getServiceId() + " has been removed from the database.");
            }
            else if (o instanceof Appointment) {
                MongoCollection<Appointment> appointments = db.getCollection("appointments", Appointment.class);
                int appointmentID = ((Appointment)o).getAppointmentID();
                appointments.deleteOne(Filters.eq("appointmentID", appointmentID));
                System.out.println("\nAppointment " + ((Appointment)o).getDateTime() + " with ID " + ((Appointment)o).getAppointmentID() + " has been removed from the database.");
            }
        } catch (Exception err) {
            System.out.println("Delete was not successful. Info:\n" + err.getMessage());
        }
    }

    /** Retrieves Collections and returns as objects */
    public <E> ArrayList<E> getCollection(String collection, Class<E> c) {
        // ArrayList<Client> clientList = new ArrayList<Client>();

        MongoCollection<E> collectionList = db.getCollection(collection, c);
        // FindIterable<Client> iterDoc = clients.find();
        // Iterator<Client> it = iterDoc.iterator();

        // while (it.hasNext()) {
        //     //System.out.println(it.next());
        //     clientList.add(new Client())
        // }
        ArrayList<E> docs = collectionList.find(new Document(), c).into(new ArrayList<>());

        return docs;
    }

    public void updateAppointment(Appointment appointment) {
        try {
        MongoCollection<Appointment> apptCol = db.getCollection("appointments", Appointment.class);
        Document filterByAppointmentID = new Document("appointmentID", appointment.getAppointmentID());
        //indOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
        apptCol.findOneAndReplace(filterByAppointmentID, appointment);
        System.out.println("Appointment replaced.");
        } catch (Exception ex) {
            System.out.println("Failed to update.");
        }
    }

    

}
