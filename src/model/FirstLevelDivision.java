package model;

public class FirstLevelDivision {

    private int divisionID;
    private String divisionName;
    private int divisionCountryID;




    public FirstLevelDivision(int divisionID, String divisionName){
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }
    public FirstLevelDivision(int divisionID, String divisionName, int divisionCountryID){
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.divisionCountryID = divisionCountryID;
    }

    public FirstLevelDivision() {

    }

    public int getDivisionID() {return divisionID; }

    public String getDivisionName() { return divisionName; }


    public int getDivisionCountryId(){return  divisionCountryID;}

    public void setDivisionID(int divisionID) { this.divisionID = divisionID; }

    public void setDivisionName(String divisionName){ this.divisionName = divisionName; }


    public void setDivisionCountryID(int divisionCountryID){this.divisionCountryID = divisionCountryID;}
    @Override
    public String toString(){
        return divisionName;
    }
}
