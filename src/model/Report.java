package model;

/**
 *
 */
public class Report {

    private String month;
    private String type;
    private int count;
    private String nameCountry;

    /**
     * Appointment constructor is called to set it's parameters.<br>
     * @param month set variable month.<br>
     * @param type set variable type.<br>
     */
    public Report(String month, String type){
        this.month = month;
        this.type = type;
    }

    /**
     * Appointment constructor is called to set it's parameters.<br>
     * @param date set variable date.<br>
     * @param type set variable type.<br>
     * @param count set variable count.<br>
     */
    public Report(String date, String type, int count){
        this.month = date;
        this.type = type;
        this.count = count;
    }

     /**
     * get for month.<br>
     * @return the month.<br>
     */
    public String getDate(){ return month; }

    /**
     * get for count.<br>
     * @return the count.<br>
     */
    public int getCount(){ return count; }

    /**
     * get for v.<br>
     * @return the type.<br>
     */
    public String getReportType(){ return type; }

    /**
     * get for nameCountry.<br>
     * @return the nameCountry.<br>
     */
    public String getCountryName(){return  nameCountry;}


    /**
     * set for nameCountry.<br>
     * @param nameCountry set nameCountry.<br>
     */
    public void setCountryName(String nameCountry){ this.nameCountry = nameCountry; }


    /**
     * set for count.<br>
     * @param count set count.<br>
     */
    public void setCount(int count){this.count = count;}


    /**
     * set for type.<br>
     * @param type set type.<br>
     */
    public void setReportType(String type){ this.type = type; }
}
