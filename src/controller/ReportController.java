package controller;

import helper.AppointmentQuery;
import helper.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import model.Country;
import model.Model;
import model.Report;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *  ReportController will do the following:<br>
 *  Filter appointments by type and month.<br>
 *  Filter appointment by contact.<br>
 *  Get total customer in each country.<br>
 */
public class ReportController implements Initializable {
    /**
     * <br>
     * FXML id selectors <br>
     * <br>
     * selectors used to read or set form fields <br>
     */
    @FXML
    private ScrollPane panel;
    @FXML
    private ComboBox<String> contact;
    @FXML
    private Text typeLabel;
    @FXML
    private Text monthLabel;
    @FXML
    private Text contactLabel;
    @FXML
    private Text customerLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label totalCustomerLabel;
    @FXML
    private TextField totalCustomers;
    @FXML
    private TextField countryNameField ;
    @FXML
    private TextField totalCustomerField;
    @FXML
    private Button Go;
    @FXML
    private Button Go2;
    @FXML
    private Button Go3;
    @FXML
    private ComboBox<String> month2;
    @FXML
    private ComboBox<Country> countryName;
    @FXML
    private ComboBox<String> type;
    @FXML
    private TableColumn<Appointment, Integer> appointmentID;
    @FXML
    private TableColumn<Report, String> month1;
    @FXML
    private TableColumn<Appointment, Integer> customerID;
    @FXML
    private TableColumn<Appointment, String> description;
    @FXML
    private TableColumn<Appointment, Integer> end;
    @FXML
    private TableColumn<Appointment, Integer> start;
    @FXML
    private TableColumn<Appointment, String> title;
    @FXML
    private TableView<Report> totalNumberTypeMonth;
    @FXML
    private TableView<Appointment> contactScheduleReport;
    @FXML
    private TableColumn<Report, Integer> total;
    @FXML
    private TableColumn<Report, String> type1;
    @FXML
    private TableColumn<Appointment, String> type2;

    ObservableList<String> monthComboBox = FXCollections.observableArrayList();
    ObservableList<String> typeComboBox = FXCollections.observableArrayList();
    ObservableList<String> contactComboBox = FXCollections.observableArrayList();

    /**
     * initialize will load the contactComboBox and countryName date from the database.<br>
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            for(int i = 0; i <AppointmentQuery.loadContact().size(); i++) {

                contactComboBox.add(AppointmentQuery.loadContact().get(i).getContactName());}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        contact.setItems(contactComboBox);

        try {
            countryName.setItems(CustomerQuery.countryDATA());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * ScheduleForEachContactButton will display the appropriate contents the is used to display <br>
     * appointments scheduled for each contact.<br>
     * @throws SQLException
     */

