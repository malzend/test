package controller;

import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Model;

import java.io.IOException;

public class UserController {

    @FXML
    private Button logOutButton;

    @FXML
    private Button viewAppointmentButton;

    @FXML
    private Button viewCutomerButton;

    @FXML
    private Button viewReportButton;


    @FXML
    void ViewAppointmentAction(ActionEvent event) throws IOException {
        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        Stage Windoe = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut, 800, 400);
        Windoe.setScene(scene);
        Windoe.setTitle("Appointment page:");
        Windoe.show();
    }

    @FXML
    void ViewCustomerAction(ActionEvent event) throws IOException {

        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        Stage Windoe = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut, 731, 400);
        Windoe.setScene(scene);
        Windoe.setTitle("Customer page:");
        Windoe.show();


    }

    @FXML
    void ViewReportAction(ActionEvent event) throws IOException {
        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
        Scene scene = new Scene(userLogOut, 766, 400);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("LogIn page:");
        window.show();
    }

    @FXML
    void logOutButton(ActionEvent event) throws IOException {
        JDBC.closeConnection();
        Model.removeAllCustomer();
        Model.removeAllAppointment();
        Model.removeAllContact();
        Model.removeAllUser();
        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
        Stage Windoe = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut, 800, 400);
        Windoe.setScene(scene);
        Windoe.setTitle("Customer page:");
        Windoe.show();
    }
}
