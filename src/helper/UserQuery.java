package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserQuery will do the following:<br>
 * Load the user Id when the application is loaded.<br>
 * User name is used to compare to the list provide of users and return their ID's if<br>
 * their is a match.
 *
 */

public class UserQuery {
    /**
     * userData loads user data from the database using a select Query.<br>
     * @return an ObservableList of User type.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public static ObservableList<User> userData() throws SQLException {
        ObservableList<User> list = FXCollections.observableArrayList();
        String customerData = "SELECT *FROM users";
        PreparedStatement sl = JDBC.connection.prepareStatement(customerData);
        ResultSet rl = sl.executeQuery();
        while (rl.next()) {

            int Appointment_ID = rl.getInt("User_ID");

            list.add(new User(Appointment_ID));

        }
        return list;
    }

    /**
     * getUser a method that is used to return user id.<br>
     * @param name set Name in the if condition.<br>
     * @return user id if the if condition is true else 0.<br>
     */
    public static int getUser(String name){
        for(User u: Model.getUser()){
            if(u.getUserName().equals(name))
                return  u.getUserID();
        }
        return  0;
    }

}
