import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.*;

public class RandomGeneratorTest {
    @Test (expected = IllegalArgumentException.class)
    public void checkGenerateIntMethodWrongRangeOnlyPositive() {
        int result = RandomGenerator.generateInt(-5, 78);
    }

    @Test
    public void checkGenerateIntMethodCorrectRange() {
        int result = RandomGenerator.generateInt(5, 78);
        int result1 = RandomGenerator.generateInt(0, 7);
        int result2 = RandomGenerator.generateInt(55, 57);
        assertTrue(result >=5);
        assertTrue(result <=78);
        assertTrue(result1 >=0);
        assertTrue(result1 <=7);
        assertTrue(result2 >=55);
        assertTrue(result2 <=57);
    }

    @Test (expected = DateTimeParseException.class)
    public void checkGenerateTimestampMethodWrongDate() {
        String result = RandomGenerator.generateTimestamp("2018-78-08T00:00:00.000-0100:2018-03-08T23:59:59.999");
    }

    @Test (expected = IllegalArgumentException.class)
    public void checkGenerateTimestampMethodWrongRange() {
        String result = RandomGenerator.generateTimestamp("2018-03-08T00:00:00.000-0100:2018-03-07T23:59:59.999");
    }

    @Test
    public void checkGenerateTimestampMethodCorrectRange() {
        LocalDateTime result = LocalDateTime.parse(RandomGenerator.generateTimestamp("2018-03-08T00:00:00.000:2018-03-08T23:59:59.997"));

        LocalDateTime start = LocalDateTime.parse("2018-03-08T00:00:00.000");
        LocalDateTime end = LocalDateTime.parse("2018-03-08T23:59:59.997");

        assertTrue(result.isAfter(start) || result.isEqual(start));
        assertTrue(result.isBefore(end)|| result.isEqual(end));

    }

    @Test
    public void checkGenerateTimestampMethodCorrectRange1() {
        LocalDateTime result = LocalDateTime.parse(RandomGenerator.generateTimestamp("2018-03-12T16:58:21.123:2018-03-25T23:59:59.999"));

        LocalDateTime start = LocalDateTime.parse("2018-03-07T16:58:21.123");
        LocalDateTime end = LocalDateTime.parse("2018-03-25T23:59:59.999");

        assertTrue(result.isAfter(start) || result.isEqual(start));
        assertTrue(result.isBefore(end) || result.isEqual(end));
    }

    @Test
    public void checkGenerateTimestampMethodCorrectRange2() {
        LocalDateTime result = LocalDateTime.parse(RandomGenerator.generateTimestamp("2018-05-08T21:00:54.425:2018-09-08T23:59:59.989"));

        LocalDateTime start = LocalDateTime.parse("2018-05-08T21:00:54.425");
        LocalDateTime end = LocalDateTime.parse("2018-09-08T23:59:59.989");

        assertTrue(result.isAfter(start) || result.isEqual(start));
        assertTrue(result.isBefore(end)|| result.isEqual(end));
    }
}
