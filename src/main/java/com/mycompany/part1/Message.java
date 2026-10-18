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
    
      public static String sentMessages = "";    // details of every message that was sent
    public static String storedEntries = "";   // one JSON object for every stored message
    public static int totalMessages = 0;       // number of messages sent
    public static int totalStored = 0;         // number of messages stored
 
    public String messageID;
    public int messageNumber;
    public String recipient;
    public String messageText;
    public String messageHash;
 
    // Used by the app: the Message ID is generated randomly
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
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
 
    // Message to display when the ID has been created (used in the unit test)
    public String getMessageIDStatus() {
        return "Message ID generated: " + messageID;
    }
 
    public static boolean isValidRecipient(String cell) {
        return cell.matches("^\\+[0-9]{1,3}[0-9]{1,10}$");
    }
}
