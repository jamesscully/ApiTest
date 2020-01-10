package com.scully.model;

import java.util.ArrayList;

/**
 * Represents the amount of passengers a car can carry.
 */
public enum CarType {

    STANDARD(4),
    EXECUTIVE(4),
    LUXURY(4),
    PEOPLE_CARRIER(6),
    LUXURY_PEOPLE_CARRIER(6),
    MINIBUS(16);

    public final int CAPACITY;

    CarType(int MAX) {
        this.CAPACITY = MAX;
    }

    /**
     * Creates the equivalent CarType from a String
     * @param in String to convert to a CarType
     * @return Equivalent CarType
     */
    public static CarType Factory(String in) {
        switch (in) {
            case "STANDARD":
                return STANDARD;
            case "EXECUTIVE":
                return EXECUTIVE;
            case "LUXURY":
                return LUXURY;
            case "PEOPLE_CARRIER":
                return PEOPLE_CARRIER;
            case "LUXURY_PEOPLE_CARRIER":
                return LUXURY_PEOPLE_CARRIER;
            case "MINIBUS":
                return MINIBUS;

            default:
                throw new IllegalArgumentException("Could not parse car type: " + in);
        }
    }

    /**
     * Returns an ArrayList of car types applicable for N passengers
     * @param passengers Amount of passengers needed
     * @return ArrayList of capable car types
     */
    public static ArrayList<CarType> getApplicableTypes(int passengers) {
        ArrayList<CarType> applicable = new ArrayList<>();

        for(CarType c : CarType.values()) {
            if(c.CAPACITY > passengers) {
                applicable.add(c);
            }
        }

        return applicable;
    }
}
