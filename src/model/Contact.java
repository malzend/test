package model;

/**
 *
 */
public class Contact {

    private int contactID = 100;
    private String contactName;
    private String email;

    /**
     *
     * @param contactID
     * @param contactName
     * @param email
     */

    public Contact(int contactID, String contactName , String email ){
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;

    }

    /**
     *
     * @param contactID
     * @param contactName
     */
   public Contact(int contactID, String contactName){
        this.contactID = contactID;
        this.contactName = contactName;

    }

     /**
     *
     * @return
     */
    public int getContactID(){ return contactID; }

    /**
     *
     * @return
     */
    public String getContactName(){
        return contactName;
    }

    /**
     *
     * @return
     */
    public String getEmail(){ return email;}

    /**
     *
     * @param contactID
     */
    public void setContactID(int contactID ){ this.contactID = contactID; }

    /**
     *
     * @param contactName
     */
    public void setContactName(String contactName){ this.contactName = contactName; }

    /**
     *
     * @param email
     */

    public void setEmail(String email){ this.email = email; }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return contactName;
    }
}
