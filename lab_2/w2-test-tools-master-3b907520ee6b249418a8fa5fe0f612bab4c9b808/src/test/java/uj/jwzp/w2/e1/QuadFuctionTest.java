package uj.jwzp.w2.e1;

import org.assertj.core.api.Assertions;
import org.junit.*;

import java.util.List;

public class QuadFuctionTest {

    @Test
    public void empty() {
        QuadFuction uut = new QuadFuction();
        List<Double> result = uut.quadFunction(1,2,3);
        Assert.assertEquals(0, result.size());
//        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void oneResult() {
        QuadFuction uut = new QuadFuction();
        List<Double> result = uut.quadFunction(2,4,2);
        Assert.assertEquals(-1.0, result.get(0), 0.0001);
//        Assertions.assertThat(result).containsExactly(-1.0);
    }

    @Test
    public void twoResults() {
        QuadFuction uut = new QuadFuction();
        List<Double> result = uut.quadFunction(2,2,-12);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(-25.5, result.get(0), 0.0001);
        Assert.assertEquals(24.5, result.get(1), 0.0001);
//        Assertions.assertThat(result).containsExactly(-25.5, 24.5);
    }

}
