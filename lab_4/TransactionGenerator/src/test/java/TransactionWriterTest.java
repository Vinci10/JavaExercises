import logic.*;
import model.Product;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionWriterTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void wrongPath() throws Exception {
        expectedEx.expect(Exception.class);
        expectedEx.expectMessage("Cannot save transactions");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("customerIds", "1:25");
        parameters.put("dateRange", "2018-03-08T00:00:00.000:2018-03-12T23:59:59.999");
        parameters.put("itemsFile", "items.csv");
        parameters.put("itemsCount", "5:15");
        parameters.put("itemsQuantity", "1:30");
        parameters.put("eventsCount", "1000");
        parameters.put("outDir", "./");
        parameters.put("jarDir", new File("").getAbsolutePath());
        TransactionWriter transactionWriter = new TransactionWriter(parameters, Mockito.mock(TransactionGenerator.class));
        transactionWriter.writeTransactions();
    }

    @Test
    public void writeToFile() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("customerIds", "1:25");
        parameters.put("dateRange", "2018-03-08T00:00:00.000:2018-03-12T23:59:59.999");
        parameters.put("itemsFile", "items.csv");
        parameters.put("itemsCount", "5:15");
        parameters.put("itemsQuantity", "1:30");
        parameters.put("eventsCount", "10");
        parameters.put("outDir", "./outTest");
        parameters.put("jarDir", new File("").getAbsolutePath());
        TransactionGenerator generator = Mockito.mock(TransactionGenerator.class);
        Mockito.when(generator.generate(Mockito.anyMap(), Mockito.anyString())).thenReturn("transaction");
        TransactionWriter transactionWriter = Mockito.spy(new TransactionWriter(parameters, generator));
        Mockito.when(transactionWriter.pathsAreCorrect()).thenReturn(true);
        Mockito.doNothing().when(transactionWriter).writeToFile(Mockito.anyInt(), Mockito.anyString());
        transactionWriter.writeTransactions();
        Mockito.verify(transactionWriter, Mockito.times(10)).writeToFile(Mockito.anyInt(),
                Mockito.anyString());
    }
}
