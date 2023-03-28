package model;

import java.time.LocalDateTime;

public class Report {

    private String month;
    private String type;
    private int count;
    private String nameCountry;

    public Report(String month, String type){
        this.month = month;
        this.type = type;
    }
    public Report(String date, String type, int count){
        this.month = date;
        this.type = type;
        this.count = count;
    }
    public String getDate(){ return month; }

    public int getCount(){ return count; }

    public String getReportType(){ return type; }

    public String getCountryName(){return  nameCountry;}

    public void setCountryName(String countryName){ this.nameCountry = countryName; }
    public void setCount(int total){this.count = total;}
    public void setReportType(String type){ this.type = type; }
}
