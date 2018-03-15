package uj.jwzp.w2.e3.external;

import uj.jwzp.w2.e3.Customer;
import uj.jwzp.w2.e3.Item;

import java.math.BigDecimal;
import java.util.Random;

//TU NIC NIE RUSZAC, BO TO ROBI INNY ZESPOL!
//ZASADY SA PRZYKLADOWE, I MOGA SIE ZMIENIAC BEZ UPRZEDZENIA!
public class DiscountsConfig {

    public static BigDecimal getDiscountForItem(Item item, Customer customer) {
        if (customer.getAddress().contains("Kraków") && item.getName().contains("Obwarzanek")) {
            return new BigDecimal(1);
        }
        if (customer.getName().contains("Arkadiusz")) {
            return item.getPrice().divide(new BigDecimal(2));
        }
        return BigDecimal.ZERO;
    }

    public static boolean isWeekendPromotion() {
        return new Random(System.currentTimeMillis()).nextBoolean();
    }
}
