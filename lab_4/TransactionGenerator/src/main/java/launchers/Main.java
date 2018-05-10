package launchers;

import logic.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import logic.CSVReader;

import java.util.Map;

public class Main {
    private final static Logger logger = LoggerFactory.getLogger("Console");

    public static void main(String[] args) {
        Map<String, String> parameters;
        try {
            ApplicationContext ctx = new AnnotationConfigApplicationContext("logic");
            CmdParser parser = (CmdParser) ctx.getBean("cmdParser", (Object) args);
            CSVReader reader = (CSVReader) ctx.getBean("CSVReader");
            logger.info("Start application");
            parameters = parser.parse();
            String format = parameters.get("format");
            DataFormatBuilder builder;
            if (format.equals("yaml")) {
                builder = (YamlBuilder) ctx.getBean("yamlBuilder");
            } else if (format.equals("xml")) {
                builder = (XMLBuilder) ctx.getBean("XMLBuilder");
            } else {
                builder = (JsonBuilder) ctx.getBean("jsonBuilder");
            }
            RandomGenerator randomGenerator = (RandomGenerator) ctx.getBean("randomGenerator");
            TransactionGenerator generator = (TransactionGenerator) ctx.getBean("transactionGenerator", builder, reader, randomGenerator);
            TransactionWriter writer = (TransactionWriter) ctx.getBean("transactionWriter", parameters, generator);
            writer.writeTransactions();
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
}


