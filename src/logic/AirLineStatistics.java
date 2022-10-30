package logic;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class AirLineStatistics {

    //mai jos trebuie private, nu public
    public static String findMostUsedCityAsDepartureForFlights (AirLineManager airLineManager){

        List<String> collectionOfAllCityNames = airLineManager.getAllFlights().stream()
                .map(flight -> flight.getFrom())
                .collect(Collectors.toList());//aici se face o lista cu orasele de plecare

        Map<String, Long> mapKeyValue = collectionOfAllCityNames.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));//aici se face un map cu key==fiecare oras, valoare==numarul de plecari din orasul respectiv

        int MostVisitedCityValueInMap = mapKeyValue.values().stream()
                .mapToInt(v -> Math.toIntExact(v))
                .max().orElseThrow(NoSuchElementException::new);//aici se afla valoarea cea mai mare, adica de unde pleaca cei mai multi oameni

        String mostDepartureCityName="noCityName";
        for (Map.Entry<String, Long> entry : mapKeyValue.entrySet()) {
            if (entry.getValue()==MostVisitedCityValueInMap) {
                mostDepartureCityName=entry.getKey();
            }
        }
        return mostDepartureCityName;
    }




}
