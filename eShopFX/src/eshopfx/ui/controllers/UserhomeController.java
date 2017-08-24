/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshopfx.ui.controllers;

import eshopfx.ui.ControlledScreen;
import eshopfx.MainApplication;
import eshopfx.data.RecipientModel;
import eshopfx.data.User;
import eshopfx.ui.ScreensController;
import eshopfx.services.CustomerService;
import eshopfx.services.RecipientService;
import eshopfx.ui.ScreenReference;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Parth
 */
public class UserhomeController implements Initializable, ControlledScreen
{
    ScreensController myController;
    
    @FXML
    Label currEmail;
    
    @FXML
    TextField firstName;
    
    @FXML
    TextField minit;
    
    @FXML
    TextField lastName;
    
    @FXML
    Label passwordFeedback;
    
    @FXML
    Label nameFeedback;
    
    @FXML
    PasswordField oldPassword;
    
    @FXML
    PasswordField newPassword;
    
    @FXML
    PasswordField confirmPassword;
    
    @FXML
    Button btnSaveChanges;
    
    @FXML
    Button btnRevertChanges;
    
    @FXML
    TableView<RecipientModel> recipientTable;    
    @FXML
    TableColumn<RecipientModel, String> firstNameCol;    
    @FXML
    TableColumn<RecipientModel, String> streetCol;    
    @FXML
    TableColumn<RecipientModel, String> cityCol;
    @FXML
    TableColumn<RecipientModel, String> stateCol;
    @FXML
    TableColumn<RecipientModel, String> zipCol;
    @FXML
    TableColumn<RecipientModel, String> countryCol;
    
    @FXML
    TextField recipNameField;     
    @FXML
    TextArea recipStreetField;    
    @FXML
    TextField recipCityField;
    @FXML
    TextField recipStateField;
    @FXML
    TextField recipZipField;
    @FXML
    TextField recipCountryField;
    @FXML
    Label recipientError;

      
    
    private User currUser;
    private CustomerService customerService = new CustomerService();
    private RecipientService recipientService = new RecipientService();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.currUser = MainApplication.getCurrentUser();
  
        currEmail.setText(currUser.getEmail());
        firstName.setText(currUser.getFirstName());
        minit.setText(currUser.getMiddleInitial());
        lastName.setText(currUser.getLastName());
        
        try
        {
            recipientService.populateRecipients(currUser);
        } catch (SQLException ex)
        {
            Logger.getLogger(UserhomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        streetCol.setCellValueFactory(new PropertyValueFactory<>("streetAddr"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        
        recipientTable.setItems(currUser.getRecipientData());
        
        recipientTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends RecipientModel> observable, RecipientModel oldValue, RecipientModel newValue) ->
        {
            if(recipientTable.getSelectionModel().getSelectedIndex() >= 0)
            {
                showRecipientDetails(newValue);
            }
        });
        
        recipNameField.addEventFilter(KeyEvent.KEY_TYPED, alphabeticStringValidation(35));
        recipStreetField.addEventFilter(KeyEvent.KEY_TYPED, alphaNumericValidation(50));
        recipCityField.addEventFilter(KeyEvent.KEY_TYPED, alphabeticValidation(15));
        recipStateField.addEventFilter(KeyEvent.KEY_TYPED, alphabeticValidation(2));
        recipZipField.addEventFilter(KeyEvent.KEY_TYPED, numericValidation(10));
        recipCountryField.addEventFilter(KeyEvent.KEY_TYPED, alphabeticValidation(2));
        
                
    }
    
    @FXML
    private void deleteAccount(ActionEvent event) throws SQLException
    {
        customerService.deleteCustomer(currUser.getEmail());
        myController.setScreen(ScreenReference.LOGIN_SCREEN_ID);
        
    }
    @FXML
    private void logOut(ActionEvent event)
    {
        MainApplication.setCurrentUser(null);
        myController.setScreen(ScreenReference.LOGIN_SCREEN_ID);
    }
           
    @FXML
    private void saveChanges(ActionEvent event) throws SQLException
    {
        if(verifyNameForm())
        {
            if(oldPassword.getText().isEmpty() || newPassword.getText().isEmpty() || confirmPassword.getText().isEmpty())
            {
                currUser.setFirstName(firstName.getText());
                currUser.setLastName(lastName.getText());
                currUser.setMiddleInitial(minit.getText());
                customerService.updateUser(currUser);
            }
            else
            {
                if(verifyPassword())
                {
                    currUser.setFirstName(firstName.getText());
                    currUser.setLastName(lastName.getText());
                    currUser.setMiddleInitial(minit.getText());
                    currUser.setPassword(newPassword.getText());
                    customerService.updateUser(currUser);
                }
            }
        }
    }
    
    @FXML
    private void revertChanges(ActionEvent event)
    {
        firstName.setText(currUser.getFirstName());
        minit.setText(currUser.getMiddleInitial());
        lastName.setText(currUser.getLastName());
        oldPassword.setText("");
        newPassword.setText("");
        confirmPassword.setText("");
    }
    
    @FXML
    private void handleDeleteRecipient() throws SQLException
    {
        int selectedIndex  = recipientTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex>=0)
        {
            if(selectedIndex==0)
            {
                showEmpty();
            }
            RecipientModel deletedRecipient = recipientTable.getItems().remove(selectedIndex);
            recipientService.deleteRecipient(deletedRecipient);
        }
    }
    
