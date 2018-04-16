/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.globalkinetic.daoImpl;

import com.mycompany.globalkinetic.dao.GlobalDao;
import com.mycompany.globalkinetic.domain.UserDo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Siyanda
 */
public class GlobalDaoImpl implements GlobalDao {
    
    private Connection connection = null;
    private Statement statement = null;
    /**
     * This is the fault constructor
     */
    public GlobalDaoImpl() {

    }
    
    public void initializeConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:derby://localhost/GlobalDB", "gadmin", "gadmin");
            }
        } catch (SQLException sQLException) {
            System.out.println("GlobalDaoImpl: There is a problem with the connection..." + sQLException.getMessage());
        }
    }
    
    /**
     * This method is used to insert user details in the database.
     * 
     * @param userDo
     * @return 
     */
    @Override
    public int insertUserDetails(UserDo userDo) {
        int userID = 0;
        initializeConnection();
        
        try {
            statement = connection.createStatement();
            String insertSQL = "INSERT INTO UserTable (UserName, Password, LastLogin, IsAdmin, Phone)"
                    + " VALUES ('" + userDo.getUserName() + "', '"
                    + userDo.getPassword() + "', "
                    + userDo.getLastLogin() + ", "
                    + userDo.getIsAdmin() + ", '"
                    + userDo.getPhoneNumber()
                    + "')";
            statement.execute(insertSQL);
            userID = getNewUserID();
            statement.close();
            statement = null;
            return userID;
        } catch (SQLException ex) {
            Logger.getLogger(GlobalDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userID;
    }
    
    /**
     * This method returns the newly created userID
     * @return
     * @throws SQLException 
     */
    public int getNewUserID() throws SQLException {
        int userID = 0;
        statement = connection.createStatement();
        String selectSQL  = "SELECT MAX(UserID) AS UserID FROM UserTable";
        statement.executeQuery(selectSQL);
        ResultSet resultSet = statement.getResultSet();
            
        if (resultSet.next()) {    
            userID = resultSet.getInt(1);
        }
        
        return userID;
    }
    
    /**
     * This method is used to validate if the user exists in the database
     * the method is used for the login functionality.
     * 
     * @param userName
     * @param password
     * @return 
     */
    @Override
    public boolean doesUserExist(String userName, String password) {
        boolean doesUserExist = true;
        initializeConnection();
        
        try {
            statement = connection.createStatement();
            String selectSQL = "SELECT * FROM USERTABLE WHERE USERNAME = '"
                    + userName + "' AND Password = '" + password + "'";
            statement.executeQuery(selectSQL);
            ResultSet resultSet = statement.getResultSet();
            
            if (!resultSet.next()) {
                doesUserExist = false;
            } else {
                updateLastLogInColumn(resultSet.getInt("UserID"));
            }
            statement.close();
            statement = null;
        } catch (SQLException ex) {
            Logger.getLogger(GlobalDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return doesUserExist;
    }
    
    /**
     * This method updates the last log in column for the user
     * @param userID 
     */
    private void updateLastLogInColumn(int userID) throws SQLException {
        String updateSQL = "Update UserTable set LastLogIn = ? WHERE UserID = " + userID;
        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
        
        preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
        preparedStatement.execute();
        preparedStatement.close();
    }
    
    /**
     * This method is used to retrieve a list of users, it takes a boolean parameter
     * if this is true, then the method will return users logged in the past 5 minutes
     * else it will return all users registered.
     * 
     * @param onlyRecentLoggedInUsers
     * @return 
     */
    @Override
    public List<UserDo> retrieveUserDoList(boolean onlyRecentLoggedInUsers) {
        List<UserDo> userDoList = new ArrayList<UserDo>();
        initializeConnection();
        
        try {
            statement = connection.createStatement();
            StringBuilder selectSQL = new StringBuilder();
            selectSQL.append("SELECT * FROM USERTABLE");
            
            if (onlyRecentLoggedInUsers) {
                selectSQL.append(" WHERE {fn TIMESTAMPDIFF(SQL_TSI_MINUTE, LASTLOGIN, CURRENT_TIMESTAMP)} <= 5");
            }
            
            statement.executeQuery(selectSQL.toString());
            ResultSet resultSet = statement.getResultSet();
            
            while(resultSet.next()) {
                UserDo userDo = new UserDo();
                userDo.setUserName(resultSet.getString("UserName"));
                userDo.setPassword(resultSet.getString("Password"));
                userDo.setPhoneNumber(resultSet.getString("Phone"));
                userDo.setIsAdmin(resultSet.getBoolean("IsAdmin"));
                userDoList.add(userDo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GlobalDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userDoList;
    }
}
