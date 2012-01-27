package net.codjo.test.common.mock;
import java.sql.Connection;
import net.codjo.test.common.LogCallAssert;
import net.codjo.test.common.LogString;
import org.junit.Before;
import org.junit.Test;
/**
 *
 */
public class JDBCMockTest {
    private LogString logString;


    @Test
    public void test_connectionProxy() throws Exception {
        ConnectionMock connectionMock = new ConnectionMock(logString);
        LogCallAssert<Connection> logCallAssert = new LogCallAssert<Connection>(Connection.class);
        logCallAssert.assertCalls(connectionMock.get(), logString, jdbcJdk5ConnectionMethods());
    }


    private String[] jdbcJdk5ConnectionMethods() {
        return new String[]{
              "public abstract void java.sql.Connection.setReadOnly(boolean) throws java.sql.SQLException",
              "public abstract boolean java.sql.Connection.isReadOnly() throws java.sql.SQLException",
              "public abstract void java.sql.Connection.close() throws java.sql.SQLException",
              "public abstract boolean java.sql.Connection.isClosed() throws java.sql.SQLException",
              "public abstract java.sql.Statement java.sql.Connection.createStatement(int,int) throws java.sql.SQLException",
              "public abstract java.sql.Statement java.sql.Connection.createStatement(int,int,int) throws java.sql.SQLException",
              "public abstract java.sql.Statement java.sql.Connection.createStatement() throws java.sql.SQLException",
              "public abstract java.sql.PreparedStatement java.sql.Connection.prepareStatement(java.lang.String,int) throws java.sql.SQLException",
              "public abstract java.sql.PreparedStatement java.sql.Connection.prepareStatement(java.lang.String,int[]) throws java.sql.SQLException",
              "public abstract java.sql.PreparedStatement java.sql.Connection.prepareStatement(java.lang.String,java.lang.String[]) throws java.sql.SQLException",
              "public abstract java.sql.PreparedStatement java.sql.Connection.prepareStatement(java.lang.String) throws java.sql.SQLException",
              "public abstract java.sql.PreparedStatement java.sql.Connection.prepareStatement(java.lang.String,int,int) throws java.sql.SQLException",
              "public abstract java.sql.PreparedStatement java.sql.Connection.prepareStatement(java.lang.String,int,int,int) throws java.sql.SQLException",
              "public abstract java.sql.CallableStatement java.sql.Connection.prepareCall(java.lang.String) throws java.sql.SQLException",
              "public abstract java.sql.CallableStatement java.sql.Connection.prepareCall(java.lang.String,int,int,int) throws java.sql.SQLException",
              "public abstract java.sql.CallableStatement java.sql.Connection.prepareCall(java.lang.String,int,int) throws java.sql.SQLException",
              "public abstract java.lang.String java.sql.Connection.nativeSQL(java.lang.String) throws java.sql.SQLException",
              "public abstract void java.sql.Connection.setAutoCommit(boolean) throws java.sql.SQLException",
              "public abstract boolean java.sql.Connection.getAutoCommit() throws java.sql.SQLException",
              "public abstract void java.sql.Connection.commit() throws java.sql.SQLException",
              "public abstract void java.sql.Connection.rollback() throws java.sql.SQLException",
              "public abstract void java.sql.Connection.rollback(java.sql.Savepoint) throws java.sql.SQLException",
              "public abstract java.sql.DatabaseMetaData java.sql.Connection.getMetaData() throws java.sql.SQLException",
              "public abstract void java.sql.Connection.setHoldability(int) throws java.sql.SQLException",
              "public abstract void java.sql.Connection.setCatalog(java.lang.String) throws java.sql.SQLException",
              "public abstract java.lang.String java.sql.Connection.getCatalog() throws java.sql.SQLException",
              "public abstract void java.sql.Connection.setTransactionIsolation(int) throws java.sql.SQLException",
              "public abstract int java.sql.Connection.getHoldability() throws java.sql.SQLException",
              "public abstract java.sql.Savepoint java.sql.Connection.setSavepoint() throws java.sql.SQLException",
              "public abstract java.sql.Savepoint java.sql.Connection.setSavepoint(java.lang.String) throws java.sql.SQLException",
              "public abstract void java.sql.Connection.releaseSavepoint(java.sql.Savepoint) throws java.sql.SQLException",
              "public abstract int java.sql.Connection.getTransactionIsolation() throws java.sql.SQLException",
              "public abstract java.sql.SQLWarning java.sql.Connection.getWarnings() throws java.sql.SQLException",
              "public abstract void java.sql.Connection.clearWarnings() throws java.sql.SQLException",
              "public abstract java.util.Map<java.lang.String, java.lang.Class<?>> java.sql.Connection.getTypeMap() throws java.sql.SQLException",
              "public abstract void java.sql.Connection.setTypeMap(java.util.Map<java.lang.String, java.lang.Class<?>>) throws java.sql.SQLException"
        };
    }


    @Before
    public void setUp() throws Exception {
        logString = new LogString();
    }
}
