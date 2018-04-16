/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.globalkinetic.test;

import com.mycompany.globalkinetic.bo.GlobalBo;
import com.mycompany.globalkinetic.domain.UserDo;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cz Laptop
 */
public class GlobalKineticTest {
    
    public GlobalKineticTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCreateUser() {
        String userName = "Cole";
        String phoneNumber = "0603610048";
        String password = "password";
        boolean isAdmin = true;
        
        int userID = GlobalBo.createUser(userName, phoneNumber, password, isAdmin);
        System.out.println("UserID " + userID);
    }
    
    @Test
    public void testUserLogIn() {
        String userName = "Cole";
        String password = "password";
        
        boolean doesUserExist = GlobalBo.userLogIn(userName, password);
        System.out.println("Does User Exist: " + doesUserExist);
    }
    
    @Test
    public void testRetrieveUserDoList() {
        List<UserDo> userDoList = GlobalBo.retrieveUserDoList(true);
        
        for (UserDo userDo : userDoList) {
            System.out.println("User: " + userDo.getUserName());
        }
    }
}
