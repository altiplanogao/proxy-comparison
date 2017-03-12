package being.altiplano.example.cglib;

import being.altiplano.example.Calc;
import being.altiplano.example.CalcImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by gaoyuan on 12/03/2017.
 */
public class NegativeMethodInterceptor implements MethodInterceptor {
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object retVal = null;
        try {
            if (!Modifier.isAbstract(method.getModifiers())) {
                retVal = proxy.invokeSuper(obj, args);
            }
        } finally {
            if (retVal instanceof Long) {
                retVal = -(Long) retVal;
            }
        }
        return retVal;
    }

    public static Calc newInstance() {
        NegativeMethodInterceptor interceptor = new NegativeMethodInterceptor();
        Enhancer e = new Enhancer();
        e.setSuperclass(CalcImpl.class);
        e.setCallback(interceptor);
        Object bean = e.create();
        return (Calc) bean;
    }
}
