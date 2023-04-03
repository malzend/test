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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Appointment;
import model.Model;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * modify will allow the user to do the following:<br>
 * Get the user data for the selected row and load it to the page.<br>
 * Allow the user to change any data of the appointment.<br>
 * Check for any overlap in appointment except the same appointment ID.
 * Save the modify appointment in the database and display an alert if process was<br>
 * successful or not.<br>
 */
public class ModifyAppointment implements Initializable {
    /**
     * <br>
     * FXML id selectors. <br>
     * <br>
     * selectors used to read or set form fields. <br>
     */

    public TextField appointmentIDText;
    @FXML
    private ComboBox<Integer> userComboBox;
    @FXML
    private TextField appointmentDescription;
    @FXML
    private ComboBox<String> contactAppointmentCombBox;
    @FXML
    private ComboBox<Integer> customerComboBox;
    @FXML
    private TextField locationAppointmentText;
    @FXML
    private ComboBox<Integer> hourComboBox;
    @FXML
    private ComboBox<Integer> hourComboBox1;
    @FXML
    private ComboBox<Integer> minutesStart;
    @FXML
    private ComboBox<Integer> minutesEnd;
    @FXML
    private DatePicker endPickerDate;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField typeAppointmentText;
    /**
     * selectedAppointment define a new instance of appointment for the purpose fo comparing if appointment info changed or not.<br>
     */
    public Appointment selectedAppointment;
    /**
     * Create a ObservableList minutes of Integer Type.<br>
     */
    ObservableList<Integer> minutes = FXCollections.observableArrayList();
    /**
     * initialize will populate the time, date and contact names for each combo, also  user ID and Customer id will<br>
     * populated for the Database.<br>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known. <br>
     * @param resources The resources used to localize the root object, or null if the root object was not localized.<br>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            CustomerQuery.firstLevelDivisionData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        minutes.addAll(0, 15, 30, 45, 59);
        hourComboBox.setItems(Model.businessTimeStart());
        minutesStart.setItems(minutes);
        hourComboBox1.setItems(Model.businessTimeStart());
        minutesEnd.setItems(minutes);
        try {
            for(int i = 0; i <AppointmentQuery.loadContact().size();i++){
                contactAppointmentCombBox.getItems().add(AppointmentQuery.loadContact().get(i).getContactName()); }}
        catch (SQLException throwables) { throwables.printStackTrace(); }


        try{ for (int i = 0; i < UserQuery.userData().size();i++) { userComboBox.getItems().add(UserQuery.userData().get(i).getUserID()); } } catch (SQLException throwables) { throwables.printStackTrace(); }
        try{ for (int i = 0; i < CustomerQuery.customerDataNew().size();i++){ customerComboBox.getItems().add(CustomerQuery.customerDataNew().get(i).getCustomerID()); }  } catch (SQLException throwables) { throwables.printStackTrace(); }
    }

    /**
     * setAppointment will get the selected appointment row information and load it to modify page.<br>
     * @param appointment will pass a instance of data that is passed form appointment table.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    public void setAppointment(Appointment appointment) throws SQLException {

        selectedAppointment = appointment;
        LocalDate start = appointment.getStartTimeDate().toLocalDate();
        LocalDate end = appointment.getEndTimeDate().toLocalDate();
        endPickerDate.setValue(end);
        DatePicker.setValue(start);
        appointmentDescription.setText(appointment.getDescription());
        titleTextField.setText(appointment.getTitle());
        locationAppointmentText.setText(appointment.getLocation());
        typeAppointmentText.setText(appointment.getType());
        contactAppointmentCombBox.setValue(Model.getContact_Name(appointment.getContactID()));
        typeAppointmentText.setText(String.valueOf(appointment.getType()));
        int hour  = appointment.getStartTimeDate().getHour();
        int minuteStart = appointment.getStartTimeDate().getMinute();
        int hour1  = appointment.getEndTimeDate().getHour();
        int minutesEnd = appointment.getEndTimeDate().getMinute();
        hourComboBox.setValue(hour);
        minutesStart.setValue(minuteStart);
        this.minutesEnd.setValue(minutesEnd);
        hourComboBox1.setValue(hour1);
        appointmentIDText.setText(String.valueOf(appointment.getAppointmentID()));
        customerComboBox.setValue(appointment.getCustomerID());
        userComboBox.setValue(appointment.getUserID());

    }
    /**
     * Description of the method.<br>
     * Lambda 2 helps in filtering the appointment based on the Customer id and date. <br>
     * This simplifies the process of obtaining the data of the appointment with minimizing memory allocation and code execution. <br>
     *
     * appointmentButtonAction will do the following:<br>
     * Get the user input and convert time and date to LocalDateTime type.<br>
     * Check if their is an overlap with another appointment if the Customer already has other appointment listed in the day.<br>
     * Insert the date if their is no overlap with other appointment, if their is an overlap display an alert with the a overlap<br>
     * appointment.<br>
     * The for loop will loop the contactless to get it's id.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void appointmentButtonAction( )throws SQLException {
        int contactNum = -1;
        int appointmentID = Integer.parseInt(appointmentIDText.getText());
        String title = titleTextField.getText();
        String description = appointmentDescription.getText();
        String location = locationAppointmentText.getText();
        String type = typeAppointmentText.getText();
        LocalDate startDate = DatePicker.getValue();
        LocalDate endDate = endPickerDate.getValue();
        int hourStart = hourComboBox.getValue();
        int minutesStart = this.minutesStart.getValue();
        LocalTime startTime = LocalTime.of(hourStart,minutesStart);
        int hourEnd = hourComboBox1.getValue();
        int minutesEnd = this.minutesEnd.getValue();
        LocalTime endTime = LocalTime.of(hourEnd,minutesEnd);
        int customer = customerComboBox.getValue();
        int user = userComboBox.getValue();


        for(int i = 0;i<  AppointmentQuery.loadContact().size();i++){
            if (AppointmentQuery.loadContact().get(i).getContactName().equals(contactAppointmentCombBox.getSelectionModel().getSelectedItem())){
                contactNum =  AppointmentQuery.loadContact().get(i).getContactID();
            }
        }


        int contactID = contactNum;

        LocalDateTime startDateTime = LocalDateTime.of(startDate,startTime );
        LocalDateTime endDateTime = LocalDateTime.of(endDate,endTime );

        /**
         * check to see if the appointment filed change or not.<br>
         */
        Appointment noChange = (new Appointment(appointmentID,title,description,location,type,startDateTime,endDateTime,user,customer,contactID));
        if(selectedAppointment.getAppointmentID() == noChange.getAppointmentID() && selectedAppointment.getTitle().equals(noChange.getTitle()) && selectedAppointment.getDescription().equals(noChange.getDescription()) &&
           selectedAppointment.getLocation().equals(noChange.getLocation()) &&selectedAppointment.getType().equals(noChange.getType()) && selectedAppointment.getEndTimeDate().isEqual(noChange.getEndTimeDate()) && selectedAppointment.getStartTimeDate().isEqual(noChange.getStartTimeDate()) &&
           selectedAppointment.getUserID() == noChange.getUserID() && selectedAppointment.getCustomerID() == noChange.getCustomerID() && selectedAppointment.getContactID() == noChange.getContactID()){

            Alert alertType;
            alertType = new Alert(Alert.AlertType.WARNING);
            alertType.setTitle("No changes");
            alertType.setHeaderText("No change to appointment");
            alertType.setContentText("No changes have been made to the appointment.");
            alertType.show();

        }
        else{

            boolean test = false;

            int appointmentNum = 0;

            // Lambda 2
            FilteredList<Appointment> customerInList = AppointmentQuery.appointmentData_new().filtered(t ->
            { int num = t.getCustomerID();return num == customer && t.getAppointmentID() != appointmentID; });
            /**
             * The if condition checks if the appointmentLisT is empty or not.<br>
             * If not empty then it will processed to the check if there is an overlap between the customer existing<br>
             * appointment or not.
             */
            if (customerInList.size() > 0) {
                for (int i = 0; i <  customerInList.size(); i++) {

                    LocalDateTime testStartDate = customerInList.get(i).getStartTimeDate();
                    LocalDateTime testEndDate = customerInList.get(i).getEndTimeDate();

                    if(!(startDateTime.isAfter(testEndDate) || startDateTime.equals(testEndDate)
                            || endDateTime.isBefore(testStartDate) || endDateTime.equals(testStartDate))){
                        test = true;
                        appointmentNum = i;
                        break;
                    }

                }

                /**
                 * if their is an overlap then display an alert.<br.
                 * if not processed to add the new appointment to the database.<br>
                 */
                if (test == true) {
                    Alert alertType = new Alert(Alert.AlertType.WARNING);
                    alertType.setTitle("Appointment Overlapped");
                    alertType.setHeaderText("This appointment is Overlapping");
                    alertType.setContentText("The appointment is Overlapped with the following appointment \n" + customerInList.get(appointmentNum).getAppointmentID());
                    alertType.show();
                }

                else {

                    String processResult = AppointmentQuery.updateAppointment(appointmentID, title, description, location, type, startDateTime, endDateTime, user, customer, contactID);

                    if(processResult.equals("Success")) {
                        Alert alertType;
                        alertType = new Alert(Alert.AlertType.CONFIRMATION);
                        alertType.setTitle("Insert record states");
                        alertType.setHeaderText("Modify appointment was successful");
                        alertType.setContentText("You have modified the appointment successful and updates have been applied ");
                        alertType.show();
                    } else {
                        Alert alertType;
                        alertType = new Alert(Alert.AlertType.WARNING);
                        alertType.setTitle("Insert record states");
                        alertType.setHeaderText("Insert was not successful");
                        alertType.setContentText("The appointment failed to be modified");
                        alertType.show();
                    }
                }
            }else{
                String processResult = AppointmentQuery.updateAppointment(appointmentID, title, description, location, type, startDateTime, endDateTime, user, customer, contactID);

                if(processResult.equals("Success")) {
                    Alert alertType;
                    alertType = new Alert(Alert.AlertType.CONFIRMATION);
                    alertType.setTitle("Insert record states");
                    alertType.setHeaderText("Modify appointment was successful");
                    alertType.setContentText("You have modified the appointment successful and updates have been applied ");
                    alertType.show();
                } else {
                    Alert alertType;
                    alertType = new Alert(Alert.AlertType.WARNING);
                    alertType.setTitle("Insert record states");
                    alertType.setHeaderText("Insert was not successful");
                    alertType.setContentText("The appointment failed to be modified");
                    alertType.show();
                }
            }
        }

    }
    /**
     * appointmentExitButtonAction allow the user to exit form the page to the appointment page.<br>
     * @param event on button will exit the program <br>
     * @throws IOException will throw an exception when the load meathead is not
     */
    @FXML
    void appointmentExitButtonAction(ActionEvent event) throws IOException {
        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut);
        Window.setScene(scene);
        Window.setTitle("Customer page:");
        Window.show();
    }


}
