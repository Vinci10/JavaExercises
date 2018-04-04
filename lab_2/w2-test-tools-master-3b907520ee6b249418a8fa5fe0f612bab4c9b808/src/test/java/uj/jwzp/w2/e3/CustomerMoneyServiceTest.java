package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class CustomerMoneyServiceTest {
    @Mock
    private PersistenceLayer persistenceLayer;

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
        BigDecimal money;
        customerMoneyService.addMoney(c, BigDecimal.valueOf(50));
        money = customerMoneyService.getMoney(c);
        Assert.assertEquals(BigDecimal.valueOf(60), money);
    }

    @Test
    public void notPay() {
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        CustomerMoneyService customerMoneyService = new CustomerMoneyService(persistenceLayer);
        boolean pay = customerMoneyService.pay(c, BigDecimal.valueOf(15));
        Assert.assertFalse(pay);
    }

    @Test
    public void pay() {
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        CustomerMoneyService customerMoneyService = new CustomerMoneyService(persistenceLayer);
        boolean pay = customerMoneyService.pay(c, BigDecimal.valueOf(8));
        Assert.assertTrue(pay);
    }

    @Test
    public void addMoney() {
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        CustomerMoneyService customerMoneyService = new CustomerMoneyService(persistenceLayer);
        BigDecimal money = customerMoneyService.getMoney(c);
        Assert.assertEquals(BigDecimal.valueOf(10), money);
        customerMoneyService.addMoney(c, BigDecimal.valueOf(5));
        money = customerMoneyService.getMoney(c);
        Assert.assertEquals(BigDecimal.valueOf(15), money);
    }
}
