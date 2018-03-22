import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Main {
    private final static Logger logger = LoggerFactory.getLogger("Console");
    public static void main(String[] args){
        Map<String, String> parameters;
        try {
            logger.info("Start application");
            parameters = new CmdParser(args).parse();
            TransactionWriter writer = new TransactionWriter();
            logger.info("Generating transactions...");
            writer.generateTransactionsInJsonFormat(parameters, new CSVReader());
            writer.writeAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}


