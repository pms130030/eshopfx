/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eshopfx.services;

import eshopfx.data.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Parth
 */
public class LoginService
{
    public User authenticate(String email, String password) throws SQLException
    {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        User user  = new User(email);
        String query = "Select * from Customer where UserEmail=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        
        preparedStatement.execute();
        ResultSet result = preparedStatement.getResultSet();
        
        String dbpass = null;
        if(result.next())
        {
            dbpass = result.getString("password");
        }
        
        
        
        if(dbpass.equals(password))
        {
            user.setFirstName(result.getString("FName"));
            user.setMiddleInitial(result.getString("MInit"));
            user.setLastName(result.getString("LName"));
            user.setPassword(dbpass);
            result.close();
            preparedStatement.close();
            return user;
        }
        else
        {
            result.close();
            preparedStatement.close();
            return null;
        }
    }

    public String addNewCustomer(String email, String password, String fName, String middleinit, String lastName) throws SQLException
    {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String queryEmail = "Select UserEmail from Customer where UserEmail=?";
        PreparedStatement emailPreparedStatement = connection.prepareStatement(queryEmail);
        emailPreparedStatement.setString(1, email);
        emailPreparedStatement.execute();
        ResultSet emailResult = emailPreparedStatement.getResultSet();
        String dbpass = null;
        if (emailResult.wasNull())
        {
            return "This email aready exists";
        }
        String query = "insert into Customer values (?,?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, fName);
        preparedStatement.setString(4, middleinit);
        preparedStatement.setString(5, lastName);
        preparedStatement.execute();
        
        preparedStatement.close();
        return null;
    }
}
