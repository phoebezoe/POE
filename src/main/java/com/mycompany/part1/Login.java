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

    public boolean checkUserName(String username) { //username needs to contain a _ be less than 5 characters
        boolean Username;
        if (username.contains("_") && username.length() <= 5){// using if else to output a message depending on if the user followed the rules
        Username = true;
        System.out.println("Username successfully captured.");
    }else{
        Username = false;
       System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        return Username;
    }
     public boolean checkPasswordComplexity(String password) { // ensuring password follows the ruls
        boolean Password;
      if( password.matches ("^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$")){
      Password = true;
          System.out.println("Password successfully captured.");
    }else{
      Password = false;
          System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
      }
      return Password;
    }

    public boolean checkCellPhoneNumber(String cellphoneNumber) {// for south african format phne number
        boolean cellphone;
        String regexPattern = "^(\\+27)[6-8][0-9]{8}$";//regex for south african phone numbers
        
        if (cellphoneNumber.matches(regexPattern)){
            cellphone = true;
            System.out.println("Cell number successfully captured.");
        }else{
            cellphone =  false;
            System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
        }
        return cellphone;
    }
    public String registerUser(String username, String password){
        
        if(!checkUserName(username)){
            return "The username is incorrectly formatted.";
        }
        if(!checkPasswordComplexity(password)){
            return "The password does not meet the complexity requirements";
    }
      
        return "Username and password successfully captured.";
    }
     public boolean loginUser(String username, String password, String enteredUsername, String enteredPassword) {
        boolean logininformation; 
        
        if(enteredUsername.equals(username) && enteredPassword.equals(password)){
         logininformation = true;
        System.out.println("A successful login");
    }else{
        logininformation = false;
            System.out.println("A failed login");
}
    return logininformation;
}

    public String returnLoginStatus(String username, String password, String enteredUsername, String enteredPassword, String firstName, String lastName) {
        
        if(loginUser(username, password, enteredUsername, enteredPassword)){
        
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }else{
            return "Username or password incorrect, please try again.";
    }
}
}
//Logano keys, 2023. Java program to validate South African phone numbers using RegeX. [video online] Available at: https://www.youtube.com/watch?v=2M1CpEJZ6rk [Accessed 18 September 2026].
//W3Schools, 2026. JavaScript if, else, and else if. [online] Available at: https://www.w3schools.com/js/js_if_else.asp [Accessed 18 September 2026].
//freddiednml, 2017. south african mobile number regex [Source code]. Available at: https://github.com/ansman/validate.js/issues/235 [Accessed 18 September 2026].