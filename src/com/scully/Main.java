package com.scully;

public class Main {

    // args format: [passengers] [pickup_lat] [pickup_lng] [dropoff_lat] [dropoff_lng] (we'll start with this one)
    // alternatively we could do: pickup[lat,lng] dropoff[lat,lng]
    public static void main(String[] args) {

        // test that our number of args is valid, we want either 4 or 5
        if(args.length < 4 || args.length > 5) {
            System.err.println("Incorrect number of arguments");
            System.err.println("Argument format: pickup_latitude pickup_longitude dropoff_latitude dropoff_longitude [passengers]");
            System.exit(1);
        }

        int passengers = 0;
        double  pLat = 0.0, pLng = 0.0,
                dLat = 0.0, dLng = 0.0;

        try {

            // if we have 5 arguments, then we must have passenger count
            if(args.length == 5) {
                passengers = Integer.parseInt(args[4]);
            }

            pLat = Double.parseDouble(args[0]);
            pLng = Double.parseDouble(args[1]);
            dLat = Double.parseDouble(args[2]);
            dLng = Double.parseDouble(args[3]);

        } catch (NumberFormatException e) {
            System.err.println("Invalid argument(s) passed");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Passenger count: " + passengers);

        System.out.println(
                String.format("Pickup Location: (%f,%f)", pLat, pLng)
        );
        System.out.println(
                String.format("Dropoff Location: (%f,%f)", dLat, dLng)
        );

        // get Daves result
        SupplierAPI.query(SupplierAPI.SUP_DAVE, pLat, pLng, dLat, dLng);
    }
}
