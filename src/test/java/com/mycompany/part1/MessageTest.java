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
    
     Message msg1;   // Test data 1: will be SENT
    Message msg2;   // Test data 2: will be DISCARDED
 
    @BeforeEach
    public void setUp() {
        Message.reset();
        msg1 = new Message("0012345678", 0, "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
        msg2 = new Message("0098765432", 1, "08575975889",
                "Hi Keegan, did you receive the payment?");
    }
 
    // ---- The message should not be more than 250 characters ----
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
 
    // ---- The recipient number is correctly formatted ----
    @Test
    public void testRecipientSuccess() {
        assertEquals("Cell phone number successfully captured.", msg1.checkRecipientCell());
    }
 
    @Test
    public void testRecipientFailure() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg2.checkRecipientCell());
    }
 
    
}
