package constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


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

    public static String flightDoesNotExist(String str) {
        return "The flight with id " + str + " does not exist !";
    }


    public static String flightSuccessfullyDeleted(String str) {
        return "Flight with id " + str + " successfully deleted";
    }


    public static String cancelledFlightAndNotifyUsers(String str,int id){
        return "The user with email " +str+ " was notified that the flight with id " +id+ " was canceled!";
    }

    public static String displayFlightsInWriter(String from, String to, LocalDate date, int duration){
        return "Flight from " +from+ " to "+to+", date "+date+", duration "+duration;
    }


    public static String flightSuccessfullyAdded(String from, String to, LocalDate date, int duration){
        return "Flight from "+from+" to "+to+", date "+date+", duration "+duration+ " successfully added!";
    }


    public static String flightWithSameIdConflict(String str) {
        return "Cannot add flight! There is already a flight with id "+ str;
    }

    public static String notificationPersistFlights(LocalTime currentTime){
        return "The flights was successfully saved in the database at "+ currentTime + "!";
    }

    public static String notificationPersistUsers(LocalTime currentTime){
        return "The users was successfully saved in the database at "+ currentTime + "!";
    }

    public static String wrongCommand(Commands command){
        return "The provided command: "+command.toString()+" is not recognised";
    }
    public static String wrongCommand2(String s){
        return "The provided command: "+s+" is not recognised";
    }

    public static String loginSuccessful(String email) {
        LocalDateTime now = LocalDateTime.now();
        return "User with email " +email+ " is the current user started from "+ now;
    }

    public static String logoutSuccessful(String email) {
        LocalDateTime now = LocalDateTime.now();
        return "User with email "+ email +" successfully disconnected at "+ now;
    }

    public static String userWasNotConnected(String email) {
        return "The user with email "+email+" was not connected!";
    }

    public static String noConnectedUser(){
        return "There is no connected user!";
    }

    public static String notificationDisplayMyFlights(String from, String to, LocalDate date, int duration){
        return "Flight from " +from+" to "+to+", date "+date+", duration "+ String.valueOf(duration);
    }

    public static String flightWithIdDoesNotExist(String str){
        return "The flight with id "+str+ "does not exist!";
    }

    public static String flightAlreadyInUserFlightList(String str1, String str2){
        return "The user with email "+ str1+ " already have a ticket for flight with id "+str2;
    }
    public static String flightForUserAdded(String str1, String str2){
        return "The flight with id " + str1 + " was successfully added for user with email " + str2;
    }

    public static String flightNotInUserFlightList(String str1, String str2){
        return "The user with email "+str1+"does not have a ticket for the flight with id "+ str2;
    }

    public static String cancelTicket(String str1, String str2){
        return "The user with email "+ str1+" has successfully canceled his ticket for flight with id "+ str2;
    }
}
