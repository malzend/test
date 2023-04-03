package model;
/**
 * This class Contact contains the methods, gets and sets, that specifies class variables<br>
 * via Utilizing the class constructor to access these methods.<br>
 * */
public class Country {
    private int countryID;
    private String country;
    /**
     * Country constructor is called to set it's parameters.<br>
     * @param countryID set countryID.<br>
     * @param country set country.<br>
     */
    public Country(int countryID, String country){
        this.countryID = countryID;
        this.country = country;
    }
    /**
     * get for countryID.<br>
     * @return the countryID.<br>
     */
    public int getCountryID(){ return countryID;}
    /**
     * get for country.<br>
     * @return the country.<br>
     */
    public String getCountry(){ return country; }
    /**
     * set for countryID.<br>
     * @param countyID set countyID.<br>
     */
    public void setCountryID(int countyID){ this.countryID = countryID; }
    /**
     * set for country.<br>
     * @param country set country.<br>
     */
    public void setCountry(String country){ this.country= country ; }
    /**
     * set for location.<br>
     * @return country set country.<br>
     */
    @Override
    public String toString(){
        return country;
    }
}


