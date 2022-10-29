package logic;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class AirLineStatistics {

    //mai jos trebuie private, nu public
    public static String findMostUsedCityAsDepartureForFlights (AirLineManager airLineManager){
        List<String> collectionOfAllCityNames = airLineManager.getAllFlights().stream()
                .map(flight -> flight.getFrom())
                .collect(Collectors.toList());

        Set<String> collectionOfSingleCityNames = airLineManager.getAllFlights().stream()
                .map(flight -> flight.getFrom())
                .collect(Collectors.toSet());

        System.out.println(collectionOfAllCityNames);
        return "placeholder";
    }




}
