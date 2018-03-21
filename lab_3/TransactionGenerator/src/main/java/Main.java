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
            TransactionLogger log = new TransactionLogger();
            logger.info("Generate transaction and log to file");
            log.generateTransactionAndLog(parameters, new CSVReader());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}


