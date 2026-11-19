/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author Student
 */
public class MessageTest {
    
     Message msg1;  
    Message msg2;   
 
    @BeforeEach
    public void setUp() {
        Message.reset();
        msg1 = new Message("0012345678", 0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
        msg2 = new Message("0098765432", 1, "08575975889",
                "Hi Keegan, did you receive the payment?");
    }
 
    
    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", msg1.checkMessageLength());
    }
 
    @Test
    public void testMessageLengthFailure() {
        String longText = "a".repeat(260);
        Message m = new Message("0012345678", 0, "+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 10, please reduce the size.",
                m.checkMessageLength());
    }
 
    @Test
    public void testRecipientSuccess() {
        assertEquals("Cell phone number successfully captured.", msg1.checkRecipientCell());
    }
 
    @Test
    public void testRecipientFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg2.checkRecipientCell());
    }
 
     @Test
    public void testMessageHashTestCase1() {
        assertEquals("00:0:HITONIGHT", msg1.createMessageHash());
    }
 
    @Test
    public void testMessageHashesInLoop() {
        for (int i = 0; i < 2; i++) {
            Message m = msg1;
            String expected = "00:0:HITONIGHT";
            if (i == 1) {
                m = msg2;
                expected = "00:1:HIPAYMENT";
            }
            assertEquals(expected, m.createMessageHash());
        }
    }
 
    @Test
    public void testMessageIDCreated() {
        Message m = new Message(0, "+27718693002", "Hello there");   // random ID
        assertTrue(m.checkMessageID());
        assertEquals(10, m.messageID.length());
        assertEquals("Message ID generated: " + m.messageID, m.getMessageIDStatus());
        System.out.println(m.getMessageIDStatus());
    }
 
    @Test
    public void testMessageIDTooLong() {
        Message m = new Message("12345678901", 0, "+27718693002", "Hello there");
        assertFalse(m.checkMessageID());
    }
 
    @Test
    public void testSendMessage() {
        assertEquals("Message successfully sent.", msg1.SentMessage(1));
    }
 
    @Test
    public void testDisregardMessage() {
        assertEquals("Press 0 to delete the message.", msg2.SentMessage(2));
    }
 
    @Test
    public void testStoreMessage() {
        assertEquals("Message successfully stored.", msg1.SentMessage(3));
        assertTrue(new File("messages.json").exists());   
    }
 
    @Test
    public void testTotalMessages() {
        msg1.SentMessage(1);   // sent
        msg2.SentMessage(2);   // discarded
        assertEquals(1, Message.returnTotalMessagess());
    }
 
     @Test
    public void testPrintMessages() {
        msg1.SentMessage(1);
        String printed = Message.printMessages();
        assertTrue(printed.contains("Message ID: 0012345678"));
        assertTrue(printed.contains("Message Hash: 00:0:HITONIGHT"));
        assertTrue(printed.contains("Recipient: +27718693002"));
        assertTrue(printed.contains("Message: Hi Mike, can you join us for dinner tonight?"));
    }

    // ---------------- Part 3: test data from the task ----------------
    Message t1, t2, t3, t4, t5;

    // Populates the arrays with Test Data 1 to 5 (Sent, Stored, Disregard, Sent, Stored)
    private void populateTestData() {
        Message.reset();
        t1 = new Message("0834557896", 1, "+27834557896", "Did you get the cake?");
        t2 = new Message("0838884561", 2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        t3 = new Message("0834484567", 3, "+27834484567", "Yohoooo, I am at your gate.");
        t4 = new Message("0838884567", 4, "0838884567", "It is dinner time!");
        t5 = new Message("0838884565", 5, "+27838884567", "Ok, I am leaving without you.");
        t1.SentMessage(1);
        t2.SentMessage(3);
        t3.SentMessage(2);
        t4.SentMessage(1);
        t5.SentMessage(3);
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        populateTestData();
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("Did you get the cake?", "It is dinner time!"));
        assertEquals(expected, Message.sentMessages);
    }

    @Test
    public void testDisregardedAndStoredArraysPopulated() {
        populateTestData();
        assertEquals(Arrays.asList("Yohoooo, I am at your gate."), Message.disregardedMessages);
        assertEquals(2, Message.storedMessages.size());
        assertEquals(5, Message.messageHashes.size());
        assertEquals(5, Message.messageIDs.size());
    }

    @Test
    public void testLongestStoredMessage() {
        populateTestData();
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                Message.displayLongestStoredMessage());
    }

    @Test
    public void testSearchByMessageID() {
        populateTestData();
        assertEquals("It is dinner time!", Message.searchByMessageID("0838884567"));
        assertEquals("0838884567", Message.getRecipientByMessageID("0838884567"));
    }

    @Test
    public void testSearchByRecipient() {
        populateTestData();
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."));
        assertEquals(expected, Message.searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteByMessageHash() {
        populateTestData();
        String result = Message.deleteByMessageHash(t2.messageHash);
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
        assertEquals(1, Message.storedMessages.size());
        assertEquals(4, Message.messageHashes.size());
    }

    @Test
    public void testDisplayReport() {
        populateTestData();
        String report = Message.displayReport("Sent");
        assertTrue(report.contains(t1.messageHash));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains(t4.messageHash));
        assertTrue(report.contains("It is dinner time!"));
    }

    @Test
    public void testReadJsonIntoStoredArray() {
        populateTestData();                 // writes messages 2 and 5 to messages.json
        Message.reset();                    // empty every array
        Message.loadStoredMessages();       // read the JSON file back in
        assertEquals(Arrays.asList("Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."), Message.storedMessages);
    }

    @Test
    public void testMessageIDGeneratedWithSubstring() {
        for (int i = 0; i < 50; i++) {
            assertEquals(10, Message.generateMessageID().length());
        }
    }
}  
