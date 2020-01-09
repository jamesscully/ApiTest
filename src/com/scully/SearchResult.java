package com.scully;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents data retrieved from the API requests
 */
public class SearchResult {

    public String supplierName    = "";
    public String pickupLocation  = "";
    public String dropoffLocation = "";

    boolean availableJourney = false;

    // since the car type should be unique, we can store it as <type,price>
    private HashMap<CarType,Integer> tripOptions = new HashMap<>();

    public SearchResult(String response) {

        // provided that hasType is called before getPriceForType this will make the object useless,
        // which avoids throwing null around.
        if(response.isEmpty()) {
            return;
        }

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
                Integer price   = obj.getInt("price");

                tripOptions.put(carType, price);
            }

            availableJourney = true;

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
}
