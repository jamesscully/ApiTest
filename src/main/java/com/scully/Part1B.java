package com.scully;

import com.scully.model.CarType;
import com.scully.model.SearchResult;
import com.scully.search.SearchTaxis;

import java.util.ArrayList;
import java.util.Map;

/**
 * Part B: Console application to filter by number of passengers
 */

public class Part1B {

    public static final String ARGS_FORMAT = "pickup_latitude pickup_longitude dropoff_latitude dropoff_longitude passengers";
    public static int passengers = 0;

    public static void main(String[] args) {

        // test that our number of args is valid, we want either 4 or 5
        if(args.length != 5) {
            throw new IllegalArgumentException("Incorrect number of arguments.\n Argument format: " + ARGS_FORMAT);
        }

        double  pLat = 0.0, pLng = 0.0,
                dLat = 0.0, dLng = 0.0;

        try {
            passengers = Integer.parseInt(args[4]);

            pLat = Double.parseDouble(args[0]);
            pLng = Double.parseDouble(args[1]);
            dLat = Double.parseDouble(args[2]);
            dLng = Double.parseDouble(args[3]);

        } catch (NumberFormatException e) {
            System.err.println("Could not parse arguments: ");
            e.printStackTrace();
            throw e;
        }

        SearchResult davesResults = SearchTaxis.query(SearchTaxis.SUP_DAVE, pLat, pLng, dLat, dLng, passengers);


        if(davesResults.errorCreating) {
            System.out.println("No results found for Dave's Taxis");
            return;
        }

        System.out.println(
                String.format("Results for %d passengers with Dave's Taxis:", passengers)
        );

        ArrayList<CarType> validTypes = CarType.getApplicableTypes(passengers);

        for(Map.Entry<CarType, Integer> entry : davesResults.getTripOptions().entrySet()) {

            CarType carType = CarType.Factory(entry.getKey().toString());
            int price = entry.getValue();

            // ignore those which we can't fit in
            if(!validTypes.contains(carType)) {
                continue;
            }

            System.out.println("\t" + carType + " - " + price);
        }








    }
}
