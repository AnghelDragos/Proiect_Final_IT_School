package logic;

import constants.Commands;
import data.Flight;
import data.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static constants.Messages.wrongCommand;
import static constants.Messages.wrongCommand2;

public class AirLineStatistics {
    //we create another main just to test the statistics
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
                writerManager.write(wrongCommand2(arguments[0]));
                Commands command = Commands.valueOf("DEFAULT2");

            }

            line = readerManager.readLine();

        }

        String mostUsedCityAsDepartureForFlights = findMostUsedCityAsDepartureForFlights(airLineManager);
        System.out.println("Returns the city from which the most flights are from: ");
        System.out.println(mostUsedCityAsDepartureForFlights);
        System.out.println();

        User userWhoTravelTheMost = findUserWhoTravelTheMost(airLineManager);
        System.out.println("Returns the user that is in flight te most amount of time: ");
        System.out.println(userWhoTravelTheMost);
        System.out.println();

        List<User> userWhoTraveledToCity = findAllUsersWhoTraveledToCity(airLineManager, "London");
        System.out.println("Returns a list with users that have traveled to the city sent as parameter: ");
        System.out.println(userWhoTraveledToCity);
        System.out.println();

        LocalDate startDate = LocalDate.parse("2022-11-11");
        LocalDate endDate = LocalDate.parse("2022-11-16");
        List<Flight> allFlightsBetweenDates = findAllFlightsBetweenDates(airLineManager, startDate, endDate);
        System.out.println("Returns all flights that have taken place between the two inputed dates: ");
        System.out.println(allFlightsBetweenDates);
        System.out.println();


        Flight shortestFlight = findShortestFlight(airLineManager);
        System.out.println("Returns the flight with the shortest duration: ");
        System.out.println(shortestFlight);
        System.out.println();


        LocalDate localDate = LocalDate.parse("2022-11-15");
        List<User> allUsersWhoTraveledIn = findAllUsersWhoTraveledIn(airLineManager, localDate);
        System.out.println("Returns all users who have traveled in specified day: ");
        System.out.println(allUsersWhoTraveledIn);
        System.out.println();
    }




    private static String findMostUsedCityAsDepartureForFlights (AirLineManager airLineManager){

        List<String> collectionOfAllCityNames = airLineManager.getAllFlights().stream()
                .map(flight -> flight.getFrom())
                .collect(Collectors.toList());

        Map<String, Long> mapKeyValue = collectionOfAllCityNames.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        int mostVisitedCityValueInMap = mapKeyValue.values().stream()
                .mapToInt(v -> Math.toIntExact(v))
                .max().orElseThrow(NoSuchElementException::new);

        String mostDepartureCityName="noCityName";
        for (Map.Entry<String, Long> entry : mapKeyValue.entrySet()) {
            if (entry.getValue()==mostVisitedCityValueInMap) {
                mostDepartureCityName=entry.getKey();
            }
        }
        return mostDepartureCityName;
    }


    private static User findUserWhoTravelTheMost(AirLineManager manager){

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

        Optional<Integer> max = mapTotalDurationAndUser.keySet().stream()
                .max((integer, anotherInteger) -> integer.compareTo(anotherInteger));
        int numarulCelMaiMareDeOreZburate = max.get();


        String mailulUseruluiCautat="";
        for(User user:allUsers){
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
        Optional<User> anyUser = allUsers.stream()
                .filter((User u) -> {
                    return u.getEmail().equals(finalMailulUseruluiCautat);
                })
                .findAny();
        return anyUser.get();
    }


    private static List<User> findAllUsersWhoTraveledToCity(AirLineManager manager, String city){

        List<User> allUsers = manager.getAllUsers();
        List<Flight> allFlights = manager.getAllFlights();

        List<Flight> colectieDeZboruriCuDestinatiaOrasulCerut = manager.getAllFlights().stream()
                .filter(flight -> flight.getTo().toString().equalsIgnoreCase(city))
                .collect(Collectors.toList());

        List<Integer> listWithIds = new ArrayList<>();
        for(Flight flightToDestinationCity: colectieDeZboruriCuDestinatiaOrasulCerut){
            int idPeRand=flightToDestinationCity.getId();
            listWithIds.add(idPeRand);
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

        List<Flight> allFlights = manager.getAllFlights();
        List<Flight> listaZboruri = allFlights.stream()
                .filter((Flight f) -> f.getDate().isAfter(startDate))
                .filter((Flight f) -> f.getDate().isBefore(endDate))
                .collect(Collectors.toList());

        return listaZboruri;
    }


    private static Flight findShortestFlight(AirLineManager manager){

        List<Flight> allFlights = manager.getAllFlights();

        int minimumDuration=0;
        for(int i=0;i<allFlights.size()-1;i++){
            if(allFlights.get(i).getDuration()<allFlights.get(i+1).getDuration()){
                minimumDuration= allFlights.get(i).getDuration();
            }
            else{
                minimumDuration= allFlights.get(i+1).getDuration();
            }
        }

        int finalMinimumDuration = minimumDuration;
        List<Flight> listaZboururiCuDurataCeaMaiScurta = allFlights.stream()
                .filter(f -> f.getDuration() == finalMinimumDuration)
                .collect(Collectors.toList());


        int idCelMaiMic=1;
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

        List<User> allUsers = manager.getAllUsers();
        List<User> useriCareAuCalatoritInAceaZi = allUsers.stream()
                .filter((User u) -> u.getUserFlights().stream().anyMatch((f -> f.getDate().isEqual(date))))
                .collect(Collectors.toList());

        return useriCareAuCalatoritInAceaZi;
    }

}
