/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class Message {
    
   
    public static ArrayList<String> sentMessages = new ArrayList<>();         // text of every sent message
    public static ArrayList<String> disregardedMessages = new ArrayList<>();  // text of every disregarded message
    public static ArrayList<String> storedMessages = new ArrayList<>();       // text of every stored message (read from JSON)
    public static ArrayList<String> messageHashes = new ArrayList<>();        // every message hash
    public static ArrayList<String> messageIDs = new ArrayList<>();           // every message ID

    // Parallel arrays: index 0 in every one of these 5 lists belongs to the same message
    public static ArrayList<String> messageRecipients = new ArrayList<>();
    public static ArrayList<String> messageTexts = new ArrayList<>();
    public static ArrayList<String> messageFlags = new ArrayList<>();        

    public static final String JSON_FILE = "messages.json";

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
        Random random = new Random();
        long number = 1_000_000_000_000L + (long) (random.nextDouble() * 9_000_000_000_000L);
        String fullID = String.valueOf(number);
        return fullID.substring(0, 10);
    }
 
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
            addToArrays("Sent");
            return "Message successfully sent.";
        } else if (choice == 2) {
            addToArrays("Disregard");
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
 
    // Adds this message to the parallel arrays and to the array that matches its flag
    private void addToArrays(String flag) {
        messageIDs.add(messageID);
        messageHashes.add(messageHash);
        messageRecipients.add(recipient);
        messageTexts.add(messageText);
        messageFlags.add(flag);

        if (flag.equals("Sent")) {
            sentMessages.add(messageText);
        } else if (flag.equals("Disregard")) {
            disregardedMessages.add(messageText);
        } else {
            storedMessages.add(messageText);
        }
    }
 
    public static String printMessages() {
        return displayReport("Sent");
    }
 
    public static int returnTotalMessagess() {
        return sentMessages.size();
    }
 
    public void storeMessage() {
        addToArrays("Stored");
        writeStoredMessagesToJson();
    }
 
    // Writes every stored message to messages.json
    // Reference: Google, 2026. Gson User Guide. [online] Available at: https://github.com/google/gson/blob/main/UserGuide.md [Accessed 19 November 2026].
    public static void writeStoredMessagesToJson() {
        String json = "";
        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals("Stored")) {
                if (!json.isEmpty()) {
                    json = json + ",\n";
                }
                json = json + "  {\n"
                        + "    \"messageID\": \"" + escapeJson(messageIDs.get(i)) + "\",\n"
                        + "    \"messageHash\": \"" + escapeJson(messageHashes.get(i)) + "\",\n"
                        + "    \"recipient\": \"" + escapeJson(messageRecipients.get(i)) + "\",\n"
                        + "    \"message\": \"" + escapeJson(messageTexts.get(i)) + "\"\n"
                        + "  }";
            }
        }
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            writer.write("[\n" + json + "\n]\n");
        } catch (IOException e) {
            System.out.println("Could not write " + JSON_FILE + ": " + e.getMessage());
        }
    }
 
    // Reads messages.json into the Stored Messages array (and the parallel arrays)
    // Reference: Google, 2026. Gson User Guide and JsonParser.parseReader. [online] Available at: https://github.com/google/gson/blob/main/UserGuide.md [Accessed 19 November 2026].
    public static void loadStoredMessages() {
        File file = new File(JSON_FILE);
        if (!file.exists()) {
            return;
        }
        try (FileReader reader = new FileReader(file)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                String id = obj.get("messageID").getAsString();
                if (!messageIDs.contains(id)) {          // do not load the same message twice
                    messageIDs.add(id);
                    messageHashes.add(obj.get("messageHash").getAsString());
                    messageRecipients.add(obj.get("recipient").getAsString());
                    messageTexts.add(obj.get("message").getAsString());
                    messageFlags.add("Stored");
                    storedMessages.add(obj.get("message").getAsString());
                }
            }
        } catch (Exception e) {
            System.out.println("Could not read " + JSON_FILE + ": " + e.getMessage());
        }
    }
 
    public static String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
 
    public static String displaySenderAndRecipient(String sender) {
        String result = "";
        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals("Stored")) {
                result = result + "Sender: " + sender + " | Recipient: " + messageRecipients.get(i) + "\n";
            }
        }
        if (result.isEmpty()) {
            return "No stored messages.";
        }
        return result;
    }
 
    public static String displayLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        String longest = storedMessages.get(0);
        for (String text : storedMessages) {
            if (text.length() > longest.length()) {
                longest = text;
            }
        }
        return longest;
    }
 
    public static String searchByMessageID(String id) {
        int index = messageIDs.indexOf(id);
        if (index == -1) {
            return "Message ID not found.";
        }
        return messageTexts.get(index);
    }
 
    public static String getRecipientByMessageID(String id) {
        int index = messageIDs.indexOf(id);
        if (index == -1) {
            return "Message ID not found.";
        }
        return messageRecipients.get(index);
    }
 
    public static ArrayList<String> searchByRecipient(String recipientNumber) {
        ArrayList<String> found = new ArrayList<>();
        for (int i = 0; i < messageRecipients.size(); i++) {
            if (messageRecipients.get(i).equals(recipientNumber) && !messageFlags.get(i).equals("Disregard")) {
                found.add(messageTexts.get(i));
            }
        }
        return found;
    }
 
    public static String deleteByMessageHash(String hash) {
        int index = messageHashes.indexOf(hash.trim().toUpperCase());
        if (index == -1) {
            return "Message hash not found.";
        }
        String text = messageTexts.get(index);
        String flag = messageFlags.get(index);

        if (flag.equals("Sent")) {
            sentMessages.remove(text);
        } else if (flag.equals("Disregard")) {
            disregardedMessages.remove(text);
        } else {
            storedMessages.remove(text);
        }
        messageIDs.remove(index);
        messageHashes.remove(index);
        messageRecipients.remove(index);
        messageTexts.remove(index);
        messageFlags.remove(index);

        if (flag.equals("Stored")) {
            writeStoredMessagesToJson();      // keep the JSON file up to date
        }
        return "Message: \"" + text + "\" successfully deleted.";
    }
 
    // f) full report for "Sent" or "Stored" messages
    public static String displayReport(String flag) {
        String report = "";
        for (int i = 0; i < messageFlags.size(); i++) {
            if (messageFlags.get(i).equals(flag)) {
                report = report + "Message ID: " + messageIDs.get(i) + "\n"
                        + "Message Hash: " + messageHashes.get(i) + "\n"
                        + "Recipient: " + messageRecipients.get(i) + "\n"
                        + "Message: " + messageTexts.get(i) + "\n\n";
            }
        }
        return report;
    }
 
    public static void reset() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        messageRecipients.clear();
        messageTexts.clear();
        messageFlags.clear();
    }
 
    @Override
    public String toString() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText;
    }
}
