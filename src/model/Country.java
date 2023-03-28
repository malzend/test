package model;

/**
 *
 */
public class Country {

    private int countryID;
    private String country;

    /**
     *
     * @param countryID
     * @param country
     */

    public Country(int countryID, String country){
        this.countryID = countryID;
        this.country = country;

    }

    /**
     *
     * @return
     */
    public int getCountryID(){ return countryID;}

    /**
     *
     * @return
     */
    public String getCountry(){ return country; }

    /**
     *
     * @param CountyID
     */
    public void setCountryID(int CountyID){ this.countryID = countryID; }

    /**
     *
     * @param country
     */
    public void setCountry(String country){ this.country= country ; }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return country;
    }
}


