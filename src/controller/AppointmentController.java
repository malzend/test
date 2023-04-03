package controller;

import helper.AppointmentQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * AppointmentController will do the following:<br>
 * Data will be loaded to the appointments table for the database.<br>
 * A user can add an appointment for a new or existing customer to the database .<br>
 * Appointment can be modified.<br>
 * A user can delete an appointment for a customer,<br>
 * Appointments will be checked for overlaps.<br>
 */
public class AppointmentController implements Initializable {
    /**
     * <br>
     * FXML id selectors <br>
     * <br>
     * selectors used to read or set form fields <br>
     */
    @FXML
    private TableColumn<Appointment, Integer> appointmentIDColumn;
    @FXML
    private TableColumn<Appointment, String> contactColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIDColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> endTimeColumn;
    @FXML
    private TableColumn<Appointment, String> startTimeColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, Integer> userIDColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;
    @FXML
    private TableView<Appointment> modifyAppointmentTable;
    @FXML
    private RadioButton radioByWeek ;
    @FXML
    private RadioButton radioByMonthAction ;

    /**
     * initialize will be used to load date to the appointments for the database.<br>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known. <br>
     * @param resource The resources used to localize the root object, or null if the root object was not localized.<br>
     */
    @FXML
    public void initialize(URL location, ResourceBundle resource) {
        try {
            modifyAppointmentTable.setItems(AppointmentQuery.appointmentData_new());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Type"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTimeDate"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndTimeDate"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserID"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactID"));
    }

    /**
     * addAppointmentAction will load the Customer.fxml page.<br>
     * @param event will add when the button is clicked.<br>
     * @throws IOException will throw an exception when the load meathead null.<br>
     * */
    @FXML
    void addAppointmentAction(ActionEvent event) throws IOException {
        Parent customerPage = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
        Scene scene = new Scene(customerPage);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("LogIn page:");
        window.show();
    }

    /**
     * exitButtonAction will load the User.fxml page.<br>
     * @param event on button will exit the program <br>
     * @throws IOException will throw an exception when the load meathead null.<br>
     */
    @FXML
    void exitButtonAction(ActionEvent event) throws IOException {
        Parent customerPage = FXMLLoader.load(getClass().getResource("/View/User.fxml"));
        Scene scene = new Scene(customerPage);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("LogIn page:");
        window.show();
    }

    /**
     * modifyAppointmentAction will load the ModifyAppointment.fxml page and send the appointment data form the appointment table<br>
     * to modify page.<br>
     * @param event modify information to database when clicked.<br>
     * @throws IOException will throw an exception when the load meathead null.<br>
     */
    @FXML
    void modifyAppointmentAction(ActionEvent event) throws IOException {
        try {
            Stage stage;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();
            ModifyAppointment sendAppointment = loader.getController();
            sendAppointment.setAppointment(modifyAppointmentTable.getSelectionModel().getSelectedItem())
            ;

            stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException | SQLException var7) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Appointment selected:");
            alert.setContentText("Please select an appointment to modify.");
            alert.show();
        }
    }

    /**
     * modifyDeleteAction will get the selected row appointment id and delete it via sql query that is stored in the appointment query.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void modifyDeleteAction() throws SQLException {
        try {
            int selectedID = modifyAppointmentTable.getSelectionModel().getSelectedItem().getAppointmentID();
            String type = modifyAppointmentTable.getSelectionModel().getSelectedItem().getType();

            Appointment selectedItem = modifyAppointmentTable.getSelectionModel().getSelectedItem();
            System.out.print(selectedItem);
            String processResult = AppointmentQuery.appointmentDelete(selectedID);
            Alert alertType;
            /**
             * If processResult is true the the alert will display successful delete if not then the alert will display not successful.<br>
             */
            if (processResult == "Success") {
                alertType = new Alert(Alert.AlertType.CONFIRMATION);
                alertType.setTitle("Deleting record states");
                alertType.setHeaderText("Deleting was successful");
                alertType.setContentText("You have Delete the selected customer record with the following: \n" + selectedID + " " + type);
            } else {
                alertType = new Alert(Alert.AlertType.WARNING);
                alertType.setTitle("Deleting record states");
                alertType.setHeaderText(" Delete was not successful");
                alertType.setContentText("You have not Delete the selected customer record");
            }
            alertType.show();
        } catch (NullPointerException var7){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Appointment selected:");
            alert.setContentText("Please select an appointment to delete.");
            alert.show();

        }
        /**
         * The table will be refreshed with the new data set for the database.<br>
         */
        modifyAppointmentTable.setItems(AppointmentQuery.appointmentData_new());
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Type"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Contact"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTimeDate"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndTimeDate"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserID"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactID"));

    }


//    @FXML
//    void modifyAppointmentAction() {
//    }

    /**
     * radioByWeekAction will filter the appointment by current week using AppointmentQuery.appointmentsByWeek().<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void radioByWeekAction()throws SQLException {
        if(radioByWeek.isSelected()){
            modifyAppointmentTable.setItems(AppointmentQuery.appointmentsByWeek());
            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Type"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Contact"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTimeDate"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndTimeDate"));
            userIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserID"));
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactID"));

        }else
        {
            radioByWeek.setSelected(false);
            modifyAppointmentTable.setItems(AppointmentQuery.appointmentData_new());
            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Type"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Contact"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTimeDate"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndTimeDate"));
            userIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserID"));
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactID"));
        }
    }

    /**
     * RadioByMonthAction will filter the table by current month via AppointmentQuery.appointmentsByMonth().<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void RadioByMonthAction() throws SQLException {
        if(radioByMonthAction.isSelected()){
            modifyAppointmentTable.setItems(AppointmentQuery.appointmentsByMonth() );
            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Type"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Contact"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTimeDate"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndTimeDate"));
            userIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserID"));
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactID"));

        }else
        {
            radioByMonthAction.setSelected(false);
            modifyAppointmentTable.setItems(AppointmentQuery.appointmentData_new());
            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Type"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Contact"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTimeDate"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndTimeDate"));
            userIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserID"));
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactID"));
        }
    }

    /**
     * allRadioAction will load all data back.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void allRadioAction() throws SQLException {

        if(radioByMonthAction.isSelected()){
            modifyAppointmentTable.setItems(AppointmentQuery.appointmentData_new() );
            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Type"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Contact"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTimeDate"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndTimeDate"));
            userIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserID"));
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactID"));

        }else
        {
            radioByMonthAction.setSelected(false);
            modifyAppointmentTable.setItems(AppointmentQuery.appointmentData_new());
            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Location"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String >("Type"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Contact"));
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTimeDate"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndTimeDate"));
            userIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("UserID"));
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactID"));
        }

    }
}
