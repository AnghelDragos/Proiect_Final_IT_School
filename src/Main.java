import constants.Commands;
import logic.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static constants.Commands.LOGIN;
import static constants.Commands.SIGNUP;
import static constants.Messages.*;

public class Main {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/airline_reservation_system";
    private static final String USER="root";
    private static final String PASSWORD="";


    public static void main(String[] args) {
        AirLineManager airLineManager = new AirLineManager();
        ReaderManager readerManager = new ReaderManager();
        WriterManager writerManager = new WriterManager();

        String line = readerManager.readLine();
        while (line != null) {
            String[] arguments = line.split(" ");
            //EVENTUAL: tratat si in caz de exceptie sa se foloseasca o comanda default
            //Commands command = Commands.valueOf(arguments[0]);
            try {
                Commands command = Commands.valueOf(arguments[0]);
                switch (command) {
                    case SIGNUP: {
                        //procesare specifica
                        airLineManager.signUp(arguments);
                        break;
                    }
                    case LOGIN: {
                        airLineManager.login(arguments);
                        break;
                    }
                    case LOGOUT: {
                        airLineManager.logout(arguments);
                        break;
                    }
                    case ADD_FLIGHT: {
                        airLineManager.addFlightForUser(arguments);
                        break;
                    }
                    case CANCEL_FLIGHT: {
                        airLineManager.cancelFlightForUser(arguments);
                        break;
                    }
                    case ADD_FLIGHT_DETAILS: {
                        airLineManager.addFlight(arguments);
                        break;
                    }
                    case DELETE_FLIGHT: {
                        airLineManager.deleteFlight(arguments);
                        break;
                    }
                    case DISPLAY_MY_FLIGHTS: {
                        airLineManager.displayMyFlights();
                        break;
                    }
                    case DISPLAY_FLIGHTS: {
                        airLineManager.displayFlights();
                        break;
                    }
                    case PERSIST_FLIGHTS: {
                        airLineManager.persistFlights();
                        break;
                    }
                    case PERSIST_USERS: {
                        airLineManager.persistUsers();
                        break;
                    }

                    case DEFAULT2: {
                        writerManager.write(wrongCommand(command));
                        break;
                    }

                    default: {
                        writerManager.write(wrongCommand(command));
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Comanda introdusa nu este recunoscuta: "+arguments[0]);
                writerManager.write(wrongCommand2(arguments[0]));
                Commands command = Commands.valueOf("DEFAULT2");

            }

            //trecem la urmatoarea linie
            line = readerManager.readLine();

        }




        try(Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        ){
//            String testStringUsers = "INSERT INTO users VALUES (null, 'emailtest3@gmail.com','Marin','parolaMarin')";
//            statement.executeUpdate(testStringUsers);
//
//            String testStringFlights = "INSERT INTO flights VALUES ('1', 'flight_from','flight_to', '1998/2/24', '120')";
//            statement.executeUpdate(testStringFlights);

        } catch(SQLException throwables){
            throwables.printStackTrace();
        }



    }//sus e in interiorul main-ului

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
    resultSet.updateDate(5, Date.valueOf("1997-10-10"));
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

}//sus e in clasa main, in afara main-ului
