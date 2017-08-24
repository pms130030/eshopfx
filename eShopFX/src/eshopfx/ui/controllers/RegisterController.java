/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshopfx.ui.controllers;

import eshopfx.ui.ControlledScreen;
import eshopfx.ui.ScreensController;
import eshopfx.services.LoginService;
import eshopfx.ui.ScreenReference;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Parth
 */
public class RegisterController implements Initializable, ControlledScreen
{
    ScreensController myController;

    @FXML
    TextField email;
    
    @FXML
    PasswordField password;
    
    @FXML
    PasswordField confirmPassword;
    
    @FXML
    TextField firstName;
    
    @FXML
    TextField minit;
    
    @FXML
    TextField lastName;
    
    @FXML
    Button register;
    
    @FXML
    Button cancel;
    
    @FXML
    Label formError;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        password.setFocusTraversable(true);
        confirmPassword.setFocusTraversable(true);
    }
    
    @FXML
    private void register(ActionEvent event) throws SQLException
    {
        if(verifyForm())
        {
            LoginService loginService = new LoginService();
            String out  = loginService.addNewCustomer(email.getText(), password.getText(), firstName.getText(), minit.getText(), lastName.getText());
            if(out==null)
            {
                formError.setText("Success!");
                myController.setScreen(ScreenReference.LOGIN_SCREEN_ID);
            }
            else
            {
                formError.setText(out);
            }
        }
    }
    
    @FXML
    private void cancel(ActionEvent event)
    {
        myController.setScreen(ScreenReference.LOGIN_SCREEN_ID);
    }
    
    private boolean verifyForm()
    {
        
        if(!(email.getText().contains(".")) || !(email.getText().contains("@")))
        {
            formError.setText("Invalid Email");
            return false;
        }
        else if(!password.getText().equals(confirmPassword.getText()))
        {
            formError.setText("Mismatching passwords");
            return false;
        } else if(minit.getText().length()>1)
        {
            formError.setText("Middle inital can only be one character!");
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
    }
    
}