    @FXML
    void ScheduleForEachContactButton() throws SQLException {
        type.setVisible(false);
        month2.setVisible(true);
        totalCustomers.setVisible(false);
        customerLabel.setVisible(false);
        contact.setVisible(true);
        Go.setVisible(false);
        typeLabel.setVisible(false);
        monthLabel.setVisible(false);
        contactLabel.setVisible(true);
        countryLabel.setVisible(false);
        totalCustomerLabel.setVisible(false);


        panel.setVisible(true);
        totalNumberTypeMonth.setVisible(false);
        contactScheduleReport.setVisible(true);
        countryName.setVisible(false);
        countryNameField.setVisible(false) ;
        totalCustomerField.setVisible(false);


        Go2.setVisible(true);
        Go3.setVisible(false);
        month2.setVisible(false);
        type.setVisible(false);

        contactScheduleReport.setItems(AppointmentQuery.appointmentData_new());
        appointmentID.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("AppointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<Appointment,String>("Title"));
        type2.setCellValueFactory(new PropertyValueFactory<Appointment,String>("Type"));
        description.setCellValueFactory(new PropertyValueFactory<Appointment,String>("Description"));
        start.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("StartTimeDate"));
        end.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("EndTimeDate"));
        customerID.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("CustomerID"));

    }

    /**
     * exitButtonAction used to load the User page.
     * @param event on button will exit the program <br>
     * @throws IOException
     */

    @FXML
    void exitButtonAction(ActionEvent event) throws IOException {
        Parent customerPage = FXMLLoader.load(getClass().getResource("/View/User.FXML"));
        Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();


        Scene scene = new Scene(customerPage,600,400);
        Window.setScene(scene);
        Window.setTitle("Report Page:");
        Window.show();
    }

    /**
     * totalCustomerAction filters total customer by the type and the month.<br>
     * totalCustomerAction will display the appropriate contents the is used to display <br>
     * appointments scheduled for each contact.<br>
     * @throws SQLException
     */

    @FXML
    void totalCustomerAction() throws SQLException {

        monthComboBox.addAll("1","2", "3", "4", "5", "6", "0", "8", "9", "10", "11", "12");
        month2.setItems(monthComboBox);

        for(int i = 0; i < AppointmentQuery.appointmentData_new().size();i++){
            if(typeComboBox.contains(AppointmentQuery.appointmentData_new().get(i).getType())){

            }
            else{ typeComboBox.add(AppointmentQuery.appointmentData_new().get(i).getType());
            }
        }
        type.setItems(typeComboBox);
        totalNumberTypeMonth.getItems().clear();
        Model.deleteReport();
        AppointmentQuery.loadTypeMonth();

        contactScheduleReport.setVisible(false);
        totalNumberTypeMonth.setVisible(true);
        Go.setVisible(true);
        contactLabel.setVisible(false);
        monthLabel.setVisible(true);
        typeLabel.setVisible(true);
        month2.setVisible(true);
        type.setVisible(true);
        Go2.setVisible(false);
        Go3.setVisible(false);
        contactScheduleReport.setVisible(false);
        contact.setVisible(false);
        countryName.setVisible(false);
        countryNameField.setVisible(false);
        countryLabel.setVisible(false);
        totalCustomerLabel.setVisible(false);
        totalCustomerField.setVisible(false);

        panel.setVisible(false);

        totalNumberTypeMonth.setItems(Model.getReport());
        type1.setCellValueFactory(new PropertyValueFactory<Report, String>("Date"));
        month1.setCellValueFactory(new PropertyValueFactory<Report, String>("ReportType"));
    }

    /**
     * whatToDoButtonAction filter the total number of customers in each country. <br>
     * will display the appropriate contents the is used to display <br>
     * appointments scheduled for each contact.<br>
     */

    @FXML
    void whatToDoButtonAction() {

        try {
            AppointmentQuery.loadTypeMonth();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        contactScheduleReport.setVisible(false);
        totalNumberTypeMonth.setVisible(false);
        type.setVisible(false);

        contact.setVisible(false);
        month2.setVisible(false);
        countryName.setVisible(true);
        totalCustomerField.setVisible(true);
        countryNameField.setVisible(true);

        customerLabel.setVisible(false);
        typeLabel.setVisible(false);
        monthLabel.setVisible(false);
        contactLabel.setVisible(false);
        countryLabel.setVisible(true);
        totalCustomerLabel.setVisible(true);

        Go.setVisible(false);
        Go2.setVisible(false);
        Go3.setVisible(true);
        panel.setVisible(false);


    }



    @FXML
    void totalNumberTypeMonthAction() {
    }

    /**
     * GoAction calls the method joinTypeMonth from AppointmentQuery and passed type and month.<br>
     * The for loop add the total customer by type and month and displays the output in a text field.
     * @throws SQLException
     */
    public void GoAction() throws SQLException {
        int countType = 0;
        for ( int i = 0; i<totalNumberTypeMonth.getItems().size(); i++) {
            totalNumberTypeMonth.getItems().clear();
            Model.deleteReport();
        }

        String typeSelection = type.getSelectionModel().getSelectedItem();
        String monthSelection =month2.getSelectionModel().getSelectedItem();


        AppointmentQuery.joinTypeMonth(monthSelection,typeSelection);
        for ( int i = 0; i<Model.getReport().size(); i++) {
            countType++;
        }
        totalNumberTypeMonth.setItems(Model.getReport());
        type1.setCellValueFactory(new PropertyValueFactory<Report, String>("Date"));
        month1.setCellValueFactory(new PropertyValueFactory<Report, String>("ReportType"));
        totalCustomers.setText(String.valueOf(countType));

    }

    /**
     * go2ActionButton method get the contact name and then compares it in if condition.<br>
     * If the condition is true then it returns the appointments accosted with the contact.
     *
     * @throws SQLException
     */
    @FXML
    void go2ActionButton() throws SQLException {
        contactScheduleReport.getItems().clear();

        int countType = 0;
        String contactName = contact.getSelectionModel().getSelectedItem();
        if(contactName.equals("Anika Costa") ){
            countType = 1;
        }
        else if(contactName.equals("Daniel Garcia")){
            countType = 2;
        }
        else if(contactName.equals("Li Lee")){
            countType = 3;
        }

        contactScheduleReport.setItems(AppointmentQuery.customerContact(countType));
        appointmentID.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("AppointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<Appointment,String>("Title"));
        type2.setCellValueFactory(new PropertyValueFactory<Appointment,String>("Type"));
        description.setCellValueFactory(new PropertyValueFactory<Appointment,String>("Description"));
        start.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("StartTimeDate"));
        end.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("EndTimeDate"));
        customerID.setCellValueFactory(new PropertyValueFactory<Appointment,Integer>("CustomerID"));

    }

    /**
     * go3Action method filters the total customers in each country.<br>
     * The loop will loop through each customer data and return the county base on division ID.<br>
     * @throws SQLException
     */
    @FXML
    void go3Action() throws SQLException {
        Model.removeFilterDivision();

        int countCustomer = 0;
        String countryN = countryName.getValue().getCountry();
        Model.getCountryID(countryN);

        for(int i = 0; i <CustomerQuery.customerDataNew().size(); i++){
            for(int b = 0; b <Model.getFilterDivision().size(); b++){
                if(CustomerQuery.customerDataNew().get(i).getDivisionID() == Model.getFilterDivision().get(b).getDivisionID()){
                    countCustomer++;
                }

            }}

        countryNameField.setText(countryN);
        totalCustomerField.setText(String.valueOf(countCustomer));
        Model.removeFilterDivision();
    }

}
