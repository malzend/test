package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerQuery {

    public static ObservableList<Customer> customerDataNew() throws SQLException {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        String customerData = "SELECT * FROM customers ";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {

            int customerID = rl.getInt("Customer_ID");
            String customerName = rl.getString("Customer_Name");
            String address = rl.getString("Address");
            String postalCode = rl.getString("Postal_Code");
            String phoneNumber = rl.getString("Phone");
            int divisionID = rl.getInt("Division_ID");

            list.add(new Customer(customerID, customerName, address, postalCode, phoneNumber,divisionID));


        }
        return list;
    }

    public static int customerID() throws SQLException {

        int customerID = 0;
        String customerData = "SELECT * FROM customers WHERE Customer_ID =(SELECT MAX(Customer_ID) FROM customers); ";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {
            customerID = rl.getInt("Customer_ID");

        }

        return customerID;
    }

    public static String customerAdd( String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {
        String goodInsert = "Success";
        String badInsert = "Not successful ";
        String customerAdd = "INSERT INTO customers (Customer_Name,Address, Postal_Code, Phone,Division_ID) VALUES(?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(customerAdd);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
       ps.setInt(5, divisionID);
        int insertResult = ps.executeUpdate();
        if (insertResult > 0) {
            return goodInsert;
        } else {
            return badInsert;
        }
    }
    public static String customerPostAdd() throws SQLException {
        String goodInsert = "Success";
        String badInsert = "Not successful ";
        String customerAdd = "INSERT INTO customers (Customer_Name,Address, Postal_Code, Phone,Division_ID) VALUES(?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(customerAdd);
        ps.setString(1, "");
        ps.setString(2, "");
        ps.setString(3, "");
        ps.setString(4, "");
        ps.setInt(5, 1);
        int insertResult = ps.executeUpdate();
        if (insertResult > 0) {
            return goodInsert;
        } else {
            return badInsert;
        }
    }

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

    public static String customerDeleteEmpty() throws SQLException {
        String goodDelete = "Success";
        String badDelete = "Not successful";

        String customerDelete = "DELETE FROM customers WHERE Customer_Name = ? ";
        PreparedStatement ps = JDBC.connection.prepareStatement(customerDelete);
        ps.setString(1, "");
        int deleteResult = ps.executeUpdate();

        if (deleteResult > 0) {
            return goodDelete;
        } else {
            return badDelete;
        }
    }


    public static String updateCustomer(int customerID, String customerName, String address, String postalCode, String phone) throws SQLException{
        String goodDelete = "Success";
        String badDelete = "Not successful";
        String sql = "UPDATE customers SET Customer_Name = ?,Address = ?,Postal_Code = ?,Phone = ?  WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, customerID);
        int rowAffected = ps.executeUpdate();

        if (rowAffected > 0) {
            return goodDelete;
        } else {
            return badDelete;
        }
    }




    public static String updateCustomer_New(int customerID, String customerName, String address, String postalCode, String phone, int division_ID) throws SQLException{
        String goodDelete = "Success";
        String badDelete = "Not successful";
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?,Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, division_ID);
        ps.setInt(6,customerID);
        int rowAffected = ps.executeUpdate();
        if (rowAffected > 0) {
            return goodDelete;
        } else {
            return badDelete;
        }
    }
    public static String updateDivision(int customerID, int divisionID) throws SQLException{
        String goodDelete = "Success";
        String badDelete = "Not successful";
        String sql = "UPDATE customers SET Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(2, customerID);
        ps.setInt(1, divisionID);
        int rowAffected = ps.executeUpdate();

        if (rowAffected > 0) {
            return goodDelete;
        } else {
            return badDelete;
        }
    }
    public static ObservableList<Country> countryDATA() throws SQLException {
        ObservableList<Country> list = FXCollections.observableArrayList();
        String customerData = "SELECT * FROM countries";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {
            int Country_ID = rl.getInt("Country_ID");
            String Country = rl.getString("Country");


            list.add(new Country(Country_ID, Country));

        }
        return list;
    }

    public static void firstLevelDivisionData() throws SQLException {
        String customerData = "SELECT * FROM first_level_divisions ";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {


            int divisionID = rl.getInt("Division_ID");
            String divisionName = rl.getString("Division");
            int countryID = rl.getInt("Country_ID");


            Model.addFirstLevelDivision(new FirstLevelDivision(divisionID,divisionName,countryID));


        }
    }

}
