package controller;

import helper.AppointmentQuery;
import helper.CustomerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.Model;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The CustomerController class will do the following:<br>
 * load the customer table with customer ID, Name, Address, Phone Number, Postal Code.<br>
 * Add a new customer record and modify them in the database.<br>
 * Deleting of a customer record form the database.<br>
 *
 */
public class CustomerController implements Initializable{
    /**
     * <br>
     * FXML id selectors <br>
     * <br>
     * selectors used to read or set form fields <br>
     */
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> address;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> phoneNumber;
    @FXML
    private TableColumn<Customer, String> postalCode;
    @FXML
    private TableColumn<Customer, String> Country;
    @FXML
    private TableColumn<Customer,String> First_Level_Division;

    /**
     * initialize will populate data to the customer table from the database.<br>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known. <br>
     * @param resource The resources used to localize the root object, or null if the root object was not localized.<br>
     */
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        try { customerTable.setItems(CustomerQuery.customerDataNew()); }
        catch (SQLException throwable) {throwable.printStackTrace(); }
        customerID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("CustomerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustomerName"));
        address.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<Customer, String>("PostalCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("PhoneNumber"));
        First_Level_Division.setCellValueFactory(new PropertyValueFactory<Customer,String>("DivisionName"));
        Country.setCellValueFactory(new PropertyValueFactory<Customer,String>("CountryName"));

    }

    /**
     * addCustomerAction will load the customer add page.<br>
     * @param event will add customer information when it's clicked.<br>
     * @throws IOException will throw an exception when the load meathead null.<br>
     */
   @FXML
    void addCustomerAction(ActionEvent event) throws IOException {

        Parent customerPage = FXMLLoader.load(getClass().getResource("/View/AddCustomer.FXML"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(customerPage);
        Window.setScene(scene);
        Window.setTitle("Customer Page:");
        Window.show();
    }
    /**
     * deleteCustomerAction will delete the customer table using the customerId.<br>
     * The customerID will be sent to the database via a sql query that is stored in helper.CustomerQuery.
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void deleteCustomerAction() throws SQLException {
        /**
         * selectedID is used to hold the customer row id column and then passed to CustomerQuery.customerDelete().<br>
         * An alert will be displayed to show if the delete was successful or not.<br>
         */
        try {
            int selectedID = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
           Customer selectedItem = customerTable.getSelectionModel().getSelectedItem();

            for (int i = 0; i < AppointmentQuery.appointmentData_new().size(); i++)
                if (selectedID == AppointmentQuery.appointmentData_new().get(i).getCustomerID()){
                    AppointmentQuery.appointmentDelete(AppointmentQuery.appointmentData_new().get(i).getAppointmentID());
                }
            System.out.print(selectedItem);
            String processResult = CustomerQuery.customerDelete(selectedID);
            Alert alertType;
            if (processResult == "Success") {
                alertType = new Alert(Alert.AlertType.CONFIRMATION);
                alertType.setTitle("Deleting record states");
                alertType.setHeaderText("Deleting was successful");
                alertType.setContentText("You have Delete the selected customer record");
            } else {
                alertType = new Alert(Alert.AlertType.WARNING);
                alertType.setTitle("Deleting record states");
                alertType.setHeaderText(" Delete was not successful");
                alertType.setContentText("You have not Delete the selected customer record");
            }
            alertType.show();


        }
        /**
         * Catch is used to display an alert if the delete button is clicked with out selecting a row.<br>
         */
        catch (NullPointerException var7){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No customer record is selected:");
            alert.setContentText("Please select a customer to delete.");
            alert.show();

        }
        /**
         * Date is refreshed after the delete is done.<br>
         */
        customerTable.setItems(CustomerQuery.customerDataNew());
        customerID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("CustomerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("CustomerName"));
        address.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<Customer, String>("PostalCode"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("PhoneNumber"));
        First_Level_Division.setCellValueFactory(new PropertyValueFactory<Customer,String>("DivisionName"));
        Country.setCellValueFactory(new PropertyValueFactory<Customer,String>("CountryName"));

    }
    /**
     * exitAction loads the page for user.<br>
     * @param event on button will exit the program <br>
     * @throws IOException will throw an exception when the load meathead is not
     */
    @FXML
    void exitAction(ActionEvent event) throws IOException {
        Parent customerPage = FXMLLoader.load(getClass().getResource("/View/User.FXML"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(customerPage);
        Window.setScene(scene);
        Window.setTitle("Customer Page:");
        Window.show();
    }
    /**
     * makeAppointmentAction will load the AddAppointment page to add a new appointments for the customer.<br>
     * @param event will load the appointment add page.<br>
     * @throws IOException will throw an exception when the load meathead null.<br>
     */
    @FXML
    void makeAppointmentAction(ActionEvent event) throws IOException {
        try {
            Stage stage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddAppointment.fxml"));
            loader.load();
            addAppointmentController customerIDToAppointment = loader.getController();

            customerIDToAppointment.setColumnUserAndCustomer(customerTable.getSelectionModel().getSelectedItem().getCustomerID());
            stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException var7){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No customer record is selected:");
            alert.setContentText("Please select a customer inorder to make an appointment.");
            alert.show();

        }
    }
    /**
     * modifyCustomerAction load the modify page and send the customer info.<br>
     * @param event load modify page and send the customer information when it's clicked,<br>
     * @throws IOException will throw an exception when the load meathead null.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void modifyCustomerAction(ActionEvent event) throws IOException, SQLException {
        try {
            Stage stage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            loader.load();
            ModifyCustomerController customerController = loader.getController();
            customerController.setCustomer(customerTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException var7){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No customer record isd  selected:");
            alert.setContentText("Please select a customer inorder to modify.");alert.show();

        }
    }
}

