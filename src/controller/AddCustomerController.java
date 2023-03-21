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

import java.awt.*;
import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private javafx.scene.control.TextField addressTextBox;

    @FXML
    private ComboBox<String> countryTextBox;

    @FXML
    private javafx.scene.control.TextField customerIdTextBox;

    @FXML
    private javafx.scene.control.TextField customerTextBox;

    @FXML
    private ComboBox<FirstLevelDivision> firstLevelDivisionCombBox;

    @FXML
    private javafx.scene.control.TextField phoneNumberTextBox;

    @FXML
    private javafx.scene.control.TextField postalCodeTextBox;

    @FXML
   public void initialize(URL location, ResourceBundle resource) {
        try {
            CustomerQuery.customerPostAdd();
            customerIdTextBox.setText(String.valueOf(CustomerQuery.customerID()));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

     try{
        for(int i = 0; i < CustomerQuery.countryDATA().size();i++){
            countryTextBox.getItems().add( Model.getCountryName(i)); }}
     catch (SQLException throwables){
         throwables.printStackTrace();
     }
    }

    @FXML
    void addButtonAction(ActionEvent event) throws IOException, SQLException {
        String customerName = customerTextBox.getText();
        String address = addressTextBox.getText();
        String postalCode = addressTextBox.getText();
        String phoneNumber = phoneNumberTextBox.getText();
        int test = firstLevelDivisionCombBox.getValue().getDivisionID();
        int customerID = Integer.valueOf(customerIdTextBox.getText());

      //  String processResult =  CustomerQuery.customerAdd(customerName, address,postalCode, phoneNumber, test);

        String processResult = CustomerQuery.updateCustomer_New(customerID,customerName, address,postalCode, phoneNumber, test);

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

    @FXML
    void addressTextBoxAction(ActionEvent event) { }

    @FXML
    void coutryTextBoxAction(ActionEvent event) throws SQLException {
        Model.removeFilterDivision();
        SingleSelectionModel<String> country = countryTextBox.getSelectionModel();

        String countryID = country.getSelectedItem();
        Model.getCountryID(countryID);

        firstLevelDivisionCombBox.setItems(Model.getFilterDivision());
    }

    @FXML
    void customerIdTextBoxAction(ActionEvent event) throws SQLException { }

    @FXML
    void customerTectBoxAction(ActionEvent event) { }

    @FXML
    void firstLevelDivisionTextBoxAction(ActionEvent event) {
    }

    @FXML
    void phoneNumberTextBoxAction(ActionEvent event) { }

    @FXML
    void postalCodeTextBoxCtion(ActionEvent event) { }

    @FXML
    void exitButtonAction(ActionEvent event) throws IOException, SQLException {
        CustomerQuery.customerDeleteEmpty();
        Model.removeFilterDivision();
        Parent customerPage = FXMLLoader.load(getClass().getResource("/View/Customer.FXML"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(customerPage,731,400);
        Window.setScene(scene);
        Window.setTitle("Customer Page:");
        Window.show();
    }

}
