import com.scully.Main;
import com.scully.SearchTaxis;
import com.scully.SearchResult;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    String[] VALID_ARGS = new String[] {"51", "1", "51", "2"};

    @Test
    public void testArgs() {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[] {"invalid"}));
        assertThrows(NumberFormatException.class, () -> Main.main(new String[] {
                "51", "1",
                "51aaa", "2"
        }));
    }

    // check that our parameters passed in are being presented to and received from the API properly
    @Test
    public void testParamsPassed() {
        SearchResult testOne = SearchTaxis.query(SearchTaxis.SUP_DAVE, 50, 50, 40, 40);

        // quite redundant, but hardcoding here would cause a headache if source changed
        assertEquals(testOne.supplierName, SearchTaxis.SUP_DAVE);

        // using simple integers - floating point precision would make this very difficult
        assertEquals(testOne.pickupLocation, "" + 50 + "," + 50);
        assertEquals(testOne.dropoffLocation, "" + 40 + "," + 40);

    }


}
