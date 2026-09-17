/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1;
import java.util.regex.Pattern;
/**
 *
 * @author Student
 */
public class Login {
    private String username;
    private String password;
    private String cellphoneNumber;
    private String firstName;
    private String lastName;

  
    public Login(String username, String cellphoneNumber, String password,
                 String firstName, String lastName) {
        this.username = username;
        this.cellphoneNumber = cellphoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean checkUserName(String username) {
        boolean Username;
        if (username.contains("_") && username.length() <= 5){
        Username = true;
        System.out.println("Username successfully captured.");
    }else{
        Username = false;
       System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        return Username;
    }
}
