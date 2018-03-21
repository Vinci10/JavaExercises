import org.junit.Assert;
import org.junit.Test;

public class ProductTest {

    @Test
    public void getName() {
        Product p = new Product("x", 25.0);
        Assert.assertEquals(p.getName(), "x");
    }

    @Test
    public void getPrice() {
        Product p = new Product("x", 25.0);
        Assert.assertEquals(p.getPrice(), 25.0, 0);
    }
}
