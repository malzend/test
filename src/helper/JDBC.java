package helper;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class JDBC {


    private static final String protocol = "JDBC";
    private static final String vender  = ":mysql:";
    private static final String location = "//localhost/";
    private static final String dataBaseName = "Client_schedule ";
    private static final String jdbcUrl = protocol + vender + location + dataBaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String useName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;


    public static void openConnection() {


        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(jdbcUrl, useName, password);
            System.out.println("Connect successful: ");


        } catch (Exception e) {
            System.out.println("Error: " + e);

        }
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connect Closed: ");

        } catch (Exception e) {
            System.out.println("Error: " + e);

        }
    }
}
