package logic;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
@Scope("singleton")
public class RandomGenerator {
    private final Random random;

    public RandomGenerator() {
        random = new Random();
    }

    public int generateInt(int min, int max) throws IllegalArgumentException {
        if (min < 0 || max < 0 || max < min) throw new IllegalArgumentException();
        return random.nextInt((max - min) + 1) + min;
    }

    public String generateTimestamp(String s) throws DateTimeParseException {
        int index = s.indexOf(':', 18);
        String st = s.substring(0, index);
        if (st.length() > 23) {
            st = st.substring(0, 23);
        }
        String e = s.substring(index + 1, s.length());
        if (e.length() > 23) {
            e = e.substring(0, 23);
        }
        LocalDateTime start = LocalDateTime.parse(st);
        LocalDateTime end = LocalDateTime.parse(e);
        long amount = start.until(end, ChronoUnit.NANOS);
        if (amount < 0) throw new IllegalArgumentException();
        long generatedDate = (long) (random.nextFloat() * amount);
        return start.plusNanos(generatedDate).toString();
    }
}
