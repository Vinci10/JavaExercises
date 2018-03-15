package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.DiscountsConfig;

import java.math.BigDecimal;

public class DiscountsService {

    public BigDecimal getDiscountForItem(Item item, Customer customer) {
        return DiscountsConfig.getDiscountForItem(item, customer);
    }

    public boolean isWeekendPromotion() {
        return DiscountsConfig.isWeekendPromotion();
    }
}
