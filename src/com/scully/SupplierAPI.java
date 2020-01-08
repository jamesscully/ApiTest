package com.scully;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Timer;

public class SupplierAPI {

    public static final String   API_BASE = "https://techtest.rideways.com/";

    public static final String   SUP_DAVE  = "dave/";
    public static final String   SUP_ERIC  = "eric/";
    public static final String   SUP_JEFF  = "jeff/";

    /**
     * Returns the JSON result from a supplier
     * @param supplier The suppliers endpoint from SUP_DAVE, SUP_ERIC or SUP_JEFF
     * @param pLat Pickup Latitude
     * @param pLng Pickup Longitude
     * @param dLat Dropoff Latitude
     * @param dLng Dropoff Longitude
     */
    public static void query(String supplier, double pLat, double pLng, double dLat, double dLng) {
        String endpoint   = API_BASE + supplier;
        String parameters = String.format("?pickup=%f,%f&dropoff=%f,%f", pLat, pLng, dLat, dLng);
        
        try {
            URL url = new URL(endpoint + parameters);


        } catch (MalformedURLException e) {
            System.err.println("Malformed url: " + endpoint + parameters);
            e.printStackTrace();
        }

    }


}
