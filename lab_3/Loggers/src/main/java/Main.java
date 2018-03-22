import com.external.PaymentsService;
import com.internal.DiscountCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("Start");
        CmdParser cmdParser = new CmdParser(args);
        Map<String, String> parameters = cmdParser.parse();
        DiscountCalculator discountCalculator = new DiscountCalculator();
        logger.info("Calculate discount");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(parameters.get("ticketPrice")));
        BigDecimal discount = discountCalculator.calculateDiscount(price, Integer.parseInt(parameters.get("age")));
        BigDecimal finalPrice = price.subtract(discount);
        logger.info("Final price: " + finalPrice);
        PaymentsService paymentsService = new PaymentsService();
        boolean makePayment = paymentsService.makePayment(Long.parseLong(parameters.get("customerId")), Long.parseLong(parameters.get("companyId")),
                finalPrice);
        logger.info("Make payment: " + makePayment);
    }
}
