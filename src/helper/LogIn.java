package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * LogIn will do the following:<br>
 * The credentials that are given in the login page will be used to validate if the user are in <br>
 * database or not.
 */
public class LogIn {
    /**
     * validateUser takes two permeates of string to validate user info
     * @param uName set User_Name in sql query.<br>
     * @param passWord set Password in sql query.<br>
     * @return true if user name and password are in the database or false if not
     */
    public static boolean validateUser(String uName,String passWord) {

        try{
        String sql = "SELECT* FROM users WHERE User_Name = ? and Password = ?";
        PreparedStatement validate = JDBC.connection.prepareStatement(sql);
        validate.setString(1,uName);
        validate.setString(2, passWord);
        ResultSet resultSet = validate.executeQuery();
        while(resultSet.next()){
            return true;
        }
        }catch (SQLException e){
            System.out.println(e);
        }
      return false;
    }
}
