package utils;


import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class SecurityTest {

    @Test
    public void testHashPassword() throws Exception {
        assertTrue(Security.hashPassword("admin").length() > 0);
        assertFalse(Security.hashPassword("admin").equals("admin"));
    }

    @Test
    public void testIsPasswordCorrect() throws Exception {
        String password = "admin";
        String hashed = Security.hashPassword(password);
        assertTrue(Security.isPasswordCorrect(password, hashed));
    }

    @Test
    public void testIsPasswordValid() throws Exception {
        assertTrue(Security.isPasswordValid("Test1234!"));
        assertFalse(Security.isPasswordValid("test"));
    }

}