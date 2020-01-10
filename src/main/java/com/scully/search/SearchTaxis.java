package com.scully.search;

import com.scully.model.SearchResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class SearchTaxis {

    public static final String   API_BASE = "https://techtest.rideways.com/";

    public static final String   SUP_DAVE  = "dave/";
    public static final String   SUP_ERIC  = "eric/";
    public static final String   SUP_JEFF  = "jeff/";

    public static final int CONNECTION_TIMEOUT = 2;

    /**
     * Returns the JSON result from a supplier
     * @param supplier The suppliers endpoint from SUP_DAVE, SUP_ERIC or SUP_JEFF
     * @param pLat Pickup Latitude
     * @param pLng Pickup Longitude
     * @param dLat Dropoff Latitude
     * @param dLng Dropoff Longitude
     */
    public static SearchResult query(String supplier, double pLat, double pLng, double dLat, double dLng, int passengers) {
        String endpoint   = API_BASE + supplier;
        String parameters = String.format("?pickup=%f,%f&dropoff=%f,%f", pLat, pLng, dLat, dLng);

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

    public static SearchResult query(String supplier, double pLat, double pLng, double dLat, double dLng) {
        String endpoint   = API_BASE + supplier;
        String parameters = String.format("?pickup=%f,%f&dropoff=%f,%f", pLat, pLng, dLat, dLng);

        try {
            URL url = new URL(endpoint + parameters);
            return new SearchResult(getResponseJson(url), 0);
        } catch (MalformedURLException e) {
            System.err.println("URL was malformed: ");
            e.printStackTrace();
        }

        // if we've reached here, we've encountered an error.
        return new SearchResult("", 0);
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



}
