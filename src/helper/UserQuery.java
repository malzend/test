package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Model;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserQuery {

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

    public static int getUser(String name){
        for(User u: Model.getUser()){
            if(u.getUserName().equals(name))
                return  u.getUserID();
        }
        return  0;
    }
    public static int getUserID(int id){
        for(User u: Model.getUser()){
            if(u.getUserID() == id)
                return  u.getUserID();
        }
        return  0;
    }
}
