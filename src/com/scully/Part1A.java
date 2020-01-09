package com.scully;

import java.util.Map;

/**
 * Part A: Console application to print the search results for Dave's Taxis
 */
public class Part1A {

    public static final String ARGS_FORMAT = "pickup_latitude pickup_longitude dropoff_latitude dropoff_longitude";

    public static void main(String[] args) {

        // test that our number of args is valid, we want either 4 or 5
        if(args.length != 4) {
            throw new IllegalArgumentException("Incorrect number of arguments.\n Argument format: " + ARGS_FORMAT);
        }

        double  pLat = 0.0, pLng = 0.0,
                dLat = 0.0, dLng = 0.0;

        try {
            pLat = Double.parseDouble(args[0]);
            pLng = Double.parseDouble(args[1]);
            dLat = Double.parseDouble(args[2]);
            dLng = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            System.err.println("Could not parse arguments: ");
            e.printStackTrace();
            throw e;
        }

        SearchResult davesResults = SearchTaxis.query(SearchTaxis.SUP_DAVE, pLat, pLng, pLat, pLng);

        if(!davesResults.availableJourney) {
            System.out.println("No results found for Dave's Taxis");
            return;
        }

        System.out.println("Results for Dave's Taxis:");
        for(Map.Entry<CarType, Integer> entry : davesResults.getTripOptions().entrySet()) {
            String carType = entry.getKey().toString();
               int price = entry.getValue();

            System.out.println("\t" + carType + " - " + price);
        }
    }
}
