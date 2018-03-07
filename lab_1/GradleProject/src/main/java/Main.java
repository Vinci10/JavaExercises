import org.apache.commons.lang.math.NumberRange;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        NumberRange range = new NumberRange(5,25);
        System.out.println(range.getMinimumNumber());
        Properties prop = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("db.properties")) {
            prop.load(input);
            System.out.println("db.properties");
            System.out.println("database.jdbc.driverClass=" + prop.getProperty("database.jdbc.driverClass"));
            System.out.println("database.jdbc.connectionURL=" + prop.getProperty("database.jdbc.connectionURL"));
            System.out.println("database.jdbc.username=" + prop.getProperty("database.jdbc.username"));
            System.out.println("database.jdbc.password=" + prop.getProperty("database.jdbc.password"));
        } catch (IOException ex) {
            System.out.println("error");
        }
    }
}
