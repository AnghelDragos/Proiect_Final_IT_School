package logic;

import constants.Commands;
import data.Flight;
import data.User;

import java.security.AllPermission;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static constants.Messages.wrongCommand;
import static constants.Messages.wrongCommand2;

public class AirLineStatistics {

    //mai jos este creat un main, pentru testare
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
                writerManager.write(wrongCommand2(arguments[0]));
                Commands command = Commands.valueOf("DEFAULT2");

            }

            //trecem la urmatoarea linie
            line = readerManager.readLine();

        }

        //mai jos sunt testate metodele
        String mostUsedCityAsDepartureForFlights = findMostUsedCityAsDepartureForFlights(airLineManager);
        System.out.println("Va returna orașul din care exista cele mai multe zboruri: ");
        System.out.println(mostUsedCityAsDepartureForFlights);
        System.out.println();

        User userWhoTravelTheMost = findUserWhoTravelTheMost(airLineManager);
        System.out.println("Va returna userul ale cărui zboruri insumeaza cele mai multe minute: ");
        System.out.println(userWhoTravelTheMost);
        System.out.println();

        List<User> userWhoTraveledToCity = findAllUsersWhoTraveledToCity(airLineManager, "Viena");
        System.out.println("Întoarce lista tuturor utilizatorilor care au calatorit în orașul trimis ca parametru: ");
        System.out.println(userWhoTraveledToCity);
        System.out.println();

        LocalDate startDate = LocalDate.parse("2022-11-11");
        LocalDate endDate = LocalDate.parse("2022-11-13");
        List<Flight> allFlightsBetweenDates = findAllFlightsBetweenDates(airLineManager, startDate, endDate);
        System.out.println("Întoarce toate zborurile care au avut loc între cele doua date calendaristice: ");
        System.out.println(allFlightsBetweenDates);
        System.out.println();


        Flight shortestFlight = findShortestFlight(airLineManager);
        System.out.println("Întoarce zborul cu durata cea mai scurta: ");
        System.out.println(shortestFlight);
        System.out.println();


        LocalDate localDate = LocalDate.parse("2022-11-12");
        List<User> allUsersWhoTraveledIn = findAllUsersWhoTraveledIn(airLineManager, localDate);
        System.out.println("Întoarce toți utilizatorii care au calatorit în acea zi: ");
        System.out.println(allUsersWhoTraveledIn);
        System.out.println();
    }




    private static String findMostUsedCityAsDepartureForFlights (AirLineManager airLineManager){

        List<String> collectionOfAllCityNames = airLineManager.getAllFlights().stream()
                .map(flight -> flight.getFrom())
                .collect(Collectors.toList());//aici se face o lista cu orasele de plecare

        Map<String, Long> mapKeyValue = collectionOfAllCityNames.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));//aici se face un map cu key==fiecare oras, valoare==numarul de plecari din orasul respectiv

        int mostVisitedCityValueInMap = mapKeyValue.values().stream()
                .mapToInt(v -> Math.toIntExact(v))
                .max().orElseThrow(NoSuchElementException::new);//aici se afla valoarea cea mai mare, adica numarul de plecari cel mai mare din oraspul cel mai popular

        String mostDepartureCityName="noCityName";
        for (Map.Entry<String, Long> entry : mapKeyValue.entrySet()) {
            if (entry.getValue()==mostVisitedCityValueInMap) {
                mostDepartureCityName=entry.getKey();
            }
        }
        return mostDepartureCityName;
    }


    private static User findUserWhoTravelTheMost(AirLineManager manager){
//Va returna userul ale cărui zboruri insumeaza cele mai multe minute (nu cel cu cele mai multe zboruri)
        List<User> allUsers = manager.getAllUsers();
        List<Flight> allFlights = manager.getAllFlights();

        Map<Integer, User> mapTotalDurationAndUser = new HashMap<>();
        for(User user:allUsers){
            int totalMinutesSpentFlying=0;
            List<Flight> userFlights = user.getUserFlights();
            for(Flight flight:userFlights){
                int duration = flight.getDuration();
                totalMinutesSpentFlying += duration;
            }
            mapTotalDurationAndUser.put(totalMinutesSpentFlying,user);
        }

        Optional<Integer> max = mapTotalDurationAndUser.keySet().stream()//aici avem numarul cel mai mare de ore zburate de catre un user (el nu este identificat aici)
                .max((integer, anotherInteger) -> integer.compareTo(anotherInteger));
        int numarulCelMaiMareDeOreZburate = max.get();


        String mailulUseruluiCautat="";
        for(User user:allUsers){//gasire emailul userului cu numarul cei mai mare de ore zburate
            int totalMinutesSpentFlying=0;
            List<Flight> userFlights = user.getUserFlights();
            for(Flight flight:userFlights){
                int duration = flight.getDuration();
                totalMinutesSpentFlying += duration;
            }
            if(totalMinutesSpentFlying==numarulCelMaiMareDeOreZburate){
                mailulUseruluiCautat = user.getEmail();
            }
        }

        String finalMailulUseruluiCautat = mailulUseruluiCautat;
        Optional<User> anyUser = allUsers.stream()//aici trebuie gasita o solutie sa match-uil userului cu "mailulUseruluiCautat" si in final sa returnam userul
                .filter((User u) -> {
                    return u.getEmail().equals(finalMailulUseruluiCautat);
                })
                .findAny();
        return anyUser.get();
    }


    private static List<User> findAllUsersWhoTraveledToCity(AirLineManager manager, String city){
        //Întoarce lista tuturor utilizatorilor care au calatorit în orașul trimis ca parametru (case insensitive - nu conteaza daca e scris cu majuscula sau nu)
        List<User> allUsers = manager.getAllUsers();
        List<Flight> allFlights = manager.getAllFlights();

        List<Flight> colectieDeZboruriCuDestinatiaOrasulCerut = manager.getAllFlights().stream()//aici sunt gasite zborurile care au destinatie orasul cerut
                .filter(flight -> flight.getTo().toString().equalsIgnoreCase(city))
                .collect(Collectors.toList());

        List<Integer> listWithIds = new ArrayList<>();
        for(Flight flightToDestinationCity: colectieDeZboruriCuDestinatiaOrasulCerut){
            int idPeRand=flightToDestinationCity.getId();
            listWithIds.add(idPeRand);//aici sunt adaugate id-urile zborurilor in un ArrayList
        }

        List<User> SetOfUsers=new ArrayList<>();
        for(int id:listWithIds){
            for(User u: allUsers){
                List<Flight> userFlights = u.getUserFlights();
                for(Flight f: userFlights){
                    if(f.getId()==id){
                        if(!SetOfUsers.contains(u)){
                            SetOfUsers.add(u);
                        }
                    }
                }
            }
        }
        return SetOfUsers;
    }


    private static List<Flight> findAllFlightsBetweenDates(AirLineManager manager, LocalDate startDate, LocalDate endDate){
        //Întoarce toate zborurile care au avut loc între cele doua date calendaristice
        List<Flight> allFlights = manager.getAllFlights();
        List<Flight> listaZboruri = allFlights.stream()
                .filter((Flight f) -> f.getDate().isAfter(startDate))
                .filter((Flight f) -> f.getDate().isBefore(endDate))
                .collect(Collectors.toList());

        return listaZboruri;
    }


    private static Flight findShortestFlight(AirLineManager manager){
        //Întoarce zborul cu durata cea mai scurta. Dacă sunt mai multe cu aceeași durată, se întoarce cel cu id-ul mai mic.
        List<Flight> allFlights = manager.getAllFlights();

        int minimumDuration=0;
        for(int i=0;i<allFlights.size()-1;i++){
            if(allFlights.get(i).getDuration()<allFlights.get(i+1).getDuration()){
                minimumDuration= allFlights.get(i).getDuration();
            }
        }

        int finalMinimumDuration = minimumDuration;
        List<Flight> listaZboururiCuDurataCeaMaiScurta = allFlights.stream()
                .filter(f -> f.getDuration() == finalMinimumDuration)
                .collect(Collectors.toList());

        int idCelMaiMic=listaZboururiCuDurataCeaMaiScurta.get(0).getId();
        for(Flight g:listaZboururiCuDurataCeaMaiScurta){
            if(g.getId()<idCelMaiMic){
                idCelMaiMic=g.getId();
            }
        }

        int finalIdCelMaiMic = idCelMaiMic;
        Optional<Flight> zborulCuDurataCeaMaiMicaSiCuIdCelMaiMic = allFlights.stream()
                .filter(f -> f.getId() == finalIdCelMaiMic)
                .findAny();
        return zborulCuDurataCeaMaiMicaSiCuIdCelMaiMic.get();
    }


    private static List<User> findAllUsersWhoTraveledIn(AirLineManager manager, LocalDate date){
        //Întoarce toți utilizatorii care au calatorit în acea zi.
        List<User> allUsers = manager.getAllUsers();
        List<User> useriCareAuCalatoritInAceaZi = allUsers.stream()
                .filter((User u) -> u.getUserFlights().stream().allMatch(f -> f.getDate().isEqual(date)))
                .collect(Collectors.toList());
        //System.out.println(useriCareAuCalatoritInAceaZi);
        return useriCareAuCalatoritInAceaZi;
    }

}
