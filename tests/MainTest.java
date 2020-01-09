import com.scully.Main;
import com.scully.SupplierAPI;
import com.scully.SupplierResult;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

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
        SupplierResult testOne = SupplierAPI.query(SupplierAPI.SUP_DAVE, 50, 50, 40, 40);

        // quite redundant, but hardcoding here would cause a headache if source changed
        assertEquals(testOne.supplierName, SupplierAPI.SUP_DAVE);

        // using simple integers - floating point precision would make this very difficult
        assertEquals(testOne.pickupLocation, "" + 50 + "," + 50);
        assertEquals(testOne.dropoffLocation, "" + 40 + "," + 40);

    }


}
