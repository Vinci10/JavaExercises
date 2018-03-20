import org.apache.commons.cli.*;

import java.io.File;
import java.security.CodeSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            parameters.put("customerIds", commandLine.getOptionValue("customerIds", "1:20"));
            parameters.put("dateRange", commandLine.getOptionValue("dateRange", getDefaultTimestamp()));
            parameters.put("itemsFile", commandLine.getOptionValue("itemsFile"));
            parameters.put("itemsCount", commandLine.getOptionValue("itemsCount", "1:5"));
            parameters.put("itemsQuantity", commandLine.getOptionValue("itemsQuantity", "1:5"));
            parameters.put("eventsCount", commandLine.getOptionValue("eventsCount", "100"));
            String dir = getCurrentDirectory();
            parameters.put("outDir", commandLine.getOptionValue("outDir", dir));
            parameters.put("jarDir", dir);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        return parameters;
    }

    private void addOptions() {
        options.addOption(new Option("customerIds", true, "range of customers id"));
        options.addOption(new Option("dateRange", true, "range of dates"));
        Option opt = new Option("itemsFile", true, "name of .csv file which contains names of products");
        opt.setRequired(true);
        options.addOption(opt);
        options.addOption(new Option("itemsCount", true, "count of generated items"));
        options.addOption(new Option("itemsQuantity", true, "quantity of items"));
        options.addOption(new Option("eventsCount", true, "count of generated transactions"));
        options.addOption(new Option("outDir", true, "output directory"));
    }

    private String getDefaultTimestamp() {
        LocalDate date = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(date, LocalTime.of(0, 0, 0, 0));
        LocalDateTime end = LocalDateTime.of(date, LocalTime.of(23, 59, 59, 999));
        return start.toString() + ":00.000:" + end.toString();
    }

    private String getCurrentDirectory() {
        CodeSource codeSource = CmdParser.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().getPath());
        return jarFile.getParentFile().getPath();
    }
}
