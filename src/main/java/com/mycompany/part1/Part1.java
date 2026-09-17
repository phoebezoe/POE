/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.part1;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class Part1 {

    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);

        System.out.print("Please enter your first name: ");
        String firstName = input.nextLine();
        System.out.print("Please enter your last name: ");
        String lastName = input.nextLine();

        System.out.print("Please enter a username that must contain '_' and be atleast 5 characters: ");
        String username = input.nextLine();
        System.out.print(" Please enter a password that has 8+ chararacters, capital, number and special characters: ");
        String password = input.nextLine();
        System.out.print("Please enter a phone  number (e.g. +27838968976):");
        String cellphoneNumber = input.nextLine(); 
        
       
         Login login = new Login(username, cellphoneNumber, password, firstName,lastName);

        String registrationResult = login.registerUser(username, password);
        System.out.println(registrationResult);

        if (registrationResult.equals("Username and password successfully captured.")) {
            System.out.print("Please re-enter your username: ");
            String enteredUsername = input.nextLine();
            System.out.print("Please re-enter your password: ");
            String enteredPassword = input.nextLine();

          
            System.out.println(login.returnLoginStatus(username, password, enteredUsername, enteredPassword, firstName, lastName));
        }

        input.close();
    }
}

