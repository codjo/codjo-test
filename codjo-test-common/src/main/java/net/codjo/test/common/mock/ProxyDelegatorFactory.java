package net.codjo.test.common.mock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
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


    public Object invoke(Object proxy, Method jdbcMethod, Object[] args) throws Throwable {
        Method proxyMethod = delegate.getClass().getMethod(jdbcMethod.getName(), jdbcMethod.getParameterTypes());
        if (proxyMethod == null) {
            throw new UnsupportedOperationException("Unsupported method " + jdbcMethod.getName());
        }

        proxyMethod.setAccessible(true);
        
        try {
            return proxyMethod.invoke(delegate, args);
        }
        catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }


    public static <T> T getProxy(final Object delegate, Class<T> stubClass) {
        //noinspection unchecked
        return (T)Proxy.newProxyInstance(stubClass.getClassLoader(),
                                         new Class[]{stubClass},
                                         new ProxyDelegatorFactory(delegate));
    }
}

