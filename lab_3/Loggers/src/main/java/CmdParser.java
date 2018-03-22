import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class CmdParser {
    private String[] args;
    private Options options;

    public CmdParser(String[] args) {
        this.args = args;
        options = new Options();
        addOptions();
    }

    public Map<String, String> parse() throws IllegalArgumentException {
        Map<String, String> parameters = new HashMap<>();
        CommandLineParser commandLineParser = new BasicParser();
        CommandLine commandLine;
        try {
            commandLine = commandLineParser.parse(options, args);
            parameters.put("customerId", commandLine.getOptionValue("customerId"));
            parameters.put("ticketPrice", commandLine.getOptionValue("ticketPrice"));
            parameters.put("age", commandLine.getOptionValue("age"));
            parameters.put("companyId", commandLine.getOptionValue("companyId"));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        return parameters;
    }

    private void addOptions() {
        Option opt = new Option("customerId", true, "customer Id");
        opt.setRequired(true);
        options.addOption(opt);
        Option opt2 = new Option("ticketPrice", true, "ticket price");
        opt.setRequired(true);
        options.addOption(opt2);
        Option opt3 = new Option("age", true, "customer age");
        opt.setRequired(true);
        options.addOption(opt3);
        Option opt4 = new Option("companyId", true, "company Id");
        opt.setRequired(true);
        options.addOption(opt4);
    }
}