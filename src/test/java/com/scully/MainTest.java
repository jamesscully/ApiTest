package com.scully;

import com.scully.model.CarType;
import com.scully.model.Location;
import com.scully.model.SearchResult;
import com.scully.search.SearchTaxis;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    String[] INVALID_ARGS_NOPASSENGERS = new String[] {"51,1", "51,2aaa"};
    String[] INVALID_ARGS_PASSENGERS = new String[] {"51,1", "51,2", "5aaaa"};

    String[] VALID_ARGS_NOPASSENGERS = new String[] {"51,1", "51,2"};
    String[] VALID_ARGS_PASSENGERS = new String[] {"51,1", "51,2", "2"};

    @Test
    public void testArgs() {
        assertThrows(IllegalArgumentException.class, () -> Part1A.main(new String[] {"invalid"}));
        assertThrows(IllegalArgumentException.class, () -> Part1B.main(new String[] {"invalid"}));
        assertThrows(IllegalArgumentException.class, () -> Part1C.main(new String[] {"invalid"}));

        // part A only takes locations, not passengers
        assertThrows(NumberFormatException.class, () -> Part1A.main(INVALID_ARGS_NOPASSENGERS));


        assertThrows(NumberFormatException.class, () -> Part1B.main(INVALID_ARGS_PASSENGERS));
        assertThrows(NumberFormatException.class, () -> Part1B.main(INVALID_ARGS_NOPASSENGERS));

        assertThrows(NumberFormatException.class, () -> Part1C.main(INVALID_ARGS_PASSENGERS));
        assertThrows(NumberFormatException.class, () -> Part1C.main(INVALID_ARGS_NOPASSENGERS));


        // part A only takes locations, not passengers
        assertDoesNotThrow(() -> Part1A.main(VALID_ARGS_NOPASSENGERS));

        assertDoesNotThrow(() -> Part1B.main(VALID_ARGS_PASSENGERS));
        assertDoesNotThrow(() -> Part1B.main(VALID_ARGS_NOPASSENGERS));


        assertDoesNotThrow(() -> Part1C.main(VALID_ARGS_PASSENGERS));
        assertDoesNotThrow(() -> Part1C.main(VALID_ARGS_NOPASSENGERS));

    }

    @Test
    public void testLocation() {
        assertThrows(NumberFormatException.class, () -> new Location("thisIsNotANumber"));
        assertDoesNotThrow(() -> new Location("51,2"));
        assertDoesNotThrow(() -> new Location("51,4"));

        assertDoesNotThrow(() -> new Location(51, 2));
        assertDoesNotThrow(() -> new Location(51, 76));

        assertEquals("51.0,1.0", new Location(51, 1).toString());
        assertEquals("51.0,8.0", new Location(51, 8).toString());
        assertEquals("51.0,42.0", new Location(51, 42).toString());
    }

    @Test
    public void testCapacities() {
        /* From technical_test
        | Car Type              | Maximum passengers |
        | STANDARD              | 4                  |
        | EXECUTIVE             | 4                  |
        | LUXURY                | 4                  |
        | PEOPLE_CARRIER        | 6                  |
        | LUXURY_PEOPLE_CARRIER | 6                  |
        | MINIBUS               | 16                 | */

        // false - just out of boundary
        assertFalse(CarType.STANDARD.canHoldPassengers(5));
        assertFalse(CarType.EXECUTIVE.canHoldPassengers(5));
        assertFalse(CarType.LUXURY.canHoldPassengers(5));
        assertFalse(CarType.PEOPLE_CARRIER.canHoldPassengers(7));
        assertFalse(CarType.LUXURY_PEOPLE_CARRIER.canHoldPassengers(7));
        assertFalse(CarType.MINIBUS.canHoldPassengers(17));

        // truths - just on boundary
        assertTrue(CarType.STANDARD.canHoldPassengers(4));
        assertTrue(CarType.EXECUTIVE.canHoldPassengers(4));
        assertTrue(CarType.LUXURY.canHoldPassengers(4));
        assertTrue(CarType.PEOPLE_CARRIER.canHoldPassengers(6));
        assertTrue(CarType.LUXURY_PEOPLE_CARRIER.canHoldPassengers(6));
        assertTrue(CarType.MINIBUS.canHoldPassengers(16));


    }


    @Test
    public void testCheapest() {

        // create our artificial API responses, we'll be able to definitely say which should be cheapest
        SearchResult.Builder eric = new SearchResult.Builder().name(SearchTaxis.SUP_ERIC).dropoff("51,2").pickup("51,3").passengers(2);;

                             eric.option(CarType.EXECUTIVE, 50);
                             eric.option(CarType.LUXURY_PEOPLE_CARRIER, 42);


        SearchResult.Builder jeff = new SearchResult.Builder().name(SearchTaxis.SUP_JEFF).dropoff("51,2").pickup("51,3").passengers(2);

                             jeff.option(CarType.EXECUTIVE, 5000);
                             jeff.option(CarType.LUXURY_PEOPLE_CARRIER, 1337);


        SearchResult.Builder dave = new SearchResult.Builder().name(SearchTaxis.SUP_DAVE).dropoff("51,2").pickup("51,3").passengers(2);

                             dave.option(CarType.EXECUTIVE, 2);
                             dave.option(CarType.LUXURY_PEOPLE_CARRIER, 9001);

        ArrayList<SearchResult> results = new ArrayList<>();
        ArrayList<CarType>      types   = new ArrayList<>();

        results.add(eric.build());
        results.add(dave.build());
        results.add(jeff.build());

        // add the above specified options
        types.add(CarType.EXECUTIVE);
        types.add(CarType.LUXURY_PEOPLE_CARRIER);

        SearchResult allSuppliers = SearchTaxis.findCheapestRides(types, results).build();

        assertEquals(2, allSuppliers.getPriceByType(CarType.EXECUTIVE));
        assertEquals(42, allSuppliers.getPriceByType(CarType.LUXURY_PEOPLE_CARRIER));
    }

}
