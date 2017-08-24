/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshopfx.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Parth
 */
public class User
{
    private final String email;
    private String password;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private ObservableList<RecipientModel> recipientData;

    public User(String email)
    {
        this.email = email;
        recipientData = FXCollections.observableArrayList();
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleInitial()
    {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial)
    {
        this.middleInitial = middleInitial;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public ObservableList<RecipientModel> getRecipientData()
    {
        return recipientData;
    }

    public void setRecipientData(ObservableList<RecipientModel> recipientData)
    {
        this.recipientData = recipientData;
    }
            
}
