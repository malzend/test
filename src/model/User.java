package model;

import java.sql.SQLException;

/**
 *
 */
public class User {


        private String userName;
        private String passWord;
        private int userID ;

    /**
     * User constructor is called to set it's parameters.<br>
     * @param userID set variable userID.<br>
     * @param userName set variable userName.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
        public User( int userID, String userName) throws SQLException {
            this.userName = userName;
            this.userID = userID;

        }

    /**
     * User constructor is called to set it's parameters.<br>
     * @param userID set variable userID.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */

        public User(int userID) throws SQLException {
            this.userID = userID;

        }

    /**
     * get for userName.<br>
     * @return the userName.<br>
     */
        public String getUserName() {
            return userName;
        }

    /**
     * get for v.<br>
     * @return the userID.<br>
     */
        public int getUserID() { return userID;}
    /**
     * set for userID.<br>
     * @param userID set userID.<br>
     */

        public void setUserID(int userID) {
            this.userID  = userID;
        }

    /**
     * set for userName.<br>
     * @param userName set userName.<br>
     */

        public void setUserName(String userName){
            this.userName = userName;
        }

}
