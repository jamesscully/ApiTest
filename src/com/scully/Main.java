package com.scully;

import java.text.ParseException;

public class Main {

    // args format: [passengers] [pickup_lat] [pickup_lng] [dropoff_lat] [dropoff_lng] (we'll start with this one)
    // alternatively we could do: pickup[lat,lng] dropoff[lat,lng]
    public static void main(String[] args) {


        // test that our args are valid
        if(args.length != 5) {
            System.err.println("Incorrect number of arguments");
            System.err.println("Argument format: pickup_latitude pickup_longitude dropoff_latitude dropoff_longitude");
            System.exit(1);
        }

        int passengers = 0;

        double  pLat = 0.0, pLng = 0.0,
                dLat = 0.0, dLng = 0.0;

        try {
            passengers = Integer.parseInt(args[0]);

            pLat = Double.parseDouble(args[1]);
            pLng = Double.parseDouble(args[2]);
            dLat = Double.parseDouble(args[3]);
            dLng = Double.parseDouble(args[4]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid argument(s) passed");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println(
                String.format("Pickup Location: (%f,%f)", pLat, pLng)
        );
        System.out.println(
                String.format("Dropoff Location: (%f,%f)", dLat, dLng)
        );









    }
}
