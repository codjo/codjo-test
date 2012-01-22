package net.codjo.test.common.mock;
import java.sql.Connection;
import net.codjo.test.common.LogCallAssert;
import net.codjo.test.common.LogString;
import org.junit.Before;
import org.junit.Test;
/**
 *
 */
public class JDBCProxyFactoryTest {
    private LogString logString;


    @Test
    public void test_connectionProxy() throws Exception {
        Connection connectionProxy = ConnectionProxyFactory.getConnectionProxy(new ConnectionMock(logString));

        LogCallAssert logCallAssert = new LogCallAssert(Connection.class);

        logCallAssert.assertCalls(connectionProxy, logString);

    }

    @Before
    public void setUp() throws Exception {
        logString = new LogString();
    }
}
