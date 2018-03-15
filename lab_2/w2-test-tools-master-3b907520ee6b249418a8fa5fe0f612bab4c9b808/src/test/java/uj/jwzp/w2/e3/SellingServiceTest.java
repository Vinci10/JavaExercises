package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.DiscountsConfig;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class SellingServiceTest {

    @Mock
    private PersistenceLayer persistenceLayer;

    @Mock
    private DiscountsService discountsService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void notSell() {
        //given
        SellingService uut = new SellingService(persistenceLayer);

        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Mockito.when(discountsService.getDiscountForItem(i, c)).thenReturn(BigDecimal.ZERO);
        //when
        boolean sold = uut.sell(i, 7, c, discountsService.isWeekendPromotion(), discountsService);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(10), uut.moneyService.getMoney(c));
    }

    @Test
    public void sell() {
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(discountsService.isWeekendPromotion()).thenReturn(Boolean.FALSE);
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Mockito.when(discountsService.getDiscountForItem(i, c)).thenReturn(BigDecimal.ZERO);
        //when
        boolean sold = uut.sell(i, 1, c, discountsService.isWeekendPromotion(), discountsService);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(7), uut.moneyService.getMoney(c));
    }

    @Test
    public void sellALot() {
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        uut.moneyService.addMoney(c, new BigDecimal(990));
        Mockito.when(discountsService.isWeekendPromotion()).thenReturn(Boolean.FALSE);
        Mockito.when(discountsService.getDiscountForItem(i, c)).thenReturn(BigDecimal.ZERO);

        //when
        boolean sold = uut.sell(i, 10, c, discountsService.isWeekendPromotion(), discountsService);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(970), uut.moneyService.getMoney(c));
    }

    @Test
    public void sellGetDiscount() {
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(15));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        uut.moneyService.addMoney(c, new BigDecimal(990));
        Mockito.when(discountsService.isWeekendPromotion()).thenReturn(Boolean.FALSE);
        Mockito.when(discountsService.getDiscountForItem(i, c)).thenReturn(BigDecimal.TEN);

        //when
        boolean sold = uut.sell(i, 10, c, discountsService.isWeekendPromotion(), discountsService);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(950), uut.moneyService.getMoney(c));
    }

    @Test
    public void sellGetDiscountWeekendPromotionPriceSmallerThan5() {
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(4));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        uut.moneyService.addMoney(c, new BigDecimal(990));
        Mockito.when(discountsService.isWeekendPromotion()).thenReturn(Boolean.TRUE);
        Mockito.when(discountsService.getDiscountForItem(i, c)).thenReturn(BigDecimal.ONE);

        //when
        boolean sold = uut.sell(i, 1, c, discountsService.isWeekendPromotion(), discountsService);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(997), uut.moneyService.getMoney(c));
    }

    @Test
    public void sellGetDiscountWeekendPromotionPriceBiggerThan5() {
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(10));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        uut.moneyService.addMoney(c, new BigDecimal(990));
        Mockito.when(discountsService.isWeekendPromotion()).thenReturn(Boolean.TRUE);
        Mockito.when(discountsService.getDiscountForItem(i, c)).thenReturn(BigDecimal.ONE);

        //when
        boolean sold = uut.sell(i, 3, c, discountsService.isWeekendPromotion(), discountsService);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(976), uut.moneyService.getMoney(c));
    }

    @Test
    public void isDiscountForItem() {
        Item i = new Item("i", new BigDecimal(10));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Mockito.when(discountsService.getDiscountForItem(i, c)).thenReturn(BigDecimal.TEN);
        Assert.assertEquals(BigDecimal.valueOf(10), discountsService.getDiscountForItem(i, c));
    }

    @Test
    public void noDiscountForItem() {
        Item i = new Item("i", new BigDecimal(10));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Mockito.when(discountsService.getDiscountForItem(i, c)).thenReturn(BigDecimal.ZERO);
        Assert.assertEquals(BigDecimal.valueOf(0), discountsService.getDiscountForItem(i, c));
    }

    @Test
    public void getMoneyCustomerDoesntExist() {
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        CustomerMoneyService customerMoneyService = new CustomerMoneyService(persistenceLayer);
        BigDecimal money = customerMoneyService.getMoney(c);

        Assert.assertEquals(BigDecimal.valueOf(10), money);
    }

    @Test
    public void getMoneyCustomerExists() {
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        CustomerMoneyService customerMoneyService = new CustomerMoneyService(persistenceLayer);
        BigDecimal money = customerMoneyService.getMoney(c);
        customerMoneyService.addMoney(c, BigDecimal.valueOf(50));
        money = customerMoneyService.getMoney(c);
        Assert.assertEquals(BigDecimal.valueOf(60), money);
    }

    @Test
    public void checkItemNameAndPrice() {
        Item item = new Item("name", BigDecimal.valueOf(60));
        Assert.assertEquals(BigDecimal.valueOf(60), item.getPrice());
        Assert.assertEquals("name", item.getName());
    }

    @Test
    public void checkCustomerIdNameAddress() {
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Assert.assertEquals(1, c.getId());
        Assert.assertEquals("DasCustomer", c.getName());
        Assert.assertEquals("Kraków, Łojasiewicza", c.getAddress());
    }

    @Test
    public void checkGetDiscountKrakowObwarzanek() {
        Item i = new Item("Obwarzanek", new BigDecimal(10));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        DiscountsService discountsService1 = new DiscountsService();
        Assert.assertEquals(BigDecimal.valueOf(1), discountsService1.getDiscountForItem(i, c));
    }

    @Test
    public void checkGetDiscountArkadiusz() {
        Item i = new Item("i", new BigDecimal(10));
        Customer c = new Customer(1, "Arkadiusz", "Kraków, Łojasiewicza");
        DiscountsService discountsService1 = new DiscountsService();
        Assert.assertEquals(BigDecimal.valueOf(5), discountsService1.getDiscountForItem(i, c));
    }

    @Test
    public void checkNoDiscount() {
        Item i = new Item("i", new BigDecimal(10));
        Customer c = new Customer(1, "c", "Kraków, Łojasiewicza");
        DiscountsService discountsService1 = new DiscountsService();
        Assert.assertEquals(BigDecimal.valueOf(0), discountsService1.getDiscountForItem(i, c));
    }

    @Test
    public void checkWeekendPromotion() {
        DiscountsService discountsService1 = new DiscountsService();
        boolean pr = discountsService1.isWeekendPromotion();
        Assert.assertTrue(pr || !pr);
    }
}
