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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Parth
 */
public class CustomerService
{
    
    public void deleteCustomer(String email) throws SQLException
    {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String queryEmail = "Delete from Customer where UserEmail=?";
        PreparedStatement emailPreparedStatement = connection.prepareStatement(queryEmail);
        emailPreparedStatement.setString(1, email);
        
        emailPreparedStatement.execute();
        emailPreparedStatement.close();
    }
    
    public void updateUser(User user) throws SQLException
    {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String queryEmail = "Update Customer set Password=?, Fname=?, Minit=?, lName=? where UserEmail=?";
        PreparedStatement emailPreparedStatement = connection.prepareStatement(queryEmail);
        
        emailPreparedStatement.setString(1, user.getPassword());
        emailPreparedStatement.setString(2, user.getFirstName());
        emailPreparedStatement.setString(3, user.getMiddleInitial());
        emailPreparedStatement.setString(4, user.getLastName());
        emailPreparedStatement.setString(5, user.getEmail());
        
        emailPreparedStatement.execute();
        emailPreparedStatement.close();
    }
}
