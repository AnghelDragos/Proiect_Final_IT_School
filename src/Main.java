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





    }



}
