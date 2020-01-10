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
    public static SearchResult query(String supplier, double pLat, double pLng, double dLat, double dLng) {
        String endpoint   = API_BASE + supplier;
        String parameters = String.format("?pickup=%f,%f&dropoff=%f,%f", pLat, pLng, dLat, dLng);

        StringBuilder outputJson = new StringBuilder();

        try {
            URL url = new URL(endpoint + parameters);

            System.out.println("Connecting to " + endpoint);
            // create a HTTP connection to the server; we can modify properties here
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(CONNECTION_TIMEOUT * 1000);

            int responseCode = connection.getResponseCode();

            // avoids running into exceptions
            if(responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println(
                        String.format("(HTTP Error %d) Unable to connect to API, ignoring %s", responseCode, endpoint)
                );
                return new SearchResult("");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // whilst most JSON is one-line; it's possible some day it might change.
            String line = "";
            while((line = reader.readLine()) != null) {
                outputJson.append(line);
            }

        } catch (MalformedURLException e) {
            System.err.println("Malformed url: " + endpoint + parameters);
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            System.err.println("Timed out connecting to url: " + endpoint);
        } catch (IOException e) {
            System.err.println("Received server error, ignoring this supplier: " + endpoint);
            e.printStackTrace();
        }

        return new SearchResult(outputJson.toString());
    }

}
