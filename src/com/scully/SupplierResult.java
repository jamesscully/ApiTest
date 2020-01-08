package com.scully;

import org.json.JSONException;
import org.json.JSONObject;

/** This class acts as a model for the returned json **/
public class SupplierResult {

    public SupplierResult(String json) {
        try {
            JSONObject all = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
