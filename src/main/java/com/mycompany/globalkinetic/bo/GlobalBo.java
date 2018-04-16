/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.globalkinetic.bo;

import com.mycompany.globalkinetic.dao.GlobalDao;
import com.mycompany.globalkinetic.daoImpl.GlobalDaoImpl;
import com.mycompany.globalkinetic.domain.GlobalConstants;
import com.mycompany.globalkinetic.domain.UserDo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Siyanda
 */
public class GlobalBo {
    
    /**
     * This a default constructor
     */
    public GlobalBo() {
        
    }
    /**
     * This method assembles, validates then sends the user do to the dao layer 
     * to be inserted in the db if data provided is valid.
     * 
     * @param userName
     * @param phoneNumber
     * @param password
     * @param isAdmin
     * @return 
     */
    public static int createUser(String userName, String phoneNumber, String password, boolean isAdmin) {
        UserDo userDo = assembleUserDo(userName, phoneNumber, password, isAdmin, null);
        boolean isUserDoValid = isUserDoValid(userDo);
        int userID = 0;
        if (isUserDoValid) {
            GlobalDao globalDao = new GlobalDaoImpl();
            userID = globalDao.insertUserDetails(userDo);
        } else {
            System.out.println("UserBo.createUser: User information is not valid...");
            return userID;
        }
        return userID;
    }
    
    /**
     * This method assembles the user do
     * @param userName
     * @param phoneNumber
     * @param password
     * @param isAdmin
     * @return 
     */
    private static UserDo assembleUserDo(String userName, String phoneNumber, String password, boolean isAdmin, Date lastLogin) {
        UserDo userDo = new UserDo();
        userDo.setUserName(userName);
        userDo.setPhoneNumber(phoneNumber);
        userDo.setPassword(password);
        userDo.setIsAdmin(isAdmin);
        userDo.setLastLogin(lastLogin);
        
        return userDo;
    }
    
    /**
     * This method validates the user do details
     * @param userDo
     * @return 
     */
    private static boolean isUserDoValid(UserDo userDo) {
        if (userDo.getUserName() == null || userDo.getUserName().equals(GlobalConstants.EMPTY_STRING)) {
            return false;
        }
        
        if (userDo.getPassword() == null || userDo.getPassword().equals(GlobalConstants.EMPTY_STRING)) {
            return false;
        }
        
        return true;
    }
    /**
     * This method calls the db method to check is the user is registered/valid
     * @param userName
     * @param password
     * @return 
     */
    public static boolean userLogIn(String userName, String password) {
        
        GlobalDao globalDao = new GlobalDaoImpl();
        boolean isUserValid = globalDao.doesUserExist(userName, password);
        
        return isUserValid;
    }
    
    /**
     * This method is used to retrieve a list of users, it takes a boolean 
     * parameter if this is true, then the method will return users logged 
     * in the past 5 minutes else it will return all users registered.
     * 
     * @param onlyRecentLoggedInUsers
     * @return 
     */
    public static List<UserDo> retrieveUserDoList(boolean onlyRecentLoggedInUsers) {
        GlobalDao globalDao = new GlobalDaoImpl();
        List<UserDo> userDoList = globalDao.retrieveUserDoList(onlyRecentLoggedInUsers);
        
        return userDoList;
    }
}
