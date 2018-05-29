package logic;

import org.apache.commons.cli.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Lazy
public class CmdParser {
    private String[] args;
    private Options options;
    private Map<String, String> parameters;

    public CmdParser(String[] args) {
        this.args = args;
        options = new Options();
        parameters = new HashMap<>();
        addOptions();
    }

    public Map<String, String> parse() throws IllegalArgumentException, IOException, ParseException {
        CommandLineParser commandLineParser = new BasicParser();
        CommandLine commandLine;
        commandLine = commandLineParser.parse(options, args);
        if (commandLine.hasOption("configFile")) {
            parseFile(commandLine.getOptionValue("configFile"));
        } else {
            if (!commandLine.hasOption("itemsFile"))
                throw new IllegalArgumentException("Missing required parameter: itemsFile");
            if (commandLine.hasOption("broker")) {
                parameters.put("broker", commandLine.getOptionValue("broker"));
                if (!commandLine.hasOption("queue") && !commandLine.hasOption("topic"))
                    throw new IllegalArgumentException("Missing required parameters: queue or topic");
                if (commandLine.hasOption("queue") && commandLine.getOptionValue("queue").length() > 0)
                    parameters.put("queue", commandLine.getOptionValue("queue"));
                if (commandLine.hasOption("topic") && commandLine.getOptionValue("topic").length() > 0)
                    parameters.put("topic", commandLine.getOptionValue("topic"));
            }
            parameters.put("customerIds", commandLine.getOptionValue("customerIds", "1:20"));
            parameters.put("dateRange", commandLine.getOptionValue("dateRange", getDefaultTimestamp()));
            parameters.put("itemsFile", commandLine.getOptionValue("itemsFile"));
            parameters.put("itemsCount", commandLine.getOptionValue("itemsCount", "1:5"));
            parameters.put("itemsQuantity", commandLine.getOptionValue("itemsQuantity", "1:5"));
            parameters.put("eventsCount", commandLine.getOptionValue("eventsCount", "100"));
            parameters.put("format", commandLine.getOptionValue("format", "json").toLowerCase());
            String dir = getCurrentDirectory();
            parameters.put("outDir", commandLine.getOptionValue("outDir", dir));
            parameters.put("jarDir", dir);
        }
        return parameters;
    }

    private void addOptions() {
        options.addOption(new Option("customerIds", true, "range of customers id"));
        options.addOption(new Option("dateRange", true, "range of dates"));
        options.addOption(new Option("itemsFile", true, "name of .csv file which contains names of products"));
        options.addOption(new Option("itemsCount", true, "count of generated items"));
        options.addOption(new Option("itemsQuantity", true, "quantity of items"));
        options.addOption(new Option("eventsCount", true, "count of generated transactions"));
        options.addOption(new Option("outDir", true, "output directory"));
        options.addOption(new Option("format", true, "output format"));
        options.addOption(new Option("configFile", true, "file with parameters"));
        options.addOption(new Option("broker", true, "JMS broker"));
        options.addOption(new Option("queue", true, "JMS queue"));
        options.addOption(new Option("topic", true, "JMS topic"));
    }

    private String getDefaultTimestamp() {
        LocalDate date = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(date, LocalTime.of(0, 0, 0, 0));
        LocalDateTime end = LocalDateTime.of(date, LocalTime.of(23, 59, 59, 999));
        return start.toString() + ":00.000:" + end.toString();
    }

    private String getCurrentDirectory() {
        return new File("").getAbsolutePath();
    }

    private void parseFile(String path) throws IOException {
        path = Paths.get(getCurrentDirectory()).resolve(path).toString();
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Map<String, String> v = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] params = line.trim().split("=");
            v.put(params[0].trim(), params[1].trim());
        }
        br.close();
        parameters.put("customerIds", v.getOrDefault("customerIds", "1:20"));
        parameters.put("dateRange", v.getOrDefault("dateRange", getDefaultTimestamp()).replaceAll("\"", ""));
        parameters.put("itemsFile", v.get("itemsFile"));
        parameters.put("itemsCount", v.getOrDefault("itemsCount", "1:5"));
        parameters.put("itemsQuantity", v.getOrDefault("itemsQuantity", "1:5"));
        parameters.put("eventsCount", v.getOrDefault("eventsCount", "100"));
        parameters.put("format", v.getOrDefault("format", "json").toLowerCase());
        String dir = getCurrentDirectory();
        parameters.put("outDir", v.getOrDefault("outDir", dir));
        parameters.put("jarDir", dir);
    }
}
