package com.scully;

import com.scully.model.CarType;
import com.scully.model.Location;
import com.scully.model.SearchResult;
import com.scully.search.SearchTaxis;

import java.util.ArrayList;
import java.util.Map;

/**
 * Part B: Console application to filter by number of passengers
 */

public class Part1B {

    public static final String ARGS_FORMAT = "pickup (51,1), dropoff (51,2), passengers";
    public static int passengers = 1;

    public static void main(String[] args) {

        // test that our number of args is valid, we want 3 or 4
        if(!(args.length >= 2 && args.length < 4)) {
            throw new IllegalArgumentException("Incorrect number of arguments.\n Argument format: " + ARGS_FORMAT);
        }

        Location pickup  = new Location(args[0]);
        Location dropoff = new Location(args[1]);

        try {

            if(args.length > 2)
                passengers = Integer.parseInt(args[2]);

        } catch (NumberFormatException e) {
            System.err.println("Could not parse arguments: ");
            e.printStackTrace();
            throw e;
        }

        SearchResult davesResults = SearchTaxis.query(SearchTaxis.SUP_DAVE, pickup, dropoff, passengers);

        if(davesResults.errorCreating) {
            System.out.println("No results found for Dave's Taxis");
            return;
        }

        System.out.println(
                String.format("Results for %d passengers with Dave's Taxis:", passengers)
        );

        ArrayList<CarType> validTypes = CarType.getApplicableTypes(passengers);

        for(Map.Entry<CarType, Integer> entry : davesResults.getTripOptions().entrySet()) {

            CarType carType = entry.getKey();
            int price = entry.getValue();

            // ignore those which we can't fit in
            if(!validTypes.contains(carType)) {
                continue;
            }

            System.out.println("\t" + carType + " - " + price);
        }
    }
}
