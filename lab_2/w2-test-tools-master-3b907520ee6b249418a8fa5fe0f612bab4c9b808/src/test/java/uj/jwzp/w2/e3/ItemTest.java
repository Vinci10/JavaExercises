package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ItemTest {
    @Test
    public void checkItemNameAndPrice() {
        Item item = new Item("name", BigDecimal.valueOf(60));
        Assert.assertEquals(BigDecimal.valueOf(60), item.getPrice());
        Assert.assertEquals("name", item.getName());
    }
}
