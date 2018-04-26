package logic;

import model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Service
@Lazy
public class TransactionGenerator {
    private DataFormatBuilder builder;
    private CSVReader csvReader;
    private final static Logger consoleLogger = LoggerFactory.getLogger("Console");
    private int counter;

    public TransactionGenerator(DataFormatBuilder builder, CSVReader reader) {
        this.builder = builder;
        this.csvReader = reader;
        counter = 0;
    }

    public String generate(Map<String, String> parameters, String itemsPath) throws Exception {
        try {
            builder.addNumber("id", counter);
            String generatedDate = RandomGenerator.generateTimestamp(parameters.get("dateRange"));
            builder.addString("timestamp", generatedDate + "-0100");
            String[] range = parameters.get("customerIds").split(":");
            builder.addNumber("customer_id", RandomGenerator.generateInt(Integer.parseInt(range[0]), Integer.parseInt(range[1])));
            BigDecimal sum = generateItems(csvReader.read(itemsPath), parameters.get("itemsCount").split(":"),
                    parameters.get("itemsQuantity").split(":"));
            builder.addNumber("sum", sum.setScale(2, BigDecimal.ROUND_HALF_UP));
            String result = builder.getResult();
            builder.reset();
            counter++;
            return result;
        } catch (DateTimeParseException e) {
            consoleLogger.error(e.getMessage());
            throw new Exception("Invalid date");
        } catch (IllegalArgumentException e) {
            consoleLogger.error(e.getMessage());
            throw new Exception("Invalid range in parameters");
        } catch (Exception e) {
            consoleLogger.error(e.getMessage());
            throw new Exception("Invalid parameters");
        }
    }

    public BigDecimal generateItems(List<Product> products, String[] itemsCount, String[] quantityRange) throws Exception {
        builder.startArray("items");
        int size = products.size() - 1;
        int count = RandomGenerator.generateInt(Integer.parseInt(itemsCount[0]), Integer.parseInt(itemsCount[1]));
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < count; i++) {
            int productId = RandomGenerator.generateInt(0, size);
            Product product = products.get(productId);
            int quantity = RandomGenerator.generateInt(Integer.parseInt(quantityRange[0]), Integer.parseInt(quantityRange[1]));
            sum = sum.add(BigDecimal.valueOf(quantity * product.getPrice()));
            builder.addProductToArray(product, quantity);
        }
        builder.endArray();
        return sum;
    }

    public String getOutputType() {
        return builder.getOutputType();
    }

}
