package net.codjo.test.common.mock;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import net.codjo.test.common.LogString;
@SuppressWarnings({"UnusedDeclaration", "OverlyComplexClass"})
public class CallableStatementMock extends PreparedStatementMock {
    private final CallableStatement stub = ProxyDelegatorFactory.getProxy(this, CallableStatement.class);
    private LogString log;
    private Object object;


    public CallableStatementMock() {
        this.log = new LogString();
    }


    public CallableStatementMock(LogString log) {
        super(new LogString("statement", log));
        this.log = log;
    }


    @Override
    public CallableStatement getStub() {
        return stub;
    }


    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        log.call("registerOutParameter", parameterIndex, sqlType);
    }


    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
        log.call("registerOutParameter", parameterIndex, sqlType, scale);
    }


    public void mockObject(Object anObject) {
        object = anObject;
    }


    public boolean wasNull() throws SQLException {
        log.call("wasNull");
        return null == object;
    }


    public String getString(int parameterIndex) throws SQLException {
        log.call("getString", parameterIndex);
        return (String)object;
    }


    public boolean getBoolean(int parameterIndex) throws SQLException {
        log.call("getBoolean", parameterIndex);
        return (Boolean)object;
    }


    public byte getByte(int parameterIndex) throws SQLException {
        log.call("getByte", parameterIndex);
        return (Byte)object;
    }


    public short getShort(int parameterIndex) throws SQLException {
        log.call("getShort", parameterIndex);
        return (Short)object;
    }


    public int getInt(int parameterIndex) throws SQLException {
        log.call("getInt", parameterIndex);
        return (Integer)object;
    }


    public long getLong(int parameterIndex) throws SQLException {
        log.call("getLong", parameterIndex);
        return (Long)object;
    }


    public float getFloat(int parameterIndex) throws SQLException {
        log.call("getFloat", parameterIndex);
        return (Float)object;
    }


    public double getDouble(int parameterIndex) throws SQLException {
        log.call("getDouble", parameterIndex);
        return (Double)object;
    }


    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        log.call("getBigDecimal", parameterIndex, scale);
        return (BigDecimal)object;
    }


    public byte[] getBytes(int parameterIndex) throws SQLException {
        log.call("getBytes", parameterIndex);
        return (byte[])object;
    }


    public Date getDate(int parameterIndex) throws SQLException {
        log.call("getDate", parameterIndex);
        return (Date)object;
    }


    public Time getTime(int parameterIndex) throws SQLException {
        log.call("getTime", parameterIndex);
        return (Time)object;
    }


    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        log.call("getTimestamp", parameterIndex);
        return (Timestamp)object;
    }


    public Object getObject(int parameterIndex) throws SQLException {
        log.call("getObject", parameterIndex);
        return object;
    }


    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        log.call("getBigDecimal", parameterIndex);
        return (BigDecimal)object;
    }


    public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
        log.call("getObject", i, map);
        return object;
    }


    public Ref getRef(int i) throws SQLException {
        log.call("getRef", i);
        return (Ref)object;
    }


    public Blob getBlob(int i) throws SQLException {
        log.call("getBlob", i);
        return (Blob)object;
    }


    public Clob getClob(int i) throws SQLException {
        log.call("getClob", i);
        return (Clob)object;
    }


    public Array getArray(int i) throws SQLException {
        log.call("getArray", i);
        return (Array)object;
    }


    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        log.call("getDate", parameterIndex, cal);
        return (Date)object;
    }


    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        log.call("getTime", parameterIndex, cal);
        return (Time)object;
    }


    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        log.call("getTimestamp", parameterIndex, cal);
        return (Timestamp)object;
    }


    public void registerOutParameter(int paramIndex, int sqlType, String typeName) throws SQLException {
        log.call("registerOutParameter", paramIndex, sqlType, typeName);
    }


    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        log.call("registerOutParameter", parameterName, sqlType);
    }


    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        log.call("registerOutParameter", parameterName, sqlType, scale);
    }


    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        log.call("registerOutParameter", parameterName, sqlType, typeName);
    }


    public URL getURL(int parameterIndex) throws SQLException {
        log.call("getURL", parameterIndex);
        return (URL)object;
    }


    public void setURL(String parameterName, URL val) throws SQLException {
        log.call("setURL", parameterName, val);
    }


    public void setNull(String parameterName, int sqlType) throws SQLException {
        log.call("setNull", parameterName, sqlType);
    }


    public void setBoolean(String parameterName, boolean x) throws SQLException {
        log.call("setBoolean", parameterName, x);
    }


    public void setByte(String parameterName, byte x) throws SQLException {
        log.call("setByte", parameterName, x);
    }


    public void setShort(String parameterName, short x) throws SQLException {
        log.call("setShort", parameterName, x);
    }


    public void setInt(String parameterName, int x) throws SQLException {
        log.call("setInt", parameterName, x);
    }


    public void setLong(String parameterName, long x) throws SQLException {
        log.call("setLong", parameterName, x);
    }


    public void setFloat(String parameterName, float x) throws SQLException {
        log.call("setFloat", parameterName, x);
    }


    public void setDouble(String parameterName, double x) throws SQLException {
        log.call("setDouble", parameterName, x);
    }


    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        log.call("setBigDecimal", parameterName, x);
    }


    public void setString(String parameterName, String x) throws SQLException {
        log.call("setString", parameterName, x);
    }


    public void setBytes(String parameterName, byte[] x) throws SQLException {
        log.call("setBytes", parameterName, x);
    }


    public void setDate(String parameterName, Date x) throws SQLException {
        log.call("setDate", parameterName, x);
    }


    public void setTime(String parameterName, Time x) throws SQLException {
        log.call("setTime", parameterName, x);
    }


    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        log.call("setTimestamp", parameterName, x);
    }


    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        log.call("setAsciiStream", parameterName, x, length);
    }


    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        log.call("setAsciiStream", parameterName, x, length);
    }


    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        log.call("setBinaryStream", parameterName, x, length);
    }


    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        log.call("setObject", parameterName, x, targetSqlType, scale);
    }


    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        log.call("setObject", parameterName, x, targetSqlType);
    }


    public void setObject(String parameterName, Object x) throws SQLException {
        log.call("setObject", parameterName, x);
    }


    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        log.call("setCharacterStream", parameterName, reader, length);
    }


    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        log.call("setDate", parameterName, x, cal);
    }


    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        log.call("setTime", parameterName, x, cal);
    }


    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        log.call("setTimestamp", parameterName, x, cal);
    }


    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        log.call("setNull", parameterName, sqlType, typeName);
    }


    public String getString(String parameterName) throws SQLException {
        log.call("getString", parameterName);
        return (String)object;
    }


    public boolean getBoolean(String parameterName) throws SQLException {
        log.call("getBoolean", parameterName);
        if (object == null) {
            return false;
        }
        return (Boolean)object;
    }


    public byte getByte(String parameterName) throws SQLException {
        log.call("getByte", parameterName);
        return (Byte)object;
    }


    public short getShort(String parameterName) throws SQLException {
        log.call("getShort", parameterName);
        return (Short)object;
    }


    public int getInt(String parameterName) throws SQLException {
        log.call("getInt", parameterName);
        return (Integer)object;
    }


    public long getLong(String parameterName) throws SQLException {
        log.call("getLong", parameterName);
        return (Long)object;
    }


    public float getFloat(String parameterName) throws SQLException {
        log.call("getFloat", parameterName);
        return (Float)object;
    }


    public double getDouble(String parameterName) throws SQLException {
        log.call("getDouble", parameterName);
        return (Double)object;
    }


    public byte[] getBytes(String parameterName) throws SQLException {
        log.call("getBytes", parameterName);
        return (byte[])object;
    }


    public Date getDate(String parameterName) throws SQLException {
        log.call("getDate", parameterName);
        return (Date)object;
    }


    public Time getTime(String parameterName) throws SQLException {
        log.call("getTime", parameterName);
        return (Time)object;
    }


    public Timestamp getTimestamp(String parameterName) throws SQLException {
        log.call("getTimestamp", parameterName);
        return (Timestamp)object;
    }


    public Object getObject(String parameterName) throws SQLException {
        log.call("getObject", parameterName);
        return object;
    }


    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        log.call("getBigDecimal", parameterName);
        return (BigDecimal)object;
    }


    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        log.call("getObject", parameterName, map);
        return object;
    }


    public Ref getRef(String parameterName) throws SQLException {
        log.call("getRef", parameterName);
        return (Ref)object;
    }


    public Blob getBlob(String parameterName) throws SQLException {
        log.call("getBlob", parameterName);
        return (Blob)object;
    }


    public Clob getClob(String parameterName) throws SQLException {
        log.call("getClob", parameterName);
        return (Clob)object;
    }


    public Array getArray(String parameterName) throws SQLException {
        log.call("getArray", parameterName);
        return (Array)object;
    }


    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        log.call("getDate", parameterName, cal);
        return (Date)object;
    }


    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        log.call("getTime", parameterName, cal);
        return (Time)object;
    }


    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        log.call("getTimestamp", parameterName, cal);
        return (Timestamp)object;
    }


    public URL getURL(String parameterName) throws SQLException {
        log.call("getURL", parameterName);
        return (URL)object;
    }
}