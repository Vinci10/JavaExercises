import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

public class CSVReaderTest {
    private CSVReader csvReader = new CSVReader();
    @Test
    public void noProducts() throws IOException {
        BufferedReader br = Mockito.mock(BufferedReader.class);
        when(br.readLine()).thenReturn("name,price", null);
        List<Product> products = csvReader.getProducts(br);
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void someProducts() throws IOException {
        BufferedReader br = Mockito.mock(BufferedReader.class);
        when(br.readLine()).thenReturn("name,price","\"mleko 3% 1l\",2.30","\"bułeczka\",1.20", null);
        List<Product> products = csvReader.getProducts(br);
        Assert.assertEquals(2, products.size());
        Assert.assertEquals(products.get(0).getName(), "mleko 3% 1l");
        Assert.assertEquals(products.get(0).getPrice(), 2.30, 0);

        Assert.assertEquals(products.get(1).getName(), "bułeczka");
        Assert.assertEquals(products.get(1).getPrice(), 1.20, 0);
    }
}
