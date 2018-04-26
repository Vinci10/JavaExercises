//import logic.*;
//import model.Product;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.io.*;
//import java.util.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TransactionWriterTest {
//    static String path;
//    @Rule
//    public ExpectedException expectedEx = ExpectedException.none();
//
//    private static CSVReader csvReader;
//
//    @BeforeClass
//    public static void init() throws IOException {
//        File classfile = new File(TransactionWriter.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//        path = classfile.getParentFile().getPath();
//        csvReader = Mockito.mock(CSVReader.class);
//        List<Product> products = Arrays.asList(new Product("name", 1.0));
//        Mockito.when(csvReader.read(Mockito.any())).thenReturn(products);
//    }
//
//    @Test
//    public void wrongPath() throws Exception {
//        expectedEx.expect(Exception.class);
//        expectedEx.expectMessage("Wrong output directory or csv file");
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("customerIds", "1:25");
//        parameters.put("dateRange", "2018-03-08T00:00:00.000:2018-03-12T23:59:59.999");
//        parameters.put("itemsFile", "items.csv");
//        parameters.put("itemsCount", "5:15");
//        parameters.put("itemsQuantity", "1:30");
//        parameters.put("eventsCount", "1000");
//        parameters.put("outDir", "./");
//        parameters.put("jarDir", path);
//        TransactionWriter transactionWriter = Mockito.spy(new TransactionWriter(new JsonBuilder()));
//        Mockito.when(transactionWriter.pathsAreCorrect()).thenReturn(false);
//        transactionWriter.generate(parameters, csvReader);
//    }
//
//    @Test
//    public void invalidDate() throws Exception {
//        expectedEx.expect(Exception.class);
//        expectedEx.expectMessage("Invalid date");
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("customerIds", "1:25");
//        parameters.put("dateRange", "2018-03-08T00:0ff0:00.000:2018-03-12T23:59:59.999");
//        parameters.put("itemsFile", "items.csv");
//        parameters.put("itemsCount", "5:15");
//        parameters.put("itemsQuantity", "1:30");
//        parameters.put("eventsCount", "1000");
//        parameters.put("outDir", "./");
//        parameters.put("jarDir", path);
//        TransactionWriter transactionWriter = Mockito.spy(new TransactionWriter(new JsonBuilder()));
//        Mockito.when(transactionWriter.pathsAreCorrect()).thenReturn(true);
//        transactionWriter.generate(parameters,csvReader);
//    }
//
//    @Test
//    public void invalidRange() throws Exception {
//        expectedEx.expect(Exception.class);
//        expectedEx.expectMessage("Invalid range in parameters");
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("customerIds", "-1:25");
//        parameters.put("dateRange", "2018-03-08T00:00:00.000:2018-03-12T23:59:59.999");
//        parameters.put("itemsFile", "items.csv");
//        parameters.put("itemsCount", "5:15");
//        parameters.put("itemsQuantity", "1:30");
//        parameters.put("eventsCount", "1000");
//        parameters.put("outDir", "./");
//        parameters.put("jarDir", path);
//        TransactionWriter transactionWriter = Mockito.spy(new TransactionWriter(new JsonBuilder()));
//        Mockito.when(transactionWriter.pathsAreCorrect()).thenReturn(true);
//        transactionWriter.generate(parameters,csvReader);
//    }
//
//    @Test
//    public void invalidParameters() throws Exception {
//        expectedEx.expect(Exception.class);
//        expectedEx.expectMessage("Invalid parameters");
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("customerIds", "-1:25");
//        parameters.put("dateRange", "2018-03-08T00:00:00999");
//        parameters.put("itemsFile", "items.csv");
//        parameters.put("itemsCount", "5:15");
//        parameters.put("itemsQuantity", "1:30");
//        parameters.put("eventsCount", "1000");
//        parameters.put("outDir", "./");
//        parameters.put("jarDir", path);
//        TransactionWriter transactionWriter = Mockito.spy(new TransactionWriter(new JsonBuilder()));
//        Mockito.when(transactionWriter.pathsAreCorrect()).thenReturn(true);
//        transactionWriter.generate(parameters,csvReader);
//    }
//
//    @Test
//    public void correctParameters() throws Exception {
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("customerIds", "1:25");
//        parameters.put("dateRange", "2018-03-08T00:00:00.000:2018-03-12T23:59:59.999");
//        parameters.put("itemsFile", "items.csv");
//        parameters.put("itemsCount", "5:15");
//        parameters.put("itemsQuantity", "1:30");
//        parameters.put("eventsCount", "10");
//        parameters.put("outDir", "./");
//        parameters.put("jarDir", path);
//        TransactionWriter transactionWriter = Mockito.spy(new TransactionWriter(new JsonBuilder()));
//        Mockito.when(transactionWriter.pathsAreCorrect()).thenReturn(true);
//        transactionWriter.generate(parameters,csvReader);
//    }
//
//    @Test
//    public void writeItems() throws Exception {
//        TransactionWriter transactionWriter = new TransactionWriter(Mockito.mock(DataFormatBuilder.class));
//        double sum = transactionWriter.writeItems(Arrays.asList(new Product("name", 5.0)), new String[]{"1", "2"},
//                new String[]{"1", "1"});
//        Assert.assertTrue(sum == 5.0 || sum == 10.0);
//    }
//
//    @Test
//    public void writeToFile() throws Exception {
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("customerIds", "1:25");
//        parameters.put("dateRange", "2018-03-08T00:00:00.000:2018-03-12T23:59:59.999");
//        parameters.put("itemsFile", "items.csv");
//        parameters.put("itemsCount", "5:15");
//        parameters.put("itemsQuantity", "1:30");
//        parameters.put("eventsCount", "10");
//        parameters.put("outDir", "./");
//        parameters.put("jarDir", path);
//        TransactionWriter transactionWriter = Mockito.spy(new TransactionWriter(new YamlBuilder()));
//        Mockito.when(transactionWriter.pathsAreCorrect()).thenReturn(true);
//        transactionWriter.generate(parameters,csvReader);
//        transactionWriter.writeAll();
//        Mockito.verify(transactionWriter, Mockito.times(10)).writeToFile(Mockito.any(PrintWriter.class),
//                Mockito.anyString(),Mockito.anyString());
//    }
//}
