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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;


public class LogInController implements Initializable {

    @FXML
    private Button LogIn;

    @FXML
    private TextField passWord;

    @FXML
    private TextField timeZoneTextBox;

    @FXML
    private TextField userName;

    @FXML
    void LogIn(ActionEvent event) throws IOException, SQLException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

       // Signature sign = Signature.getInstance("SHA256withRSA");

        //KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //keyPairGen.initialize(2048);

       // KeyPair pair = keyPairGen.generateKeyPair();

       // PublicKey publicKey = pair.getPublic();

       // Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        //cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        JDBC.openConnection();

        String uName = userName.getText();
        String  password = passWord.getText();
        AppointmentQuery.loadUserData(uName);

        boolean test = helper.LogIn.validateUser(uName, password);

        if (test == true) {
            FileWriter file = new FileWriter("/Users/mohamed/Downloads/test/src/activityLogin.txt",true);
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
            LocalTime osTime = LocalTime.parse(timeFormat.format(LocalTime.now().plusMinutes(4)));
            LocalTime scheduled;

            filteredListAppointment.setPredicate((t) -> {
                LocalDate dateOfAppointment = LocalDate.parse(dateFormat.format(t.getStartTimeDate()));
                int whoUser = UserQuery.getUser(uName);
                return dateOfAppointment.isEqual(osDate) && t.getUserID() == whoUser;
            });

            if(filteredListAppointment.isEmpty()){
                Alert alertAppointment = new Alert(Alert.AlertType.CONFIRMATION);
                alertAppointment.setTitle("Appointment for today");
                alertAppointment.setHeaderText("No Upcoming appointment within 15 minutes for User");
                alertAppointment.setContentText("You have no upcoming appointment for now ");

                alertAppointment.showAndWait();
            }
            else if(filteredListAppointment.size() > 0) {

                for (int i = 0; i < filteredListAppointment.size(); i++) {
                    scheduled = LocalTime.parse(timeFormat.format(filteredListAppointment.get(i).getStartTimeDate()));
                    if (scheduled.equals(osTime)) {

                        Alert alertAppointment = new Alert(Alert.AlertType.CONFIRMATION);
                        alertAppointment.setTitle("Appointment for today");
                        alertAppointment.setHeaderText("appointment within 15 minutes.");
                        alertAppointment.setContentText("\n\n The appointment are the following: \n\n AppointmentID " + filteredListAppointment.get(i).getAppointmentID()
                                + "\n\n Date: " + dateFormat.format(filteredListAppointment.get(i).getStartTimeDate()) + " Time: " + timeFormat.format(filteredListAppointment.get(i).getStartTimeDate()));
                        alertAppointment.showAndWait();
                    }
                }
            }

            Parent customerPage = FXMLLoader.load(getClass().getResource("/View/User.fxml"));
            Scene scene = new Scene(customerPage, 600, 400);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setTitle("Welcome");
            window.show();
        }
        else {

            FileWriter file = new FileWriter("/Users/mohamed/Downloads/test/src/activityLogin.txt",true);
            file.write("Noun-Successful login: "+ LocalDateTime.now()+"\n");
            file.close();

            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("You have entered an incorrect credentials ");
            alert1.setContentText("Please enter User name and a password ");
            alert1.show();
        }
    }

    @FXML
    void SendpassWord(ActionEvent event) { }

    @FXML
    void sendUserName(ActionEvent event) { }
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        String timeZone = TimeZone.getDefault().getDisplayName();
        timeZoneTextBox.setText(timeZone);
        timeZoneTextBox.setEditable(false);
    }


}
