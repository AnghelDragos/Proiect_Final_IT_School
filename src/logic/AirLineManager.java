package logic;

import data.Flight;
import data.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static constants.Messages.*;
import static data.FileInfo.createWriter;

public class AirLineManager {
    //TODO update constructor writerManager

    BufferedWriter bufferedWriter1 = new BufferedWriter(createWriter());
    private WriterManager writerManager = new WriterManager(bufferedWriter1);


    //colectie utilizatori
    private List<User> allUsers = new ArrayList<>();
    private User currentUser;
    //colectie zboruri
    private List<Flight> allFlights = new ArrayList<>();

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
        currentUser = user;

    }

    public void logout(String[] arguments){
        //TODO sa facem null userul curent daca sunt verificate toate cerintele
        currentUser = null;
    }

    //TODO de implementat cu validari
    public void addFlight(String[] arguments) {
        Flight flight = new Flight(Integer.parseInt(arguments[1]),arguments[2], arguments[3], LocalDate.parse(arguments[4]), Integer.parseInt((arguments[5]))); //aici nu foloseste informatiile din input -> trebuie facut....
        allFlights.add(flight);
    }

    //TODO mai multe validari. Sa verificam daca zborul exista. Daca zborul NU exista, atunci nu avem cum sa il stergem.
    // in cazul in care el exista, atunci scriem mesajul ca a fost sters
    //iar toti utilizatorii care aveau deja bilet vor fi notificati. Deci se va scrie pentru fiecare utilizator
    // un anumit mesaj. Si se va sterge aceasta intrare din zborurile utilizatorilor
    public void deleteFlight(String[] arguments) {
        Optional<Flight> optionalFlight = allFlights.stream()
                .filter(flight -> flight.getId() == Integer.parseInt(arguments[1]))
                .findFirst();
        if(optionalFlight.isEmpty()){
            System.out.println("The flight with id " + arguments[1] + " does not exist!"); // TODO writer punct write
            return;
        }

        Flight flight = optionalFlight.get();
        allFlights.remove(flight);
        System.out.println("Flight with id " + arguments[1]+ " was successfully deleted");

        // anunt toti userii
        for(User user: allUsers){
            if(user.getUserFlights().contains(flight)){
                user.deleteFlight(flight);
                System.out.println("The user with email was notified that the flight with id was cancelled!"); //TODO writer punct write
            }
        }
    }

    public void displayFlights() {
        allFlights.stream().forEach(t-> writerManager.write(t.toString()));
        writerManager.flush();
        System.out.println("All flights had been printed");
    }
}
