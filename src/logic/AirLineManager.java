package logic;

import data.Flight;
import data.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static constants.Messages.*;
import static data.FileInfo.createWriter;

public class AirLineManager {


    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/airline_reservation_system";
    private static final String USER="root";
    private static final String PASSWORD="";




    //BufferedWriter bufferedWriter1 = new BufferedWriter(createWriter());
    //private WriterManager writerManager = new WriterManager(bufferedWriter1);
    private WriterManager writerManager = new WriterManager();

    //colectie utilizatori
    private List<User> allUsers = new ArrayList<>();
    private User currentUser;
    //colectie zboruri
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

        //DONE, daca exista deja utilizatorul
        Optional<User> optionalUserGasit = allUsers.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
        if (!optionalUserGasit.isEmpty()) { // daca optional nu e gol, atunci utilizatorul exista deja
            writerManager.write(userAlreadyExists(email));
        }
        else {
        //validari
        if (!password.equals(password2)) {
            writerManager.write(cannotAddUserPasswordDiff()); // DONE, aici e deja adaugat de Costi writer manager!
        } else if (password.length() < 8) {
            writerManager.write(passwordTooShort());
        } else {
            //caz fericit
            User user = new User(email, name, password);
            //DONE, adaugat in colectia de useri
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
        if (optionalAllFlights.isEmpty()) {//aici nu exista zborul cu zbor_id
            writerManager.write(flightWithIdDoesNotExist(arguments[1]));
        }
        else{//aici exista zborul in lista de toate zborurile, si trebuie adaugat mai jos pentru user
            Optional<Flight> optionalUserFlight = currentUser.getUserFlights().stream()
                    .filter(flight -> flight.getId() ==  Integer.parseInt(arguments[1]))
                    .findAny();
            if(!optionalUserFlight.isEmpty()){//aici s-a gasit zborul deja in lista userului
                writerManager.write(flightAlreadyInUserFlightList(currentUser.getEmail(), arguments[1]));
            }
            else{//aici se adauga zborul in lista userului de zboruri
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
        if (optionalAllFlights.isEmpty()) {//aici nu exista zborul cu zbor_id
            writerManager.write(flightWithIdDoesNotExist(arguments[1]));
        } else {
            Optional<Flight> optionalUserFlight = currentUser.getUserFlights().stream()
                    .filter(flight -> allFlights.contains(flight))// luam toate zborurile userului, si le comparam cu zborul cu id-ul cerut
                    .findAny();
            if (optionalUserFlight.isEmpty()) {//aici userul nu are bilet pe avionul cu flight_id
                writerManager.write(flightNotInUserFlightList(currentUser.getEmail(), arguments[1]));
            }
            else{//aici userul are bilet de avion, si trebuie anulat zborul mai jos
                currentUser.getUserFlights().remove(optionalAllFlights); // sper sa mearga optional aici
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

        // anunt toti userii
        for(User user: allUsers){
            if(user.getUserFlights().contains(flight)){
                user.deleteFlight(flight);
                writerManager.write(cancelledFlightAndNotifyUsers(user.getEmail(), flight.getId()));
            }
        }
    }

    public void persistFlights(){
        //Se vor adaugă în baza de date toate zborurile și se va scrie: „The flights was successfully saved in the database at <current_time>!”
        LocalTime currentTime = LocalTime.now();
        writerManager.write(notificationPersistFlights(currentTime));

        for(Flight f:allFlights){
            try(Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
//            PreparedStatement preparedStatement = connection.prepareStatement(prepareStatement);
//            CallableStatement clbStatement = connection.prepareCall(storedProcedureCall)
            ) {
//            statementInsert();
//            statementSelect(statement);
//            statementUpdateNext(statement);
//            preparedStatement(preparedStatement);
//            callableStatement(clbStatement);

                String testStringFlights = "INSERT INTO flights VALUES ('1', 'flight_from','flight_to', '1998/2/24', '120')";
                statement.executeUpdate(testStringFlights); // TODO poate pe asta il lasi in main (trebuie verificat cum il facem sa porneasca)
            }
            catch(SQLException throwables){
                throwables.printStackTrace();
            }

            /*
            //TODO aici jos e copiat din Main2_de_sters
            private static void statementUpdateNext(Statement statement) throws SQLException {
                String select= "SELECT * from students";
                ResultSet resultSet = statement.executeQuery(select);
                resultSet.moveToInsertRow();

                resultSet.updateInt(1,id);
                resultSet.updateString("firstname","Andrei");
                resultSet.updateString("lastname", "Mircea");
                resultSet.updateString(4,email);
                resultSet.updateDate(5,Date.valueOf("1997-10-10"));
                resultSet.insertRow();
            }

            private static void callableStatement(CallableStatement clbStatement) throws SQLException {
                clbStatement.setString(1, "alexandru.marin@gmail.com");
                ResultSet resultSet = clbStatement.executeQuery();
            }

            private static void preparedStatement(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,"alexandru.marin@gmail.com");
                ResultSet resultSet = preparedStatement.executeQuery();
                preparedStatement.setString(1,"marinica@gmail.com");
                preparedStatement.executeQuery();
            }

            private static void statementSelect(Statement statement) throws SQLException {
                String select = "SELECT * from students";
                try (ResultSet resultSet = statement.executeQuery(select)){
                    List<Student_de_sters> studentList = new ArrayList<>();
                    while(resultSet.next()){
                        Student_de_sters student = new Student_de_sters();
                        student.setId(resultSet.getInt("id"));
                        student.setFirstName(resultSet.getString(2));
                        student.setLastName(resultSet.getString(3));
                        student.setEmail(resultSet.getString(4));
                        student.setBirthdate(resultSet.getDate(5));
                        studentList.add(student);
                    }
                    studentList.forEach(System.out::println);//afiseaza toti studentii din tabela noastra(ii ia din baza de date)
                };
            }

            private static void statementInsert() {
                String insertAStudent = "INSERT INTO students VALUES (null, 'Ionut', 'Marin', 'marinica@gmail.com', '1998/2/24')";
                String insertAPerson = "INSERT INTO people VALUES (null, 'Ionut', 'Marin', 'marinica@gmail.com')";

                //statement.executeUpdate(insertAStudent);
                //statement.executeUpdate(insertAPerson);
            }
        //TODO aici sus e copiat din Main2_de_sters
        */

        }
    }


    public void persistUsers(){
        //„The users was successfully saved in the database at <current_time>!”
        //TODO "ALTER TABLE `table_name` AUTO_INCREMENT=1" pentru a reseta id-ul userilor nou adaugati
        LocalTime currentTime = LocalTime.now();
        writerManager.write(notificationPersistUsers(currentTime));

        for(User u: allUsers){

        }
    }


    public void displayFlights() {
        allFlights.stream()
                .forEach(t-> writerManager.write(displayFlightsInWriter(t.getFrom(), t.getTo(), t.getDate(), t.getDuration())));
        //writerManager.flush();
    }
}
