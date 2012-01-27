package net.codjo.test.common.mock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import net.codjo.test.common.LogCallAssert;
import net.codjo.test.common.LogString;
import org.exolab.castor.jdo.Database;
import org.junit.Before;
import org.junit.Test;
/**
 *
 */
public class JDBCMockTest {
    private LogString logString;


    @Test
    public void test_databaseProxy() throws Exception {
        DatabaseMock mock = new DatabaseMock(logString);
        LogCallAssert<Database> logCallAssert = new LogCallAssert<Database>(Database.class);
        logCallAssert.assertCalls(mock.getStub(), logString, castorDatabaseMethods());
    }


    @Test
    public void test_connectionProxy() throws Exception {
        ConnectionMock connectionMock = new ConnectionMock(logString);
        LogCallAssert<Connection> logCallAssert = new LogCallAssert<Connection>(Connection.class);
        logCallAssert.assertCalls(connectionMock.getStub(), logString, jdbcJdk5ConnectionMethods());
    }


    @Test
    public void test_statementProxy() throws Exception {
        StatementMock mock = new StatementMock(logString);
        LogCallAssert<Statement> logCallAssert = new LogCallAssert<Statement>(Statement.class);
        logCallAssert.assertCalls(mock.getStub(), logString, jdbcJdk5StatementMethods());
    }


    @Test
    public void test_preparedStatementProxy() throws Exception {
        PreparedStatementMock mock = new PreparedStatementMock(logString);
        LogCallAssert<PreparedStatement> logCallAssert = new LogCallAssert<PreparedStatement>(PreparedStatement.class);
        logCallAssert.assertCalls(mock.getStub(), logString, jdbcJdk5PreparedStatementMethods());
    }


    private String[] jdbcJdk5PreparedStatementMethods() {
        return new String[]{
              "public abstract void java.sql.PreparedStatement.setBoolean(int,boolean) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setByte(int,byte) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setDouble(int,double) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setFloat(int,float) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setInt(int,int) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setLong(int,long) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setShort(int,short) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setTimestamp(int,java.sql.Timestamp,java.util.Calendar) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setTimestamp(int,java.sql.Timestamp) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setURL(int,java.net.URL) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setTime(int,java.sql.Time,java.util.Calendar) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setTime(int,java.sql.Time) throws java.sql.SQLException",
              "public abstract boolean java.sql.PreparedStatement.execute() throws java.sql.SQLException",
              "public abstract java.sql.ResultSet java.sql.PreparedStatement.executeQuery() throws java.sql.SQLException",
              "public abstract int java.sql.PreparedStatement.executeUpdate() throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setNull(int,int) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setNull(int,int,java.lang.String) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setBigDecimal(int,java.math.BigDecimal) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setString(int,java.lang.String) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setBytes(int,byte[]) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setDate(int,java.sql.Date) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setDate(int,java.sql.Date,java.util.Calendar) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setAsciiStream(int,java.io.InputStream,int) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setUnicodeStream(int,java.io.InputStream,int) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setBinaryStream(int,java.io.InputStream,int) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.clearParameters() throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setObject(int,java.lang.Object,int,int) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setObject(int,java.lang.Object,int) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setObject(int,java.lang.Object) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.addBatch() throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setCharacterStream(int,java.io.Reader,int) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setRef(int,java.sql.Ref) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setBlob(int,java.sql.Blob) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setClob(int,java.sql.Clob) throws java.sql.SQLException",
              "public abstract void java.sql.PreparedStatement.setArray(int,java.sql.Array) throws java.sql.SQLException",
              "public abstract java.sql.ResultSetMetaData java.sql.PreparedStatement.getMetaData() throws java.sql.SQLException",
              "public abstract java.sql.ParameterMetaData java.sql.PreparedStatement.getParameterMetaData() throws java.sql.SQLException"
        };
    }


