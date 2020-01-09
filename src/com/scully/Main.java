package com.scully;

import java.util.ArrayList;

public class Main {

    // args format: [passengers] [pickup_lat] [pickup_lng] [dropoff_lat] [dropoff_lng] (we'll start with this one)
    // alternatively we could do: pickup[lat,lng] dropoff[lat,lng]

    public static final String ARGS_FORMAT = "pickup_latitude pickup_longitude dropoff_latitude dropoff_longitude [passengers]";

    public static int passengers = 0;

    public static void main(String[] args) {

        // test that our number of args is valid, we want either 4 or 5
        if(args.length < 4 || args.length > 5) {
            throw new IllegalArgumentException("Incorrect number of arguments.\n Argument format: " + ARGS_FORMAT);
        }

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
            System.err.println("Could not parse arguments: ");
            e.printStackTrace();
            throw e;
        }

        // get Daves result
        SupplierResult dave = SupplierAPI.query(SupplierAPI.SUP_DAVE, pLat, pLng, dLat, dLng);

        SupplierResult eric = SupplierAPI.query(SupplierAPI.SUP_ERIC, pLat, pLng, dLat, dLng);
        SupplierResult jeff = SupplierAPI.query(SupplierAPI.SUP_JEFF, pLat, pLng, dLat, dLng);

        getCheapests(dave, eric, jeff);
    }

    public static void getCheapests(SupplierResult ... suppliers) {
        // reduces the need to go over un-needed types
        ArrayList<CarTypeEnum> types = CarTypeEnum.getApplicableTypes(passengers);

        System.out.println("%%%%%%%%%%\nFinding cheapest prices");

        for(CarTypeEnum c : types) {

            String cheapestSupplier = "";
            int    cheapestPrice    = Integer.MAX_VALUE;

            boolean modified = false;

            for(SupplierResult supplier : suppliers) {

                // nothing to do if we don't have the type
                if(!supplier.hasType(c))
                    continue;

                modified = true;

                int price = supplier.getPriceByType(c);

                if(price < cheapestPrice) {
                    cheapestPrice    = price;
                    cheapestSupplier = supplier.supplierName;
                }

                // by here, we should have cheapest price
            }

            // we don't want to print if no one had this type
            if(modified) {
                System.out.println(
                        String.format("%s - %s - %d", c.toString(), cheapestSupplier, cheapestPrice)
                );
            }

        }


    }
}
