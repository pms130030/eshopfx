/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshopfx;

import eshopfx.ui.ScreensController;
import eshopfx.data.User;
import eshopfx.services.DatabaseConnection;
import eshopfx.ui.ScreenReference;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Parth
 */
public class MainApplication extends Application
{
    private static User currUser;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(ScreenReference.LOGIN_SCREEN_ID, ScreenReference.LOGIN_SCREEN_FILE);
        mainContainer.loadScreen(ScreenReference.REGISTER_SCREEN_ID, ScreenReference.REGISTER_SCREEN_FILE);
        
        mainContainer.setScreen(ScreenReference.LOGIN_SCREEN_ID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void stop(){
        System.out.println("Closing DB Connection");
        DatabaseConnection.getInstance().closeConnection();
    }

    public static User getCurrentUser()
    {
        return currUser;
    }

    public static void setCurrentUser(User currUser)
    {
        MainApplication.currUser = currUser;
    }
    
    
    
    

    
    
}
