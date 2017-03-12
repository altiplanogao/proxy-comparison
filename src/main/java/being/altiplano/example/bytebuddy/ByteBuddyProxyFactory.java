package being.altiplano.example.bytebuddy;

import being.altiplano.example.Calc;
import being.altiplano.example.CalcImpl;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.concurrent.Callable;

/**
 * Created by gaoyuan on 12/03/2017.
 */
public class ByteBuddyProxyFactory {
    public static class Target {
        public static long calc(@SuperCall Callable<Long> zuper) throws Exception {
            return -zuper.call();
        }
    }

    public static Calc newInstance() {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(CalcImpl.class)
                .method(ElementMatchers.takesArguments(2))
                .intercept(MethodDelegation.to(Target.class))
                .make()
                .load(ByteBuddyProxyFactory.class.getClassLoader())
                .getLoaded();

        try {
            return (Calc) dynamicType.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
