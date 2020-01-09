import com.scully.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    String[] INVALID_ARGS_NOPASSENGERS = new String[] {"51", "1", "51", "2aaaa"};
    String[] INVALID_ARGS_PASSENGERS = new String[] {"51", "1", "51", "2", "5aaaa"};

    @Test
    public void testArgs() {
        assertThrows(IllegalArgumentException.class, () -> Part1A.main(new String[] {"invalid"}));
        assertThrows(IllegalArgumentException.class, () -> Part1B.main(new String[] {"invalid"}));
        assertThrows(IllegalArgumentException.class, () -> Part1C.main(new String[] {"invalid"}));

        assertThrows(NumberFormatException.class, () -> Part1A.main(INVALID_ARGS_NOPASSENGERS));
        assertThrows(NumberFormatException.class, () -> Part1B.main(INVALID_ARGS_PASSENGERS));
        assertThrows(NumberFormatException.class, () -> Part1C.main(INVALID_ARGS_PASSENGERS));

    }

}
