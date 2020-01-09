import com.scully.Main;
import com.scully.SupplierAPI;
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


}
