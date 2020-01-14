package com.scully;

import com.scully.model.CarType;
import com.scully.model.Location;
import com.scully.model.SearchResult;
import com.scully.search.SearchTaxis;

import java.util.Map;

/**
 * Part A: Console application to print the search results for Dave's Taxis
 */
public class Part1A {

    public static final String ARGS_FORMAT  = "[pickup] 51,1, [dropoff] 51,2";
    public static final String ARGS_EXAMPLE = "51,1 51,2";

    public static int passengers = 1;
    public static void main(String[] args) {

        // test that our number of args is valid, we want 2
        if(args.length != 2) {
            throw new IllegalArgumentException("Incorrect number of arguments.\n Argument format: " + ARGS_FORMAT + "\n" + "Example: " + ARGS_EXAMPLE);
        }

        Location pickup  = new Location(args[0]);
        Location dropoff = new Location(args[1]);

        SearchResult davesResults = SearchTaxis.query(SearchTaxis.SUP_DAVE, pickup, dropoff, passengers);

        if(davesResults.errorCreating) {
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
