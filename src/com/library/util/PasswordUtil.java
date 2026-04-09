package com.library.util;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    //  For hashing the password
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(5));
    }

    // To verify the password.
    public static boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
