package com.scully.search;

import com.scully.model.CarType;
import com.scully.model.Location;
import com.scully.model.SearchResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

public class SearchTaxis {

    public static final String   API_BASE = "https://techtest.rideways.com/";

    public static final String   SUP_DAVE  = "dave/";
    public static final String   SUP_ERIC  = "eric/";
    public static final String   SUP_JEFF  = "jeff/";

    public static final String[] ALL_SUPPLIERS =  {SUP_DAVE, SUP_ERIC, SUP_JEFF};

    public static final int CONNECTION_TIMEOUT = 2;

    private static boolean SHOW_COMPARISONS = true;

    /**
     * Returns the JSON result from a supplier
     * @param supplier The suppliers endpoint from SUP_DAVE, SUP_ERIC or SUP_JEFF
     * @param pickup
     * @param dropoff
     */
    public static SearchResult query(String supplier, Location pickup, Location dropoff, int passengers) {
        String endpoint   = API_BASE + supplier;
        String parameters = getApiParameters(pickup, dropoff);

        try {
            URL url = new URL(endpoint + parameters);
            return new SearchResult(getResponseJson(url), passengers);
        } catch (MalformedURLException e) {
            // this should only occur if we've messed up, not the user
            System.err.println("URL was malformed, check SUP_X variables: ");
            e.printStackTrace();
        }

        // if we've reached here, we've encountered an error
        return new SearchResult("", passengers);
    }

    public static SearchResult query(String supplier, Location pickup, Location dropoff) {
        return query(supplier, pickup, dropoff, 1);
    }

    public static SearchResult queryAll(Location pickup, Location dropoff, int passengers) {
        ArrayList<SearchResult> results = new ArrayList<>();

        for(String supplier : ALL_SUPPLIERS) {
            results.add(
                    query(supplier, pickup, dropoff, passengers)
            );
        }

        StringBuilder allNames = new StringBuilder();

        for(String s : ALL_SUPPLIERS) {
            allNames.append(s.substring(0, s.length() - 1)).append(" ");
        }

        // creates a new SearchResult featuring all supplier names, pickup/dropoff and most importantly, cheapest rides.
        SearchResult.Builder all = findCheapestRides(CarType.getApplicableTypes(passengers), results);
            all.name(allNames.toString().trim());
            all.pickup(pickup.toString());
            all.dropoff(dropoff.toString());
            all.passengers(passengers);

        return all.build();
    }

    public static SearchResult queryAll(Location pickup, Location dropoff) {
        // we can assume that if no passengers set, there's only 1 person riding
        return queryAll(pickup, dropoff, 1);
    }

    public static SearchResult.Builder findCheapestRides(ArrayList<CarType> typesNeeded,
                                                  ArrayList<SearchResult> supplierResults)
    {
        SearchResult.Builder allSupplier = new SearchResult.Builder();


        // for all car types, search each supplier, update cheapest variables.

        for(CarType type : typesNeeded) {

            String cheapestSupplier = "NONE";
            int    cheapestPrice    = Integer.MAX_VALUE;

            boolean modified = false;

            for(SearchResult supplier : supplierResults) {

                // if we don't have the type, or an error occurred, skip this supplier
                if(!supplier.hasType(type) || supplier.errorCreating)
                    continue;

                // by this point, we're going to modify the cheapest variables.
                // saves comparing default str,price -> new str,price
                modified = true;

                // returns the price of the car type
                int price = supplier.getPriceByType(type);


                if(SHOW_COMPARISONS)
                    System.out.println(
                            String.format("DEBUG (comparing): CHEAPEST(%s, %s, PRICE: %d) WITH (%s, %s, PRICE: %d)", type, cheapestSupplier, cheapestPrice, type, supplier.supplierName, price)
                    );

                // if we've found a cheaper price, replace!
                if(price < cheapestPrice) {

                    if(SHOW_COMPARISONS)
                        System.out.println(
                                String.format("DEBUG (comparing): Cheapest supplier for %s is now %s with a price of %d (-%d)", type, supplier.supplierName, price, (cheapestPrice - price))
                        );

                    cheapestPrice = price;
                    cheapestSupplier = supplier.supplierName;
                }


            }

            // by here, we've found the cheapest supplier for this type
            // if we have modified i.e. found and updated our cheapest prices, then we add it
            if(modified)
                allSupplier.option(type, cheapestPrice);

            // print output if need be
            if(SHOW_COMPARISONS)
                System.out.println("DEBUG (comparing): FINISHED SEARCH FOR TYPE " + type + "\n\n");
        }

        return allSupplier;
    }

    private static String getResponseJson(URL url) {

        StringBuilder outputJson = new StringBuilder();

        try {
            System.out.println("Connecting to " + url.toString());
            // create a HTTP connection to the server; we can modify properties here
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // by default, 2 seconds. (timeout is measured in milliseconds)
            connection.setConnectTimeout(CONNECTION_TIMEOUT * 1000);

            int responseCode = connection.getResponseCode();

            // avoids running into exceptions
            if(responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println(
                        String.format("(HTTP Error %d) Unable to connect to API, ignoring %s", responseCode, url.toString())
                );
                return "";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // whilst most JSON is one-line; it's possible some day it might change.
            String line = "";
            while((line = reader.readLine()) != null) {
                outputJson.append(line);
            }

            // these shouldn't throw; they're unpredictable and other suppliers may respond
        } catch (SocketTimeoutException e) {
            System.err.println("Timed out connecting to url: " + url);
        } catch (IOException e) {
            System.err.println("Received server error, ignoring this supplier: " + url);
            e.printStackTrace();
        }

        return outputJson.toString();
    }

    /**
     * Generates the GET parameters
     * @param pickup
     * @param dropoff
     * @return
     */
    public static String getApiParameters(Location pickup, Location dropoff) {
        return String.format("?pickup=%f,%f&dropoff=%f,%f", pickup.lat, pickup.lng, dropoff.lat, dropoff.lng);
    }



}
