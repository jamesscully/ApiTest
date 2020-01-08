package com.scully;

// each enum corresponds to the maximum amount of people it can carry; should attempt to add comparator here
public enum CarTypeEnum {

    STANDARD(4),
    EXECUTIVE(4),
    LUXURY(4),
    PEOPLE_CARRIER(6),
    LUXURY_PEOPLE_CARRIER(6),
    MINIBUS(16);

    private final int MAX;

    CarTypeEnum(int MAX) {
        this.MAX = MAX;
    }

    public static CarTypeEnum Factory(String in) {
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
}
