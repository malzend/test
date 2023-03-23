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
import java.util.Objects;

/**
 *<p> UserController class will do the following:<br>
 * Displays three buttons view Appointments, view Users and view report.<br>
 * </p>
 */
public class UserController {
    /**
     * <br>
     * FXML id selectors <br>
     * <br>
     * selectors used to read or set form fields <br>
     */
    @FXML
    private Button logOutButton;

    @FXML
    private Button viewAppointmentButton;

    @FXML
    private Button viewCutomerButton;

    @FXML
    private Button viewReportButton;

    /**
     *The ViewAppointmentAction will load the frame for appointments table page.<br>
     * @param event
     * @throws IOException
     */
    @FXML
    void ViewAppointmentAction(ActionEvent event) throws IOException {
        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Appointment.fxml"));
        Stage Window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut);
        Window.setScene(scene);
        Window.setTitle("Appointment page:");
        Window.show();
    }

    /**
     *ViewCustomerAction will load the frame for customer table page. <br>
     * @param event
     * @throws IOException
     */

    @FXML
    void ViewCustomerAction(ActionEvent event) throws IOException {

        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        Stage Windoe = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut);
        Windoe.setScene(scene);
        Windoe.setTitle("Customer page:");
        Windoe.show();


    }

    /**
     *ViewReportAction will load the report page.<br>
     * @param event
     * @throws IOException
     */
    @FXML
    void ViewReportAction(ActionEvent event) throws IOException {
        Parent userLogOut = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Report.fxml")));
        Scene scene = new Scene(userLogOut);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setTitle("LogIn page:");
        window.show();
    }

    /**
     *logOutButton will exit the user page and load the login page.<br>
     * @param event on button will exit the program <br>
     * @throws IOException
     */

    @FXML
    void logOutButton(ActionEvent event) throws IOException {
        JDBC.closeConnection();
        Model.removeAllCustomer();
        Model.removeAllAppointment();
        Model.removeAllContact();
        Model.removeAllUser();
        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
        Stage Window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut);
        Window.setScene(scene);
        Window.setTitle("Customer page:");
        Window.show();
    }
}
