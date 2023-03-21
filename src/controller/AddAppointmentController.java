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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private DatePicker DatePicker;
    @FXML
    private DatePicker endDataPicker;
    @FXML
    private ComboBox<String> HourComboBox;
    @FXML
    private ComboBox<String> hourCombBox1;
    @FXML
    private ComboBox<String> secondComboBox;
    @FXML
    private ComboBox<String> secondComboBox1;
    @FXML
    private TextField appointmentDescription;
    @FXML
    private ComboBox<Integer> userIDText;
    @FXML
    private Button addButton;
    @FXML
    private TextField appointmentIDText;
    @FXML
    private ComboBox<Contact> contactAppointmentCombBox;
    @FXML
    private ComboBox<Integer> customerIDText;
    @FXML
    private Button exitButton;
    @FXML
    private TextField locationAppointmentText;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField typeAppointmentText;

    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AppointmentQuery.appointmentAddEmpty();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        HourComboBox.setItems(hours);
        secondComboBox.setItems(minutes);
        hourCombBox1.setItems(hours);
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

    public void setColumnUserAndCustomer(int id) throws SQLException {
        customerIDText.setValue(id);
        appointmentIDText.setText(String.valueOf(AppointmentQuery.leastappointmentID()));
    }
    @FXML
    void UserIDTextAction(ActionEvent event) { }

    @FXML
    void appointmentDescriptionTextAction(ActionEvent event) { }

    @FXML
    void appointmentButtonAction(ActionEvent event) throws SQLException {


        DateTimeFormatter appointmentTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter appointmentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String title = titleTextField.getText();
        String description = appointmentDescription.getText();
        String location = locationAppointmentText.getText();
        String type = typeAppointmentText.getText();
        LocalDate startDate = DatePicker.getValue();
        LocalDate endDate = endDataPicker.getValue();
        int user = userIDText.getSelectionModel().getSelectedItem();
        int customer = customerIDText.getSelectionModel().getSelectedItem();
        String hourStart = HourComboBox.getValue();
        String secondStart = secondComboBox.getValue();
        String timeOne = hourStart+":"+secondStart+":00";
        Time timeOneConvert = Time.valueOf(timeOne);
        String hourStart1 = hourCombBox1.getValue();
        String secondStart1 = secondComboBox1.getValue();
        String timeTwo = hourStart1+":"+secondStart1+":00";
        Time timeTwoConvert = Time.valueOf(timeTwo);
        int appointmentID = Integer.parseInt(appointmentIDText.getText());

        int contactID = contactAppointmentCombBox.getValue().getContactID();

        LocalDateTime startDateTime = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), Integer.parseInt(hourStart), Integer.parseInt(secondStart));
        LocalDateTime endDateTime = LocalDateTime.of(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), Integer.parseInt(hourStart1), Integer.parseInt(secondStart1));

        boolean test = false;
        int appointmentNum = 0;

        FilteredList<Appointment> appointmentList = AppointmentQuery.appointmentData_new().filtered(t -> { int num = t.getCustomerID();return num == customer && t.getAppointmentID() != appointmentID;  });


        if (appointmentList.size() > 0) {


            for (int i = 0; i < appointmentList.size(); i++) {

                String dateOne = appointmentDate.format(appointmentList.get(i).getStartTimeDate());
                String dateTwo = appointmentDate.format(appointmentList.get(i).getStartTimeDate());
                LocalDate testDateForStart = LocalDate.parse(dateOne);
                LocalDate testDateForEnd =LocalDate.parse(dateTwo);

                LocalDateTime testStartDate = appointmentList.get(i).getStartTimeDate();
                LocalDateTime testEndDate = appointmentList.get(i).getEndTimeDate();

                Time testTimeForStart = Time.valueOf(appointmentTime.format(testStartDate));

                Time testTimeForEnd = Time.valueOf(appointmentTime.format(testEndDate));


                if(startDate.equals(testDateForEnd) && endDate.equals(testDateForStart)) {
                    if(timeOneConvert.equals(testTimeForStart) || timeTwoConvert.equals(testTimeForEnd)){
                        test = true;
                        appointmentNum = i;
                        break;
                    }

                    else  if (timeOneConvert.equals(testTimeForStart) && timeTwoConvert.equals(testTimeForEnd)) {
                        test = true;
                        appointmentNum = i;
                        break;
                    }
                    else if(timeOneConvert.after(testTimeForStart) && timeTwoConvert.before(testTimeForEnd)){
                        test = true;
                        appointmentNum = i;
                        break;
                    }
                    else if(timeTwoConvert.before(testTimeForEnd) && timeTwoConvert.after(testTimeForStart) ){
                        test = true;
                        appointmentNum = i;
                        break;
                    }
                }
            }}

        if ( test == true ) {
            Alert alertType = new Alert(Alert.AlertType.WARNING);
            alertType.setTitle("Appointment Overlapped");
            alertType.setHeaderText("This appointment is Overlapping");
            alertType.setContentText("The appointment is Overlapped with the following appointment \n" + appointmentList.get(appointmentNum).getAppointmentID());
            alertType.show();
        }

        else if (test == false){
            String processResult = AppointmentQuery.updateAppointment(appointmentID,title, description, location, type,  startDateTime, endDateTime, user, customer, contactID);

            if(processResult.equals("Success")) {
                Alert alertType = new Alert(Alert.AlertType.CONFIRMATION);
                alertType.setTitle("Insert record states");
                alertType.setHeaderText("Insert was successful");
                alertType.setContentText("You have Inserted a new Customer appointment record");
                alertType.show();
            } else {
                Alert alertType = new Alert(Alert.AlertType.WARNING);
                alertType.setTitle("Insert record states");// line 2
                alertType.setHeaderText("Insert was not successful");// line 3
                alertType.setContentText("The new customer appointment record Insertion failed");// line 4
                alertType.show();
            }
        }

        }

    @FXML
    void appointmentexitButtonAction(ActionEvent event) throws IOException, SQLException {
        AppointmentQuery.appointmentDeleteEmpty();

        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut,800,400);
        Window.setScene(scene);
        Window.setTitle("Customer page:");
        Window.show();
    }

    @FXML
    void appointmentIDTextAction(ActionEvent event) { }
    @FXML
    void contactCombBoxAction(ActionEvent event) { }
    @FXML
    void customerIDTextAction(ActionEvent event) { }
    @FXML
    void endDateTextAction(ActionEvent event) { }
    @FXML
    void locationAppointmentTextAction(ActionEvent event) { }
    @FXML
    void titleTextAction(ActionEvent event) { }
    @FXML
    void typeAppointmentTextAction(ActionEvent event) { }
    @FXML
    void DatePickerAction(ActionEvent event) { }
    @FXML
    void endDataPicker(ActionEvent event){}
    @FXML
    void secondCombBox1(ActionEvent event) { }
    @FXML
    void secondComboBox(ActionEvent event) {}
    @FXML
    void HourComboBox(ActionEvent event){}
    @FXML
    void hourCombBox1(ActionEvent event){}

}