    @FXML
    private void handleEditRecipient()
    {
        toggleRecipientFields();
    }
    
    @FXML
    private void handleUpdateRecipient() throws SQLException
    {
        int selectedIndex = recipientTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex>0)
        {
            recipientTable.getItems().get(selectedIndex).setName(recipNameField.getText());
            recipientTable.getItems().get(selectedIndex).setStreetAddr(recipStreetField.getText());
            recipientTable.getItems().get(selectedIndex).setCity(recipCityField.getText());
            recipientTable.getItems().get(selectedIndex).setState(recipStateField.getText());
            recipientTable.getItems().get(selectedIndex).setCountry(recipCountryField.getText());
            recipientTable.refresh();
            toggleRecipientFields();
            recipientService.updateRecipient(recipientTable.getItems().get(selectedIndex));
        }
    }
    
    @FXML
    private void handleCreateNewRecipient() throws SQLException
    {
        recipientTable.getSelectionModel().clearSelection();
        recipNameField.setText("");
        recipStreetField.setText("");
        recipCityField.setText("");
        recipStateField.setText("");
        recipZipField.setText("");
        recipCountryField.setText("");
        toggleRecipientFields();
    }
    
    @FXML
    private void handleSaveNewRecipient() throws SQLException
    {
        RecipientModel newRecipient = new RecipientModel();
        if(verifyRecipient())
        {
            newRecipient.setName(recipNameField.getText());
            newRecipient.setStreetAddr(recipStreetField.getText());
            newRecipient.setCity(recipCityField.getText());
            newRecipient.setState(recipStateField.getText().toUpperCase());
            newRecipient.setZip(recipZipField.getText());
            newRecipient.setCountry(recipCountryField.getText().toUpperCase());
            newRecipient.setRecipientID(recipientService.addRecipient(currUser.getEmail(), newRecipient));
            
            recipientTable.getItems().add(newRecipient);
            recipientTable.refresh();
            
            toggleRecipientFields();
        }
        
    }
    
    private void showEmpty()
    {
        recipNameField.setText("");
        recipStreetField.setText("");
        recipCityField.setText("");
        recipStateField.setText("");
        recipZipField.setText("");
        recipCountryField.setText("");
    }
    
    private void showRecipientDetails(RecipientModel recipient)
    {
        recipNameField.setText(recipient.getName());
        recipStreetField.setText(recipient.getStreetAddr());
        recipCityField.setText(recipient.getCity());
        recipStateField.setText(recipient.getState());
        recipZipField.setText(recipient.getZip());
        recipCountryField.setText(recipient.getCountry());
    }
    
    private boolean verifyPassword()
    {
        if(!oldPassword.getText().equals(currUser.getPassword())) 
        {
            passwordFeedback.setText("Incorrect current password!");
            return false;
        } 
        else if(!newPassword.getText().equals(confirmPassword.getText()))
        {
            passwordFeedback.setText("Mismatching new passwords!");
            return false;
        } 
        else
        {
            return true;
        }
    }
    
    private boolean verifyNameForm()
    {
        if(firstName.getText() == null || minit.getText() == null || lastName.getText() == null)
        {
            nameFeedback.setText("Fields cannot be empty!");
            return false;
        }
        else if(minit.getText().length()>1)
        {
            nameFeedback.setText("Middle Initial field limited to one character.");
            return false;
        }
        else
        {
            return true;
        }
    }
    
    private void toggleRecipientFields()
    {
        recipNameField.setEditable(!recipNameField.isEditable());
        recipStreetField.setEditable(!recipStreetField.isEditable());
        recipCityField.setEditable(!recipCityField.isEditable());
        recipStateField.setEditable(!recipStateField.isEditable());
        recipZipField.setEditable(!recipZipField.isEditable());
        recipCountryField.setEditable(!recipCountryField.isEditable());
        if(recipNameField.isEditable())
        {
            recipNameField.requestFocus();
        }
        
    }
    
    private boolean verifyRecipient()
    {
        if(recipNameField.getText().isEmpty() || recipStreetField.getText().isEmpty() 
                || recipCityField.getText().isEmpty() || recipStateField.getText().isEmpty() 
                || recipZipField.getText().isEmpty() || recipCountryField.getText().isEmpty())
        {
            recipientError.setText("Fields cannot be empty!");
            return false;
        }
        else
        {
            return true;
        }
    }
    
    private EventHandler<KeyEvent> numericValidation(final Integer maxLength)
    {
        return (KeyEvent event) ->
        {
            TextField textField = (TextField) event.getSource();
            if(textField.getText().length() >= maxLength)
            {
                event.consume();
            }
            if(event.getCharacter().matches("[0-9-]"))
            {
                if(textField.getText().contains("-") && event.getCharacter().matches("[-]"))
                {
                    event.consume();
                }
                else if (textField.getText().length() == 0 && event.getCharacter().matches("[-]"))
                {
                    event.consume();
                }
            }
            else
            {
                event.consume();
            }
        };
    }
    
    private EventHandler<KeyEvent> alphabeticValidation(final Integer maxLength)
    {
        return (KeyEvent event) ->
        {
            TextField textField = (TextField) event.getSource();
            if(textField.getText().length() >= maxLength)
            {
                event.consume();
            }
            if(event.getCharacter().matches("[A-Za-z]"))
            {
            } else
            {
                event.consume();
            }
        };
    }
    private EventHandler<KeyEvent> alphabeticStringValidation(final Integer maxLength)
    {
        return (KeyEvent event) ->
        {
            TextField textField = (TextField) event.getSource();
            if(textField.getText().length() >= maxLength)
            {
                event.consume();
            }
            if(event.getCharacter().matches("[A-Za-z ]"))
            {
            } else
            {
                event.consume();
            }
        };
    }
    
    private EventHandler<KeyEvent> alphaNumericValidation(final Integer maxLength)
    {
        return (KeyEvent event) ->
        {
            TextArea textArea = (TextArea) event.getSource();
            if(textArea.getText().length() >= maxLength)
            {
                event.consume();
            }
            if(event.getCharacter().matches("[a-zA-Z0-9 ]"))
            {
            } else
            {
                event.consume();
            }
        };
    }

    @Override
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
    }

    
}
