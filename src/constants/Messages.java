package constants;

public class Messages {
    public static String cannotAddUserPasswordDiff(){
        return "Cannot add user! The passwords are different!";
    }

    public static String cannotFindUserWithEmail(String email){
        return "Cannot find user with email: " + email;
    }
}
