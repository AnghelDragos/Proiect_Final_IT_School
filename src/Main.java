import constants.Commands;
import logic.*;

import static constants.Messages.*;

public class Main {

    public static void main(String[] args) {
        AirLineManager airLineManager = new AirLineManager();
        ReaderManager readerManager = new ReaderManager();
        WriterManager writerManager = new WriterManager();

        String line = readerManager.readLine();
        while (line != null) {
            String[] arguments = line.split(" ");

            try {
                Commands command = Commands.valueOf(arguments[0]);
                switch (command) {
                    case SIGNUP: {

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
                System.out.println("Input not recognised: "+arguments[0]);
                writerManager.write(wrongCommand2(arguments[0]));
                Commands command = Commands.valueOf("DEFAULT2");

            }


            line = readerManager.readLine();

        }





    }



}
