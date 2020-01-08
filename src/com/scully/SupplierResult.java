package com.scully;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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

    private String supplierName    = "";
    private String pickupLocation  = "";
    private String dropoffLocation = "";

    // since the car type should be unique, we can store it as <type,price>
    private HashMap<String,Integer> tripOptions = new HashMap<>();

    public SupplierResult(String response) {
        try {
            // contains all of the returned JSON
            JSONObject json = new JSONObject(response);

            System.out.println(json);

            supplierName    = json.getString("supplier_id");
            pickupLocation  = json.getString("pickup");
            dropoffLocation = json.getString("dropoff");

            System.out.println(
                    String.format("Read from JSON:\n\tSupplier: %s\n\tPickup: %s\n\tDropoff: %s", supplierName, pickupLocation, dropoffLocation)
            );

            JSONArray options = (JSONArray) json.get("options");

            for(int i = 0; i < options.length(); i++) {
                // the current key-value pair we're looking at, i.e. {"car_type":"STANDARD","price":370137}
                JSONObject obj = options.getJSONObject(i);

                String carType = obj.getString("car_type");
                Integer price   = obj.getInt("price");

                System.out.println(
                        String.format("Adding CarType: %s with a price of: %d to HashMap", carType, price)
                );

                tripOptions.put(carType, price);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
