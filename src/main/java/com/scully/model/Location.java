package com.scully.model;

public class Location {

    /**
     * Creates a location based on String input i.e. "51,1"
     * @param loc Location in format "51,1"
     */

    public double lat;
    public double lng;

    public Location(String loc) {
        String[] splitLoc = loc.split(",");

        try {
            lat = Double.parseDouble(splitLoc[0]);
            lng = Double.parseDouble(splitLoc[1]);
        } catch (NumberFormatException e) {
            System.err.println("Could not parse location: " + loc);
            e.printStackTrace();
            throw e;
        }
    }

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return lat + "," + lng;
    }
}
