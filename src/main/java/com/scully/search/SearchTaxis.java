package com.scully.search;

import com.scully.model.CarType;
import com.scully.model.Location;
import com.scully.model.SearchResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class SearchTaxis {

    public static final String   API_BASE = "https://techtest.rideways.com/";

    public static final String   SUP_DAVE  = "dave/";
    public static final String   SUP_ERIC  = "eric/";
    public static final String   SUP_JEFF  = "jeff/";

    public static final String[] ALL_SUPPLIERS =  {SUP_DAVE, SUP_ERIC, SUP_JEFF};

    public static final int CONNECTION_TIMEOUT = 2;

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
            System.err.println("URL was malformed: ");
            e.printStackTrace();
        }

        // if we've reached here, we've encountered an error
        return new SearchResult("", passengers);
    }

    public static SearchResult query(String supplier, Location pickup, Location dropoff) {
        return query(supplier, pickup, dropoff, 1);
    }

    public static SearchResult queryAll(Location pickup, Location dropoff, int passengers) {
        String parameters = getApiParameters(pickup, dropoff);

        ArrayList<SearchResult> results = new ArrayList<>();

        for(String supplier : ALL_SUPPLIERS) {
            String endpoint = API_BASE + supplier;

            try {
                URL url = new URL(endpoint + parameters);
                SearchResult toAdd = new SearchResult(getResponseJson(url), 0);
                results.add(toAdd);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Got passengers: " + passengers);

        SearchResult.Builder all = findCheapestRides(CarType.getApplicableTypes(passengers), results);
        all.pickup(pickup.toString());
        all.dropoff(dropoff.toString());
        all.passengers(passengers);

        return all.build();
    }

    public static SearchResult queryAll(Location pickup, Location dropoff) {
        // we can assume that if no passengers set, there's only 1 person riding
        return queryAll(pickup, dropoff, 1);
    }

    private static SearchResult.Builder findCheapestRides(ArrayList<CarType> typesNeeded,
                                                  ArrayList<SearchResult> supplierResults)
    {
        SearchResult.Builder allSupplier = new SearchResult.Builder();

        for(CarType type : typesNeeded) {

            String cheapestSupplier = "";
            int    cheapestPrice    = Integer.MAX_VALUE;

            boolean modified = false;

            for(SearchResult supplier : supplierResults) {


                if(!supplier.hasType(type) || supplier.errorCreating) {
                    continue;
                }

                modified = true;

                int price = supplier.getPriceByType(type);

                if(price < cheapestPrice) {
                    cheapestPrice = price;
                    cheapestSupplier = supplier.supplierName;
                }
            }

            // by here, we've found the cheapest supplier for this type
            // if we have modified i.e. found and updated our cheapest prices, then we add it
            if(modified)
                allSupplier.option(type, cheapestPrice);
        }

        return allSupplier;
    }



    private static String getResponseJson(URL url) {

        StringBuilder outputJson = new StringBuilder();

        try {
            System.out.println("Connecting to " + url.toString());
            // create a HTTP connection to the server; we can modify properties here
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

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

        } catch (SocketTimeoutException e) {
            System.err.println("Timed out connecting to url: " + url);
        } catch (IOException e) {
            System.err.println("Received server error, ignoring this supplier: " + url);
            e.printStackTrace();
        }

        return outputJson.toString();
    }

    public static String getApiParameters(Location pickup, Location dropoff) {
        return String.format("?pickup=%f,%f&dropoff=%f,%f", pickup.lat, pickup.lng, dropoff.lat, dropoff.lng);
    }



}
