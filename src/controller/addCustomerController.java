package controller;

import helper.CustomerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.FirstLevelDivision;
import model.Model;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * AddCustomerController will do the following:
 * Allow the user to add customer information.
 * Store the customer record in the database
 * Allow the user to exit the page.
 */
public class addCustomerController implements Initializable {
    /**
     * <br>
     * FXML id selectors <br>
     * <br>
     * selectors used to read or set form fields <br>
     */
    @FXML
    private javafx.scene.control.TextField addressTextBox;

    @FXML
    private ComboBox<String> countryTextBox;

    @FXML
    private javafx.scene.control.TextField customerTextBox;

    @FXML
    private ComboBox<FirstLevelDivision> firstLevelDivisionCombBox;

    @FXML
    private javafx.scene.control.TextField phoneNumberTextBox;

    @FXML
    private javafx.scene.control.TextField postalCodeTextBox;

    /**
     * initialize will populate the countyTextBox date form the dataBase<br>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known. <br>
     * @param resource The resources used to localize the root object, or null if the root object was not localized.<br>
     */
    @FXML
    public void initialize(URL location, ResourceBundle resource) {


        try{
            for(int i = 0; i < CustomerQuery.countryDATA().size();i++){
                countryTextBox.getItems().add( Model.getCountryName(i)); }}
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /**
     * addButtonAction will add a new user with the given input from the user.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void addButtonAction() throws SQLException {
        String customerName = customerTextBox.getText();
        String address = addressTextBox.getText();
        String postalCode = postalCodeTextBox.getText();
        String phoneNumber = phoneNumberTextBox.getText();
        int test = firstLevelDivisionCombBox.getValue().getDivisionID();

        String processResult =  CustomerQuery.customerAdd(customerName, address,postalCode, phoneNumber, test);
        /**
         * the if condition will check if the processResult is ture or false.<br>
         * if false then it will display not successful if ture it will display successful.<br>
         * */
        if(processResult.equals("Success") ){
            Alert alertType= new Alert(Alert.AlertType.CONFIRMATION);
            alertType.setTitle("Insert record states");
            alertType.setHeaderText("Insert was successful");
            alertType.setContentText("You have Inserted a new customer record");
            alertType.show();
        } else {
            Alert alertType=new Alert(Alert.AlertType.WARNING);
            alertType.setTitle("Insert record states");
            alertType.setHeaderText("Insert was not successful");
            alertType.setContentText("The new customer Insertion failed");
            alertType.show();
        }
    }
    /**
     * countryTextBoxAction returns the user selection of a country and stores it in a variables.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void countryTextBoxAction() throws SQLException {
        Model.removeFilterDivision();
        SingleSelectionModel<String> country = countryTextBox.getSelectionModel();

        String countryID = country.getSelectedItem();
        Model.getCountryID(countryID);

        firstLevelDivisionCombBox.setItems(Model.getFilterDivision());
    }

    /**
     * exitButtonAction will load the User.fxml page.<br>
     * @param event on button will exit the program <br>
     * @throws IOException will throw an exception when the load meathead is not.<br>
     *
     */
    @FXML
    void exitButtonAction(ActionEvent event) throws IOException {
        Model.removeFilterDivision();
        Parent customerPage = FXMLLoader.load(getClass().getResource("/View/Customer.FXML"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(customerPage);
        Window.setScene(scene);
        Window.setTitle("Customer Page:");
        Window.show();
    }

}
