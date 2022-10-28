import constants.Commands;
import logic.AirLineManager;
import logic.ReaderManager;

import java.util.Arrays;

import static constants.Commands.LOGIN;
import static constants.Commands.SIGNUP;

public class Main {

    public static void main(String[] args) {
        AirLineManager airLineManager = new AirLineManager();
        ReaderManager readerManager = new ReaderManager();

        String line = readerManager.readLine();
        while (line != null) {
            //procesam comanda de pe linie
            String[] arguments = line.split(" ");

            //EVENTUAL: tratat si in caz de exceptie sa se foloseasca o comanda default
            Commands command = Commands.valueOf(arguments[0]);

            switch(command) {
                case SIGNUP: {
                    //procesare specifica
                    airLineManager.signUp(arguments);
                    break;
                }
                case LOGIN: {
                    airLineManager.login(arguments);
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
                case DISPLAY_FLIGHTS: {
                    airLineManager.displayFlights();
                }
            }

            //trecem la urmatoarea linie
            line = readerManager.readLine();

        }

    }

}

/*
Mai sunt de implementat: la FileInfo lafel pentru partea de output. De preferat sa si creem output.txt pt ca asta nu trebuie
sa existe la inceput. Putem sa il steregem eventual.
Si: la AirlineManager sa implementam toate metodele, si sa folosim writer-ul pentru a scrie comenzile in loc de acest sout.

*/