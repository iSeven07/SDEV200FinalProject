package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Date: 2-22-2023
 * Course: SDEV-200
 * Last Update: 3-5-2023
 * 
 * Notes: The application is still a work in progress. 
 * 
 */

//import java.time.LocalDate;
import java.util.Date;



//import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
//import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
//import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/** Main Application */
public class Main extends Application {

    /** Database */
    MongoDB db;

    /** Interactive UI Elements */
    private Button btBookAppointment = new Button("Book");
    private Button btEditAppointment = new Button("Edit");
    private Button btDeleteAppointment = new Button("Delete");
    private Button btCancelEdit = new Button("Cancel Edit");
    private TextField tfFirstName = new TextField();
    private TextField tfLastName = new TextField();
    private TextField tfPhoneNumber = new TextField();
    private ChoiceBox<Massage> cbMassage = new ChoiceBox<>();
    private ChoiceBox<Scrub> cbScrub = new ChoiceBox<>();
    private ChoiceBox<Therapist> cbTherapist = new ChoiceBox<>();
    private DatePicker dpDate = new DatePicker();
    private ChoiceBox<String> cbTime = new ChoiceBox<>();
    private TextField tfSearch = new TextField();
    private Text statusText = new Text();

    /** Current User Selections */
    //private Massage selectedMassage;
    //private Scrub selectedScrub;
    private Therapist selectedTherapist;
    private Appointment selectedAppointment;
    private Client selectedClient;
    //private ArrayList<Service> services = new ArrayList<Service>(Arrays.asList(null, null));
    //private ObservableList<Service> services = FXCollections.observableArrayList(null, null);

    /** App Collections */
    private ArrayList<Client> clients = new ArrayList<Client>();
    //private ArrayList<Therapist> therapists = new ArrayList<Therapist>();
    private ObservableList<Therapist> therapists = FXCollections.observableArrayList();
    private ObservableList<Massage> massages = FXCollections.observableArrayList();
    private ObservableList<Scrub> scrubs = FXCollections.observableArrayList();
    //private ArrayList<Massage> massages = new ArrayList<Massage>();
    //private ArrayList<Scrub> scrubs = new ArrayList<Scrub>();
    //private ArrayList<Appointment> appointments = new ArrayList<Appointment>();
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(); // May replace ArrayList<Appointment> for searching
    private FilteredList<Appointment> filteredList;


