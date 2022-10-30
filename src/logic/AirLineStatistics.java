package logic;

import data.Flight;
import data.User;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class AirLineStatistics {

    private static String findMostUsedCityAsDepartureForFlights (AirLineManager airLineManager){

        List<String> collectionOfAllCityNames = airLineManager.getAllFlights().stream()
                .map(flight -> flight.getFrom())
                .collect(Collectors.toList());//aici se face o lista cu orasele de plecare

        Map<String, Long> mapKeyValue = collectionOfAllCityNames.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));//aici se face un map cu key==fiecare oras, valoare==numarul de plecari din orasul respectiv

        int MostVisitedCityValueInMap = mapKeyValue.values().stream()
                .mapToInt(v -> Math.toIntExact(v))
                .max().orElseThrow(NoSuchElementException::new);//aici se afla valoarea cea mai mare, adica numarul de plecari cel mai mare din oraspul cel mai popular

        String mostDepartureCityName="noCityName";
        for (Map.Entry<String, Long> entry : mapKeyValue.entrySet()) {
            if (entry.getValue()==MostVisitedCityValueInMap) {
                mostDepartureCityName=entry.getKey();
            }
        }
        return mostDepartureCityName;
    }

    //TODO mai jos return type trebuie sa fie User ; metoda trebuie sa fie public
    public static void findUserWhoTravelTheMost(AirLineManager manager){
//Va returna userul ale cărui zboruri insumeaza cele mai multe minute (nu cel cu cele mai multe zboruri)
        List<User> allUsers = manager.getAllUsers();
        List<Flight> allFlights = manager.getAllFlights();

        //int maxMinutesSpentFlying=0; // nefolosit momentan
        Map<Integer, User> mapTotalDurationAndUser = new HashMap<>();
        for(User user:allUsers){
            int totalMinutesSpentFlying=0;
            List<Flight> userFlights = user.getUserFlights();
            for(Flight flight:userFlights){
                int duration = flight.getDuration();
                totalMinutesSpentFlying=totalMinutesSpentFlying+ duration;
            }
            mapTotalDurationAndUser.put(totalMinutesSpentFlying,user);
        }
        System.out.println(mapTotalDurationAndUser);

//        return User;
    }



}
