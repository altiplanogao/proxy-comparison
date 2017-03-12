package being.altiplano.example.javaproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by gaoyuan on 12/03/2017.
 */
public class NegativeInvocationHandler implements InvocationHandler {
    private final Object obj;

    public NegativeInvocationHandler(Object obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        Object result;
        final String methodName = m.getName();
        try {
            System.out.println("Enter method: " + methodName);
            result = m.invoke(obj, args);
            if (result instanceof Long) {
                result = -(Long) result;
            }
        } catch (InvocationTargetException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            System.out.println("Leave method: " + methodName);
        }
        return result;
    }

    public static <T> T newInstance(Object ob, Class<T> type) {
        return (T) Proxy.newProxyInstance(ob.getClass().getClassLoader(),
                new Class<?>[]{type}, new NegativeInvocationHandler(ob));
    }

}
