import java.util.Map;

public class Main {
    public static void main(String[] args){
        Map<String, String> parameters;
        try {
            parameters = new CmdParser(args).parse();
            TransactionWriter writer = new TransactionWriter();
            writer.generateTransactionsInJsonFormat(parameters, new CSVReader());
            writer.writeAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


