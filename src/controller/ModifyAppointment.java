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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Appointment;
import model.Model;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ModifyAppointment implements Initializable {

    public TextField appointmentIDText;
    @FXML
    private ComboBox<Integer> userComboBox;

    @FXML
    private Button addButton;

    @FXML
    private TextField appointmentDescription;


    @FXML
    private ComboBox<String> contactAppointmentCombBox;

    @FXML
    private ComboBox<Integer> customerComboBox;

    @FXML
    private VBox endTimeText;

    @FXML
    private TextField endtTimeText;

    @FXML
    private Button exitButton;

    @FXML
    private TextField locationAppointmentText;

    @FXML
    private ComboBox<String> hourComboBox;

    @FXML
    private ComboBox<String> hourComboBox1;
    @FXML
    private ComboBox<String> secondComboBox;

    @FXML
    private ComboBox<String> secondComboBox1;
    @FXML
    private DatePicker endDate;
    @FXML
    private DatePicker startDate;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField typeAppointmentText;

    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            CustomerQuery.firstLevelDivisionData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "15", "30", "45");
        hourComboBox.setItems(hours);
        secondComboBox.setItems(minutes);
        hourComboBox1.setItems(hours);
        secondComboBox1.setItems(minutes);
        try {
        for(int i = 0; i <AppointmentQuery.loadContact().size();i++){
            contactAppointmentCombBox.getItems().add(AppointmentQuery.loadContact().get(i).getContactName()); }}
        catch (SQLException throwables) { throwables.printStackTrace(); }


        try{ for (int i = 0; i < UserQuery.userData().size();i++) { userComboBox.getItems().add(UserQuery.userData().get(i).getUserID()); } } catch (SQLException throwables) { throwables.printStackTrace(); }
        try{ for (int i = 0; i < CustomerQuery.customerDataNew().size();i++){ customerComboBox.getItems().add(CustomerQuery.customerDataNew().get(i).getCustomerID()); }  } catch (SQLException throwables) { throwables.printStackTrace(); }
    }
    @FXML
    public void setAppointment(Appointment appointment) throws SQLException {

        LocalDate start = appointment.getStartTimeDate().toLocalDate();
        LocalDate end = appointment.getEndTimeDate().toLocalDate();
        endDate.setValue(end);
        startDate.setValue(start);
        appointmentDescription.setText(appointment.getDescription());
        titleTextField.setText(appointment.getTitle());
        locationAppointmentText.setText(appointment.getLocation());
        typeAppointmentText.setText(appointment.getType());
        contactAppointmentCombBox.setValue(Model.getContact_Name(appointment.getContactID()));
        typeAppointmentText.setText(String.valueOf(appointment.getType()));
        int hour  = appointment.getStartTimeDate().getHour();
        int second = appointment.getStartTimeDate().getMinute();
        int hour1  = appointment.getEndTimeDate().getHour();
        int second1 = appointment.getEndTimeDate().getMinute();
        hourComboBox.setValue(String.valueOf(hour));
        secondComboBox.setValue(String.valueOf(second));
        secondComboBox1.setValue(String.valueOf(second1));
        hourComboBox1.setValue(String.valueOf(hour1));
        appointmentIDText.setText(String.valueOf(appointment.getAppointmentID()));
        customerComboBox.setValue(appointment.getCustomerID());
        userComboBox.setValue(appointment.getUserID());


    }

    @FXML
    void UserIDTextAction(ActionEvent event) {

    }

    @FXML
    void appoiitmentDescriptionTextAction(ActionEvent event) {

    }

    @FXML
    void appointmentButtonAction(ActionEvent event) throws SQLException {
        DateTimeFormatter appointmentTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter appointmentDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        int contactNum = -1;
        int appointmentID = Integer.parseInt(appointmentIDText.getText());
        String title = titleTextField.getText();
        String description = appointmentDescription.getText();
        String location = locationAppointmentText.getText();
        String type = typeAppointmentText.getText();
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        String hourStart = hourComboBox.getValue();
        String secondStart = secondComboBox.getValue();
        String timeOne = hourStart + ":" + secondStart + ":00";
        Time timeOneConvert = Time.valueOf(timeOne);
        String hourStart1 = hourComboBox1.getValue();
        String secondStart1 = secondComboBox1.getValue();
        String timeTwo = hourStart1 + ":" + secondStart1 + ":00";
        Time timeTwoConvert = Time.valueOf(timeTwo);
        int customer = customerComboBox.getValue();
        int user = userComboBox.getValue();

        for(int i = 0;i<  AppointmentQuery.loadContact().size();i++){
            if (AppointmentQuery.loadContact().get(i).getContactName().equals(contactAppointmentCombBox.getSelectionModel().getSelectedItem())){
                contactNum =  AppointmentQuery.loadContact().get(i).getContactID();
            }
        }

        int contactID = contactNum;

        LocalDateTime startDateTime = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), Integer.parseInt(hourStart), Integer.parseInt(secondStart));
        LocalDateTime endDateTime = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth(), Integer.parseInt(hourStart1), Integer.parseInt(secondStart1));
        boolean test = false;

        int appointmentNum = 0;
        int customerIdAlert = 0;
        FilteredList<Appointment> customerInList = AppointmentQuery.appointmentData_new().filtered(t ->
        { int num = t.getCustomerID();return num == customer && t.getAppointmentID() != appointmentID; });

        if (customerInList.size() > 0) {

            for (int i = 0; i <  customerInList.size(); i++) {
                customerIdAlert = customerInList.get(i).getAppointmentID();
                String dateOne = appointmentDate.format(customerInList.get(i).getStartTimeDate());
                String dateTwo = appointmentDate.format(customerInList.get(i).getEndTimeDate());
                LocalDate testDateForStart = LocalDate.parse(dateOne);
                LocalDate testDateForEnd = LocalDate.parse(dateTwo);

                LocalDateTime testStartDate = customerInList.get(i).getStartTimeDate();
                LocalDateTime testEndDate = customerInList.get(i).getEndTimeDate();

                Time testTimeForStart = Time.valueOf(appointmentTime.format(testStartDate));
                Time testTimeForEnd = Time.valueOf(appointmentTime.format(testEndDate));

                if (start.equals(testDateForEnd) && end.equals(testDateForStart)) {
                 if(appointmentID != customerInList.get(i).getAppointmentID() && timeOneConvert != testTimeForStart && timeTwoConvert != testTimeForEnd ){

                       if (timeOneConvert.equals(testTimeForStart) || timeTwoConvert.equals(testTimeForEnd)) {
                            test = true;
                            appointmentNum = i;
                            break;
                        } else if (timeOneConvert.equals(testTimeForStart) && timeTwoConvert.equals(testTimeForEnd)) {
                            test = true;
                            appointmentNum = i;
                            break;
                        } else if (timeOneConvert.after(testTimeForStart) && timeTwoConvert.before(testTimeForEnd)) {
                            test = true;
                            appointmentNum = i;
                            break;
                        } else if (timeTwoConvert.before(testTimeForEnd) && timeTwoConvert.after(testTimeForStart)) {
                            test = true;
                            appointmentNum = i;
                            break;
                        }
                    }
                }


                else if (appointmentID == customerInList.get(i).getAppointmentID() && timeOneConvert == testTimeForStart && timeTwoConvert == testTimeForEnd){
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



        if (test == true) {
            Alert alertType = new Alert(Alert.AlertType.WARNING);
            alertType.setTitle("Appointment Overlapped");
            alertType.setHeaderText("This appointment is Overlapping");
            alertType.setContentText("The appointment is Overlapped with the following appointment \n" + customerIdAlert);
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
                alertType.setTitle("Insert record states");// line 2
                alertType.setHeaderText("Insert was not successful");// line 3
                alertType.setContentText("The appointment failed to be modified");// line 4
                alertType.show();
            }
        }
        }












    @FXML
    void appointmentIDTextAction(ActionEvent event) {

    }

    @FXML
    void appointmentExitButtonAction(ActionEvent event) throws IOException {
        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut,800,400);
        Window.setScene(scene);
        Window.setTitle("Customer page:");
        Window.show();
    }

    @FXML
    void contactCombBoxAction(ActionEvent event) {

    }

    @FXML
    void customerIDTextAction(ActionEvent event) {

    }

    @FXML
    void endDateTextAction(ActionEvent event) {

    }

    @FXML
    void endTimeTextAction(MouseEvent event) {

    }

    @FXML
    void endTimetText(ActionEvent event) {

    }

    @FXML
    void locationAppointmentTextAction(ActionEvent event) {

    }

    @FXML
    void startDateTextActiom(ActionEvent event) {

    }

    @FXML
    void startTimeTextAction(ActionEvent event) {

    }

    @FXML
    void titleTextAction(ActionEvent event) {

    }

    @FXML
    void typeAppointmentTextAction(ActionEvent event) {

    }

    @FXML
    void endDateAction(ActionEvent event) {
    }
    @FXML
    void startDateAction(ActionEvent event) {
    }
    @FXML
    void hourComboBoxAction1(ActionEvent event) {
    }
    @FXML
    void secondComboBoxAction1(ActionEvent event) {
    }
    @FXML
    void hourComboBoxAction(ActionEvent event){}
    @FXML
    void secondComboBoxAction(ActionEvent event){}
}