    @Override
    public void start(Stage primaryStage) throws Exception {
    
        load(); // Populate Application Data (MongoDB)
        //testData();

        appointmentList = FXCollections.observableArrayList(Store.getAppointments());
        filteredList = new FilteredList<>(appointmentList);

        // Filtered List will be used for searching the TableView
        //FilteredList<Appointment> filteredAppointments = new FilteredList<>(appointmentList, p -> true);

        /** Data Table */
        TableView<Appointment> tableView = new TableView<>();

        // Columns
        TableColumn<Appointment, String> column1 = new TableColumn<>("ID");
        TableColumn<Appointment, String> column2 = new TableColumn<>("Client Name");
        TableColumn<Appointment, String> column3 = new TableColumn<>("Phone Number");
        TableColumn<Appointment, String> column4 = new TableColumn<>("Therapist Name");
        TableColumn<Appointment, String> column5 = new TableColumn<>("Services");
        TableColumn<Appointment, String> column6 = new TableColumn<>("Date / Time");

        column1.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        column2.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("clientPhoneNumber"));
        column4.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        column5.setCellValueFactory(new PropertyValueFactory<>("servicesString"));
        column6.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);

        // Load appointments into Table
        // for (Appointment appointment : appointmentList) {
        //     tableView.getItems().add(appointment);
        // }
        //tableView.getItems().addAll(Store.getAppointments());
        tableView.setItems(filteredList); // Set tableView to the filtered list


        // Labels
        Label lbFirstName = new Label("First Name:");
        Label lbLastName = new Label("Last Name:");
        Label lbPhoneNumber = new Label("Phone Number:");
        Label lbMassageType = new Label ("Massage:");
        Label lbScrubType = new Label ("Scrub:");
        Label lbTherapist = new Label("Therapist:");
        Label lbDate = new Label("Date:");

        // Choice Boxes -- Needs Work
        // for (Massage massage : massages) {
        //     //cbMassage.getItems().add(massage.getStyle());
        //     cbMassage.getItems().add(massage);
        // }
        // for (Scrub scrub : scrubs) {
        //     //cbScrub.getItems().add(scrub.getProductType());
        //     cbScrub.getItems().add(scrub);
        // }
        // for (Therapist therapist : therapists) {
        //     //cbTherapist.getItems().add(therapist.getName());
        //     cbTherapist.getItems().add(therapist);
        // }
        cbMassage.getItems().addAll(Store.getMassages());
        cbScrub.getItems().addAll(Store.getScrubs());
        cbTherapist.getItems().addAll(Store.getTherapists());

        // Grid
        GridPane gridPane = new GridPane();
        gridPane.add(lbFirstName, 0, 0);
        gridPane.add(tfFirstName, 1, 0);
        gridPane.add(lbLastName, 0, 1);
        gridPane.add(tfLastName, 1, 1);
        gridPane.add(lbPhoneNumber, 0, 2);
        gridPane.add(tfPhoneNumber, 1, 2);
        gridPane.add(lbMassageType, 2, 0);
        gridPane.add(cbMassage, 3, 0);
        gridPane.add(lbScrubType, 2, 1);
        gridPane.add(cbScrub, 3, 1);
        gridPane.add(lbTherapist, 2, 2);
        gridPane.add(cbTherapist, 3, 2);
        gridPane.add(lbDate, 2, 3);
        gridPane.add(dpDate, 3, 3);
        gridPane.add(cbTime, 4, 3);
        
        gridPane.add(btBookAppointment, 5, 0);
        gridPane.add(btEditAppointment, 5, 1);
        gridPane.add(btDeleteAppointment, 5, 2);
        gridPane.add(btCancelEdit, 5, 3);

        // UI Settings
        cbScrub.setDisable(true);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        btBookAppointment.setPrefWidth(100);
        btEditAppointment.setPrefWidth(100);
        btDeleteAppointment.setPrefWidth(100);
        btCancelEdit.setPrefWidth(100);
        btCancelEdit.setVisible(false);
        
        ScrollPane sp = new ScrollPane();
        sp.setContent(statusText);
        sp.setHbarPolicy(ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        sp.setVvalue(1.0);
        sp.setMinHeight(50);


        /** Process Events */

        // Book Button
        btBookAppointment.setOnAction( e -> {

            if (checkInput()) {
            }
            else {
                Date date = Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                // FUTURE - Add Hours to Date using: date = DateUtils.addHours(date, time) or similar
    
                // Create Appointment
                Client newClient = new Client(tfFirstName.getText(), tfLastName.getText());
                newClient.setPhoneNumber(tfPhoneNumber.getText());

                // Does client already exist by phone number?
                boolean clientExists = false;
                for(Client client : Store.getClients()) {
                    if((client.getPhoneNumber().equals(newClient.getPhoneNumber()))) {
                        clientExists = true;
                        newClient = client; // Set newClient to client already in system
                    }
                }
                if (!clientExists) {
                    // Add Client to DB and Store if they do not exist
                    Store.addClient(newClient);
                    db.insertObject(newClient);
                    System.out.println("Client will be added.");
                }
                else {
                    System.out.println("Client found by phone number.");
                }

                // Create Appointment Object
                ArrayList<Integer> tempServices = new ArrayList<Integer>();
                tempServices.add(cbMassage.getSelectionModel().getSelectedItem().getServiceId());
                if (cbScrub.getSelectionModel().getSelectedItem() != null) {
                    tempServices.add(cbScrub.getSelectionModel().getSelectedItem().getServiceId());
                }
                Appointment newAppointment = new Appointment(newClient.getUserID(), selectedTherapist.getUserID(), tempServices, date);

                // Add Appointment to DB and Store
                //Store.addAppointment(newAppointment);
                appointmentList.add(newAppointment); // ObservableList should automatically update the Store Arraylist
                //filteredList.add(newAppointment); // add to filter
                db.insertObject(newAppointment);

                // There is a better method to update ObservableList when its watched list changes... however,
                // this works for now as more research is needed on ObservableList listeners.
                //filteredList = new FilteredList<>(FXCollections.observableArrayList(Store.getAppointments()));
                //tableView.setItems(filteredList);
                
                // Add Appointment to Table
                //tableView.getItems().add(newAppointment);
                


                clearInput();
            }     
        });

        // Therapist Select
        cbTherapist.setOnAction(e -> {
            selectedTherapist = Store.getTherapists().get(cbTherapist.getSelectionModel().getSelectedIndex());
        });

        // Massage Select
        cbMassage.setOnAction(e -> {
            //selectedMassage = massages.get(cbMassage.getSelectionModel().getSelectedIndex());
            //services.add(selectedMassage);
            //services.set(0, selectedMassage);
            //System.out.println(((Massage)services.get(0)).getStyle());
            cbScrub.setDisable(false);
        });

        // Scrub Select
        cbScrub.setOnAction(e -> {
            //selectedScrub = scrubs.get(cbScrub.getSelectionModel().getSelectedIndex());
            //services.add(selectedScrub);
            //services.set(1, selectedScrub);
            //System.out.println(((Scrub)services.get(1)).getProductType());
        });

        // Delete Button
        btDeleteAppointment.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
            Appointment deleteApt = tableView.getSelectionModel().getSelectedItem();
            db.deleteObject(deleteApt);

            //Store.getAppointments().remove(deleteApt);
            appointmentList.remove(deleteApt); // ObservableList should automatically remove the object from the Store ArrayList
            //filteredList.remove(deleteApt);
            //tableView.getItems().remove(deleteApt);
            //filteredList.remove(deleteApt);

            // There is a better method to update ObservableList when its watched list changes... however,
            // this works for now as more research is needed on ObservableList listeners.
            //filteredList = new FilteredList<>(FXCollections.observableArrayList(Store.getAppointments()));
            //tableView.setItems(filteredList);
        
            BookingDialog  deleteDialog = new BookingDialog("Delete Appointment", "Booking #" + deleteApt.getAppointmentID() + " on " + deleteApt.getDateTime() + " has been deleted.");
            deleteDialog.showAndWait();
            }
            else {
                BookingDialog deleteDialog = new BookingDialog("Deletion Issue", "You do not have an appointment selected.");
                deleteDialog.showAndWait();
            }
        });

        // Edit Button
        btEditAppointment.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {

                if (btEditAppointment.getText().equals("Edit")) 
                { // Get the Selected Appointment
                    selectedAppointment = tableView.getSelectionModel().getSelectedItem();
                    // Set the editable fields
// NEED TO FIX
                    // tfFirstName.setText(selectedAppointment.getClient().getFirstName());
                    // tfLastName.setText(selectedAppointment.getClient().getLastName());
                    // tfPhoneNumber.setText(selectedAppointment.getClient().getPhoneNumber());
                    // selectedTherapist = selectedAppointment.getTherapist();
                    //System.out.println(Store.getClients().get(selectedAppointment.getClient() - 1).getFirstName());

                    for (Client client : Store.getClients()) {
                        if (client.getUserID() == selectedAppointment.getClient()) {
                            selectedClient = client;
                        }
                    }

                    try {
                    // tfFirstName.setText(Store.getClients().get(selectedAppointment.getClient() - 1).getFirstName());
                    // tfLastName.setText(Store.getClients().get(selectedAppointment.getClient() - 1).getLastName());
                    // tfPhoneNumber.setText(Store.getClients().get(selectedAppointment.getClient() - 1).getPhoneNumber());
                    tfFirstName.setText(selectedClient.getFirstName());
                    tfLastName.setText(selectedClient.getLastName());
                    tfPhoneNumber.setText(selectedClient.getPhoneNumber());

                    //cbTherapist.getSelectionModel().select(Store.getTherapists().get(selectedAppointment.getTherapist() - 1));
                    //cbTherapist.setValue(Store.getTherapists().get(selectedAppointment.getTherapist() - 1));
                    //System.out.println(Store.getTherapists().get(selectedAppointment.getTherapist() - 3).getName());
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        System.out.println("Client in Array: " + (selectedAppointment.getClient() - 1));
                    }

                    for (Therapist therapist : Store.getTherapists()) {
                        if (therapist.getUserID() == selectedAppointment.getTherapist()) {
                            cbTherapist.getSelectionModel().select(therapist);
                        }
                    }

                    //System.out.println(selectedTherapist.getName());
                    //cbTherapist.setValue(therapists.get(0));
// NEED TO FIX
                    // cbTherapist.getSelectionModel().select(selectedTherapist);
                    // cbMassage.getSelectionModel().select((Massage)(selectedAppointment.getServices().get(0)));
                    for (Massage massage: Store.getMassages()) {
                        if (massage.getServiceId() == selectedAppointment.getServices().get(0)) {
                            cbMassage.getSelectionModel().select(massage);
                        }
                    }
                    try {
                    //cbScrub.getSelectionModel().select((Scrub)(selectedAppointment.getServices()).get(1));
                    for (Scrub scrub : Store.getScrubs()) {
                        if (scrub.getServiceId() == selectedAppointment.getServices().get(1)) {
                            cbScrub.getSelectionModel().select(scrub);
                        }
                    }
                    } catch (Exception ex) {
                        System.out.println("No scrub found. Continuing...");
                    }

                    //cbScrub.setSelectionModel(null); // Work in Progress
                    dpDate.setValue(selectedAppointment.getDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                    btEditAppointment.setText("Save");
                    btCancelEdit.setVisible(true);
                    tableView.setDisable(true);
                    btBookAppointment.setDisable(true);
                    btDeleteAppointment.setDisable(true);
                }
                else if (!checkInput()) {
// NEED TO FIX
                    // selectedAppointment.getClient().setFirstName(tfFirstName.getText());
                    // selectedAppointment.getClient().setLastName(tfLastName.getText());
                    // selectedAppointment.getClient().setPhoneNumber(tfPhoneNumber.getText());

                    for(Client client : Store.getClients()) {
                        if (client.getUserID() == selectedAppointment.getClient()) {
                            client.setFirstName(tfFirstName.getText());
                            client.setLastName(tfLastName.getText());
                            client.setPhoneNumber(tfPhoneNumber.getText());
                            db.updateClient(client);
                        }
                    }

                    ArrayList<Integer> tempServices = new ArrayList<Integer>();

                    tempServices.add(cbMassage.getSelectionModel().getSelectedItem().getServiceId());
                    if (cbScrub.getSelectionModel().getSelectedItem() != null) {
                        tempServices.add(cbScrub.getSelectionModel().getSelectedItem().getServiceId());
                    }
// NEED TO FIX                 
                    selectedAppointment.setServices(tempServices);
                    selectedAppointment.setTherapist(selectedTherapist.getUserID());



                    Date date = Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    // FUTURE - Add Hours to Date using: date = DateUtils.addHours(date, time) or similar
                    selectedAppointment.setDateTime(date);

                    //Bson filter = Filters.eq("appointmentID", selectedAppointment.getAppointmentID());
                    db.updateAppointment(selectedAppointment);

                    BookingDialog editDialog = new BookingDialog("Edit Booking", "Update of Booking #" + selectedAppointment.getAppointmentID() + " was successful!");
                    editDialog.showAndWait();

                    btEditAppointment.setText("Edit");
                    tableView.setDisable(false);
                    btBookAppointment.setDisable(false);
                    btDeleteAppointment.setDisable(false);
                    tableView.refresh();
                    clearInput();
                }
            }
            else {
                BookingDialog selectedDialog = new BookingDialog("Selection Issue", "You do not have an appointment selected.");
                selectedDialog.showAndWait();
            }
        });

        btCancelEdit.setOnAction(e -> {
            clearInput();
            btEditAppointment.setText("Edit");
            tableView.setDisable(false);
            btBookAppointment.setDisable(false);
            btDeleteAppointment.setDisable(false);
            btCancelEdit.setVisible(false);
            cbScrub.setDisable(true);
        });

        // Searching
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(appointment -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // show full list
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return appointment.getClientName().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // List Changes (ObservableList)
        appointmentList.addListener((ListChangeListener<Appointment>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    System.out.println("Added: " + change.getAddedSubList() + " to Data Store.");
                    //tableView.getItems().addAll(change.getAddedSubList());
                }
                else if (change.wasRemoved()) {
                    System.out.println("Removed: " + change.getRemoved() + " from Data Store.");
                    //tableView.getItems().removeAll(change.getRemoved());
                }
            }
        });

        // Create a scene and place it in the stage

        // Search Area
        HBox hbox = new HBox(new Label("Search: "), tfSearch);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0, 0, 10, 10));

        // Title
        Text textHeader = new Text("Pseudo Massage");
        textHeader.setFont(Font.font("veranda", FontWeight.BOLD, FontPosture.REGULAR, 48));
        HBox hbox2 = new HBox(textHeader);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setPadding(new Insets(0, 10, 10, 0));

        VBox vbox = new VBox(hbox2, gridPane, hbox, tableView, sp);
        Scene scene = new Scene(vbox, 700, 500);
        primaryStage.setTitle("Pseudo Massage Booking App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Used by an IDE to run the application */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /** Used to create connection to MongoDB and load the application variables */
    public void load() {
        try {
            statusText.setText("\tConnecting to database...");
            db = new MongoDB();
            statusText.setText(statusText.getText() + "\n\tConnected to database successfully.");
            statusText.setText(statusText.getText() + "\n\tLoading database...");
            Store.setScrubs(db.getCollection("scrubs", Scrub.class));
            Store.setMassages(db.getCollection("massages", Massage.class));
            Store.setClients(db.getCollection("clients", Client.class));
            Store.setTherapists(db.getCollection("therapists", Therapist.class));
            Store.setAppointments(db.getCollection("appointments", Appointment.class));
            statusText.setText(statusText.getText() + "\n\tLoaded collections successfully.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            statusText.setText(statusText.getText() + "\n\tConnection to database failed.");
        }

    }

    /** Clear User Input */
    public void clearInput() {
        tfFirstName.setText(null);
        tfLastName.setText(null);
        tfPhoneNumber.setText(null);
        cbMassage.setValue(null);
        cbScrub.setValue(null);
        cbTherapist.setValue(null);
        // cbMassage.setSelectionModel(null); // Work in Progress
        // cbScrub.setSelectionModel(null); // Work in Progress
        // cbTherapist.setSelectionModel(null); // Work in Progress
        dpDate.setValue(null);
    }

    public boolean checkInput() {
        if (tfFirstName.getText().trim().equals("") || tfLastName.getText().trim().equals("") ||
                tfPhoneNumber.getText().trim().equals("") || cbMassage.getSelectionModel().getSelectedItem() == null ||
                cbTherapist.getSelectionModel().getSelectedItem() == null || dpDate.getValue() == null) {
            BookingDialog missingDialog = new BookingDialog("Missing Information!", "You have missing information.");
            missingDialog.showAndWait();
            return true;
        }
        return false;
    }
    
    /** Generate Test Data */
    public void testData() {
    /** User Creation */
    Client client1 = new Client("John", "Smith");
    client1.setPhoneNumber("123-456-7890");
    clients.add(client1);

    db.insertObject(client1);

    Client client2 = new Client("Julie", "Sans");
    client2.setPhoneNumber("123-455-6654");
    clients.add(client2);

    db.insertObject(client2);

    Therapist therapist1 = new Therapist("Jane", "Doe");
    therapists.add(therapist1);

    db.insertObject(therapist1);

    /** Service Creation */
    Massage massage1 = new Massage("Swedish Massage", 90, 80.00);
    Massage massage2 = new Massage("Deep Tissue Massage", 120, 80.00);

    massages.add(massage1);
    massages.add(massage2);

    db.insertObject(massage1);
    db.insertObject(massage2);

    Scrub scrub1 = new Scrub("Sugar Scrub", 30.00);
    Scrub scrub2 = new Scrub("Salt Scrub", 30.00);

    scrubs.add(scrub1);
    scrubs.add(scrub2);

    db.insertObject(scrub1);
    db.insertObject(scrub2);

    /** Appointment Creation */
    Appointment appointment1 = new Appointment(client1.getUserID(), therapist1.getUserID(), new ArrayList<Integer>(Arrays.asList(massage1.getServiceId(), scrub1.getServiceId())), new java.util.Date());
    Appointment appointment2 = new Appointment(client2.getUserID(), therapist1.getUserID(), new ArrayList<Integer>(Arrays.asList(massage2.getServiceId(), scrub2.getServiceId())), new java.util.Date());
    
    //appointmentList.add(appointment1);
    //appointmentList.add(appointment2);

    db.insertObject(appointment1);
    db.insertObject(appointment2);

    /** Default Test Fields */
    tfSearch.setText("Search coming soon...");
    tfSearch.setEditable(false);
    tfSearch.setDisable(true);

    cbTime.setDisable(true);

    }

    
}
