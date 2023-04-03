package model;

/**
 * This class FirstLevelDivision contains the methods, gets and sets, that specifies class variables<br>
 * via Utilizing the class constructor to access these methods.<br>
 * */
public class FirstLevelDivision {

    private int divisionID;
    private String divisionName;
    private int divisionCountryID;

    /**
     * Appointment constructor is called to set it's parameters.<br>
     * @param divisionID set variable divisionID.<br>
     * @param divisionName set variable divisionName.<br>
     * @param divisionCountryID set variable divisionCountryID.<br>
     */
    public FirstLevelDivision(int divisionID, String divisionName, int divisionCountryID){
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.divisionCountryID = divisionCountryID;
    }
    /**
     * get for divisionID<br>
     * @return the appointmentID.<br>
     */
    public int getDivisionID() {return divisionID; }
    /**
     * get for divisionName<br>
     * @return the divisionName.<br>
     */
    public String getDivisionName() { return divisionName; }

    /**
     * get for divisionCountryID<br>
     * @return the divisionCountryID.<br>
     */
    public int getDivisionCountryId(){return  divisionCountryID;}
    /**
     * set for divisionID.<br>
     * @param divisionID set divisionID.<br>
     */
    public void setDivisionID(int divisionID) { this.divisionID = divisionID; }
    /**
     * set for divisionName.<br>
     * @param divisionName set divisionName.<br>
     */
    public void setDivisionName(String divisionName){ this.divisionName = divisionName; }
    /**
     * set for divisionCountryID.<br>
     * @param divisionCountryID set divisionCountryID.<br>
     */
    public void setDivisionCountryID(int divisionCountryID){this.divisionCountryID = divisionCountryID;}

    /**
     * get for divisionName.<br>
     * @return the divisionName.<br>
     */
    @Override
    public String toString(){
        return divisionName;
    }
}
