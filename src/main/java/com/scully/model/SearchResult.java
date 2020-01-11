package com.scully.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents data retrieved from the API requests
 */


public class SearchResult {

    public String supplierName    = "";
    public String pickupLocation  = "";
    public String dropoffLocation = "";

    public int passengers;

    // this will be included in json even if we have a correct output - ignore it.
    @JsonIgnore
    public boolean errorCreating = true;

    // since the car type should be unique, we can store it as <type,price>
    private HashMap<CarType,Integer> tripOptions = new HashMap<>();

    public SearchResult(String response, int passengers) {
        // provided that hasType is called before getPriceForType this will make the object useless,
        // which avoids throwing null around.
        if(response.isEmpty()) {
            return;
        }

        this.passengers = passengers;

        try {
            // contains all of the returned JSON
            JSONObject json = new JSONObject(response);

            supplierName    = json.getString("supplier_id");
            pickupLocation  = json.getString("pickup");
            dropoffLocation = json.getString("dropoff");

            JSONArray options = (JSONArray) json.get("options");

            for(int i = 0; i < options.length(); i++) {
                // the current key-value pair we're looking at, i.e. {"car_type":"STANDARD","price":370137}
                JSONObject obj = options.getJSONObject(i);

                CarType carType = CarType.Factory(obj.getString("car_type"));

                if(!carType.canHoldPassengers(passengers))
                    continue;

                Integer price   = obj.getInt("price");

                tripOptions.put(carType, price);
            }

            errorCreating = false;

        } catch (JSONException e) {
            System.err.println("Error parsing JSON from API: ");
            e.printStackTrace();
        }
    }

    public HashMap<CarType, Integer> getTripOptions() {
        return tripOptions;
    }

    public boolean hasType(CarType e) {
        return tripOptions.containsKey(e);
    }

    public int getPriceByType(CarType e) {
        return tripOptions.get(e);
    }

    private SearchResult(String supplierName, String pickupLocation, String dropoffLocation, int passengers, HashMap<CarType, Integer> tripOptions) {
        this.supplierName = supplierName;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.passengers = passengers;
        this.tripOptions = tripOptions;
    }

    public static class Builder {
        String supplierName    = "";
        String pickupLocation  = "";
        String dropoffLocation = "";

        int passengers;

        // since the car type should be unique, we can store it as <type,price>
        private HashMap<CarType,Integer> tripOptions = new HashMap<>();

        public Builder() {
            passengers = 0;
        }

        public Builder name(String name) {
            this.supplierName = name;
            return this;
        }

        public Builder pickup(String pickup) {
            this.pickupLocation = pickup;
            return this;
        }

        public Builder dropoff(String dropoff) {
            this.dropoffLocation = dropoff;
            return this;
        }

        public Builder passengers(int passengers) {
            this.passengers = passengers;
            return this;
        }

        public Builder option(CarType type, int price) {
            this.tripOptions.put(type, price);
            return this;
        }

        public SearchResult build() {
            return new SearchResult(supplierName, pickupLocation, dropoffLocation, passengers, tripOptions);
        }
    }
}
