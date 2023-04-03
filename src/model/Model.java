package model;

import helper.AppointmentQuery;
import helper.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.*;

/**
 * Model class will fo the following:
 * Load first level division data using an ObservableList .<br>
 * Load county data using ObservableList.<br>
 * Filter division base on country.<br>
 * Get country ID.<br>
 * Get division ID.<br>
 * Convert est business hours to the locale time zone of the machine.<br>
 */
public class Model {

    private static final ObservableList<User> USERS = FXCollections.observableArrayList();
    private static final ObservableList<Country> COUNTRY = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> APPOINTMENTS = FXCollections.observableArrayList();
    private static final ObservableList<FirstLevelDivision> FirstLevelDivision = FXCollections.observableArrayList();
    private static final ObservableList<Contact> CONTACTS = FXCollections.observableArrayList();
    private static final ObservableList<Customer> CUSTOMERS = FXCollections.observableArrayList();
    private static final ObservableList<FirstLevelDivision> filterDivision = FXCollections.observableArrayList();

    /**
     * getCustomer method used to get list of Customers.<br>
     * @return CUSTOMERS list.<br>
     */
    public static ObservableList<Customer> getCustomer() {
        return CUSTOMERS;
    }
    /**
     * getCountry method used to get the list of Country.<br>
     * @return COUNTRY list.<br>
     */
    public static ObservableList<Country> getCountry() { return COUNTRY; }
    /**
     * getUser method used to get the user list.<br>
     * @return filterDivision list.<br>
     */
    public static ObservableList<User> getUser() { return USERS; }

    /**
     * getFilterDivision method used to get the accosted division for each country.<br>
     * @return filterDivision list.<br>
     */
    public static ObservableList<FirstLevelDivision> getFilterDivision() { return filterDivision; }

    /**
     * addUser used to add a new division to the list.<br>
     * @param user add to the list.<br>
     */
    public static void addUser(User user) { USERS.add(user);}

    /**
     * addFirstLevelDivision used to add a new division to the list.<br>
     * @param firstLevelDivision add to the list.<br>
     */
    public static void addFirstLevelDivision(FirstLevelDivision firstLevelDivision){ FirstLevelDivision.add(firstLevelDivision); }

    /**
     * removeAllCustomer method deletes all customer for the list br>
     */
    public static void removeAllCustomer() { CUSTOMERS.clear(); }
    /**
     * removeAllAppointment method deletes all appointments form the list br>
     */
    public static void removeAllAppointment() { APPOINTMENTS.clear(); }
    /**
     * removeAllContact method deletes all contacts form the list br>
     */
    public static void removeAllContact() { CONTACTS.clear(); }
    /**
     * removeAllUser method deletes  all customer form the list  br>
     */
    public static void removeAllUser() { USERS.clear();}


    /**
     * removeFilterDivision method deletes filterDivision that is accosted to each country.<br>
     */
    public static void removeFilterDivision() { filterDivision.clear();}

    /**
     * getCountryName method gets the Country name for the database.<br>
     * @param i to pass the position of country in the list.<br>
     * @return Country if ture else null.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static String getCountryName(int i) throws SQLException {

        while(i< CustomerQuery.countryDATA().size()){
            return CustomerQuery.countryDATA().get(i).getCountry();
        }
        return null;
    }

    /**
     * getCountry_Name method used to get the country name.<br>
     * @param ID is used in the if condition as comparison against the division.<br>
     * @return country if true else null.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static String getCountry_Name(int ID) throws SQLException {

    for(int i = 0; i < CustomerQuery.countryDATA().size(); i++){
        if(ID== CustomerQuery.countryDATA().get(i).getCountryID())
            return CustomerQuery.countryDATA().get(i).getCountry();
        }
        return null;
    }

    /**
     * getCountryID method used to get the country ID.<br>
     * @param countryName s used in the if condition as comparison against the division.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static void getCountryID(String countryName) throws SQLException {
        int countryIDforDivisoon = 0;
        for( int i = 0; i < CustomerQuery.countryDATA().size(); i++){
            if(CustomerQuery.countryDATA().get(i).getCountry().contains(countryName))
               countryIDforDivisoon = CustomerQuery.countryDATA().get(i).getCountryID();

            }
        for(int i = 0; i < FirstLevelDivision.size(); i++) {
            if (countryIDforDivisoon == FirstLevelDivision.get(i).getDivisionCountryId()) {
                filterDivision.add(FirstLevelDivision.get(i));
            }
        }

    }

    /**
     * getFirstLevelDivisionID method used to get the id of a division.<br>
     * @param firstLevelDivisionName is used in the if condition as comparison against the division.<br>
     * @return division ID if true else 0.<br>
     */
    public static int getFirstLevelDivisionID(String firstLevelDivisionName) {
        for( int i = 0 ; i < FirstLevelDivision.size(); i++){
            if(FirstLevelDivision.get(i).getDivisionName() == firstLevelDivisionName)
            return FirstLevelDivision.get(i).getDivisionID();
        }
       return 0;
    }

