package being.altiplano.example.proxytest;

import being.altiplano.example.Calc;
import being.altiplano.example.CalcImpl;
import being.altiplano.example.bytebuddy.ByteBuddyProxyFactory;
import being.altiplano.example.cglib.NegativeMethodInterceptor;
import being.altiplano.example.javassist.JavassistProxyFactory;
import being.altiplano.example.javaproxy.NegativeInvocationHandler;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by gaoyuan on 12/03/2017.
 */
public class TestProxies {
    @Test
    public void testInvocationHandler() {
        Calc calc = NegativeInvocationHandler.newInstance(new CalcImpl(), Calc.class);
        test(calc);
    }

    @Test
    public void testCglib() {
        Calc calc = NegativeMethodInterceptor.newInstance();
        test(calc);
    }

    @Test
    public void testByteBuddy() {
        Calc calc = ByteBuddyProxyFactory.newInstance();
        test(calc);
    }

    @Test
    public void testJavassist() throws CannotCompileException, InstantiationException, NotFoundException, IllegalAccessException, IOException {
        Calc calc = JavassistProxyFactory.newInstance();
        test(calc);
    }

    private void test(Calc calc) {
        int a = 100;
        int b = 10;

        Assert.assertEquals(calc.plus(a, b), -(a + b));
        Assert.assertEquals(calc.minus(a, b), -(a - b));
        Assert.assertEquals(calc.multiply(a, b), -(a * b));
        Assert.assertEquals(calc.divide(a, b), -(a / b));
    }
}
