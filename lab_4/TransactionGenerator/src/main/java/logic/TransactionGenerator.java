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
    private RandomGenerator random;

    public TransactionGenerator(DataFormatBuilder builder, CSVReader reader, RandomGenerator random) {
        this.builder = builder;
        this.csvReader = reader;
        this.random = random;
        counter = 1;
    }

    public String generate(Map<String, String> parameters, String itemsPath) throws Exception {
        try {
            builder.addNumber("id", counter);
            String generatedDate = random.generateTimestamp(parameters.get("dateRange"));
            builder.addString("timestamp", generatedDate + "-0100");
            String[] range = parameters.get("customerIds").split(":");
            builder.addNumber("customer_id", random.generateInt(Integer.parseInt(range[0]), Integer.parseInt(range[1])));
            BigDecimal sum = generateItems(csvReader.read(itemsPath), parameters.get("itemsCount").split(":"),
                    parameters.get("itemsQuantity").split(":"));
            builder.addNumber("sum", sum.setScale(2, BigDecimal.ROUND_HALF_UP));
            String result = builder.getResult();
            builder.reset();
            counter++;
            return result;
        } catch (DateTimeParseException e) {
            consoleLogger.error(e.toString());
            throw new Exception("Invalid date");
        } catch (IllegalArgumentException e) {
            consoleLogger.error(e.toString());
            throw new Exception("Invalid range in parameters");
        } catch (Exception e) {
            consoleLogger.error(e.toString());
            throw new Exception("Invalid parameters");
        }
    }

    public BigDecimal generateItems(List<Product> products, String[] itemsCount, String[] quantityRange) throws Exception {
        builder.startArray("items");
        int size = products.size() - 1;
        int count = getRandomCount(Integer.parseInt(itemsCount[0]), Integer.parseInt(itemsCount[1]));
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < count; i++) {
            Product product = getRandomProduct(products, size);
            int quantity = getRandomQuantity(Integer.parseInt(quantityRange[0]), Integer.parseInt(quantityRange[1]));
            sum = sum.add(BigDecimal.valueOf(quantity * product.getPrice()));
            builder.addProductToArray(product, quantity);
        }
        builder.endArray();
        return sum;
    }

    public String getOutputType() {
        return builder.getOutputType();
    }

    public Product getRandomProduct(List<Product> products, int size) {
        int productId = random.generateInt(0, size);
        return products.get(productId);
    }

    public int getRandomQuantity(int min, int max){
        return random.generateInt(min, max);
    }

    public int getRandomCount(int min, int max) {
        return random.generateInt(min, max);
    }
}
