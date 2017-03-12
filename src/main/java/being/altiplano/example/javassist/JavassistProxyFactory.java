package being.altiplano.example.javassist;

import being.altiplano.example.Calc;
import being.altiplano.example.CalcImpl;
import javassist.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gaoyuan on 12/03/2017.
 */
public class JavassistProxyFactory {
    public static Calc newInstance() throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException, IOException {
        Set<String> calcMethods = new HashSet<String>();
        for (Method m : Calc.class.getMethods()) {
            calcMethods.add(m.getName());
        }
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.makeClass(CalcImpl.class.getName() + "Ext");
        cc.setSuperclass(cp.get(CalcImpl.class.getName()));

        for (String methodName : calcMethods) {
            CtMethod m = CtNewMethod.make("public long " + methodName + "(long a, long b) { return - super." + methodName + "(a,b); }", cc);
            cc.addMethod(m);
        }
        cc.writeFile(System.getProperty("java.io.tmpdir") + JavassistProxyFactory.class.getName());

        Class c = cc.toClass();
        Calc h = (Calc) c.newInstance();
        return h;
    }
}