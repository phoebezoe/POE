/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.part1.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    public LoginTest() {
    }
     @Test
   
    public void testCheckUserNameCorrect() {  // checks username (contains underscore, <= 5 chars)
        String firstName = "";
        String lastName = "";
        String userTest = "kyl_1";
        String password = "";
        String cellNumber = "";

        Login instance = new Login(userTest, cellNumber, password, firstName, lastName);

        boolean expected = true;
        boolean actual = instance.checkUserName(userTest);

        assertEquals(expected, actual);
        assertTrue(instance.checkUserName(userTest));
    }
     @Test
    public void testCheckUserNameIncorrect() {
        String firstName = "";
        String lastName = "";
        String userTest = "kyle!!!!!!";
        String password = "";
        String cellNumber = "";

        Login instance = new Login(userTest, cellNumber, password, firstName, lastName);

        boolean expected = false;
        boolean actual = instance.checkUserName(userTest);

        assertEquals(expected, actual);
        assertFalse(instance.checkUserName(userTest));
    }
    @Test
   
    public void testCheckPaswordComplexityCorrect() { //checks password
        String firstName = "";
        String lastName = "";
        String username = "";
        String passwordTest = "Ch&&sec@ke99!";
        String cellNumber = "";

        Login instance = new Login(username, cellNumber, passwordTest, firstName, lastName);
        boolean expected = true;
        boolean actual = instance.checkPasswordComplexity(passwordTest);

        assertEquals(expected, actual);
        assertTrue(instance.checkPasswordComplexity(passwordTest));
    }
 @Test

    public void testCheckPaswordComplexityIncorrect() {
        String firstName = "";
        String lastName = "";
        String username = "";
        String passwordTest = "password";
        String cellNumber = "";

        Login instance = new Login(username, cellNumber, passwordTest, firstName, lastName);

        boolean expected = false;
        boolean actual = instance.checkPasswordComplexity(passwordTest);

        assertEquals(expected, actual);
        assertFalse(instance.checkPasswordComplexity(passwordTest));
    }

   
    @Test
   
    public void testCheckCellPhoneNumberCorrect() {  // ensures that cellphone number has the correct format
        String firstName = "";
        String lastName = "";
        String username = "";
        String password = "";
        String cellNumberTest = "+27728614789";

        Login instance = new Login(username, cellNumberTest, password, firstName, lastName);

        boolean expected = true;
        boolean actual = instance.checkCellPhoneNumber(cellNumberTest);

        assertEquals(expected, actual);
        assertTrue(instance.checkCellPhoneNumber(cellNumberTest));
    }
 @Test
   
    public void testCheckCellPhoneNumberIncorrect() { // rejects incorrect phon number
        String firstName = "";
        String lastName = "";
        String username = "";
        String password = "";
        String cellNumberTest = "02966773";

        Login instance = new Login(username, cellNumberTest, password, firstName, lastName);

        boolean expected = false;
        boolean actual = instance.checkCellPhoneNumber(cellNumberTest);

        assertEquals(expected, actual);
        assertFalse(instance.checkCellPhoneNumber(cellNumberTest));
    }

    @Test
    
    public void testLoginSuccessful() {
        String firstName = "";
        String lastName = "";
        String username = "kyl_e";
        String password = "Ferrer0rocher!";
        String cellNumber = "";
        String enteredUsername = "kyl_e";
        String enteredPassword = "Ferrer0rocher!";

        Login instance = new Login(username, cellNumber, password, firstName, lastName);

         assertTrue(instance.loginUser(username, password, enteredUsername, enteredPassword));
    }

    @Test
    public void testLoginFailed() {
        String firstName = "";
        String lastName = "";
        String username = "kyle!!!!!";
        String password = "T0blerone?";
        String cellNumber = "";
        String enteredUsername = "bobby";
        String enteredPassword = "Cadbury_2";

        Login instance = new Login(username, cellNumber, password, firstName, lastName);

        assertFalse(instance.loginUser(username, password, enteredUsername, enteredPassword));
    }
}
