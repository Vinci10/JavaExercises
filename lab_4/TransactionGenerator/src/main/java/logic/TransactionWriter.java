package logic;

import model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Lazy
public class TransactionWriter {
    private List<String> transactions;
    private File outDir;
    private File itemsFile;
    private final static Logger fileLogger = LoggerFactory.getLogger("File");
    private final static Logger consoleLogger = LoggerFactory.getLogger("Console");
    private DataFormatBuilder builder;

    public TransactionWriter(DataFormatBuilder builder) {
        this.builder = builder;
        transactions = new ArrayList<>();
    }

    public void generate(Map<String, String> parameters, CSVReader csvReader) throws Exception {
        calculatePaths(parameters);
        int eventsCount = Integer.parseInt(parameters.get("eventsCount"));
        try {
            if (pathsAreCorrect()) {
                for (int i = 1; i <= eventsCount; i++) {
                    builder.addNumber("id", i);
                    String generatedDate = RandomGenerator.generateTimestamp(parameters.get("dateRange"));
                    builder.addString("timestamp", generatedDate + "-0100");
                    String[] range = parameters.get("customerIds").split(":");
                    builder.addNumber("customer_id", RandomGenerator.generateInt(Integer.parseInt(range[0]), Integer.parseInt(range[1])));
                    double sum = writeItems(csvReader.read(itemsFile), parameters.get("itemsCount").split(":"),
                            parameters.get("itemsQuantity").split(":"));
                    String str = String.format("%1.2f", sum).replace(',', '.');
                    sum = Double.valueOf(str);
                    builder.addNumber("sum", sum);
                    transactions.add(builder.getResult());
                    consoleLogger.info("Transaction was generated successfully");
                    builder.reset();
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

    public double writeItems(List<Product> products, String[] itemsCount, String[] quantityRange) throws Exception {
        builder.startArray("items");
        int size = products.size() - 1;
        int count = RandomGenerator.generateInt(Integer.parseInt(itemsCount[0]), Integer.parseInt(itemsCount[1]));
        double sum = 0;
        for (int i = 0; i < count; i++) {
            int productId = RandomGenerator.generateInt(0, size);
            Product product = products.get(productId);
            int quantity = RandomGenerator.generateInt(Integer.parseInt(quantityRange[0]), Integer.parseInt(quantityRange[1]));
            sum += quantity * product.getPrice();
            builder.addProductToArray(product, quantity);
        }
        builder.endArray();
        return sum;
    }

    public void writeToFile(PrintWriter writer, String transaction, String path) throws IOException {
        writer.println(transaction);
        fileLogger.info("Generated transaction: " + path);
    }

    public void writeAll() throws IOException {
        consoleLogger.info("Writing all transactions to files... Output directory: " + outDir.getPath());
        try {
            int size = transactions.size();
            for (int i = 0; i < size; i++) {
                String p = Paths.get(outDir.getPath(), "transaction" + (i + 1) + builder.getOutputType()).toString();
                try (PrintWriter out = new PrintWriter(p)) {
                    writeToFile(out, transactions.get(i), p);
                }
            }
        } catch (IOException e) {
            throw new IOException("Cannot write transaction to file or wrong csv file");
        }
    }

    private void calculatePaths(Map<String, String> parameters) {
        consoleLogger.debug("Calculate paths");
        Path jarPath = Paths.get(parameters.get("jarDir"));
        Path outPath = Paths.get(parameters.get("outDir"));
        outDir = new File(jarPath.resolve(outPath).normalize().toString());
        Path itemsFilePath = Paths.get(parameters.get("itemsFile"));
        itemsFile = new File(jarPath.resolve(itemsFilePath).normalize().toString());
    }

    public boolean pathsAreCorrect() {
        if (outDir != null && itemsFile != null && outDir.exists() && itemsFile.exists()){
            consoleLogger.debug("Correct paths");
            return true;
        }
        consoleLogger.error("Incorrect paths");
        return false;
    }
}
