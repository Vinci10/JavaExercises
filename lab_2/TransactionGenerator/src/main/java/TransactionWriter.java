import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

public class TransactionWriter {
    public void writeTransactions(HashMap<String, String> parameters) {
        try {
            Gson gson = new Gson();
            int eventsCount = Integer.parseInt(parameters.get("eventsCount"));
            Path jarPath = Paths.get(parameters.get("jarDir"));
            Path outPath = Paths.get(parameters.get("outDir"));
            File outDir = new File(jarPath.resolve(outPath).normalize().toString());
            Path itemsFilePath = Paths.get(parameters.get("itemsFile"));
            File itemsFile = new File(jarPath.resolve(itemsFilePath).normalize().toString());
            if (outDir.exists() && itemsFile.exists()) {
                File transaction;
                Path transactionPath;
                OutputStreamWriter outputStreamWriter;
                JsonWriter writer;
                for (int i = 1; i <= eventsCount; i++) {
                    transactionPath = Paths.get(outDir.getPath(), "transaction" + i + ".json");
                    transaction = new File(transactionPath.toString());
                    outputStreamWriter = new FileWriter(transaction);
                    writer = gson.newJsonWriter(outputStreamWriter);
                    writer.setIndent("\t");
                    writer.beginObject();
                    writer.name("id").value(i);
                    String generatedDate = RandomGenerator.generateTimestamp(parameters.get("dateRange"));
                    writer.name("timestamp").value(generatedDate + "-0100");
                    String[] range = parameters.get("customerIds").split(":");
                    writer.name("customer_id").value(RandomGenerator.generateInt(Integer.parseInt(range[0]), Integer.parseInt(range[1])));
                    double sum = writeItems(writer, CSVReader.read(itemsFile), parameters.get("itemsCount").split(":"),
                            parameters.get("itemsQuantity").split(":"));
                    String str = String.format("%1.2f", sum).replace(',', '.');
                    sum = Double.valueOf(str);
                    writer.name("sum").value(sum);
                    writer.endObject();
                    writer.flush();
                    writer.close();
                    System.out.println("Generated file: " + transaction.getAbsolutePath());
                }
            } else {
                System.out.println("Wrong output directory or csv file");
            }
        } catch (InvalidPathException e) {
            System.out.println("Invalid path");
        } catch (IOException e) {
            System.out.println("Cannot write transaction to file or wrong csv file");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid range in parameters");
        } catch (Exception e) {
            System.out.println("Invalid parameters");
        }
    }

    private double writeItems(JsonWriter writer, List<Product> products, String[] itemsCount, String[] quantityRange) throws IOException {
        writer.name("items").beginArray();
        int size = products.size() - 1;
        int count = RandomGenerator.generateInt(Integer.parseInt(itemsCount[0]), Integer.parseInt(itemsCount[1]));
        double sum = 0;
        for (int i = 0; i < count; i++) {
            int productId = RandomGenerator.generateInt(0, size);
            Product product = products.get(productId);
            writer.beginObject();
            writer.name("name").value(product.getName());
            int quantity = RandomGenerator.generateInt(Integer.parseInt(quantityRange[0]), Integer.parseInt(quantityRange[1]));
            writer.name("quantity").value(quantity);
            double price = product.getPrice();
            writer.name("price").value(price);
            sum += quantity * price;
            writer.endObject();
        }
        writer.endArray();
        return sum;
    }
}
