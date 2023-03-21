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

//mport org.jetbrains.annotations.NotNull;

public class ModifyCustomerController implements Initializable {
    @FXML
    private Button addButton;

    @FXML
    private TextField addressTextBox;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private TextField customerIdTextBox;

    @FXML
    private TextField customerTextBox;

    @FXML
    private Button exitButton;

    @FXML
    private ComboBox<String> firstLevelDivisionCombBox;

    @FXML
    private TextField phoneNumberTextBox;

    @FXML
    private TextField postalCodeTextBox;

    static int userID = 0;

    @FXML
    public void initialize(URL location, ResourceBundle resource) {
        try {
        for(int i = 0; i < CustomerQuery.countryDATA().size();i++){

                countryComboBox.getItems().add(Model.getCountryName(i));}
            } catch (SQLException throwables) {
                throwables.printStackTrace();

        }

    }
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

    @FXML
    void exitButtonAction(ActionEvent event) throws IOException {
        if(!(firstLevelDivisionCombBox.getItems() == null)){ firstLevelDivisionCombBox.getItems().clear(); }
        if(!(countryComboBox.getItems() == null)){ countryComboBox.getItems().clear();}

        Model.removeFilterDivision();
        Parent userLogOut = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(userLogOut,731,400);
        Window.setScene(scene);
        Window.setTitle("Customer page:");
        Window.show();
    }

    @FXML
    void countryTextBoxAction(ActionEvent event) throws SQLException {

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

    @FXML
    void firstLevelDivisionTextBoxAction(ActionEvent event) {
    }

    @FXML
    void modifyButtonAction(ActionEvent event) throws SQLException {
        String customerName = customerTextBox.getText();
        String address = addressTextBox.getText();
        String postalCode = addressTextBox.getText();
        String phoneNumber = phoneNumberTextBox.getText();
        SingleSelectionModel<String> firstLevelDivision =  firstLevelDivisionCombBox.getSelectionModel();
        String test = firstLevelDivision.getSelectedItem();
        int dID = Model.getFirstLevelDivisionID(test);

        String processResult =  CustomerQuery.updateCustomer(userID, customerName, address,postalCode, phoneNumber);
        String whatHappend =  CustomerQuery.updateDivision(userID,dID);
        System.out.println(whatHappend);
        Alert alertType;
        if(processResult == "Success"){
            Model.modifyCustomer(userID,new Customer(userID , customerName,address,postalCode,phoneNumber, dID));
            alertType = new Alert(Alert.AlertType.CONFIRMATION);
            alertType.setTitle(" Modify record States");
            alertType.setHeaderText("Modify was successful");
            alertType.setContentText("You have Modify the selected customer record");
        } else {
            alertType = new Alert(Alert.AlertType.WARNING);
            alertType.setTitle("Modify record States");// line 2
            alertType.setHeaderText(" Modify was not successful");// line 3
            alertType.setContentText("You have not Modified the selected customer record");// line 4
        }
        alertType.show();
    }

    @FXML
    void phoneNumberTextBoxAction(ActionEvent event) { }
    @FXML
    void postalCodeTextBoxCtion(ActionEvent event) { }
    @FXML
    void addressTextBoxAction(ActionEvent event) { }
    @FXML
    public void customerTextBoxAction(ActionEvent actionEvent) { }
    @FXML
    public void customerIdTextBoxAction(ActionEvent actionEvent) { }

}