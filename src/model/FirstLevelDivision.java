package model;

/**
 *
 */
public class FirstLevelDivision {

    private int divisionID;
    private String divisionName;
    private int divisionCountryID;

    /**
     *
     * @param divisionID
     * @param divisionName
     * @param divisionCountryID
     */
    public FirstLevelDivision(int divisionID, String divisionName, int divisionCountryID){
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.divisionCountryID = divisionCountryID;
    }

    /**
     *
     * @return
     */
    public int getDivisionID() {return divisionID; }

    /**
     *
     * @return
     */
    public String getDivisionName() { return divisionName; }

    /**
     *
     * @return
     */
    public int getDivisionCountryId(){return  divisionCountryID;}

    /**
     *
     * @param divisionID
     */
    public void setDivisionID(int divisionID) { this.divisionID = divisionID; }

    /**
     *
     * @param divisionName
     */

    public void setDivisionName(String divisionName){ this.divisionName = divisionName; }

    /**
     *
     * @param divisionCountryID
     */
    public void setDivisionCountryID(int divisionCountryID){this.divisionCountryID = divisionCountryID;}

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return divisionName;
    }
}
