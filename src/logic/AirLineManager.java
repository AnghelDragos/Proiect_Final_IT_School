package logic;

import data.Flight;
import data.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static constants.Messages.*;

public class AirLineManager {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/airline_reservation_system";
    private static final String USER="root";
    private static final String PASSWORD="";

    private WriterManager writerManager = new WriterManager();

    private List<User> allUsers = new ArrayList<>();
    private User currentUser;
    private List<Flight> allFlights = new ArrayList<>();

    public List<Flight> getAllFlights() {
        return allFlights;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void signUp(String[] arguments) {
        String email = arguments[1];
        String name = arguments[2];
        String password = arguments[3];
        String password2 = arguments[4];

        Optional<User> optionalUserFound = allUsers.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
        if (!optionalUserFound.isEmpty()) {
            writerManager.write(userAlreadyExists(email));
        }
        else {

        if (!password.equals(password2)) {
            writerManager.write(cannotAddUserPasswordDiff());
        } else if (password.length() < 8) {
            writerManager.write(passwordTooShort());
        } else {

            User user = new User(email, name, password);
            allUsers.add(user);
            writerManager.write(userSuccessfullyAdded(email));
        }
    }
    }

    public void login(String[] arguments) {
        String email = arguments[1];
        String parola = arguments[2];

        Optional<User> optionalUser = allUsers.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if(optionalUser.isEmpty()){
            writerManager.write(cannotFindUserWithEmail(email));
            return;
        }

        User user = optionalUser.get();
        if (!user.getParola().equals(parola)){
            writerManager.write(incorrectPassword());
            return;
        }

        if(currentUser != null){
            writerManager.write(anotherUserAlreadyConnected());
            return;
        }
        writerManager.write(loginSuccessful(user.getEmail()));
        currentUser = user;
    }

    public void logout(String[] arguments){
        if(currentUser.getEmail().equals(arguments[1])){
            writerManager.write(logoutSuccessful(arguments[1]));
            currentUser = null;
        }
        else{
            writerManager.write(userWasNotConnected(arguments[1]));
        }

    }

    public void displayMyFlights(){
        if(currentUser == null){
            writerManager.write(noConnectedUser());
        }
        else{
            currentUser.getUserFlights().stream()
                            .forEach(t->writerManager.write(notificationDisplayMyFlights(t.getFrom(), t.getTo(), t.getDate(), t.getDuration())));
        }
    }

    public void addFlightForUser(String[] arguments){
        if(currentUser == null){
            writerManager.write(noConnectedUser());
            return;
        }

        Optional<Flight> optionalAllFlights = allFlights.stream()
                .filter(flight -> flight.getId() == Integer.parseInt(arguments[1]))
                .findAny();
        if (optionalAllFlights.isEmpty()) {
            writerManager.write(flightWithIdDoesNotExist(arguments[1]));
        }
        else{
            Optional<Flight> optionalUserFlight = currentUser.getUserFlights().stream()
                    .filter(flight -> flight.getId() ==  Integer.parseInt(arguments[1]))
                    .findAny();
            if(!optionalUserFlight.isEmpty()){
                writerManager.write(flightAlreadyInUserFlightList(currentUser.getEmail(), arguments[1]));
            }
            else{
                for(Flight flight:allFlights){
                    if(flight.getId()==Integer.parseInt(arguments[1])){
                        currentUser.addFlight(flight);
                    }
                }
                writerManager.write(flightForUserAdded(arguments[1], currentUser.getEmail()));
            }

        }

    }
    public void cancelFlightForUser(String[] arguments) {
        if (currentUser == null) {
            writerManager.write(noConnectedUser());
            return;
        }

        Optional<Flight> optionalAllFlights = allFlights.stream()
                .filter(flight -> flight.getId() == Integer.parseInt(arguments[1]))
                .findAny();
        if (optionalAllFlights.isEmpty()) {
            writerManager.write(flightWithIdDoesNotExist(arguments[1]));
        } else {
            Optional<Flight> optionalUserFlight = currentUser.getUserFlights().stream()
                    .filter(flight -> allFlights.contains(flight))
                    .findAny();
            if (optionalUserFlight.isEmpty()) {
                writerManager.write(flightNotInUserFlightList(currentUser.getEmail(), arguments[1]));
            }
            else{
                currentUser.getUserFlights().remove(optionalAllFlights.get());
                writerManager.write(cancelTicket(currentUser.getEmail(), arguments[1]));
            }

        }
    }

    public void addFlight(String[] arguments) {
        Optional<Flight> optionalFlight = allFlights.stream()
                .filter(flight -> flight.getId() == Integer.parseInt(arguments[1]))
                .findAny();
        if (optionalFlight.isEmpty()) {
        Flight flight = new Flight(Integer.parseInt(arguments[1]), arguments[2], arguments[3], LocalDate.parse(arguments[4]), Integer.parseInt((arguments[5])));
        allFlights.add(flight);
        writerManager.write(flightSuccessfullyAdded(flight.getFrom(), flight.getTo(), flight.getDate(), flight.getDuration()));
        }
        else{
            writerManager.write(flightWithSameIdConflict(arguments[1]));
        }
    }

    public void deleteFlight(String[] arguments) {
        Optional<Flight> optionalFlight = allFlights.stream()
                .filter(flight -> flight.getId() == Integer.parseInt(arguments[1]))
                .findFirst();
        if(optionalFlight.isEmpty()){
            writerManager.write(flightDoesNotExist(arguments[1]));
            return;
        }

        Flight flight = optionalFlight.get();
        allFlights.remove(flight);
        writerManager.write(flightSuccessfullyDeleted(arguments[1]));


        for(User user: allUsers){
            if(user.getUserFlights().contains(flight)){
                user.deleteFlight(flight);
                writerManager.write(cancelledFlightAndNotifyUsers(user.getEmail(), flight.getId()));
            }
        }
    }

    public void persistFlights(){

        LocalTime currentTime = LocalTime.now();
        writerManager.write(notificationPersistFlights(currentTime));

            try(Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
            ) {

                for(Flight f:allFlights) {
                    String select = "SELECT * from flights";
                    ResultSet resultSet = statement.executeQuery(select);
                    resultSet.moveToInsertRow();
                    resultSet.updateInt("id", f.getId());
                    resultSet.updateString("flight_from", f.getFrom());
                    resultSet.updateString("flight_to", f.getTo());
                    resultSet.updateDate("date", Date.valueOf(f.getDate()));
                    resultSet.updateInt("duration", f.getDuration());
                    resultSet.insertRow();
                }
            }
            catch(SQLException throwables){
                throwables.printStackTrace();
            }
    }


    public void persistUsers(){

        LocalTime currentTime = LocalTime.now();
        writerManager.write(notificationPersistUsers(currentTime));

        try(Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        ) {
            for(User u:allUsers) {
                String select = "SELECT * from users";
                ResultSet resultSet = statement.executeQuery(select);
                resultSet.moveToInsertRow();
                resultSet.updateString("email", u.getEmail());
                resultSet.updateString("user_name", u.getEmail());
                resultSet.updateString("user_password", u.getParola());
                resultSet.insertRow();
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }


    public void displayFlights() {
        allFlights.stream()
                .forEach(t-> writerManager.write(displayFlightsInWriter(t.getFrom(), t.getTo(), t.getDate(), t.getDuration())));

    }
}
