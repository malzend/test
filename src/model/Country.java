package model;

public class Country {

    private int countryID;
    private String country;

    public Country(int countryID, String country){
        this.countryID = countryID;
        this.country = country;

    }

    public int getCountryID(){ return countryID;}

    public String getCountry(){ return country; }

    public void setCountryID(int CountyID){
        this.countryID = countryID;
    }

    public void setCountry(String country){ this.country= country ; }

    @Override
    public String toString(){
        return country;
    }
}


