package uj.jwzp.w2.e1;

import java.util.ArrayList;
import java.util.List;

public class QuadFuction {

    public List<Double> quadFunction(double a, double b, double c) {
        double delta = b * b - 4 * a * c;
        ArrayList<Double> result = new ArrayList<>(2);
        if (delta > 0) {
            result.add(0, (-b - delta) / (2 * a));
            result.add(1, (-b + delta) / (2 * a));
        } else if (delta == 0) {
            result.add(0, -b / (2 * a));
        }
        return result;
    }
}
