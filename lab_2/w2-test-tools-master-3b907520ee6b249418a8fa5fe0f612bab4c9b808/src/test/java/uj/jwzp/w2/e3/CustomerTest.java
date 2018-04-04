package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {

    @Test
    public void checkCustomerIdNameAddress() {
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Assert.assertEquals(1, c.getId());
        Assert.assertEquals("DasCustomer", c.getName());
        Assert.assertEquals("Kraków, Łojasiewicza", c.getAddress());
    }
}
