package com.scully;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/* Example JSON pretty-printed:
{
  "supplier_id": "DAVE",
  "pickup": "51,1",
  "dropoff": "51,2",
  "options": [
    {
      "car_type": "EXECUTIVE",
      "price": 536312
    },
    {
      "car_type": "LUXURY",
      "price": 14249
    }
  ]
}
 */


/** This class acts as a model for the returned json **/
public class SupplierResult {

    public String supplierName    = "";
    public String pickupLocation  = "";
    public String dropoffLocation = "";

    boolean availableJourney = false;

    // since the car type should be unique, we can store it as <type,price>
    private HashMap<CarTypeEnum,Integer> tripOptions = new HashMap<>();

    public SupplierResult(String response) {

        if(response.isEmpty()) {
            System.err.println("Found an empty response");
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

                CarTypeEnum carType = CarTypeEnum.Factory(obj.getString("car_type"));
                Integer price   = obj.getInt("price");

                tripOptions.put(carType, price);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the options in TYPE - SUPPLIER - PRICE format
     * @param passengers Amount of needed space; n <= 0 prints all options
     */
    public void printOptions(int passengers) {

        for(Map.Entry<CarTypeEnum, Integer> entry : tripOptions.entrySet()) {

            CarTypeEnum type  = entry.getKey();
                    int price = entry.getValue();

            // if our car-type doesn't have the space, skip it
            if(type.CAPACITY < passengers)
                continue;

            availableJourney = true;

            System.out.println(
                    String.format("%s - %s - %s", type, supplierName, price)
            );
        }
        
        // prevent this being printed if we received a code 500
        if(!availableJourney && !supplierName.equals("")) {
            System.out.println("No available journeys for supplier " + supplierName);
        }
    }

    public boolean hasType(CarTypeEnum e) {
        return tripOptions.containsKey(e);
    }

    public int getPriceByType(CarTypeEnum e) {
        return tripOptions.get(e);
    }
}
