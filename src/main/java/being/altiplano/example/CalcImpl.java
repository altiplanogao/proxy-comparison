package being.altiplano.example;

/**
 * Created by gaoyuan on 12/03/2017.
 */
public class CalcImpl implements Calc {
    public long plus(long a, long b) {
        return a + b;
    }

    public long minus(long a, long b) {
        return a - b;
    }

    public long multiply(long a, long b) {
        return a * b;
    }

    public long divide(long a, long b) {
        return a / b;
    }
}
