package net.codjo.test.common.mock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 *
 */
public class ProxyDelegatorFactory implements InvocationHandler {

    private Object delegate;


    public ProxyDelegatorFactory(Object delegate) {
        this.delegate = delegate;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Find the equivalent method in the proxy class.
        Method m = delegate.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (m == null) {
            throw new UnsupportedOperationException("Unsupported method " + method.getName());
        }

        return m.invoke(delegate, args);
    }


    public static <T> T getProxy(final Object delegate, Class<T> stubClass) {
        //noinspection unchecked
        return (T)Proxy.newProxyInstance(stubClass.getClassLoader(),
                                         new Class[]{stubClass},
                                         new ProxyDelegatorFactory(delegate));
    }
}

