
package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * AppointmentQuery will do the following:<br>
 * Load the Appointment data form the dataBase.<br>
 * Insert the appointment to date base.<br>
 * Delete the appointment form database .<br>
 * Update the appointment from the database.<br>
 * Filter appointment by week and month.<br>
 * Load user data and customer data.<br>
 *
 */
public class AppointmentQuery {
    /**
     * appointmentData_new will return all the appointment that are stored in the database.<br>
     * sends a SELECT sql query to get the data.<br>
     * @return an ObservableList of type appointment will be returned.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */

    public static ObservableList<Appointment> appointmentData_new() throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        String customerData = "SELECT *, DATE(Start),DATE(End) FROM appointments";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {

            int Appointment_ID = rl.getInt("Appointment_ID");
            String Title = rl.getString("Title");
            String Description = rl.getString("Description");
            String Location = rl.getString("Location");
            String Type = rl.getString("Type");
            LocalDateTime startTimeStamp = rl.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTimeStamp = rl.getTimestamp("End").toLocalDateTime();
            int userID = rl.getInt("User_ID");
            int customerID = rl.getInt("Customer_ID");
            int contactID = rl.getInt("Contact_ID");

            list.add(new Appointment(Appointment_ID, Title, Description, Location, Type, startTimeStamp, endTimeStamp, userID, customerID, contactID));
        }
        return  list;
    }

    /**
     * appointmentDelete methode deletes an appointment from the database via delete sql query.<br>
     * The sql then returns Success if process is completed and Not successful if it's false.<br>
     * @param appointmentID set Appointment_ID in sql query.<br>
     * @return Success if the process was completed and return not successful if not.<br>
     * @throws SQLException when an invalid query process accrues.<br>
     */

    public static String appointmentDelete(int appointmentID) throws SQLException {
        String goodDelete = "Success";
        String badDelete = "Not successful";

        String customerDelete = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(customerDelete);
        ps.setInt(1, appointmentID);
        int deleteResult = ps.executeUpdate();

        if (deleteResult > 0) {
            return goodDelete;
        } else {
            return badDelete;
        }
    }

    /**
     * appointmentAdd methode adds new information to database via Insert query.<br>
     * The LocalDateTime variables are converted to Timestamp.<br>
     * @param Title sets Title  in sql query
     * @param Description sets Description in sql query
     * @param Location sets Location in sql query
     * @param Type sets Type in sql query
     * @param start sets start in sql query
     * @param end sets end in sql query
     * @param customerID sets customerID in sql query
     * @param userID sets userID in sql query
     * @param contactID sets contactID in sql query
     * @return Success if the process was completed and return not successful if not.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static String appointmentAdd(String Title, String Description, String Location, String Type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID) throws SQLException {

        Timestamp startTimestamp = Timestamp.valueOf(start);
        Timestamp endTimestamp = Timestamp.valueOf(end);

        String goodInsert = "Success";
        String badInsert = "Not successful ";
        String customerAdd = "INSERT INTO appointments (Title, Description, location, Type , Start ,End, User_ID, Customer_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(customerAdd);
        ps.setString(1, Title);
        ps.setString(2, Description);
        ps.setString(3, Location);
        ps.setString(4, Type);
        ps.setTimestamp(5, startTimestamp);
        ps.setTimestamp(6, endTimestamp);
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        int insertResult = ps.executeUpdate();
        if (insertResult > 0) {
            return goodInsert;
        } else {
            return badInsert;
        }
    }

    /**
     * updateAppointment methode updates appointment information when called.<br>
     * The sql then returns Success if process is completed and Not successful if it's false.<br>
     * @param Appointment_ID set Appointment_ID in sql.<br>
     * @param Title sets Title in sql query.<br>
     * @param Description sets Description in sql query.<br>
     * @param Location sets Location in sql query.<br>
     * @param Type sets Type in sql query.<br>
     * @param start sets start in sql query.<br>
     * @param end sets end in sql query.<br>
     * @param customerID sets customerID in sql query.<br>
     * @param userID sets userID in sql query.<br>
     * @param contactID sets contactID in sql query.<br>
     * @return Success if the process was completed and return not successful if not.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static String updateAppointment(int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime start, LocalDateTime end, int userID,  int customerID,int contactID) throws SQLException {
        Timestamp startTimestamp = Timestamp.valueOf(start);
        Timestamp endTimestamp = Timestamp.valueOf(end);

        String goodDelete = "Success";
        String badDelete = "Not successful";
        String sql = "UPDATE appointments SET  Title = ? , Description = ? , location = ? , Type = ? , Start = ?, End = ?, User_ID = ?, Customer_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, Title);
        ps.setString(2, Description);
        ps.setString(3, Location);
        ps.setString(4, Type);
        ps.setTimestamp(5, startTimestamp);
        ps.setTimestamp(6, endTimestamp);
        ps.setInt(7, userID);
        ps.setInt(8, customerID);
        ps.setInt(9, contactID);
        ps.setInt(10, Appointment_ID);

        int rowAffected = ps.executeUpdate();

        if (rowAffected > 0) {
            return goodDelete;
        } else {
            return badDelete;
        }
    }

    /**
     * loadContact method loads contact information when called .<br>
     * The methode return an ObservableList of type Contact.<br>
     * @return an ObservableList of Contact type.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static ObservableList<Contact> loadContact() throws SQLException {
        ObservableList<Contact> list = FXCollections.observableArrayList();
        String customerData = "SELECT * FROM contacts";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {
            int Contact_ID = rl.getInt("Contact_ID");
            String Contact = rl.getString("Contact_Name");


           list.add(new Contact(Contact_ID, Contact));

        }
        return list;
    }

    /**
     * loadUserData a method that will load user information from the database.<br>
     * SELECT query is used to select the necessary information form database.<br>
     * @param userName set User_Name in sql.
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static void loadUserData(String userName) throws SQLException {
        String userData = "SELECT * FROM users WHERE User_Name = ?";
        PreparedStatement sl = JDBC.connection.prepareStatement(userData);
        sl.setString(1, userName);
        ResultSet rl = sl.executeQuery();

        while (rl.next()) {

            int userID = rl.getInt("User_ID");
            String nameOfUser = rl.getString("User_Name");

            Model.addUser(new User(userID, nameOfUser));
        }
    }

    /**
     * loadTypeMonth the method will creates an sql query that inner joins appointments table <br>
     * and returns how many times a type accrued in a month. This will to help in determine the<br>
     * total of appointment type.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     * @return a ObservableList of type Report.<br>
     */
    public static  ObservableList<Report> loadTypeMonth()throws SQLException {
        ObservableList<Report> list = FXCollections.observableArrayList();
        String joinedTable = "SELECT t1.Type AS Type, monthname(t2.Start) AS Month, count(t1.Type) AS Count FROM appointments t1, appointments t2 WHERE t1.Appointment_ID = t2.Appointment_ID group by t1.Type,monthname(t2.Start);";
        PreparedStatement ps = JDBC.connection.prepareStatement(joinedTable);
        ResultSet rl = ps.executeQuery();
        while (rl.next()) {
            String monthFromData = rl.getString("Month");
            String Type = rl.getString("Type");
            int count = rl.getInt("Count");
            list.add(new Report(monthFromData, Type,count));

        }
        return list;
    }

    /**
     * customerContact this methode return a list of appointment type via SELECT Query<br>
     * and then add the lise to Appointment class.<br>
     * @param contactID set Contact_ID in sql query.<br>
     * @return a ObservableList of Appointment type.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */

    public static ObservableList<Appointment> customerContact(int contactID) throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        String customerData = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        sl.setInt(1, contactID);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {

            int Appointment_ID = rl.getInt("Appointment_ID");
            String Title = rl.getString("Title");
            String Description = rl.getString("Description");
            String Type = rl.getString("Type");
            LocalDateTime startTimeStamp = rl.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTimeStamp = rl.getTimestamp("End").toLocalDateTime();
            int customerID = rl.getInt("Customer_ID");


           list.add(new Appointment(Appointment_ID, Title, Description, Type, startTimeStamp, endTimeStamp, customerID));

        }
        return list;
    }

    /**
     * appointmentsByMonth filter appointment by the current month using select query.
     * @return an ObservableList of Appointment type.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static ObservableList<Appointment> appointmentsByMonth() throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        String customerData = "select * from appointments where MONTH(Start) = MONTH(now()) and YEAR(Start) = YEAR(now());";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {

            int Appointment_ID = rl.getInt("Appointment_ID");
            String Title = rl.getString("Title");
            String Description = rl.getString("Description");
            String Location = rl.getString("Location");
            String Type = rl.getString("Type");
            LocalDateTime startTimeStamp = rl.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTimeStamp = rl.getTimestamp("End").toLocalDateTime();
            int userID = rl.getInt("User_ID");
            int customerID = rl.getInt("Customer_ID");
            int contactID = rl.getInt("Contact_ID");

            list.add(new Appointment(Appointment_ID, Title, Description, Location, Type, startTimeStamp, endTimeStamp, userID, customerID, contactID));
        }
        return  list;
    }

    /**
     * appointmentsByWeek filter appointment by the current week using select query.
     * @return an ObservableList of Appointment type.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */

    public static ObservableList<Appointment> appointmentsByWeek() throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();

        String customerData = "Select * from appointments where Start between date_sub(now(), interval 7 day) and date_add(curdate(), interval 7 day)";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {

            int Appointment_ID = rl.getInt("Appointment_ID");
            String Title = rl.getString("Title");
            String Description = rl.getString("Description");
            String Location = rl.getString("Location");
            String Type = rl.getString("Type");
            LocalDateTime startTimeStamp = rl.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTimeStamp = rl.getTimestamp("End").toLocalDateTime();
            int userID = rl.getInt("User_ID");
            int customerID = rl.getInt("Customer_ID");
            int contactID = rl.getInt("Contact_ID");

            list.add(new Appointment(Appointment_ID, Title, Description, Location, Type, startTimeStamp, endTimeStamp, userID, customerID, contactID));
        }
        return  list;
    }
}





