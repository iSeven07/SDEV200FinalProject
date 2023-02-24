package massageapp;

/** This Class is used to connect to MongoDB Atlas */

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
 
import java.util.ArrayList;
import java.util.List;
 
public class MongoConnector {
 
   private static MongoClient instance = null;
 
   public MongoConnector(String dbUri) throws InstantiationException {
       if(instance == null) {
           instance = MongoClients.create(dbUri);
       }else{
           throw new InstantiationException("Connector Instance Already Exists");
       }
   }
 
   public MongoClient getInstance() {
       return instance;
   }
 
   /** Lists Databases in the MongoDB Cluster */
   public void listDatabases(){
       try {
           List<Document> databases = instance.listDatabases().into(new ArrayList<>());
           databases.forEach(db -> System.out.println(db.get("name")));
       } catch(Exception err){
           System.out.println(err.getMessage());
       }
   }
   
   /** Lists Collections within our specified Database: pseudo_massage */
   public void listCollections(){
    try {
        MongoDatabase database = instance.getDatabase("pseudo_massage");
       // MongoCollection<Document> collection = database.getCollection("clients");

       for (String name : database.listCollectionNames()) {
        System.out.println(name);
    }

    } catch(Exception err) {
        System.out.println(err.getMessage());
    }
   }
   /** Lists Documents within a given collection */
   public void listDocuments() {
        try {
            //MongoDatabase database = instance.getDatabase("pseudo_massage");
            //MongoCollection<Document> collections = database.getCollection("clients");

            // Code to iterate through MongoCollection<Document>
            // More research is needed as currently dealing with MongoCollection<Document> not being iterable
            System.out.println("listDocuments() is still a work in progress...");

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
   }
}
