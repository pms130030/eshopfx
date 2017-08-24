/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshopfx.services;

import eshopfx.data.RecipientModel;
import eshopfx.data.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Parth
 */
public class RecipientService
{
    public User populateRecipients(User user) throws SQLException
    {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "select Recipient.RecipientID, Name, StreetAddr,Zip,Country,State,City from Recipient " +
                        "where Recipient.UserEmail=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getEmail());
        
        preparedStatement.execute();
        ResultSet result = preparedStatement.getResultSet();
        
        while(result.next())
        {
            RecipientModel tempModel = new RecipientModel();
            tempModel.setRecipientID(result.getInt("RecipientID"));
            tempModel.setName(result.getString("Name"));
            tempModel.setStreetAddr(result.getString("StreetAddr"));
            tempModel.setZip(result.getString("Zip"));
            tempModel.setCountry(result.getString("Country"));
            tempModel.setState(result.getString("State"));
            tempModel.setCity(result.getString("City"));
            
            user.getRecipientData().add(tempModel);
        }
        
        result.close();
        preparedStatement.close();
        
        
        return user;
    }
    
    public void deleteRecipient(RecipientModel recipient) throws SQLException
    {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String query = "Update Recipient set UserEmail = NULL where RecipientID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, recipient.getRecipientID());
        
        preparedStatement.execute();
        preparedStatement.close();
    }
    
    public int addRecipient(String email, RecipientModel recipient) throws SQLException
    {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String insertRecipient = "insert into Recipient (UserEmail, Name, StreetAddr, Zip, Country, State, City) values(?,?,?,?,?,?,?)";
        
        PreparedStatement preparedStatement = connection.prepareStatement(insertRecipient,Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, recipient.getName());
        preparedStatement.setString(3, recipient.getStreetAddr());
        preparedStatement.setString(4, recipient.getZip());
        preparedStatement.setString(5, recipient.getCountry());
        preparedStatement.setString(6, recipient.getState());
        preparedStatement.setString(7, recipient.getCity());
        
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        int generatedID = -1;
        while(resultSet.next())
        {
            generatedID=resultSet.getInt(1);
        }
        
        resultSet.close();
        preparedStatement.close();
        return generatedID;
        
    }
    
    public void updateRecipient(RecipientModel recipient) throws SQLException
    {
        //insert into Recipient values ('Simonne Strangwood','bstrangwood0@linkedin.com');
        //insert into Address values ('6 Butterfield Lane', '07', 'US', 'CA', 'Los Angeles', 1);
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String updateRecipient = "update Recipient set Name=?, StreetAddr=?, Zip=?, Country=?, State=?, City=? where RecipientID=?";
        
        PreparedStatement preparedStatement = connection.prepareStatement(updateRecipient);
        preparedStatement.setString(1, recipient.getName());
        preparedStatement.setString(2, recipient.getStreetAddr());
        preparedStatement.setString(3, recipient.getZip());
        preparedStatement.setString(4, recipient.getCountry());
        preparedStatement.setString(5, recipient.getState());
        preparedStatement.setString(6, recipient.getCity());
        preparedStatement.setInt(7, recipient.getRecipientID());
        
        preparedStatement.execute();
        preparedStatement.close();
    }
}
