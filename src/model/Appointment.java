package model;

import java.sql.SQLException;
import java.time.LocalDateTime;

 /**
  * This class Appointment contains the methods, gets and sets, that specifies class variables<br>
  * via Utilizing the class constructor to access these methods.<br>
  * */
public class Appointment{

    private int appointmentID;
    private String title ;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTimeDate;
    private LocalDateTime endTimeDate;
    private int userID;
    private int customerID;
    private int contactID;

    /**
     * Appointment constructor is called to set it's parameters.<br>
     * @param appointmentID set variable appointmentID
     * @param title set variable title
     * @param description set variable description
     * @param location set variable location
     * @param type set variable type
     * @param startTimeDate set variable startTimeDate
     * @param endTimeDate set variable endTimeDate
     * @param userID set variable userID
     * @param customerID set variable customerID
     * @param contactID set variable contactID
     * @throws SQLException when an invalid query process accrue.<br>
     */

    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startTimeDate, LocalDateTime endTimeDate, int userID, int  customerID, int contactID) throws SQLException {

        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTimeDate = startTimeDate;
        this.endTimeDate = endTimeDate;
        this.userID = userID;
        this.customerID = customerID;
        this.contactID = contactID;

    }

    /**
     *
     @param appointmentID set variable appointmentID
     * @param title set variable title
     * @param description set variable description
     * @param type set variable type
     * @param startTimeDate set variable startTimeDate
     * @param endTimeDate set variable endTimeDate
     * @param customerID set variable customerID
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public Appointment(int appointmentID, String title, String description, String type, LocalDateTime startTimeDate, LocalDateTime endTimeDate,  int  customerID) throws SQLException {

        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.type = type;
        this.startTimeDate = startTimeDate;
        this.endTimeDate = endTimeDate;
        this.customerID = customerID;

    }

    /**
     * A Non Overloaded constructor.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public Appointment() throws SQLException { }

    /**
     * get for appointmentID<br>
     * @return the appointmentID.<br>
     */
    public int getAppointmentID(){ return appointmentID; }

    /**
     * get for type.<br>
     * @return the type.<br>
     */
    public String getType(){ return type; }

    /**
     * get for description.<br>
     * @return the description.<br>
     */
    public String getDescription(){ return description; }

    /**
     * get for title.<br>
     * @return the title.<br>
     */
    public String getTitle(){
        return title;
    }

    /**
     * get for location.<br>
     * @return the location.<br>
     */
    public String getLocation(){
        return location;
    }

    /**
     * get for startTimeDate.<br>
     * @return the startTimeDate.<br>
     */
    public LocalDateTime getStartTimeDate(){ return startTimeDate; }
    /**
     * get for endTimeDate.<br>
     * @return the endTimeDate.<br>
     */
    public LocalDateTime getEndTimeDate(){ return endTimeDate; }
    /**
     * get for userID.<br>
     * @return the userID.<br>
     */
    public int getUserID(){return  userID;}
    /**
     * get for customerID.<br>
     * @return the customerID.<br>
     */
    public int getCustomerID(){return customerID;}
    /**
     * get for contactID.<br>
     * @return the contactID.<br>
     */
    public int getContactID() {return contactID; }

    /**
     * set for appointmentID.<br>
     * @param appointmentID set appointmentID.<br>
     */
    public void setAppointmentID(int appointmentID){ this.appointmentID = appointmentID; }
    /**
     * set for type.<br>
     * @param type set type.<br>
     */
    public void setType(String type){ this.type = type; }
    /**
     * set for location.<br>
     * @param location set location.<br>
     */
    public void setLocation (String location){ this.location = location; }

    /**
     * set for title.<br>
     * @param title set title.<br>
     */
    public void setTitle(String title){ this.title = title;}

    /**
     * set for description.<br>
     * @param descriptionID set descriptionID.<br>
     */
    public void setDescription(String descriptionID ){
        this.description = descriptionID;
    }

    /**
     * set for location.<br>
     * @param location set location.<br>
     */
    public void getLocation(String location){
       this.location = location;
    }

    /**
     * set for startTimeDate.<br>
     * @param startTimeDate set startTimeDate.<br>
     */
    public void setStarTimeDate(LocalDateTime startTimeDate ){
        this.startTimeDate= startTimeDate ;
    }

    /**
     * set for endTimeDate.<br>
     * @param endTimeDate set endTimeDate.<br>
     */
    public void setEndTimeDate(LocalDateTime endTimeDate){
        this.endTimeDate = endTimeDate;
    }

    /**
     * set for userID.<br>
     * @param userID set userID.<br>
     */
    public void setUserID(int userID) { this.userID = userID; }

    /**
     * set for customerID.<br>
     * @param customerID set customerID.<br>
     */
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    /**
     * set for contactID.<br>
     * @param contactID set v.<br>
     */
    public void setContactID(int contactID){ this.contactID = contactID; }
}
