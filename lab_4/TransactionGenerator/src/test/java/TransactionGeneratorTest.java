import logic.*;
import model.Product;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionGeneratorTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private static CSVReader csvReader;

    @BeforeClass
    public static void init() throws IOException {
        csvReader = Mockito.mock(CSVReader.class);
        List<Product> products = Arrays.asList(new Product("name", 1.0));
        Mockito.when(csvReader.read(Mockito.any())).thenReturn(products);
    }

    @Test
    public void invalidDate() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Invalid date");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("customerIds", "1:25");
        parameters.put("dateRange", "2018-03-08T00:0ff0:00.000:2018-03-12T23:59:59.999");
        parameters.put("itemsFile", "items.csv");
        parameters.put("itemsCount", "5:15");
        parameters.put("itemsQuantity", "1:30");
        parameters.put("eventsCount", "1000");
        parameters.put("outDir", "./");
        parameters.put("jarDir", new File("").getAbsolutePath());
        TransactionGenerator transactionGenerator = new TransactionGenerator(Mockito.mock(DataFormatBuilder.class), csvReader, new RandomGenerator());
        transactionGenerator.generate(parameters, "items.csv");
    }

    @Test
    public void invalidRange() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Invalid range in parameters");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("customerIds", "-1:25");
        parameters.put("dateRange", "2018-03-08T00:00:00.000:2018-03-12T23:59:59.999");
        parameters.put("itemsFile", "items.csv");
        parameters.put("itemsCount", "5:15");
        parameters.put("itemsQuantity", "1:30");
        parameters.put("eventsCount", "1000");
        parameters.put("outDir", "./");
        parameters.put("jarDir", new File("").getAbsolutePath());
        TransactionGenerator transactionGenerator = new TransactionGenerator(Mockito.mock(DataFormatBuilder.class), csvReader, new RandomGenerator());
        transactionGenerator.generate(parameters, "items.csv");
    }

    @Test
    public void invalidParameters() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Invalid parameters");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("customerIds", "-1:25");
        parameters.put("dateRange", "2018-03-08T00:00:00999");
        parameters.put("itemsFile", "items.csv");
        parameters.put("itemsCount", "5:15");
        parameters.put("itemsQuantity", "1:30");
        parameters.put("eventsCount", "1000");
        parameters.put("outDir", "./");
        parameters.put("jarDir", new File("").getAbsolutePath());
        TransactionGenerator transactionGenerator = new TransactionGenerator(Mockito.mock(DataFormatBuilder.class), csvReader, new RandomGenerator());
        transactionGenerator.generate(parameters, "items.csv");
    }

    @Test
    public void correctParameters() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("customerIds", "1:25");
        parameters.put("dateRange", "2018-03-08T00:00:00.000:2018-03-12T23:59:59.999");
        parameters.put("itemsFile", "items.csv");
        parameters.put("itemsCount", "5:15");
        parameters.put("itemsQuantity", "1:30");
        parameters.put("eventsCount", "10");
        parameters.put("outDir", "./");
        parameters.put("jarDir", new File("").getAbsolutePath());
        TransactionGenerator transactionGenerator = new TransactionGenerator(Mockito.mock(DataFormatBuilder.class), csvReader, new RandomGenerator());
        transactionGenerator.generate(parameters, "items.csv");
    }

    @Test
    public void checkResult() throws Exception{
        Map<String, String> parameters = new HashMap<>();
        parameters.put("customerIds", "1:1");
        parameters.put("dateRange", "2018-03-08T00:00:00.000:2018-03-08T00:00:00.000");
        parameters.put("itemsFile", "items.csv");
        parameters.put("itemsCount", "1:1");
        parameters.put("itemsQuantity", "3:3");
        parameters.put("eventsCount", "10");
        parameters.put("outDir", "./");
        parameters.put("jarDir", new File("").getAbsolutePath());
        TransactionGenerator transactionGenerator = new TransactionGenerator(new JsonBuilder(), csvReader, new RandomGenerator());
        String result = transactionGenerator.generate(parameters, "items.csv");
        Assert.assertEquals("{\n" +
                "  \"id\": 1,\n" +
                "  \"timestamp\": \"2018-03-08T00:00-0100\",\n" +
                "  \"customer_id\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"name\": \"name\",\n" +
                "      \"quantity\": 3,\n" +
                "      \"price\": 1.0\n" +
                "    }\n" +
                "  ],\n" +
                "  \"sum\": 3.00\n" +
                "}", result);
    }

}
