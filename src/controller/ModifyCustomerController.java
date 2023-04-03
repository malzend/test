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
import javafx.stage.Stage;
import model.Customer;
import model.Model;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

/**
 * ModifyCustomerController will do the following:
 * Get the selected ID of the customer all it's info.<br>
 * Allow the user to modify the customer info. <br>
 * The information is then saved to the database when the button is clicked.<br>
 * Another button will be used to exit the modify page to the customer page.<br>
 */
public class ModifyCustomerController implements Initializable {
    /**
     * <br>
     * FXML id selectors <br>
     * <br>
     * selectors used to read or set form fields <br>
     */
    @FXML
    private TextField addressTextBox;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private TextField customerIdTextBox;

    @FXML
    private TextField customerTextBox;

    @FXML
    private ComboBox<String> firstLevelDivisionCombBox;

    @FXML
    private TextField phoneNumberTextBox;

    @FXML
    private TextField postalCodeTextBox;
    /**
     * used to track user id.<br>
     */
    static int userID = 0;

    /**
     * initialize will populate the data of country names and store it in the combo box. <br>
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known. <br>
     * @param resource The resources used to localize the root object, or null if the root object was not localized.<br>
     */
    @FXML
    public void initialize(URL location, ResourceBundle resource) {
        try {
        for(int i = 0; i < CustomerQuery.countryDATA().size();i++){

                countryComboBox.getItems().add(Model.getCountryName(i));}
            } catch (SQLException throwables) {
                throwables.printStackTrace();

        }

    }

    /**
     * setCustomer will get the selected Customer ID and it's relevant information to be displayed in it's <br>
     * appropriate fields.<br>
     * @param customer set the Customer informant.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    public void setCustomer( Customer customer) throws SQLException {
        firstLevelDivisionCombBox.getItems().clear();
        firstLevelDivisionCombBox.setValue(Model.customerFirstDivisionName(customer.getDivisionID()));
        countryComboBox.setValue(Model.getCountry_Name(Model.countriesFirstDivision(customer.getDivisionID())));
        firstLevelDivisionCombBox.getValue();
        customerTextBox.setText(customer.getCustomerName());
        addressTextBox.setText(customer.getAddress());
        postalCodeTextBox.setText(customer.getPostalCode());
        phoneNumberTextBox.setText(customer.getPhoneNumber());
        customerIdTextBox.setText(String.valueOf(customer.getCustomerID()));
        userID = parseInt(customerIdTextBox.getText());

    }

    /**
     * exitButtonAction will load the Customer page once the user clicks it.<br>
     * @param event on button will exit the program <br>
     * @throws IOException will throw an exception when the load meathead is not
     */

    @FXML
    void exitButtonAction(ActionEvent event) throws IOException {
        /**
         * if condition is not true then the list will be cleared.<br>
         * This is used to avoid and duplicate int the combo box.<br>
         */
        if(!(firstLevelDivisionCombBox.getItems() == null)){ firstLevelDivisionCombBox.getItems().clear(); }
        if(!(countryComboBox.getItems() == null)){ countryComboBox.getItems().clear();}

        Model.removeFilterDivision();
        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut);
        Window.setScene(scene);
        Window.setTitle("Customer page:");
        Window.show();
    }

    /**
     * CountyTextBoxAction returns the user selection of a country and stores it in a variables.<br>
     * @throws SQLException when an invalid query process accrue.<br>
     */
    @FXML
    void countryTextBoxAction() throws SQLException {

        firstLevelDivisionCombBox.getItems().clear();
        Model.removeFilterDivision();

        SingleSelectionModel<String> country = countryComboBox.getSelectionModel();
        String countryID = country.getSelectedItem();
        if (countryID != null){
        Model.getCountryID(countryID);

        int filter = Model.getFilterDivision().size();
        for(int i = 0; i < filter;i++){

            firstLevelDivisionCombBox.getItems().add(
                    Model.getFilterDivision().get(i).getDivisionName());
        }}
    }
    /**
     * modifyButtonAction will allow the user to add new information.<br>
     * @throws SQLException when an invalid query process accrue.<br>

     */
    @FXML
    void modifyButtonAction( )throws SQLException {
        /**
         * The text fields will get the text to the following variables and store it.<br>
         */
        String customerName = customerTextBox.getText();
        String address = addressTextBox.getText();
        String postalCode = postalCodeTextBox.getText();
        String phoneNumber = phoneNumberTextBox.getText();
        SingleSelectionModel<String> firstLevelDivision =  firstLevelDivisionCombBox.getSelectionModel();
        String test = firstLevelDivision.getSelectedItem();
        int dID = Model.getFirstLevelDivisionID(test);

        String processResult =  CustomerQuery.updateCustomer(userID, customerName, address,postalCode, phoneNumber);
        String whatHappend =  CustomerQuery.updateDivision(userID,dID);
        System.out.println(whatHappend);
        Alert alertType;
        /**
         * the if condition will check if the processResult is ture or false.<br>
         * if false then it will display not successful if ture it will display successful.<br>
         */
        if(processResult == "Success"){
            //Model.modifyCustomer(userID,new Customer(userID , customerName,address,postalCode,phoneNumber, dID));
            alertType = new Alert(Alert.AlertType.CONFIRMATION);
            alertType.setTitle(" Modify record States");
            alertType.setHeaderText("Modify was successful");
            alertType.setContentText("You have Modify the selected customer record");
        } else {
            alertType = new Alert(Alert.AlertType.WARNING);
            alertType.setTitle("Modify record States");
            alertType.setHeaderText(" Modify was not successful");
            alertType.setContentText("You have not Modified the selected customer record");
        }
        alertType.show();
    }


}