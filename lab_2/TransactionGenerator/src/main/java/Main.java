import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        HashMap<String, String> parameters = null;
        try {
            parameters = new CmdParser(args).parse();
            TransactionWriter writer = new TransactionWriter();
            writer.writeTransactions(parameters);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


