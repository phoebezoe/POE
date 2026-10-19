/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class Message {
    
      public static String sentMessages = "";    
    public static String storedEntries = "";   
    public static int totalMessages = 0;       
    public static int totalStored = 0;         // number of messages stored
 
    public String messageID;
    public int messageNumber;
    public String recipient;
    public String messageText;
    public String messageHash;

public Message(int messageNumber, String recipient, String messageText) {
    this(generateMessageID(), messageNumber, recipient, messageText);
}

public Message(String messageID, int messageNumber, String recipient, String messageText) {
    this.messageID = messageID;
    this.messageNumber = messageNumber;
    this.recipient = recipient;
    this.messageText = messageText;
    this.messageHash = createMessageHash();
}
     public static String generateMessageID() {
        String digits = "0123456789";
        Random random = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            int position = random.nextInt(10);
            id = id + digits.substring(position, position + 1);
        }
        return id;
    }
 
    // Boolean: true if the Message ID is no more than ten characters
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }
 
    public String getMessageIDStatus() {
        return "Message ID generated: " + messageID;
    }
 
    public static boolean isValidRecipient(String cell) {
        return cell.matches("^\\+[0-9]{1,3}[0-9]{1,10}$");
    }
    
      public String checkRecipientCell() {
        if (isValidRecipient(recipient)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
 
    // A messages not more than 250 characters
    public static boolean isValidLength(String text) {
        return text.length() <= 250;
    }
 
    // Returns the success or failure message for the message length
    public String checkMessageLength() {
        if (isValidLength(messageText)) {
            return "Message ready to send.";
        }
        int extra = messageText.length() - 250;
        return "Message exceeds 250 characters by " + extra + ", please reduce the size.";
    }
 
    public String createMessageHash() {
        String text = messageText.trim();
        int firstSpace = text.indexOf(" ");
        int lastSpace = text.lastIndexOf(" ");
 
        String firstWord = text;
        String lastWord = text;
        if (firstSpace != -1) {
            firstWord = text.substring(0, firstSpace);
            lastWord = text.substring(lastSpace + 1);
        }
 
        firstWord = firstWord.replaceAll("[^A-Za-z0-9]", "");
        lastWord = lastWord.replaceAll("[^A-Za-z0-9]", "");
 
        String firstTwo = messageID.substring(0, 2);
        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }
    
     public String SentMessage(int choice) {
        if (choice == 1) {
            sentMessages = sentMessages + this.toString() + "\n\n";
            totalMessages++;
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid option.";
        }
    }
 
    // Lets the user choose to send, disregard or store the message
    public String SentMessage(Scanner input) {
        int choice = 0;
        while (choice < 1 || choice > 3) {
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            choice =Part1.readInt(input, "Choose an option: ");
        }
 
        String result = SentMessage(choice);
        if (choice == 2) {
            System.out.println(result);                 // Press 0 to delete the message.
            String answer = input.nextLine().trim();
            while (!answer.equals("0")) {
                System.out.println("Press 0 to delete the message.");
                answer = input.nextLine().trim();
            }
            return "Message deleted.";
        }
        return result;
    }
 
    public static String printMessages() {
        return sentMessages;
    }
 
    public static int returnTotalMessagess() {
        return totalMessages;
    }
 
    public void storeMessage() {
        String entry = "  {\n"
                + "    \"messageID\": \"" + escapeJson(messageID) + "\",\n"
                + "    \"messageHash\": \"" + escapeJson(messageHash) + "\",\n"
                + "    \"recipient\": \"" + escapeJson(recipient) + "\",\n"
                + "    \"message\": \"" + escapeJson(messageText) + "\"\n"
                + "  }";
 
        if (totalStored > 0) {
            storedEntries = storedEntries + ",\n";
        }
        storedEntries = storedEntries + entry;
        totalStored++;
 
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write("[\n" + storedEntries + "\n]\n");
        } catch (IOException e) {
            System.out.println("Could not write messages.json: " + e.getMessage());
        }
    }
 
    public static String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
 
    public static void reset() {
        sentMessages = "";
        storedEntries = "";
        totalMessages = 0;
        totalStored = 0;
    }
 
    @Override
    public String toString() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText;
    }
}
