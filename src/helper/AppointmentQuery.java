
package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppointmentQuery {

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

    public static String appointmentAddEmpty() throws SQLException {
        LocalDateTime time = LocalDateTime.now();
        String goodInsert = "Success";
        String badInsert = "Not successful ";
        //DateTimeFormatter formattTimeStamp = DateTimeFormatter.ofPattern(" yyyy-mm-dd hh:mm:ss[.fffffffff]");
        Timestamp valueOne = Timestamp.valueOf(time);
        Timestamp valueTwo = Timestamp.valueOf(time);
        String customerAdd = "INSERT INTO appointments (Title, Description, location, Type , Start ,End, User_ID, Customer_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(customerAdd);
        ps.setString(1, "t");
        ps.setString(2, "t");
        ps.setString(3, "t");
        ps.setString(4, "");
        ps.setTimestamp(5, valueOne);
        ps.setTimestamp(6, valueTwo);
        ps.setInt(7, 1);
        ps.setInt(8, 1);
        ps.setInt(9, 1);
        int insertResult = ps.executeUpdate();
        if (insertResult > 0) {
            return goodInsert;
        } else {
            return badInsert;
        }
    }
    public static String appointmentDeleteEmpty() throws SQLException {
        String goodDelete = "Success";
        String badDelete = "Not successful";

        String customerDelete = "DELETE FROM appointments WHERE Start IS ? ";
        PreparedStatement ps = JDBC.connection.prepareStatement(customerDelete);
        ps.setTimestamp(1, null);
        int deleteResult = ps.executeUpdate();

        if (deleteResult > 0) {
            return goodDelete;
        } else {
            return badDelete;
        }
    }

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

    public static int leastappointmentID() throws SQLException {

        int appointment_ID = 0;
        String customerData = "SELECT * FROM appointments WHERE Appointment_ID =(SELECT MAX(Appointment_ID) FROM appointments); ";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {
            appointment_ID = rl.getInt("Appointment_ID");

        }

        return appointment_ID;
    }


    public static void joinTypeMonth(String month, String type) throws SQLException {
        String joinedTable = "SELECT t1.Type AS Type, t2.Start AS Month, COUNT(t1.Type) AS Number FROM appointments t1, appointments t2 WHERE t1.Appointment_ID = t2.Appointment_ID AND MONTH(t1.Start) = ?  AND  t1.Type = ? GROUP BY t1.Appointment_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(joinedTable);
        ps.setString(1, month);
        ps.setNString(2, type);
        ResultSet rl = ps.executeQuery();
        while (rl.next()) {
            Timestamp time = rl.getTimestamp("Month");
            String Type = rl.getString("Type");
            LocalDateTime timeDate = time.toLocalDateTime();

            Model.addReport(new Report(timeDate, Type));

        }

    }

    public static void loadTypeMonth() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
        String joinedTable = "SELECT t1.Type AS Type, t2.Start AS Month, sum(t1.Type) AS Number FROM appointments t1, appointments t2 WHERE t1.Appointment_ID = t2.Appointment_ID GROUP BY t1.Appointment_ID;";
        PreparedStatement ps = JDBC.connection.prepareStatement(joinedTable);
        ResultSet rl = ps.executeQuery();
        while (rl.next()) {
            LocalDateTime time = rl.getTimestamp("Month").toLocalDateTime();
            String Type = rl.getString("Type");
            Model.addReport(new Report(time, Type));

        }
    }

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


/*
    public static String customerDelete(int customerID) throws SQLException {
        String goodDelete = "Success";
        String badDelete = "Not successful";

        String customerDelete = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(customerDelete);
        ps.setInt(1, customerID);
        int deleteResult = ps.executeUpdate();

        if (deleteResult > 0) {
            return goodDelete;
        } else {
            return badDelete;
        }
    }


*/


