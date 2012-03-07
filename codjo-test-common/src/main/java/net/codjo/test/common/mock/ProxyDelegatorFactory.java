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
    private Method equalsMethod;


    public ProxyDelegatorFactory(Object delegate) {
        this.delegate = delegate;
        this.equalsMethod = getEqualsMethodOf(delegate);
    }


    public Object invoke(Object proxy, Method jdbcMethod, Object[] args) throws Throwable {
        Method proxyMethod = getProxyMethod(jdbcMethod);

        proxyMethod.setAccessible(true);

        if (equalsMethod.equals(proxyMethod)) {
            Object obj = args[0];
            if (obj != null && Proxy.isProxyClass(obj.getClass())) {
                args[0] = ((ProxyDelegatorFactory)Proxy.getInvocationHandler(obj)).delegate;
            }
        }

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


    private Method getProxyMethod(Method jdbcMethod) {
        try {
            return delegate.getClass().getMethod(jdbcMethod.getName(), jdbcMethod.getParameterTypes());
        }
        catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException("Proxy >" + delegate.getClass().getName() + "< "
                                                    + "does not support method >" + jdbcMethod.toGenericString() + "<");
        }
    }


    private static Method getEqualsMethodOf(Object delegate) {
        try {
            return delegate.getClass().getMethod("equals", Object.class);
        }
        catch (NoSuchMethodException e) {
            throw new InternalError("Unable to find method equals(Object) on " + delegate.getClass());
        }
    }
}

