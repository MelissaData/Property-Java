package melissadata.property.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import melissadata.property.model.PropertyTransaction;
import melissadata.property.Main;

public class PropertyController  {

    private Main main;

    private PropertyTransaction Transaction;

    @FXML
    private Button buttonSend;
    @FXML
    private Button buttonClear;
    @FXML
    private TabPane tabPane;
    private final int RESPONSE_TAB = 1;

    @FXML
    private TextField inputLicenseKeyText;
    @FXML
    private TextField inputFreeFormText;
    @FXML
    private TextField inputCountyFIPSText;
    @FXML
    private TextField inputAPNText;
    @FXML
    private TextField inputAccountText;
    @FXML
    private TextField inputMAKText;


    @FXML
    private TextField inputAddressKeyText;
    @FXML
    private TextField inputTransactionIDText;

    @FXML
    private TextArea RequestTextArea;
    @FXML
    private TextArea ResponseTextArea;

    @FXML
    private RadioButton jsonResponseFormatRadio;
    @FXML
    private RadioButton xmlResponseFormatRadio;

    @FXML
    private RadioButton methodPropertyButton;
    @FXML
    private RadioButton methodHomesByOwnerButton;
    @FXML
    private RadioButton methodDeedsButton;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public PropertyController() {
        Transaction = new PropertyTransaction();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        initializeFormatRadioButtons();
        initializeTextFields();
        sendButtonAction();
        clearButtonAction();
        updateRequestText();
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * //@param mainApp
     */
    public void setMainApp(Main main) {
        this.main = main;
    }

    public void sendButtonAction() {
        buttonSend.setOnAction((event) -> {
            ResponseTextArea.setText(Transaction.processTransaction(RequestTextArea.getText()));
            tabPane.getSelectionModel().select(RESPONSE_TAB);
        });
    }

    public void clearButtonAction(){
        buttonClear.setOnAction((event) -> {
            inputFreeFormText.clear();
            inputCountyFIPSText.clear();
            inputAPNText.clear();
            inputAccountText.clear();
            inputMAKText.clear();
            inputAddressKeyText.clear();
            inputTransactionIDText.clear();
        });
    }

    public void initializeTextFields(){
        inputLicenseKeyText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setIdentNumber(newvalue);
            updateRequestText();
        });

        inputFreeFormText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setFreeForm(newvalue);
            updateRequestText();
        });

        inputCountyFIPSText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setCountyFIPS(newvalue);
            updateRequestText();
        });

        inputAPNText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAPN(newvalue);
            updateRequestText();
        });


        inputAccountText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAccount(newvalue);
            updateRequestText();
        });

        inputMAKText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setMAK(newvalue);
            updateRequestText();
        });

        inputAddressKeyText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAddressKey(newvalue);
            updateRequestText();

        });

        inputTransactionIDText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setTransactionID(newvalue);
            updateRequestText();
        });

    }

    private void initializeFormatRadioButtons(){
        jsonResponseFormatRadio.setOnAction((event) -> {
            Transaction.setFormat("JSON");
            xmlResponseFormatRadio.setSelected(false);
            updateRequestText();
        });

        xmlResponseFormatRadio.setOnAction((event) -> {
            Transaction.setFormat("XML");
            jsonResponseFormatRadio.setSelected(false);
            updateRequestText();
        });

        methodDeedsButton.setOnAction((event) -> {
           Transaction.setEndpoint("https://property.melissadata.net/v4/WEB/LookupDeeds?");
           methodHomesByOwnerButton.setSelected(false);
           methodPropertyButton.setSelected(false);
           updateRequestText();
        });

        methodHomesByOwnerButton.setOnAction((event) -> {
            Transaction.setEndpoint("https://property.melissadata.net/v4/WEB/LookupHomesByOwner?");
            methodPropertyButton.setSelected(false);
            methodDeedsButton.setSelected(false);
            updateRequestText();
        });

        methodPropertyButton.setOnAction((event) -> {
            Transaction.setEndpoint("https://property.melissadata.net/v4/WEB/LookupProperty?");
            methodHomesByOwnerButton.setSelected(false);
            methodDeedsButton.setSelected(false);
            updateRequestText();
        });
    }


    private void updateRequestText(){
        RequestTextArea.setText(Transaction.generateRequestString());
    }
}
