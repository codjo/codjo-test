package net.codjo.test.common.mock;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import net.codjo.test.common.LogString;
/**
 *
 */
@SuppressWarnings({"UnusedDeclaration"})
public class PreparedStatementMock extends StatementMock {
    private final PreparedStatement stub = ProxyDelegatorFactory.getProxy(this, PreparedStatement.class);
    private LogString log;


    public PreparedStatementMock() {
        this.log = new LogString();
    }


    public PreparedStatementMock(LogString log) {
        super(new LogString("statement", log));
        this.log = log;
    }


    @Override
    public PreparedStatement getStub() {
        return stub;
    }


    public ResultSet executeQuery() throws SQLException {
        log.call("executeQuery");
        return resultSet;
    }


    public int executeUpdate() throws SQLException {
        log.call("executeUpdate");
        return 0;
    }


    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        log.call("setNull", parameterIndex, sqlType);
    }


    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        log.call("setBoolean", parameterIndex, x);
    }


    public void setByte(int parameterIndex, byte x) throws SQLException {
        log.call("setByte", parameterIndex, x);
    }


    public void setShort(int parameterIndex, short x) throws SQLException {
        log.call("setShort", parameterIndex, x);
    }


    public void setInt(int parameterIndex, int x) throws SQLException {
        log.call("setInt", parameterIndex, x);
    }


    public void setLong(int parameterIndex, long x) throws SQLException {
        log.call("setLong", parameterIndex, x);
    }


    public void setFloat(int parameterIndex, float x) throws SQLException {
        log.call("setFloat", parameterIndex, x);
    }


    public void setDouble(int parameterIndex, double x) throws SQLException {
        log.call("setDouble", parameterIndex, x);
    }


    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        log.call("setBigDecimal", parameterIndex, x);
    }


    public void setString(int parameterIndex, String x) throws SQLException {
        log.call("setString", parameterIndex, x);
    }


    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        log.call("setBytes", parameterIndex, x);
    }


    public void setDate(int parameterIndex, Date x) throws SQLException {
        log.call("setDate", parameterIndex, x);
    }


    public void setTime(int parameterIndex, Time x) throws SQLException {
        log.call("setTime", parameterIndex, x);
    }


    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        log.call("setTimestamp", parameterIndex, x);
    }


    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        log.call("setAsciiStream", parameterIndex, x, length);
    }


    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        log.call("setUnicodeStream", parameterIndex, x, length);
    }


    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        log.call("setBinaryStream", parameterIndex, x, length);
    }


    public void clearParameters() throws SQLException {
        log.call("clearParameters");
    }


    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
        log.call("setObject", parameterIndex, x, targetSqlType, scale);
    }


    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        log.call("setObject", parameterIndex, x, targetSqlType);
    }


    public void setObject(int parameterIndex, Object x) throws SQLException {
        log.call("setObject", parameterIndex, x);
    }


    public boolean execute() throws SQLException {
        log.call("execute");
        return false;
    }


    public void addBatch() throws SQLException {
        log.call("addBatch");
    }


    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        log.call("setCharacterStream", parameterIndex, reader, length);
    }


    public void setRef(int i, Ref x) throws SQLException {
        log.call("setRef", i, x);
    }


    public void setBlob(int i, Blob x) throws SQLException {
        log.call("setBlob", i, x);
    }


    public void setClob(int i, Clob x) throws SQLException {
        log.call("setClob", i, x);
    }


    public void setArray(int i, Array x) throws SQLException {
        log.call("setArray", i, x);
    }


    public ResultSetMetaData getMetaData() throws SQLException {
        log.call("getMetaData");
        return null;
    }


    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        log.call("setDate", parameterIndex, x, cal);
    }


    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        log.call("setTime", parameterIndex, x, cal);
    }


    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        log.call("setTimestamp", parameterIndex, x, cal);
    }


    public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
        log.call("setNull", paramIndex, sqlType, typeName);
    }


    public void setURL(int parameterIndex, URL x) throws SQLException {
        log.call("setURL", parameterIndex, x);
    }


    public ParameterMetaData getParameterMetaData() throws SQLException {
        log.call("getParameterMetaData");
        return null;
    }
}
