package model;

import java.sql.SQLException;

/**
 * This class Appointment contains the methods, gets and sets, that specifies class variables
 * via Utilizing the class constructor to access these methods<br>
 * */
public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionID;
    private String divisionName;
    private String country;

    /**
     * Customer constructor is called to set it's parameters.<br>
     * @param customerID set variable customerID.<br>
     * @param customerName set variable customerName.<br>
     * @param address set variable address.<br>
     * @param postalCode set variable postalCode.<br>
     * @param phoneNumber set variable phoneNumber.<br>
     * @param divisionID set variable divisionID.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber, int divisionID) throws SQLException {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;

    }

    /**
     * Customer constructor is called to set it's parameters.<br>
     * @param customerID set variable customerID.<br>
     * @param customerName set variable customerName.<br>
     * @param address set variable address.<br>
     * @param postalCode set variable postalCode.<br>
     * @param phoneNumber set variable phoneNumber.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber) throws SQLException {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;

    }

    /**
     * Customer constructor is called to set it's parameters.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public Customer() throws SQLException { }
    /**
     * get for customerID.<br>
     * @return the customerID.<br>
     */
    public int getCustomerID() {
        return customerID;
    }
    /**
     * get for customerName.<br>
     * @return the v.<br>
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * get for address.<br>
     * @return the address.<br>
     */
    public String getAddress() {
        return address;
    }
    /**
     * get for postalCode.<br>
     * @return the postalCode.<br>
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * get for phoneNumber.<br>
     * @return the phoneNumber.<br>
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * get for divisionID.<br>
     * @return the divisionID.<br>
     */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     * get for divisionName.<br>
     * @return the divisionName.<br>
     */
    public String getDivisionName()  {
        return divisionName = Model.getFirstLevelDivisionName(divisionID);
    }

    /**
     * get for type.<br>
     * @return the type.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public String getCountryName() throws SQLException {
        return country = Model.getFirstLevelDivisionCountry(divisionID);
    }
    /**
     * set for customerID.<br>
     * @param customerID set customerID.<br>
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * set for customerName.<br>
     * @param customerName set customerName.<br>
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /**
     * set for address.<br>
     * @param address set address.<br>
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * set for postalCode.<br>
     * @param postalCode set postalCode.<br>
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * set for phoneNumber.<br>
     * @param phoneNumber set phoneNumber.<br>
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * set for divisionID.<br>
     * @param divisionID set divisionID.<br>
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * set country
     * @throws SQLException when an invalid query process accrue.<br>
     */

    public void setCountryName() throws SQLException { this.country =Model.getFirstLevelDivisionCountry(divisionID);
    }

    /**
     * get for customerName.<br>
     * @return the customerName.<br>
     */
    @Override
    public String toString() {
        return customerName;
    }
}

