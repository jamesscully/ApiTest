package com.scully;

import com.scully.model.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    String[] INVALID_ARGS_NOPASSENGERS = new String[] {"51", "1", "51", "2aaaa"};
    String[] INVALID_ARGS_PASSENGERS = new String[] {"51", "1", "51", "2", "5aaaa"};
    String[] VALID_ARGS = new String[] {"51", "1", "51", "2"};

    @Test
    public void testArgs() {
        assertThrows(IllegalArgumentException.class, () -> Part1A.main(new String[] {"invalid"}));
        assertThrows(IllegalArgumentException.class, () -> Part1B.main(new String[] {"invalid"}));
        assertThrows(IllegalArgumentException.class, () -> Part1C.main(new String[] {"invalid"}));

        assertThrows(NumberFormatException.class, () -> Part1A.main(INVALID_ARGS_NOPASSENGERS));
        assertThrows(NumberFormatException.class, () -> Part1B.main(INVALID_ARGS_PASSENGERS));
        assertThrows(NumberFormatException.class, () -> Part1C.main(INVALID_ARGS_PASSENGERS));

        assertDoesNotThrow(() -> Part1A.main(VALID_ARGS));
        assertDoesNotThrow(() -> Part1B.main(VALID_ARGS));
        assertDoesNotThrow(() -> Part1C.main(VALID_ARGS));

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

}
