package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogIn {

    public static boolean validateUser(String uName,String passWord) throws SQLException {

        try{
        String sql = "SELECT* FROM users WHERE User_Name = ? and Password = ?";
        PreparedStatement validate = JDBC.connection.prepareStatement(sql);
        validate.setString(1,uName);
        validate.setString(2, passWord);
        ResultSet resultSet = validate.executeQuery();
        while(resultSet.next()){
           // String userName = resultSet.getString(2);
           // String password = resultSet.getString(2);

            //if(userName == uName && passWord == password)
           // {
            return true;
           // }
           // else{
             //   continue;
            //}
        }
        }catch (SQLException e){
            System.out.println(e);
        }
      return false;
    }
}
