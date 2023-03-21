package model;

import java.sql.SQLException;
import java.time.LocalDateTime;

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
    public Appointment(int appointmentID, String title, String description, String type, LocalDateTime startTimeDate, LocalDateTime endTimeDate,  int  customerID) throws SQLException {

        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.type = type;
        this.startTimeDate = startTimeDate;
        this.endTimeDate = endTimeDate;
        this.customerID = customerID;


    }

    public Appointment() throws SQLException { }

    public int getAppointmentID(){ return appointmentID; }

    public String getType(){ return type; }

    public String getDescription(){
        return description;
    }

    public String getTitle(){
        return title;
    }

    public String getLocation(){
        return location;
    }

    public LocalDateTime getStartTimeDate(){ return startTimeDate; }

    public LocalDateTime getEndTimeDate(){
        return endTimeDate;
    }

    public int getUserID(){return  userID;}

    public int getCustomerID(){return customerID;}

    public int getContactID() {return contactID; }

    public void setAppointmentID(int appointmentID){
        this.appointmentID = appointmentID;
    }

    public void setType(String type){ this.type = type; }

    public void setLocation (String location){ this.location = location; }

    public void setTitle(String Title){ this.title = Title;}

    public void setDescription(String descriptionID ){
        this.description = descriptionID;
    }

    public void getLocation(String location){
       this.location = location;
    }

    public void setStarTimeDate(LocalDateTime startTime ){
        this.startTimeDate= startTime ;
    }

    public void setEndTimeDate(LocalDateTime endTime){
        this.endTimeDate = endTime;
    }

    public void setUserID(int userID) { this.userID = userID; }

    public void setCustomerID(int customerID) { this.customerID = customerID; }

    public void setContactID(int contactID){ this.contactID = contactID; }
}
