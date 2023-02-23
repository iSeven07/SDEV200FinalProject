package massageapp;

import java.util.Date;

public abstract class User {
    /** Variables */
    private int userID;
    private String firstName, lastName;
    private Date creationDate;
    private static int numUsers; // Increases with each new user and used to set unique userID

    /** Constructors */
    protected User() {
        this.creationDate = new Date();
        setUserID();
    }
    protected User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.creationDate = new Date();
        setUserID();
    }

    /** Accessors and Mutators */
    public int getUserID() {
        return this.userID;
    }
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


    /** Sets and ups the userID to stay unique */
    private void setUserID() {
        numUsers++;
        this.userID = numUsers;
    }


}
