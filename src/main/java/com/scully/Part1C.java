package com.scully;

import com.scully.model.CarType;
import com.scully.model.Location;
import com.scully.model.SearchResult;
import com.scully.search.SearchTaxis;

import java.util.ArrayList;

/**
 * Part C?: For each car type, choose the cheapest supplier to include in your output. There should only be one supplier chosen per car type.
 */
public class Part1C {

    public static final String ARGS_FORMAT = "pickup (51,1), dropoff (51,2), passengers";
    public static int passengers = 1;

    public static void main(String[] args) {
        // test that our number of args is valid, we want 3
        if(!(args.length >= 2 && args.length < 4)) {
            throw new IllegalArgumentException("Incorrect number of arguments.\n Argument format: " + ARGS_FORMAT);
        }

        try {
            // we only want this if passengers is passed
            if(args.length > 2)
                passengers = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Could not parse arguments: ");
            e.printStackTrace();
            throw e;
        }

        Location pickup  = new Location(args[0]);
        Location dropoff = new Location(args[1]);

        SearchResult dave = SearchTaxis.query(SearchTaxis.SUP_DAVE, pickup, dropoff, passengers);

        SearchResult eric = SearchTaxis.query(SearchTaxis.SUP_ERIC, pickup, dropoff, passengers);
        SearchResult jeff = SearchTaxis.query(SearchTaxis.SUP_JEFF, pickup, dropoff, passengers);

        printCheapestSuppliers(dave, eric, jeff);
    }

    public static void printCheapestSuppliers(SearchResult... suppliers) {
        // reduces the need to go over un-needed types
        ArrayList<CarType> types = CarType.getApplicableTypes(passengers);

        System.out.println("Found cheapest results: ");

        // find the cheapest journey for a car type from all suppliers
        for(CarType c : types) {
            String cheapestSupplier = "";
            int    cheapestPrice    = Integer.MAX_VALUE;

            boolean modified = false;

            for(SearchResult supplier : suppliers) {
                // nothing to do if we don't have the type or there was an error
                if(!supplier.hasType(c) || supplier.errorCreating)
                    continue;

                modified = true;

                int price = supplier.getPriceByType(c);

                if(price < cheapestPrice) {
                    cheapestPrice    = price;
                    cheapestSupplier = supplier.supplierName;
                }
            }

            // we don't want to print if no one had this type
            if(modified) {
                System.out.println(String.format("\t%s - %s - %d", c.toString(), cheapestSupplier, cheapestPrice));
            }
        }
    }
}
