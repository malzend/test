
package controller;

import helper.AppointmentQuery;
import helper.CustomerQuery;
import helper.UserQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Model;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * addAppointmentController will do the following:<br>
 * Allow the user to add a new appointment for a customer.<br>
 * Display alert if the insertion of the date was successful or not to the database.<br>
 * Allow the user to exit the page.<br>
 */
public class addAppointmentController implements Initializable {
    /**
     * <br>
     * FXML id selectors. <br>
     * <br>
     * selectors used to read or set form fields. <br>
     */
    @FXML
    private DatePicker DatePicker;
    @FXML
    private DatePicker endDataPicker;
    @FXML
    private ComboBox<Integer> HourComboBox;
    @FXML
    private ComboBox<Integer> hourCombBox1;
    @FXML
    private ComboBox<Integer> secondComboBox;
    @FXML
    private ComboBox<Integer> secondComboBox1;
    @FXML
    private TextField appointmentDescription;
    @FXML
    private ComboBox<Integer> userIDText;
    @FXML
    private ComboBox<Contact> contactAppointmentCombBox;
    @FXML
    private ComboBox<Integer> customerIDText;
    @FXML
    private TextField locationAppointmentText;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField typeAppointmentText;

    ObservableList<Integer> minutes = FXCollections.observableArrayList();
    /**
     * initialize will populate the time , date and contact names for each combo, also  user ID and Customer id will<br>
     * populated for the Database.<br>
     *  @param url
     * @param resourceBundle
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        minutes.addAll(0, 15, 30, 45,59);
        HourComboBox.setItems(Model.businessTimeStart());
        secondComboBox.setItems(minutes);
        hourCombBox1.setItems(Model.businessTimeStart());
        secondComboBox1.setItems(minutes);
        try {
            contactAppointmentCombBox.setItems(AppointmentQuery.loadContact());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for(int i = 0; i < UserQuery.userData().size(); i++)

                userIDText.getItems().add(UserQuery.userData().get(i).getUserID());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {for(int i = 0; i < CustomerQuery.customerDataNew().size(); i++){
            customerIDText.getItems().add(CustomerQuery.customerDataNew().get(i).getCustomerID());}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * setColumnUserAndCustomer will get the customerIDText and appointmentIDText form the appointment page when<br>
     * a row is selected.<br>
     * @param id
     */
    public void setColumnUserAndCustomer(int id)  {
        customerIDText.setValue(id);
    }
    @FXML
    void UserIDTextAction() { }

    @FXML
    void appointmentDescriptionTextAction() { }

    /**
     * appointmentButtonAction will do the following:<br>
     * Get the user input and convert time and date to LocalDateTime type.<br>
     * Check if their is an overlap with another appointment if the Customer already has other appointment listed in the day.<br>
     * Insert the date if their is no overlap with other appointment, if their is an overlap display an alert with the a overlap<br>
     * appointment.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void appointmentButtonAction() throws SQLException {

        String title = titleTextField.getText();
        String description = appointmentDescription.getText();
        String location = locationAppointmentText.getText();
        String type = typeAppointmentText.getText();
        LocalDate startDate = DatePicker.getValue();
        LocalDate endDate = endDataPicker.getValue();
        int user = userIDText.getSelectionModel().getSelectedItem();
        int customer = customerIDText.getSelectionModel().getSelectedItem();
        int hourStart = HourComboBox.getValue();
        int minutesStart = secondComboBox.getValue();
        LocalTime timeOne = LocalTime.of(hourStart, minutesStart);
        int hoursEnd = hourCombBox1.getValue();
        int minutesEnd = secondComboBox1.getValue();
        LocalTime timeTwo = LocalTime.of(hoursEnd, minutesEnd);
        int contactID = contactAppointmentCombBox.getValue().getContactID();

        LocalDateTime startDateTime = LocalDateTime.of(startDate,timeOne);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, timeTwo);


        boolean test = false;
        int appointmentNum = 0;

        FilteredList<Appointment> appointmentList = AppointmentQuery.appointmentData_new().filtered(t -> {
            int num = t.getCustomerID();return num == customer; });

        /**
         * The if condition checks if the appointmentLisT is empty or not.<br>
         * If not empty then it will processed to the check if there is an overlap between the customer existing<br>
         * appointment or not.
         */
        if (appointmentList.size() > 0) {

            for (int i = 0; i < appointmentList.size(); i++) {

                LocalDateTime testStartDate = appointmentList.get(i).getStartTimeDate();
                LocalDateTime testEndDate = appointmentList.get(i).getEndTimeDate();

                if(!(startDateTime.isAfter(testEndDate) || startDateTime.equals(testEndDate)
                        || endDateTime.isBefore(testStartDate) || endDateTime.equals(testStartDate))){
                    test = true;
                    appointmentNum = i;
                    break;
                }

            }
        }

        /**
         * if their is an overlap then display an alert.<br.
         * if not processed to add the new appointment to the database.<br>
         */
        if ( test == true ) {
            Alert alertType = new Alert(Alert.AlertType.WARNING);
            alertType.setTitle("Appointment Overlapped");
            alertType.setHeaderText("This appointment is Overlapping");
            alertType.setContentText("The appointment is Overlapped with the following appointment \n" + appointmentList.get(appointmentNum).getAppointmentID());
            alertType.show();
        }

        else if (test == false){
            String processResult = AppointmentQuery.appointmentAdd(title, description, location, type,  startDateTime, endDateTime, user, customer, contactID);

            if(processResult.equals("Success")) {
                Alert alertType = new Alert(Alert.AlertType.CONFIRMATION);
                alertType.setTitle("Insert record states");
                alertType.setHeaderText("Insert was successful");
                alertType.setContentText("You have Inserted a new Customer appointment record");
                alertType.show();
            } else {
                Alert alertType = new Alert(Alert.AlertType.WARNING);
                alertType.setTitle("Insert record states");
                alertType.setHeaderText("Insert was not successful");
                alertType.setContentText("The new customer appointment record Insertion failed");
                alertType.show();
            }
        }

    }

    /**
     * appointmentTextButtonAction allow the user to exit form the page to the appointment page.
     * @param event on button will exit the program <br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void appointmentTextButtonAction(ActionEvent event) throws IOException {

        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut);
        Window.setScene(scene);
        Window.setTitle("Customer page:");
        Window.show();
    }



}