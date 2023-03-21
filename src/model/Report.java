package model;

import java.time.LocalDateTime;

public class Report {

    private LocalDateTime date;
    private String type;
    private int total;
    private String nameCountry;

    public Report(LocalDateTime date, String type){
        this.date = date;
        this.type = type;
    }
    public Report(LocalDateTime date, String type, int total){
        this.date = date;
        this.type = type;
        this.total = total;
    }
    public LocalDateTime getDate(){ return date; }

    public String getReportType(){ return type; }

    public String getCountryName(){return  nameCountry;}

    public void setCountryName(String countryName){ this.nameCountry = countryName; }

    public void setTotal(int total){this.total = total;}
    public void setReportType(String type){ this.type = type; }
}
