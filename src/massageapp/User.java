package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Course: SDEV-200
 * Class: Abstract User Class
 */

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonIgnore;
//import org.bson.codecs.pojo.annotations.BsonProperty;

public abstract class User {
    /** Variables */
    private int userID;
    private String firstName, lastName;
    private Date creationDate;
    private static int numUsers; // Increases with each new user and used to set unique userID

    /** Constructors */
    protected User() {
        //this.creationDate = new Date();
        //setUserID();
        //this.userID = numUsers - (Store.getClients().size() + Store.getTherapists().size());
        ++numUsers;
    }
    protected User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDate = new Date();
        this.userID = numUsers++;
    }

    /** Accessors and Mutators */
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public int getUserID() {
        return this.userID;
    }

    // MongoDB needs this set to public, although would prefer it be protected
    public void setUserID(int id) {
        this.userID = id;
    }

    @BsonIgnore
    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Override
    public String toString() {
        return this.getName();
    }


}
