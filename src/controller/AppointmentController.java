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

public class AppointmentController implements Initializable {
    @FXML
    private TableColumn<Appointment, Date> endDareColumn;

    @FXML
    private TableColumn<Appointment, Date> startDateColumn;
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
    private Button addAppointmentButton;

    @FXML
    private Button exitModifyButton;

    @FXML
    private Button modifyAppointmentButton;

    @FXML
    private TableView<Appointment> modifyAppointmentTable;

    @FXML
    private Button moditydeleteButton;
    @FXML
    private RadioButton radioByWeek ;
    @FXML
    private RadioButton radioByMonthAction ;

    @FXML
    public void initialize(URL location, ResourceBundle resource) {

        try {
            modifyAppointmentTable.setItems(AppointmentQuery.appointmentData_new());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    @FXML
    void addAppointmentAction(ActionEvent event) throws IOException {
        Parent customerPage = FXMLLoader.load(getClass().getResource("/View/Customer.fxml"));
        Scene scene = new Scene(customerPage,600,521);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("LogIn page:");
        window.show();
    }

    @FXML
    void exitButtonAction(ActionEvent event) throws IOException {
        Parent customerPage = FXMLLoader.load(getClass().getResource("/View/User.fxml"));
        Scene scene = new Scene(customerPage,600,400);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("LogIn page:");
        window.show();
    }


    @FXML
    void modfiyAppointmentAction(ActionEvent event) throws IOException {
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

    @FXML
    void modifydeleteAction(ActionEvent event) throws SQLException {
        try {
            int selectedID = modifyAppointmentTable.getSelectionModel().getSelectedItem().getAppointmentID();
            String type = modifyAppointmentTable.getSelectionModel().getSelectedItem().getType();

            Appointment selectedItem = modifyAppointmentTable.getSelectionModel().getSelectedItem();
            System.out.print(selectedItem);
            String processResult = AppointmentQuery.appointmentDelete(selectedID);
            Alert alertType;
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

        try {
            modifyAppointmentTable.setItems(AppointmentQuery.appointmentData_new());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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


    @FXML
     void modifyAppointmentAction() {
    }
    @FXML
    void radioByWeekActioin(ActionEvent event) throws SQLException {
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
    @FXML
    void RadioByMonthAction() throws SQLException {
        if(radioByMonthAction.isSelected()){
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
