package massageapp;

/*
 * Program: FinalProject - MassageApp
 * Author: Aaron Corns
 * Date: 2-22-2023
 * Course: SDEV-200
 * Last Update: 3-5-2023
 * 
 * Notes: The application is still a work in progress. There are several events that currently work; however, not everything is completely
 *        functional. For example, the Choice Boxes are not set to the proper selection when editing a selected appointment.
 * 
 *         There have also been some complications in excpecting a DatePicker to have a Time Selection. Also, the application does attempt 
 *         to connect to the MongoDB Atlas cluster; however, no data is currently exchanged. Additional methods need to be implemented to 
 *         take stored collection data from MongoDB and populate them into new objects using the POJO (Plain Old Java Object) method.
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
import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
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
    private TextField tfFirstName = new TextField();
    private TextField tfLastName = new TextField();
    private TextField tfPhoneNumber = new TextField();
    private ChoiceBox<Massage> cbMassage = new ChoiceBox<>();
    private ChoiceBox<Scrub> cbScrub = new ChoiceBox<>();
    private ChoiceBox<Therapist> cbTherapist = new ChoiceBox<>();
    private DatePicker dpDate = new DatePicker();
    private ChoiceBox<String> cbTime = new ChoiceBox<>();
    private TextField tfSearch = new TextField();

    /** Current User Selections */
    //private Massage selectedMassage;
    //private Scrub selectedScrub;
    private Therapist selectedTherapist;
    private Appointment selectedAppointment;
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


    @Override
    public void start(Stage primaryStage) throws Exception {
    
        load(); // Populate Application Data (MongoDB)
        testData();

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
        tableView.getItems().addAll(appointmentList);


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
        cbMassage.getItems().addAll(massages);
        cbScrub.getItems().addAll(scrubs);
        cbTherapist.getItems().addAll(therapists);

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

        // UI Settings
        cbScrub.setDisable(true);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        btBookAppointment.setPrefWidth(100);
        btEditAppointment.setPrefWidth(100);
        btDeleteAppointment.setPrefWidth(100);

        /** Process Events */

        // Book Button
        btBookAppointment.setOnAction( e -> {

            if (tfFirstName.getText().trim().equals("") || tfLastName.getText().trim().equals("") ||
                    tfPhoneNumber.getText().trim().equals("") || cbMassage.getSelectionModel().getSelectedItem() == null ||
                    cbTherapist.getSelectionModel().getSelectedItem() == null || dpDate.getValue() == null) {
                BookingDialog missingDialog = new BookingDialog("Missing Information!", "You have missing information.");
                missingDialog.showAndWait();
            }
            else {
                Date date = Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                // FUTURE - Add Hours to Date using: date = DateUtils.addHours(date, time) or similar
    
                // Create Appointment
                Client newClient = new Client(tfFirstName.getText(), tfLastName.getText());
                newClient.setPhoneNumber(tfPhoneNumber.getText());

                ArrayList<Service> tempServices = new ArrayList<Service>();

                tempServices.add(cbMassage.getSelectionModel().getSelectedItem());
                if (cbScrub.getSelectionModel().getSelectedItem() != null) {
                    tempServices.add(cbScrub.getSelectionModel().getSelectedItem());
                }
                
                Appointment newAppointment = new Appointment(newClient, selectedTherapist, tempServices, date);
                
                // Add Appointment to Table
                tableView.getItems().add(newAppointment);


                clearInput();
            }     
        });

        // Therapist Select
        cbTherapist.setOnAction(e -> {
            selectedTherapist = therapists.get(cbTherapist.getSelectionModel().getSelectedIndex());
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
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
        });

        // Edit Button
        btEditAppointment.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {

                if (btEditAppointment.getText().equals("Edit")) 
                { // Get the Selected Appointment
                    selectedAppointment = tableView.getSelectionModel().getSelectedItem();
                    // Set the editable fields
                    tfFirstName.setText(selectedAppointment.getClient().getFirstName());
                    tfLastName.setText(selectedAppointment.getClient().getLastName());
                    tfPhoneNumber.setText(selectedAppointment.getClient().getPhoneNumber());
                    selectedTherapist = selectedAppointment.getTherapist();
                    //System.out.println(selectedTherapist.getName());
                    //cbTherapist.setValue(therapists.get(0));
                    cbTherapist.getSelectionModel().select(selectedTherapist);
                    cbMassage.getSelectionModel().select((Massage)(selectedAppointment.getServices().get(0)));
                    try {
                    cbScrub.getSelectionModel().select((Scrub)(selectedAppointment.getServices()).get(1));
                    } catch (Exception ex) {
                    // No Scrub
                    }
                    //cbScrub.setSelectionModel(null); // Work in Progress
                    dpDate.setValue(
                            selectedAppointment.getDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

                    btEditAppointment.setText("Save");
                    tableView.setDisable(true);
                    btBookAppointment.setDisable(true);
                    btDeleteAppointment.setDisable(true);
                }
                else {
                    selectedAppointment.getClient().setFirstName(tfFirstName.getText());
                    selectedAppointment.getClient().setLastName(tfLastName.getText());
                    selectedAppointment.getClient().setPhoneNumber(tfPhoneNumber.getText());

                    ArrayList<Service> tempServices = new ArrayList<Service>();

                    tempServices.add(cbMassage.getSelectionModel().getSelectedItem());
                    if (cbScrub.getSelectionModel().getSelectedItem() != null) {
                        tempServices.add(cbScrub.getSelectionModel().getSelectedItem());
                    }
                       
                    selectedAppointment.setServices(tempServices);
                    selectedAppointment.setTherapist(selectedTherapist);

                    Date date = Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    // FUTURE - Add Hours to Date using: date = DateUtils.addHours(date, time) or similar
                    selectedAppointment.setDateTime(date);
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

        VBox vbox = new VBox(hbox2, gridPane, hbox, tableView);
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
            db = new MongoDB();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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
    
    /** Generate Test Data */
    public void testData() {
    /** User Creation */
    Client client1 = new Client("John", "Smith");
    client1.setPhoneNumber("123-456-7890");
    clients.add(client1);

    Client client2 = new Client("Julie", "Sans");
    client2.setPhoneNumber("123-455-6654");
    clients.add(client2);

    Therapist therapist1 = new Therapist("Jane", "Doe");
    therapists.add(therapist1);

    /** Service Creation */
    Massage massage1 = new Massage("Swedish Massage", 90, 80.00);
    Massage massage2 = new Massage("Deep Tissue Massage", 120, 80.00);

    massages.add(massage1);
    massages.add(massage2);

    Scrub scrub1 = new Scrub("Sugar Scrub", 30.00);
    Scrub scrub2 = new Scrub("Salt Scrub", 30.00);

    scrubs.add(scrub1);
    scrubs.add(scrub2);

    /** Appointment Creation */
    Appointment appointment1 = new Appointment(client1, therapist1, new ArrayList<Service>(Arrays.asList(massage1, scrub1)), new java.util.Date());
    Appointment appointment2 = new Appointment(client2, therapist1, new ArrayList<Service>(Arrays.asList(massage2, scrub2)), new java.util.Date());
    
    appointmentList.add(appointment1);
    appointmentList.add(appointment2);

    /** Default Test Fields */
    tfSearch.setText("Search coming soon...");
    tfSearch.setEditable(false);
    tfSearch.setDisable(true);

    cbTime.setDisable(true);

    }

    
}
