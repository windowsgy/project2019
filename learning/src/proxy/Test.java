package proxy;

import java.lang.reflect.Proxy;

/**
 * Created by jlgaoyuan on 2018/11/26.
 *
 */
public class Test {
    public static void main(String[] args) {
        Subject realSubject = new RealSubject();
        DynamicProxy proxy = new DynamicProxy(realSubject);
        ClassLoader classLoader = realSubject.getClass().getClassLoader();
        Subject subject = (Subject) Proxy.newProxyInstance(classLoader, new  Class[]{Subject.class}, proxy);
        subject.visit();
    }
}
