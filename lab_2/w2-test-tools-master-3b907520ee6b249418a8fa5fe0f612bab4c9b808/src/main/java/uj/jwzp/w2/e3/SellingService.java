package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingService {
    private final PersistenceLayer persistenceLayer;
    final CustomerMoneyService moneyService;
    private final DiscountsService discountsService;

    public SellingService(PersistenceLayer persistenceLayer, DiscountsService discountsService) {
        this.persistenceLayer = persistenceLayer;
        this.persistenceLayer.loadDiscountConfiguration();
        this.moneyService = new CustomerMoneyService(this.persistenceLayer);
        this.discountsService = discountsService;
    }

    public boolean sell(Item item, int quantity, Customer customer) {
        BigDecimal price = item.getPrice().subtract(discountsService.getDiscountForItem(item, customer)).multiply(BigDecimal.valueOf(quantity));
        if (discountsService.isWeekendPromotion() && price.compareTo(BigDecimal.valueOf(5)) > 0) {
            price = price.subtract(BigDecimal.valueOf(3));
        }
        boolean sold = moneyService.pay(customer, price);
        if (sold) {
            return persistenceLayer.saveTransaction(customer, item, quantity);
        } else {
            return sold;
        }
    }

}
