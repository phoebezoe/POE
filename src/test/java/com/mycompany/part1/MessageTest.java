/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

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
}