    /**
     * getFirstLevelDivisionName method used to return the division name.<br>
     * @param firstLevelDivisionID is used in the if condition as comparison against the division.<br>
     * @return DivisionName if true else null.<br>
     */

    public static String getFirstLevelDivisionName(int firstLevelDivisionID) {
        for( int i = 0 ; i < FirstLevelDivision.size(); i++){
            if(FirstLevelDivision.get(i).getDivisionID() == firstLevelDivisionID)
                return FirstLevelDivision.get(i).getDivisionName();
        }
        return null;
    }

    /**
     * getFirstLevelDivisionCountry method is used to return the country name.<br>
     * @param firstLevelDivisionID is used in the if condition as comparison against the division list
     * @return County if true else nothing.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static String getFirstLevelDivisionCountry(int firstLevelDivisionID) throws SQLException {

        for( int i = 0 ; i < FirstLevelDivision.size(); i++){
            if(FirstLevelDivision.get(i).getDivisionID() == firstLevelDivisionID)
                for(int l =0;l< CustomerQuery.countryDATA().size();l++) {
                    if (FirstLevelDivision.get(i).getDivisionCountryId() == CustomerQuery.countryDATA().get(l).getCountryID()) {
                        return CustomerQuery.countryDATA().get(l).getCountry();
                    }
                }
        }
        return "nothing";
    }

    /**
     * customerFirstDivisionName method will return the division name form the FirstLevelDivision list.<br>
     * @param ID used in if condition to compare the match ID.<br>
     * @return DivisionName.<br>
     */
    public static String customerFirstDivisionName(int ID) {
        for (int i = 0; i < FirstLevelDivision.size(); i++) {
            if (FirstLevelDivision.get(i).getDivisionID() == ID) {
                return FirstLevelDivision.get(i).getDivisionName();
            }
        }
        return null; }

    /**
     * countriesFirstDivision method will return the id of a country using the forging key that was loaded form the database to the list of division.<br>
     * @param ID used in if condition to compare the match ID.<br>
     * @return  country ID.<br>
     */
    public static int countriesFirstDivision(int ID) {
        for (int i = 0; i < FirstLevelDivision.size(); i++) {
            if ( ID == FirstLevelDivision.get(i).getDivisionID()) {
                return FirstLevelDivision.get(i).getDivisionCountryId();
            }
        }
        return 0; }

    /**
     * getContact_Name is used to get each contact name utilizing the contact ID.<br>
     * @param contactID used to compare in the if condition.<br>
     * @return String if true else null.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static String getContact_Name(int contactID) throws SQLException {

        for (int i = 0; i < AppointmentQuery.loadContact().size(); i++) {
            if (AppointmentQuery.loadContact().get(i).getContactID() == contactID ) {
                return AppointmentQuery.loadContact().get(i).getContactName();
            }
        }
        return null;
    }

    /**
     * businessTimeStart method will get the est business hours by converting the local time zone of the machine.<br>
     * @return list of Type Integer,<br>
     */
   public static ObservableList<Integer> businessTimeStart() {

        ObservableList<Integer> list =FXCollections.observableArrayList();

       LocalDateTime estLDT = LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0));
       ZoneId estZID = ZoneId.of("America/New_York");
       ZonedDateTime estZDT = ZonedDateTime.of(estLDT,estZID);
       ZoneId osZID = ZoneId.systemDefault();

       ZonedDateTime osZDT = ZonedDateTime.ofInstant(estZDT.toInstant(), osZID);
       System.out.println(estZDT);
       System.out.println(osZDT);

       int start = osZDT.getHour();
       int midnight = 0;
       for(int i = start; i <= start+13;i++){
           if(i<24){
             list.add(i);
           }
           else if(i>23){
               list.add(midnight++);
           }
       }
       return list;
    }
}

