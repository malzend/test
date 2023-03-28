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

/**
 * CustomerQuery does the following:
 * Load user data from the database.<br>
 * Insert new customer record.<br>
 * Delete customer record.<br>
 * Modify customer record.<br>
 * Load an associated date for customer like division.<br>
 */

public class CustomerQuery {
    /***
     * customerDataNew this method handled the process of getting the data and loading in to<br>
     * Customer table using a SELECT query.<br>
     * @return an ObservableList of Customer type.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
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

    /**
     * customerAdd a method that insert customer information in database using INSERT INTO sql query.<br>
     * @param customerName set Customer_Name in sql query.<br>
     * @param address set Address in sql query.<br>
     * @param postalCode set Postal_Code in sql query.<br>
     * @param phone set Phone in sql query.<br>
     * @param divisionID set Division_ID in sql query.<br>
     * @return Success if the process was completed and return not successful if not.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */

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

    /**
     * customerDelete deletes the customer record from the database using a DELETER Query.<br>
     * @param customerID set Customer_ID in sql query.<br>
     * @return Success if the process was completed and return not successful if not.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
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
    /**
     * updateCustomer is a method used to update customer information in the database Using a SELECT Query.<br>
     * @param customerID set Customer_ID in sql query.<br>
     * @param customerName set Customer_Name in sql query.<br>
     * @param address set Address in sql query.<br>
     * @param postalCode set Postal_Code in sql query.<br>
     * @param phone set Phone in sql query.<br>
     * @return Success if the process was completed and return not successful if not.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
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

    /**
     * updateDivision is a methode the updateDivision in a customer record when a division is changed.<br>
     * The method used a Update method to update the division filed in a customer.<br>
     * @param customerID set Customer_ID in sql query.<br>
     * @param divisionID set Division_ID in sql query.<br>
     * @return Success if the process was completed and return not successful if not.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */

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

    /**
     * countryDATA loads the county date from the database using a SELECT method.<br>
     * @return an ObservableList of a type Country,<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
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

    /**
     * firstLevelDivisionData is a method that uses SELECT query to get th division id for the database.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */

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
