package helper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC will do the following:
 * Establishes connection to the database. <br>
 * Open connection when the use request it to the database.<br>
 * Close connection when the use request it to the database.<br>
 */
public abstract class JDBC {

    /**
     * protocol vendor location dataBaseName are used to form the jdbUrl.<br>
     * driver is used to get the JDBC interface that will handle the communication between intellja IDE
     * useName and password are the the database retentions.
     */
    private static final String protocol = "JDBC";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String dataBaseName = "Client_schedule ";
    private static final String jdbcUrl = protocol + vendor + location + dataBaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String useName = "sqlUser";
    private static final String password = "Passw0rd!";
    /**
     * Connection manages connecting to a database, issuing queries and commands, and handling result sets obtained from the database.
     */
    public static Connection connection;

    /**
     * openConnection request connection to be open from the database and test it.
     */
    public static void openConnection() {


        try {
            Class.forName(driver);

            connection = DriverManager.getConnection(jdbcUrl, useName, password);
            System.out.println("Connect successful: ");


        } catch (Exception e) {
            System.out.println("Error: " + e);

        }
    }
    /**
     * closeConnection request connection to be close from the database and test it.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connect Closed: ");

        } catch (Exception e) {
            System.out.println("Error: " + e);

        }
    }
}
