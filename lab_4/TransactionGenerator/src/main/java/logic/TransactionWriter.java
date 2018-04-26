package logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Service
@Lazy
public class TransactionWriter {
    private Map<String, String> parameters;
    private final static Logger consoleLogger = LoggerFactory.getLogger("Console");
    private final static Logger fileLogger = LoggerFactory.getLogger("File");
    private TransactionGenerator generator;
    private String itemsPath;
    private String outDir;

    public TransactionWriter(Map<String, String> parameters, TransactionGenerator generator) {
        this.parameters = parameters;
        this.generator = generator;
        calculatePaths();
    }

    public void writeTransactions() throws Exception {
        if (!pathsAreCorrect())
            throw new Exception("Cannot save transactions");
        int eventsCount = Integer.parseInt(parameters.get("eventsCount"));
        String transaction;
        for (int i = 0; i < eventsCount; i++) {
            transaction = generator.generate(parameters, itemsPath);
            String p = Paths.get(outDir, "transaction" + (i + 1) + generator.getOutputType()).toString();
            PrintWriter writer = new PrintWriter(p);
            writer.println(transaction);
            fileLogger.info("Generated transaction: " + p);
            writer.close();
        }
        consoleLogger.info("Transaction was generated successfully");
    }

    private void calculatePaths() {
        consoleLogger.debug("Calculate paths");
        Path jarPath = Paths.get(parameters.get("jarDir"));
        Path outPath = Paths.get(parameters.get("outDir"));
        outDir = jarPath.resolve(outPath).normalize().toString();
        Path itemsFilePath = Paths.get(parameters.get("itemsFile"));
        itemsPath = jarPath.resolve(itemsFilePath).normalize().toString();
    }

    public boolean pathsAreCorrect() throws IOException {
        File out = new File(outDir);
        if (!out.exists()) {
            if (!out.mkdirs())
                consoleLogger.error("Cannot create output directory");
        }
        File itemsFile = new File(itemsPath);
        if (itemsFile.exists()) {
            consoleLogger.debug("Correct paths");
            return true;
        }
        consoleLogger.error("Incorrect csv file path");
        return false;
    }
}
