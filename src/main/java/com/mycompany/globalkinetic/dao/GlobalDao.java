/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.globalkinetic.dao;

import com.mycompany.globalkinetic.domain.UserDo;
import java.util.List;

/**
 *
 * @author Siyanda
 */
public interface GlobalDao {
    
    /**
     * This method is used to insert user details in the database.
     * 
     * @param userDo
     * @return 
     */
    public int insertUserDetails(UserDo userDo);
    
    /**
     * This method is used to validate if the user exists in the database
     * the method is used for the login functionality.
     * 
     * @param userName
     * @param password
     * @return 
     */
    public boolean doesUserExist(String userName, String password);
    
    /**
     * This method is used to retrieve a list of users, it takes a boolean parameter
     * if this is true, then the method will return users logged in the past 5 minutes
     * else it will return all users registered.
     * 
     * @param onlyRecentLoggedInUsers
     * @return 
     */
    public List<UserDo> retrieveUserDoList(boolean onlyRecentLoggedInUsers);
}
