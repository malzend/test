package model;

import java.sql.SQLException;

/**
 *
 */

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
     *
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param divisionID
     * @throws SQLException
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
     *
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @throws SQLException
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber) throws SQLException {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;

    }

    /**
     *
     * @throws SQLException
     */
    public Customer() throws SQLException {
    }

    /**
     *
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @return
     */

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     *
     * @return
     */
    public String getDivisionName() {
        return Model.getFirstLevelDivisionName(divisionID);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public String getCountryName() throws SQLException {
        return country = Model.getFirstLevelDivisionCountry(divisionID);
    }

    /**
     *
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     *
     * @throws SQLException
     */

    public void setCountryName() throws SQLException { this.country =Model.getFirstLevelDivisionCountry(divisionID);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return customerName;
    }
}

