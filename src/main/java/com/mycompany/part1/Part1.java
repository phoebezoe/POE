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
    }
}
