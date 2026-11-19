/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.part1;

import java.util.ArrayList;
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

        Login login = new Login(username, cellphoneNumber, password, firstName, lastName);

        String registrationResult = login.registerUser(username, password);
        System.out.println(registrationResult);

        if (registrationResult.equals("Username and password successfully captured.")) {
            System.out.print("Please re-enter your username: ");
            String enteredUsername = input.nextLine();
            System.out.print("Please re-enter your password: ");
            String enteredPassword = input.nextLine();

            boolean loggedIn = login.loginUser(username, password, enteredUsername, enteredPassword);

            if (loggedIn) {
                System.out.println("Welcome to QuickChat.");
                Message.loadStoredMessages();      // read messages.json into the Stored Messages array
                String senderName = firstName + " " + lastName;

                int maxMessages = readInt(input, "How many messages do you want to enter? ");
                int entered = 0;
                int choice = 0;
                while (choice != 3) {
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.println("4) Stored Messages");
                    System.out.print("Choose an option: ");
                    choice = readInt(input, "");

                    if (choice == 1) {
                        if (entered >= maxMessages) {
                            System.out.println("You have already entered all " + maxMessages + " messages.");
                        } else {
                            for (int i = entered; i < maxMessages; i++) {
                                System.out.println("\nMessage " + (i + 1) + " of " + maxMessages);
                                enterMessage(input, i);
                                entered++;
                            }
                            System.out.println("\nTotal messages sent: " + Message.returnTotalMessagess());
                        }
                    } else if (choice == 2) {
                        System.out.println(Message.printMessages());
                    } else if (choice == 3) {
                        System.out.println("Goodbye.");
                    } else if (choice == 4) {
                        storedMessagesMenu(input, senderName);
                    } else {
                        System.out.println("Invalid option. Please choose 1, 2, 3 or 4.");
                    }
                }
                System.out.println("\n--- Messages sent ---");
                System.out.println(Message.printMessages());
                System.out.println("Total messages sent: " + Message.returnTotalMessagess());
            }
        }

        input.close();
    }

    public static void enterMessage(Scanner input, int messageNumber) {

        String recipient = "";
        boolean recipientOk = false;
        while (!recipientOk) {
            System.out.print("Enter recipient cell number (e.g. +27718693002): ");
            recipient = input.nextLine().trim();
            if (Message.isValidRecipient(recipient)) {
                System.out.println("Cell phone number successfully captured.");
                recipientOk = true;
            } else {
                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
            }
        }

        String text = "";
        boolean textOk = false;
        while (!textOk) {
            System.out.print("Enter your message: ");
            text = input.nextLine().trim();
            if (text.trim().isEmpty()) {
                System.out.println("The message cannot be empty.");
            } else if (Message.isValidLength(text)) {
                System.out.println("Message sent");
                textOk = true;
            } else {
                System.out.println("Please enter a message of less than 250 characters.");
                System.out.println("Message exceeds 250 characters by " + (text.length() - 250) + ", please reduce the size.");
            }
        }

        Message msg = new Message(messageNumber, recipient, text);
        String outcome = msg.SentMessage(input);
        System.out.println(outcome);

        if (outcome.equals("Message successfully sent.")) {
            System.out.println(msg);
        }
    }

    public static void storedMessagesMenu(Scanner input, String sender) {
        int option = 0;
        while (option != 7) {
            System.out.println("\n--- Stored Messages ---");
            System.out.println("1) Display the sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search for all messages to a recipient");
            System.out.println("5) Delete a message using its hash");
            System.out.println("6) Display a report of all stored messages");
            System.out.println("7) Back to main menu");
            option = readInt(input, "Choose an option: ");

            if (option == 1) {
                System.out.println(Message.displaySenderAndRecipient(sender));
            } else if (option == 2) {
                System.out.println("Longest stored message: " + Message.displayLongestStoredMessage());
            } else if (option == 3) {
                System.out.print("Enter the message ID: ");
                String id = input.nextLine().trim();
                System.out.println("Recipient: " + Message.getRecipientByMessageID(id));
                System.out.println("Message: " + Message.searchByMessageID(id));
            } else if (option == 4) {
                System.out.print("Enter the recipient cell number: ");
                String number = input.nextLine().trim();
                ArrayList<String> found = Message.searchByRecipient(number);
                if (found.isEmpty()) {
                    System.out.println("No messages found for " + number);
                }
                for (String text : found) {
                    System.out.println(text);
                }
            } else if (option == 5) {
                System.out.print("Enter the message hash: ");
                String hash = input.nextLine();
                System.out.println(Message.deleteByMessageHash(hash));
            } else if (option == 6) {
                String report = Message.displayReport("Stored");
                System.out.println(report.isEmpty() ? "No stored messages." : report);
            } else if (option != 7) {
                System.out.println("Invalid option. Please choose 1 to 7.");
            }
        }
    }

    public static int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}
