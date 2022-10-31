import constants.Commands;
import logic.AirLineManager;
import logic.AirLineStatistics;
import logic.ReaderManager;
import logic.WriterManager;

import java.util.Arrays;

import static constants.Commands.LOGIN;
import static constants.Commands.SIGNUP;
import static constants.Messages.userAlreadyExists;
import static constants.Messages.wrongCommand;

public class Main {

    public static void main(String[] args) {
        AirLineManager airLineManager = new AirLineManager();
        ReaderManager readerManager = new ReaderManager();
        WriterManager writerManager = new WriterManager();

        String line = readerManager.readLine();
        while (line != null) {
            //procesam comanda de pe linie
            String[] arguments = line.split(" ");

            //EVENTUAL: tratat si in caz de exceptie sa se foloseasca o comanda default
            Commands command = Commands.valueOf(arguments[0]);
//            try (Commands command = Commands.valueOf(arguments[0])){
//            } catch (IllegalArgumentException e) {
//                throw new RuntimeException(e);
//            }

            try {
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

                    default: {
                        writerManager.write(wrongCommand(command));
                        System.out.println(command);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //trecem la urmatoarea linie
            line = readerManager.readLine();

        }

        AirLineStatistics airLineStatistics1 = new AirLineStatistics();
        //airLineStatistics1.findMostUsedCityAsDepartureForFlights(airLineManager);
        airLineStatistics1.findAllUsersWhoTraveledToCity(airLineManager,"Viena");

    }
}
