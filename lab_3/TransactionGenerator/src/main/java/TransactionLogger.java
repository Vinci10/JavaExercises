import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class TransactionLogger {
    private File outDir;
    private File itemsFile;
    private final static Logger fileLogger = LoggerFactory.getLogger("File");
    private final static Logger consoleLogger = LoggerFactory.getLogger("Console");


    public void generateTransactionAndLog(Map<String, String> parameters, CSVReader csvReader) throws Exception {
        calculatePaths(parameters);
        int eventsCount = Integer.parseInt(parameters.get("eventsCount"));
        JsonBuilder jsonBuilder;
        try {
            if (pathsAreCorrect()) {
                for (int i = 1; i <= eventsCount; i++) {
                    jsonBuilder = new JsonBuilder();
                    jsonBuilder.addNumber("id", i);
                    String generatedDate = RandomGenerator.generateTimestamp(parameters.get("dateRange"));
                    jsonBuilder.addString("timestamp", generatedDate + "-0100");
                    String[] range = parameters.get("customerIds").split(":");
                    jsonBuilder.addNumber("customer_id", RandomGenerator.generateInt(Integer.parseInt(range[0]), Integer.parseInt(range[1])));
                    double sum = writeItems(jsonBuilder, csvReader.read(itemsFile), parameters.get("itemsCount").split(":"),
                            parameters.get("itemsQuantity").split(":"));
                    String str = String.format("%1.2f", sum).replace(',', '.');
                    sum = Double.valueOf(str);
                    jsonBuilder.addNumber("sum", sum);
                    fileLogger.info(jsonBuilder.getResult());
                    consoleLogger.info("Transaction was logged successfully");
                }
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            throw new Exception("Wrong output directory or csv file");
        }catch (DateTimeParseException e) {
            throw new Exception("Invalid date");
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid range in parameters");
        } catch (Exception e) {
            throw new Exception("Invalid parameters");
        }
    }

    public double writeItems(JsonBuilder jsonBuilder, List<Product> products, String[] itemsCount, String[] quantityRange) throws Exception {
        jsonBuilder.startArray("items");
        int size = products.size() - 1;
        int count = RandomGenerator.generateInt(Integer.parseInt(itemsCount[0]), Integer.parseInt(itemsCount[1]));
        double sum = 0;
        for (int i = 0; i < count; i++) {
            int productId = RandomGenerator.generateInt(0, size);
            Product product = products.get(productId);
            int quantity = RandomGenerator.generateInt(Integer.parseInt(quantityRange[0]), Integer.parseInt(quantityRange[1]));
            sum += quantity * product.getPrice();
            jsonBuilder.addProductToArray(product, quantity);
        }
        jsonBuilder.endArray();
        return sum;
    }

    private void calculatePaths(Map<String, String> parameters) {
        Path jarPath = Paths.get(parameters.get("jarDir"));
        Path outPath = Paths.get(parameters.get("outDir"));
        outDir = new File(jarPath.resolve(outPath).normalize().toString());
        Path itemsFilePath = Paths.get(parameters.get("itemsFile"));
        itemsFile = new File(jarPath.resolve(itemsFilePath).normalize().toString());
    }

    public boolean pathsAreCorrect() {
        if (outDir != null && itemsFile != null && outDir.exists() && itemsFile.exists())
            return true;
        return false;
    }
}