    private String[] castorDatabaseMethods() {
        return new String[]{
              "public abstract void org.exolab.castor.jdo.Database.lock(java.lang.Object) throws org.exolab.castor.jdo.LockNotGrantedException,org.exolab.castor.jdo.ObjectNotPersistentException,org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.PersistenceException",
              "public abstract java.lang.ClassLoader org.exolab.castor.jdo.Database.getClassLoader()",
              "public abstract java.lang.Object org.exolab.castor.jdo.Database.load(java.lang.Class,org.exolab.castor.persist.spi.Complex,short) throws org.exolab.castor.jdo.ObjectNotFoundException,org.exolab.castor.jdo.LockNotGrantedException,org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.PersistenceException",
              "public abstract java.lang.Object org.exolab.castor.jdo.Database.load(java.lang.Class,java.lang.Object,java.lang.Object) throws org.exolab.castor.jdo.ObjectNotFoundException,org.exolab.castor.jdo.LockNotGrantedException,org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.PersistenceException",
              "public abstract java.lang.Object org.exolab.castor.jdo.Database.load(java.lang.Class,java.lang.Object) throws org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.ObjectNotFoundException,org.exolab.castor.jdo.LockNotGrantedException,org.exolab.castor.jdo.PersistenceException",
              "public abstract java.lang.Object org.exolab.castor.jdo.Database.load(java.lang.Class,org.exolab.castor.persist.spi.Complex) throws org.exolab.castor.jdo.ObjectNotFoundException,org.exolab.castor.jdo.LockNotGrantedException,org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.PersistenceException",
              "public abstract java.lang.Object org.exolab.castor.jdo.Database.load(java.lang.Class,java.lang.Object,short) throws org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.ObjectNotFoundException,org.exolab.castor.jdo.LockNotGrantedException,org.exolab.castor.jdo.PersistenceException",
              "public abstract void org.exolab.castor.jdo.Database.remove(java.lang.Object) throws org.exolab.castor.jdo.ObjectNotPersistentException,org.exolab.castor.jdo.LockNotGrantedException,org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.PersistenceException",
              "public abstract void org.exolab.castor.jdo.Database.close() throws org.exolab.castor.jdo.PersistenceException",
              "public abstract void org.exolab.castor.jdo.Database.flush() throws org.exolab.castor.jdo.TransactionAbortedException",
              "public abstract org.exolab.castor.jdo.Query org.exolab.castor.jdo.Database.getQuery()",
              "public abstract void org.exolab.castor.jdo.Database.create(java.lang.Object) throws org.exolab.castor.jdo.ClassNotPersistenceCapableException,org.exolab.castor.jdo.DuplicateIdentityException,org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.PersistenceException",
              "public abstract void org.exolab.castor.jdo.Database.update(java.lang.Object) throws org.exolab.castor.jdo.ClassNotPersistenceCapableException,org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.PersistenceException",
              "public abstract boolean org.exolab.castor.jdo.Database.isClosed()",
              "public abstract org.exolab.castor.persist.PersistenceInfoGroup org.exolab.castor.jdo.Database.getScope()",
              "public abstract org.exolab.castor.jdo.OQLQuery org.exolab.castor.jdo.Database.getOQLQuery(java.lang.String) throws org.exolab.castor.jdo.QueryException",
              "public abstract org.exolab.castor.jdo.OQLQuery org.exolab.castor.jdo.Database.getOQLQuery()",
              "public abstract void org.exolab.castor.jdo.Database.begin() throws org.exolab.castor.jdo.PersistenceException",
              "public abstract boolean org.exolab.castor.jdo.Database.isAutoStore()",
              "public abstract void org.exolab.castor.jdo.Database.setAutoStore(boolean)",
              "public abstract void org.exolab.castor.jdo.Database.commit() throws org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.TransactionAbortedException",
              "public abstract void org.exolab.castor.jdo.Database.rollback() throws org.exolab.castor.jdo.TransactionNotInProgressException",
              "public abstract boolean org.exolab.castor.jdo.Database.isActive()",
              "public abstract boolean org.exolab.castor.jdo.Database.isPersistent(java.lang.Object)",
              "public abstract java.lang.Object org.exolab.castor.jdo.Database.getIdentity(java.lang.Object)",
              "public abstract void org.exolab.castor.jdo.Database.makePersistent(java.lang.Object) throws org.exolab.castor.jdo.ClassNotPersistenceCapableException,org.exolab.castor.jdo.DuplicateIdentityException,org.exolab.castor.jdo.PersistenceException",
              "public abstract void org.exolab.castor.jdo.Database.deletePersistent(java.lang.Object) throws org.exolab.castor.jdo.ObjectNotPersistentException,org.exolab.castor.jdo.LockNotGrantedException,org.exolab.castor.jdo.PersistenceException",
              "public abstract void org.exolab.castor.jdo.Database.checkpoint() throws org.exolab.castor.jdo.TransactionNotInProgressException,org.exolab.castor.jdo.TransactionAbortedException"
        };
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


    private String[] jdbcJdk5StatementMethods() {
        return new String[]{
              "public abstract void java.sql.Statement.close() throws java.sql.SQLException",
              "public abstract boolean java.sql.Statement.execute(java.lang.String,java.lang.String[]) throws java.sql.SQLException",
              "public abstract boolean java.sql.Statement.execute(java.lang.String,int) throws java.sql.SQLException",
              "public abstract boolean java.sql.Statement.execute(java.lang.String) throws java.sql.SQLException",
              "public abstract boolean java.sql.Statement.execute(java.lang.String,int[]) throws java.sql.SQLException",
              "public abstract java.sql.Connection java.sql.Statement.getConnection() throws java.sql.SQLException",
              "public abstract int java.sql.Statement.getFetchDirection() throws java.sql.SQLException",
              "public abstract int java.sql.Statement.getFetchSize() throws java.sql.SQLException",
              "public abstract int java.sql.Statement.getMaxFieldSize() throws java.sql.SQLException",
              "public abstract int java.sql.Statement.getMaxRows() throws java.sql.SQLException",
              "public abstract int java.sql.Statement.getQueryTimeout() throws java.sql.SQLException",
              "public abstract int java.sql.Statement.getResultSetConcurrency() throws java.sql.SQLException",
              "public abstract int java.sql.Statement.getResultSetHoldability() throws java.sql.SQLException",
              "public abstract int java.sql.Statement.getResultSetType() throws java.sql.SQLException",
              "public abstract int java.sql.Statement.getUpdateCount() throws java.sql.SQLException",
              "public abstract void java.sql.Statement.cancel() throws java.sql.SQLException",
              "public abstract void java.sql.Statement.clearBatch() throws java.sql.SQLException",
              "public abstract void java.sql.Statement.clearWarnings() throws java.sql.SQLException",
              "public abstract boolean java.sql.Statement.getMoreResults(int) throws java.sql.SQLException",
              "public abstract boolean java.sql.Statement.getMoreResults() throws java.sql.SQLException",
              "public abstract int[] java.sql.Statement.executeBatch() throws java.sql.SQLException",
              "public abstract void java.sql.Statement.setFetchDirection(int) throws java.sql.SQLException",
              "public abstract void java.sql.Statement.setFetchSize(int) throws java.sql.SQLException",
              "public abstract void java.sql.Statement.setMaxFieldSize(int) throws java.sql.SQLException",
              "public abstract void java.sql.Statement.setMaxRows(int) throws java.sql.SQLException",
              "public abstract void java.sql.Statement.setQueryTimeout(int) throws java.sql.SQLException",
              "public abstract void java.sql.Statement.setEscapeProcessing(boolean) throws java.sql.SQLException",
              "public abstract int java.sql.Statement.executeUpdate(java.lang.String) throws java.sql.SQLException",
              "public abstract int java.sql.Statement.executeUpdate(java.lang.String,java.lang.String[]) throws java.sql.SQLException",
              "public abstract int java.sql.Statement.executeUpdate(java.lang.String,int) throws java.sql.SQLException",
              "public abstract int java.sql.Statement.executeUpdate(java.lang.String,int[]) throws java.sql.SQLException",
              "public abstract void java.sql.Statement.addBatch(java.lang.String) throws java.sql.SQLException",
              "public abstract void java.sql.Statement.setCursorName(java.lang.String) throws java.sql.SQLException",
              "public abstract java.sql.ResultSet java.sql.Statement.getGeneratedKeys() throws java.sql.SQLException",
              "public abstract java.sql.ResultSet java.sql.Statement.getResultSet() throws java.sql.SQLException",
              "public abstract java.sql.SQLWarning java.sql.Statement.getWarnings() throws java.sql.SQLException",
              "public abstract java.sql.ResultSet java.sql.Statement.executeQuery(java.lang.String) throws java.sql.SQLException"
        };
    }


    @Before
    public void setUp() throws Exception {
        logString = new LogString();
    }
}
