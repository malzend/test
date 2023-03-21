package model;

import java.sql.SQLException;

public class User {


        private String userName;
        private String passWord;
        private int userID ;

        public User( int userID, String userName) throws SQLException {
            this.userName = userName;
            this.userID = userID;

        }
        public User(int userID) throws SQLException {
            this.userID = userID;

        }


        public String getUserName() {
            return userName;
        }


        public int getUserID() { return userID;}

        public void setUserID(String uName) {
            this.userID  = userID;
        }


        public void setUserName(String userName){
            this.userName = userName;
        }
public int toInteger(){ return userID; }

}
