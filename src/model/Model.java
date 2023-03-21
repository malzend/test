package model;

import helper.AppointmentQuery;
import helper.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.*;

//import org.jetbrains.annotations.Nullable;

public class Model {

    private static final ObservableList<User> USERS = FXCollections.observableArrayList();
    private static final ObservableList<Country> COUNTRY = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> APPOINTMENTS = FXCollections.observableArrayList();
    private static final ObservableList<FirstLevelDivision> FirstLevelDivision = FXCollections.observableArrayList();
    private static final ObservableList<Contact> CONTACTS = FXCollections.observableArrayList();
    private static final ObservableList<Customer> CUSTOMERS = FXCollections.observableArrayList();
    private static final ObservableList<Report> REPORT = FXCollections.observableArrayList();
    private static final ObservableList<FirstLevelDivision> filterDivision = FXCollections.observableArrayList();




    public static ObservableList<Customer> getCustomer() {
        return CUSTOMERS;
    }

    public static ObservableList<Country> getCountry() { return COUNTRY; }

    public static ObservableList<User> getUser() { return USERS; }

    public static ObservableList<Contact> getContacts() { return CONTACTS; }

    public static ObservableList<FirstLevelDivision> getFirstLevelDivisions() { return FirstLevelDivision; }

    public static ObservableList<FirstLevelDivision> getFilterDivision() { return filterDivision; }

    public static ObservableList<Appointment> getAppointments() { return APPOINTMENTS; }

    public static ObservableList<Report> getReport() { return REPORT; }



    public static void addCustomer(Customer customer) { CUSTOMERS.add(customer); }

    public static void addUser(User user) { USERS.add(user);}

    public static void addContact(Contact contact){ CONTACTS.add(contact);}

    public static void addAppointment(Appointment appointment) { APPOINTMENTS.add(appointment); }

    public static void addFirstLevelDivision(FirstLevelDivision firstLevelDivision){ FirstLevelDivision.add(firstLevelDivision); }



    public static void addReport(Report report) {  REPORT.add(report); }

    public static void removeCustomer(Customer customer) { CUSTOMERS.remove(customer); }

    public static void removeAllCustomer() { CUSTOMERS.clear(); }

    public static void removeAllAppointment() { APPOINTMENTS.clear(); }

    public static void removeAllContact() { CONTACTS.clear(); }

    public static void removeAllUser() { USERS.clear();}

    public static void removeAllCountrys() { COUNTRY.clear();}

    public static void removeAppointment(Appointment appointment) { APPOINTMENTS.remove(appointment); }

    public static void deleteFirstLevelDivision(){ FirstLevelDivision.clear(); }

    public static void deleteReport(){REPORT.clear();}

    public static void removeFilterDivision() { filterDivision.clear();}

    public static void modifyCustomer(int ID, Customer customer) {

        for (int i = 0; i < CUSTOMERS.size(); i++) {
            if (CUSTOMERS.get(i).getCustomerID() == ID) {
                CUSTOMERS.get(i).setCustomerName(customer.getCustomerName());
                CUSTOMERS.get(i).setAddress(customer.getAddress());
                CUSTOMERS.get(i).setCustomerID(customer.getCustomerID());
                CUSTOMERS.get(i).setPostalCode(customer.getPostalCode());
                CUSTOMERS.get(i).setPhoneNumber(customer.getPhoneNumber());
                CUSTOMERS.get(i).setDivisionID(customer.getDivisionID());

            }
        }

    }


    public static void modifyAppointmentInfo(int ID, Appointment appointment) {

        for (int i = 0; i < APPOINTMENTS.size(); i++) {
            if (ID == APPOINTMENTS.get(i).getAppointmentID()) {
                APPOINTMENTS.get(i).setTitle(appointment.getTitle());
                APPOINTMENTS.get(i).setDescription(appointment.getDescription());
                APPOINTMENTS.get(i).setLocation(appointment.getLocation());
                APPOINTMENTS.get(i).setType(appointment.getType());
                APPOINTMENTS.get(i).setContactID(appointment.getContactID());
                APPOINTMENTS.get(i).setCustomerID(appointment.getCustomerID());
                APPOINTMENTS.get(i).setStarTimeDate(appointment.getStartTimeDate());
                APPOINTMENTS.get(i).setEndTimeDate(appointment.getEndTimeDate());
                APPOINTMENTS.get(i).setUserID(appointment.getUserID());

            }
        }

    }

    public static void addCountry(Country country){ COUNTRY.add(country); }

    public static String getCountryName(int i) throws SQLException {

        while(i< CustomerQuery.countryDATA().size()){
            return CustomerQuery.countryDATA().get(i).getCountry();
        }
        return null;
    }
    public static String getCountry_Name(int ID) throws SQLException {

    for(int i = 0; i < CustomerQuery.countryDATA().size(); i++){
        if(ID== CustomerQuery.countryDATA().get(i).getCountryID())
            return CustomerQuery.countryDATA().get(i).getCountry();
        }
        return null;
    }
    public static String getContactName(int i) {

        while(i< CONTACTS.size()){
            return CONTACTS.get(i).getContactName();
        }
        return null;
    }


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

    public static String getFirstLevelDivision(int i) {

        while(i< FirstLevelDivision.size()){
            return FirstLevelDivision.get(i).getDivisionName();
        }
        return null;
    }
    public static int getFirstLevelDivisionID(String firstLevelDivisionName) {

        for( int i = 0 ; i < FirstLevelDivision.size(); i++){
            if(FirstLevelDivision.get(i).getDivisionName() == firstLevelDivisionName)
            return FirstLevelDivision.get(i).getDivisionID();
        }
       return 0;
    }


    public static String customerFirstDivisionName(int ID) {
        for (int i = 0; i < FirstLevelDivision.size(); i++) {
            if (FirstLevelDivision.get(i).getDivisionID() == ID) {
                return FirstLevelDivision.get(i).getDivisionName();
            }
        }
        return null; }

    public static int countriesFirstDivision(int ID) {
        for (int i = 0; i < FirstLevelDivision.size(); i++) {
            if ( ID == FirstLevelDivision.get(i).getDivisionID()) {
                return FirstLevelDivision.get(i).getDivisionCountryId();
            }
        }
        return 0; }

    public static String getContact_Name(int contactID) throws SQLException {

        for (int i = 0; i < AppointmentQuery.loadContact().size(); i++) {
            if (AppointmentQuery.loadContact().get(i).getContactID() == contactID ) {
                return AppointmentQuery.loadContact().get(i).getContactName();
            }
        }
        return null;
    }

   public static ObservableList<Integer> businessTime() {

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
       for(int i = start; i <= start+14;i++){
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
