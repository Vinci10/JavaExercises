import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class CmdParserTest {
    @Test(expected = IllegalArgumentException.class)
    public void wrongParameterNoItemsFile() {
        String[] args = new String[]{"-customerIds", "1:20", "-dateRange", "2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100",
                "-itemsCount", "5:15", "-itemsQuantity", "1:30", "-eventsCount", "1000", "-outDir", "./"};
        new CmdParser(args).parse();
    }

    @Test
    public void correctParameters() throws URISyntaxException {
        File classfile = new File(CmdParser.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        String path = classfile.getParentFile().getPath();
        String[] args = new String[] {"-customerIds", "1:25", "-dateRange", "2018-03-08T00:00:00.000-0100:2018-03-12T23:59:59.999-0100",
                "-itemsFile", "items.csv", "-itemsCount", "5:15", "-itemsQuantity", "1:30", "-eventsCount", "1000", "-outDir", "./output"};
        Map<String, String> params1 = new CmdParser(args).parse();
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("customerIds", "1:25");
        params2.put("dateRange", "2018-03-08T00:00:00.000-0100:2018-03-12T23:59:59.999-0100");
        params2.put("itemsFile", "items.csv");
        params2.put("itemsCount", "5:15");
        params2.put("itemsQuantity", "1:30");
        params2.put("eventsCount", "1000");
        params2.put("outDir", "./output");
        params2.put("jarDir", path);
        assertEquals(params1, params2);
    }

    @Test
    public void defaultParameters() throws URISyntaxException {
        File classfile = new File(CmdParser.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        String path = classfile.getParentFile().getPath();
        String[] args = new String[] {"-itemsFile", "files/items.csv"};
        Map<String, String> params1 = new CmdParser(args).parse();
        HashMap<String, String> params2 = new HashMap<>();
        params2.put("customerIds", "1:20");
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59, 999));
        params2.put("dateRange", start.toString() + ":00.000:" + end.toString());
        params2.put("itemsFile", "files/items.csv");
        params2.put("itemsCount", "1:5");
        params2.put("itemsQuantity", "1:5");
        params2.put("eventsCount", "100");
        params2.put("outDir", path);
        params2.put("jarDir", path);
        assertEquals(params1, params2);
    }
}
