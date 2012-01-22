package net.codjo.test.common.mock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 */
public class ConnectionProxyFactory implements InvocationHandler {

    private ConnectionMock mock;


    public ConnectionProxyFactory(ConnectionMock s) {

        mock = s;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Find the equivalent method in the proxy class.
        Method m = ConnectionMock.class.getMethod(method.getName(), method.getParameterTypes());
        if (m == null) {
            throw new SQLException("Unsupported method " + method.getName());
        }

        return m.invoke(mock, args);
    }


    public static Connection getConnectionProxy(final ConnectionMock s) {
        return (Connection)Proxy.newProxyInstance
              (Connection.class.getClassLoader(),
               new Class[]{Connection.class},
               new ConnectionProxyFactory(s)
              );
    }
}

