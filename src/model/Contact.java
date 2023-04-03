package model;

/**
 * This class Contact contains the methods, gets and sets, that specifies class variables<br>
 * via Utilizing the class constructor to access these methods.<br>
 * */
public class Contact {

    private int contactID = 100;
    private String contactName;
    private String email;

    /**
     * Contact constructor is called to set it's parameters.<br>
     * @param contactID set variable contactID.<br>
     * @param contactName set variable contactName.<br>
     * @param email set variable email.<br>
     */

    public Contact(int contactID, String contactName , String email ){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;

    }

    /**
     * Contact constructor is called to set it's parameters.<br>
     * @param contactID set variable contactID.<br>
     * @param contactName set variable contactName.<br>
     */
   public Contact(int contactID, String contactName){
        this.contactID = contactID;
        this.contactName = contactName;
    }
    /**
     * get for contactID.<br>
     * @return the contactID.<br>
     */
    public int getContactID(){ return contactID; }
    /**
     * get for contactName.<br>
     * @return the contactName.<br>
     */
    public String getContactName(){
        return contactName;
    }
    /**
     * get for contactID.<br>
     * @return the type.<br>
     */
    public String getEmail(){ return email;}
    /**
     * set for contactID.<br>
     * @param contactID set type.<br>
     */
    public void setContactID(int contactID ){ this.contactID = contactID; }
    /**
     * set for contactName.<br>
     * @param contactName set contactName.<br>
     */
    public void setContactName(String contactName){ this.contactName = contactName; }
    /**
     * set for email.<br>
     * @param email set email.<br>
     */
    public void setEmail(String email){ this.email = email; }
    /**
     * set for contactName.<br>
     * @return contactName set type.<br>
     */
    @Override
    public String toString(){
        return contactName;
    }
}
