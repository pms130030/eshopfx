/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshopfx.ui.controllers;

import eshopfx.ui.ControlledScreen;
import eshopfx.MainApplication;
import eshopfx.data.User;
import eshopfx.ui.ScreensController;
import eshopfx.services.LoginService;
import eshopfx.ui.ScreenReference;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Parth
 */
public class LoginController implements Initializable, ControlledScreen
{
    ScreensController myController;
    
    @FXML
    TextField email;
    
    @FXML
    PasswordField password;
    
    @FXML
    Label errorMessage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //testing 
        email.setText("bstrangwood0@linkedin.com");
        password.setText("NsmiPkv");
        
        password.setFocusTraversable(true);
    } 
    
    @FXML
    private void login(ActionEvent event) throws SQLException
    {
        LoginService loginService = new LoginService();
        User currUser = loginService.authenticate(email.getText(), password.getText());
        if(currUser!=null)
        {  
            MainApplication.setCurrentUser(currUser);
            email.setText("");
            password.setText("");
            myController.loadScreen(ScreenReference.USERHOME_SCREEN_ID, ScreenReference.USERHOME_SCREEN_FILE);
            myController.setScreen(ScreenReference.USERHOME_SCREEN_ID);
        }
    }
    
    @FXML
    private void register(ActionEvent event)
    {
        myController.setScreen(ScreenReference.REGISTER_SCREEN_ID);
    }
    
    
       

    @Override
    public void setScreenParent(ScreensController screenParent)
    {
        myController = screenParent;
    }
    
}
