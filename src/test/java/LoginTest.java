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
}
