package com.scully;

import java.text.ParseException;

public class Main {

    // args format: [pickup_lat] [pickup_lng] [dropoff_lat] [dropoff_lng] (we'll start with this one)
    // alternatively we could do: pickup[lat,lng] dropoff[lat,lng]
    public static void main(String[] args) {


        // test that our args are valid
        if(args.length != 4) {
            System.err.println("Incorrect number of arguments");
            System.err.println("Argument format: pickup_latitude pickup_longitude dropoff_latitude dropoff_longitude");
            System.exit(1);
        }

        double  pLat = 0.0, pLng = 0.0,
                dLat = 0.0, dLng = 0.0;

        try {
            pLat = Double.parseDouble(args[0]);
            pLng = Double.parseDouble(args[1]);
            dLat = Double.parseDouble(args[2]);
            dLng = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid argument passed");
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
