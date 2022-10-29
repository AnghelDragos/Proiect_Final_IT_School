package constants;

import data.User;

public class Messages {
    public static String cannotAddUserPasswordDiff(){
        return "Cannot add user! The passwords are different!";
    }

    public static String cannotFindUserWithEmail(String email){
        return "Cannot find user with email: " + email;
    }

    public static String userAlreadyExists(String email){
        return "User already exists!" + email;
    }

    public static String userSuccessfullyAdded(String email){
        return "User with email: "+ email + " was successfully added!";
    }

    public static String incorrectPassword(){
        return "Incorrect password!";
    }

    public static String passwordTooShort(){
        return "Cannot add user! Password too short!";
    }

    public static String anotherUserAlreadyConnected(){
        return "Another user is already connected!";
    }

}
