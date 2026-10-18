/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment;
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
}
