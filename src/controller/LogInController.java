package controller;

import helper.AppointmentQuery;
import helper.CustomerQuery;
import helper.JDBC;
import helper.UserQuery;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;

import javax.crypto.NoSuchPaddingException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 *<p>LogInController class will do the following:<br>
 * Validate the user credentials.<br>
 * Check if the user has an upcoming appointment in 15 minutes.<br>
 * Display the upcoming appointment.<br>
 * Loads the User.FXML page if the credentials are valid according to the DataBase.<br>
 * </p>
 */
public class LogInController implements Initializable {
    /**
     * <br>
     * FXML id selectors <br>
     * <br>
     * selectors used to read or set form fields <br>
     */
    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label loginLabel;
    @FXML
    private Label timeZoneLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField passWordTxt;
    @FXML
    private TextField timeZoneTextBox;
    @FXML
    private TextField userNameTxt;

    private ResourceBundle rb = ResourceBundle.getBundle("language", Locale.getDefault());

    /** initialize<br>
     * Loads necessary ZoneID translates login text that are going to be used in the GUI form.<br>
     * Translate text to system language.<br>
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Locale.setDefault(new Locale("en","US"));
        timeZoneLabel.setText(ZoneId.systemDefault().toString());
        userNameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        loginLabel.setText(rb.getString("login"));
    }

    /**
     * Description of the method.<br>
     * Lambda 1 helps in filtering the upcoming appointments via comparing the customer ID and the user assigned to it.<br>
     * This Lambda helps getting the upcoming appointment with less code writing, reduces the time when the code is implemented. <br>
     *
     * @param event
     * @throws IOException
     * @throws SQLException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @FXML
    void LogIn(ActionEvent event) throws IOException, SQLException{
        /**
         * JDBC establishes the connection to the DataBase.<br>
         * uName and password are String type used to collect user input.<br>
         * uName is passed to AppointmentQuery.loadUserDate(String) and load user data.<br>
         * test is a boolean variable that is used to store true or false of the validation process
         */
        JDBC.openConnection();

        String uName = userNameTxt.getText();
        String  password = passWordTxt.getText();
        AppointmentQuery.loadUserData(uName);

        boolean test = helper.LogIn.validateUser(uName, password);

        /**
         * The if condition is used to validate test variable if it true it will do the following:<br>
         * activityLogin.txt file will record Success ful login and the time of the login. <br>
         * The data for country, first level division, Contact and one of the report features data will be loaded. <br>
         * FilteredList of Appointment type will be created and used to store associated user appointment at the current day. <br>
         *
         */
        if (test == true) {
            FileWriter file = new FileWriter("activityLogin.txt",true);
            file.write("Successful login: "+ LocalDateTime.now()+ "\n");
            file.close();

            try {
                CustomerQuery.countryDATA();
                CustomerQuery.firstLevelDivisionData();
                AppointmentQuery.loadContact();
                AppointmentQuery.loadTypeMonth();
                UserQuery.userData();
            } catch (SQLException throwable) { throwable.printStackTrace(); }

            FilteredList<Appointment> filteredListAppointment = new FilteredList<>(AppointmentQuery.appointmentData_new());
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyy-MM-dd");
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            LocalDate osDate = LocalDate.now();
            LocalTime osTimeStart = LocalTime.now();
            LocalTime osTimeEnd = osTimeStart.plusMinutes(15);
            LocalTime scheduled;

            // Lambda 1
            filteredListAppointment.setPredicate((t) -> {
                LocalDate dateOfAppointment = LocalDate.parse(dateFormat.format(t.getStartTimeDate()));
                int whoUser = UserQuery.getUser(uName);
                return dateOfAppointment.isEqual(osDate) && t.getUserID() == whoUser;
            });

            boolean upcoming = false;
            /**
             * The for loop is used to loop through the list to check if there are an upcoming appointments in the 15.<br>
             * if the is an appointment is within 15 minutes it displays an alert containing Appointment_ID, AppointmentDate, AppointmentTime and andUser.<br>
             * Else an alert of no upcoming appointment is displayed.
             *
             */
            for (int i = 0; i < filteredListAppointment.size(); i++) {
                scheduled = LocalTime.parse(timeFormat.format(filteredListAppointment.get(i).getStartTimeDate()));
                if (!(scheduled.isAfter(osTimeEnd) || scheduled.isBefore(osTimeStart))) {

                    Alert alertAppointment = new Alert(Alert.AlertType.CONFIRMATION);
                    alertAppointment.setTitle("Appointment for today");
                    alertAppointment.setHeaderText("appointment within 15 minutes for user " + filteredListAppointment.get(i).getUserID() + ".");
                    alertAppointment.setContentText("\n\n The appointment are the following: \n\n AppointmentID " + filteredListAppointment.get(i).getAppointmentID()
                            + "\n\n Date: " + dateFormat.format(filteredListAppointment.get(i).getStartTimeDate()) + " Time: " + timeFormat.format(filteredListAppointment.get(i).getStartTimeDate()));
                    alertAppointment.showAndWait();
                    upcoming = true;
                }
            }
            if(!upcoming){
                Alert alertAppointment = new Alert(Alert.AlertType.CONFIRMATION);
                alertAppointment.setTitle("Appointment for today");
                alertAppointment.setHeaderText("No Upcoming appointment within 15 minutes for User");
                alertAppointment.setContentText("You have no upcoming appointment for now ");

                alertAppointment.showAndWait();
            }


            Parent customerPage = FXMLLoader.load(getClass().getResource("/View/User.fxml"));
            Scene scene = new Scene(customerPage, 600, 400);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Welcome");
            window.show();
        }
        /**
         * else if the user is false then it will be recorded in the activityLogin.txt file and an Alert of wrong credentials wil
         * display.<br>
         */
        else {
            Locale.setDefault(new Locale("en","US"));
            FileWriter file = new FileWriter("activityLogin.txt",true);
            file.write("Noun-Successful login: "+ LocalDateTime.now()+"\n");
            file.close();

            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText(rb.getString("you") +" "+ rb.getString("have") +" "+ rb.getString("entered") +
                    " "+ rb.getString("incorrect")  +" "+ rb.getString("credentials") );
            alert1.setContentText(rb.getString("please") +" "+ rb.getString("enter") +" "+ rb.getString("UserName") +
                    " "+ rb.getString("and")  +" "+ rb.getString("a") + " " + rb.getString("Password") );
            alert1.show();
        }
    }

    @FXML
    void SendpassWord(ActionEvent event) { }

    @FXML
    void sendUserName(ActionEvent event) { }



}
