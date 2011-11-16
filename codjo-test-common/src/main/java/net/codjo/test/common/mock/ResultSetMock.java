/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.mock;
import net.codjo.test.common.LogString;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
/**
 *
 */
@SuppressWarnings({"OverlyComplexClass"})
public class ResultSetMock implements ResultSet {
    private LogString log;


    public ResultSetMock() {
        this(new LogString());
    }


    public ResultSetMock(LogString log) {
        this.log = log;
    }


    public boolean next() throws SQLException {
        log.call("next");
        return false;
    }


    public void close() throws SQLException {
        log.call("close");
    }


    public boolean wasNull() throws SQLException {
        log.call("wasNull");
        return false;
    }


    public String getString(int columnIndex) throws SQLException {
        log.call("getString", columnIndex);
        return null;
    }


    public boolean getBoolean(int columnIndex) throws SQLException {
        log.call("getBoolean", columnIndex);
        return false;
    }


    public byte getByte(int columnIndex) throws SQLException {
        log.call("getByte", columnIndex);
        return 0;
    }


    public short getShort(int columnIndex) throws SQLException {
        log.call("getShort", columnIndex);
        return 0;
    }


    public int getInt(int columnIndex) throws SQLException {
        log.call("getInt", columnIndex);
        return 0;
    }


    public long getLong(int columnIndex) throws SQLException {
        log.call("getLong", columnIndex);
        return 0;
    }


    public float getFloat(int columnIndex) throws SQLException {
        log.call("getFloat", columnIndex);
        return 0;
    }


    public double getDouble(int columnIndex) throws SQLException {
        log.call("getDouble", columnIndex);
        return 0;
    }


    @Deprecated
    public BigDecimal getBigDecimal(int columnIndex, int scale)
          throws SQLException {
        log.call("getBigDecimal", columnIndex, scale);
        return null;
    }


    public byte[] getBytes(int columnIndex) throws SQLException {
        log.call("getBytes", columnIndex);
        return new byte[0];
    }


    public Date getDate(int columnIndex) throws SQLException {
        log.call("getDate", columnIndex);
        return null;
    }


    public Time getTime(int columnIndex) throws SQLException {
        log.call("getTime", columnIndex);
        return null;
    }


    public Timestamp getTimestamp(int columnIndex)
          throws SQLException {
        log.call("getTimestamp", columnIndex);
        return null;
    }


    public InputStream getAsciiStream(int columnIndex)
          throws SQLException {
        log.call("getAsciiStream", columnIndex);
        return null;
    }


    @Deprecated
    public InputStream getUnicodeStream(int columnIndex)
          throws SQLException {

        log.call("getUnicodeStream", columnIndex);
        return null;
    }


    public InputStream getBinaryStream(int columnIndex)
          throws SQLException {
        log.call("getBinaryStream", columnIndex);
        return null;
    }


    public String getString(String columnName) throws SQLException {
        log.call("getString", columnName);
        return null;
    }


    public boolean getBoolean(String columnName) throws SQLException {
        log.call("getBoolean", columnName);
        return false;
    }


    public byte getByte(String columnName) throws SQLException {
        log.call("getByte", columnName);
        return 0;
    }


    public short getShort(String columnName) throws SQLException {
        log.call("getShort", columnName);
        return 0;
    }


    public int getInt(String columnName) throws SQLException {
        log.call("getInt", columnName);
        return 0;
    }


    public long getLong(String columnName) throws SQLException {
        log.call("getLong", columnName);
        return 0;
    }


    public float getFloat(String columnName) throws SQLException {
        log.call("getFloat", columnName);
        return 0;
    }


    public double getDouble(String columnName) throws SQLException {
        log.call("getDouble", columnName);
        return 0;
    }


    @Deprecated
    public BigDecimal getBigDecimal(String columnName, int scale)
          throws SQLException {
        log.call("getBigDecimal", columnName, scale);
        return null;
    }


    public byte[] getBytes(String columnName) throws SQLException {
        log.call("getBytes", columnName);
        return new byte[0];
    }


    public Date getDate(String columnName) throws SQLException {
        log.call("getDate", columnName);
        return null;
    }


    public Time getTime(String columnName) throws SQLException {
        log.call("getTime", columnName);
        return null;
    }


    public Timestamp getTimestamp(String columnName)
          throws SQLException {
        log.call("getTimestamp", columnName);
        return null;
    }


    public InputStream getAsciiStream(String columnName)
          throws SQLException {
        log.call("getAsciiStream", columnName);
        return null;
    }


    @Deprecated
    public InputStream getUnicodeStream(String columnName)
          throws SQLException {
        log.call("getUnicodeStream", columnName);
        return null;
    }


    public InputStream getBinaryStream(String columnName)
          throws SQLException {
        log.call("getBinaryStream", columnName);
        return null;
    }


    public SQLWarning getWarnings() throws SQLException {
        log.call("getWarnings");
        return null;
    }


    public void clearWarnings() throws SQLException {
        log.call("clearWarnings");
    }


    public String getCursorName() throws SQLException {
        log.call("getCursorName");
        return null;
    }


    public ResultSetMetaData getMetaData() throws SQLException {
        log.call("getMetaData");
        return null;
    }


    public Object getObject(int columnIndex) throws SQLException {
        log.call("getObject", columnIndex);
        return null;
    }


    public Object getObject(String columnName) throws SQLException {
        log.call("getObject", columnName);
        return null;
    }


    public int findColumn(String columnName) throws SQLException {
        log.call("findColumn", columnName);
        return 0;
    }


    public Reader getCharacterStream(int columnIndex)
          throws SQLException {
        log.call("getCharacterStream", columnIndex);
        return null;
    }


    public Reader getCharacterStream(String columnName)
          throws SQLException {
        log.call("getCharacterStream", columnName);
        return null;
    }


    public BigDecimal getBigDecimal(int columnIndex)
          throws SQLException {
        log.call("getBigDecimal", columnIndex);
        return null;
    }


    public BigDecimal getBigDecimal(String columnName)
          throws SQLException {
        log.call("getBigDecimal", columnName);
        return null;
    }


    public boolean isBeforeFirst() throws SQLException {
        log.call("isBeforeFirst");
        return false;
    }


    public boolean isAfterLast() throws SQLException {
        log.call("isAfterLast");
        return false;
    }


    public boolean isFirst() throws SQLException {
        log.call("isFirst");
        return false;
    }


    public boolean isLast() throws SQLException {
        log.call("isLast");
        return false;
    }


    public void beforeFirst() throws SQLException {
        log.call("beforeFirst");
    }


    public void afterLast() throws SQLException {
        log.call("afterLast");
    }


    public boolean first() throws SQLException {
        log.call("first");
        return false;
    }


    public boolean last() throws SQLException {
        log.call("last");
        return false;
    }


    public int getRow() throws SQLException {
        log.call("getRow");
        return 0;
    }


    public boolean absolute(int row) throws SQLException {
        log.call("absolute", row);
        return false;
    }


    public boolean relative(int rows) throws SQLException {
        log.call("relative", rows);
        return false;
    }


    public boolean previous() throws SQLException {
        log.call("previous");
        return false;
    }


    public void setFetchDirection(int direction) throws SQLException {
        log.call("setFetchDirection", direction);
    }


    public int getFetchDirection() throws SQLException {
        log.call("getFetchDirection");
        return 0;
    }


    public void setFetchSize(int rows) throws SQLException {
        log.call("setFetchSize", rows);
    }


    public int getFetchSize() throws SQLException {
        log.call("getFetchSize");
        return 0;
    }


    public int getType() throws SQLException {
        log.call("getType");
        return 0;
    }


    public int getConcurrency() throws SQLException {
        log.call("getConcurrency");
        return 0;
    }


    public boolean rowUpdated() throws SQLException {
        log.call("rowUpdated");
        return false;
    }


    public boolean rowInserted() throws SQLException {
        log.call("rowInserted");
        return false;
    }


    public boolean rowDeleted() throws SQLException {
        log.call("rowDeleted");
        return false;
    }


    public void updateNull(int columnIndex) throws SQLException {
        log.call("updateNull");
    }


    public void updateBoolean(int columnIndex, boolean value)
          throws SQLException {
        log.call("updateBoolean", columnIndex, value);
    }


    public void updateByte(int columnIndex, byte value)
          throws SQLException {
        log.call("updateByte", columnIndex, value);
    }


    public void updateShort(int columnIndex, short value)
          throws SQLException {
        log.call("updateShort", columnIndex, value);
    }


    public void updateInt(int columnIndex, int value)
          throws SQLException {
        log.call("updateInt", columnIndex, value);
    }


    public void updateLong(int columnIndex, long value)
          throws SQLException {
        log.call("updateLong", columnIndex, value);
    }


    public void updateFloat(int columnIndex, float value)
          throws SQLException {
        log.call("updateFloat", columnIndex, value);
    }


    public void updateDouble(int columnIndex, double value)
          throws SQLException {
        log.call("updateDouble", columnIndex, value);
    }


    public void updateBigDecimal(int columnIndex, BigDecimal value)
          throws SQLException {
        log.call("updateBigDecimal", columnIndex, value);
    }


    public void updateString(int columnIndex, String value)
          throws SQLException {
        log.call("updateString", columnIndex, value);
    }


    public void updateBytes(int columnIndex, byte[] value)
          throws SQLException {
        log.call("updateBytes", columnIndex, value);
    }


    public void updateDate(int columnIndex, Date value)
          throws SQLException {
        log.call("updateDate", columnIndex, value);
    }


    public void updateTime(int columnIndex, Time value)
          throws SQLException {
        log.call("updateTime", columnIndex, value);
    }


    public void updateTimestamp(int columnIndex, Timestamp value)
          throws SQLException {
        log.call("updateTimestamp", columnIndex, value);
    }


    public void updateAsciiStream(int columnIndex, InputStream value, int length)
          throws SQLException {
        log.call("updateAsciiStream", columnIndex, value, length);
    }


    public void updateBinaryStream(int columnIndex, InputStream value, int length)
          throws SQLException {
        log.call("updateBinaryStream", columnIndex, value, length);
    }


    public void updateCharacterStream(int columnIndex, Reader value, int length)
          throws SQLException {
        log.call("updateCharacterStream", columnIndex, value, length);
    }


    public void updateObject(int columnIndex, Object value, int scale)
          throws SQLException {
        log.call("updateObject", columnIndex, value, scale);
    }


    public void updateObject(int columnIndex, Object value)
          throws SQLException {
        log.call("updateObject", columnIndex, value);
    }


    public void updateNull(String columnName) throws SQLException {
        log.call("updateNull", columnName);
    }


    public void updateBoolean(String columnName, boolean value)
          throws SQLException {
        log.call("updateBoolean", columnName, value);
    }


    public void updateByte(String columnName, byte value)
          throws SQLException {
        log.call("updateByte", columnName, value);
    }


    public void updateShort(String columnName, short value)
          throws SQLException {
        log.call("updateShort", columnName, value);
    }


    public void updateInt(String columnName, int value)
          throws SQLException {
        log.call("updateInt", columnName, value);
    }


    public void updateLong(String columnName, long value)
          throws SQLException {
        log.call("updateLong", columnName, value);
    }


    public void updateFloat(String columnName, float value)
          throws SQLException {
        log.call("updateFloat", columnName, value);
    }


    public void updateDouble(String columnName, double value)
          throws SQLException {
        log.call("updateDouble", columnName, value);
    }


    public void updateBigDecimal(String columnName, BigDecimal value)
          throws SQLException {
        log.call("updateBigDecimal", columnName, value);
    }


    public void updateString(String columnName, String value)
          throws SQLException {
        log.call("updateString", columnName, value);
    }


    public void updateBytes(String columnName, byte[] value)
          throws SQLException {
        log.call("updateBytes", columnName, value);
    }


    public void updateDate(String columnName, Date value)
          throws SQLException {
        log.call("updateDate", columnName, value);
    }


    public void updateTime(String columnName, Time value)
          throws SQLException {
        log.call("updateTime", columnName, value);
    }


    public void updateTimestamp(String columnName, Timestamp value)
          throws SQLException {
        log.call("updateTimestamp", columnName, value);
    }


    public void updateAsciiStream(String columnName, InputStream value, int length)
          throws SQLException {
        log.call("updateAsciiStream", columnName, value, length);
    }


    public void updateBinaryStream(String columnName, InputStream value, int length)
          throws SQLException {
        log.call("updateBinaryStream", columnName, value, length);
    }


    public void updateCharacterStream(String columnName, Reader reader, int length)
          throws SQLException {
        log.call("updateCharacterStream", columnName, reader, length);
    }


    public void updateObject(String columnName, Object value, int scale)
          throws SQLException {
        log.call("updateObject", columnName, value, scale);
    }


    public void updateObject(String columnName, Object value)
          throws SQLException {
        log.call("updateObject", columnName, value);
    }


    public void insertRow() throws SQLException {
        log.call("insertRow");
    }


    public void updateRow() throws SQLException {
        log.call("updateRow");
    }


    public void deleteRow() throws SQLException {
        log.call("deleteRow");
    }


    public void refreshRow() throws SQLException {
        log.call("refreshRow");
    }


    public void cancelRowUpdates() throws SQLException {
        log.call("cancelRowUpdates");
    }


    public void moveToInsertRow() throws SQLException {
        log.call("moveToInsertRow");
    }


    public void moveToCurrentRow() throws SQLException {
        log.call("moveToCurrentRow");
    }


    public Statement getStatement() throws SQLException {
        log.call("getStatement");
        return null;
    }


    public Object getObject(int index, Map<String, Class<?>> map)
          throws SQLException {
        log.call("getObject", index, map);
        return null;
    }


    public Ref getRef(int index) throws SQLException {
        log.call("getRef", index);
        return null;
    }


    public Blob getBlob(int index) throws SQLException {
        log.call("getBlob", index);
        return null;
    }


    public Clob getClob(int index) throws SQLException {
        log.call("getClob", index);
        return null;
    }


    public Array getArray(int index) throws SQLException {
        log.call("getArray", index);
        return null;
    }


    public Object getObject(String colName, Map<String, Class<?>> map)
          throws SQLException {
        log.call("getObject", colName, map);
        return null;
    }


    public Ref getRef(String colName) throws SQLException {
        log.call("getRef", colName);
        return null;
    }


    public Blob getBlob(String colName) throws SQLException {
        log.call("getBlob", colName);
        return null;
    }


    public Clob getClob(String colName) throws SQLException {
        log.call("getClob", colName);
        return null;
    }


    public Array getArray(String colName) throws SQLException {
        log.call("getArray", colName);
        return null;
    }


    public Date getDate(int columnIndex, Calendar cal)
          throws SQLException {
        log.call("getDate", columnIndex, cal);
        return null;
    }


    public Date getDate(String columnName, Calendar cal)
          throws SQLException {
        log.call("getDate", columnName, cal);
        return null;
    }


    public Time getTime(int columnIndex, Calendar cal)
          throws SQLException {
        log.call("getTime", columnIndex, cal);
        return null;
    }


    public Time getTime(String columnName, Calendar cal)
          throws SQLException {
        log.call("getTime", columnName, cal);
        return null;
    }


    public Timestamp getTimestamp(int columnIndex, Calendar cal)
          throws SQLException {
        log.call("getTimestamp", columnIndex, cal);
        return null;
    }


    public Timestamp getTimestamp(String columnName, Calendar cal)
          throws SQLException {
        log.call("getTimestamp", columnName, cal);
        return null;
    }


    public URL getURL(int columnIndex) throws SQLException {
        log.call("getURL", columnIndex);
        return null;
    }


    public URL getURL(String columnName) throws SQLException {
        log.call("getURL", columnName);
        return null;
    }


    public void updateRef(int columnIndex, Ref value)
          throws SQLException {
        log.call("updateRef", columnIndex, value);
    }


    public void updateRef(String columnName, Ref value)
          throws SQLException {
        log.call("updateRef", columnName, value);
    }


    public void updateBlob(int columnIndex, Blob value)
          throws SQLException {
        log.call("updateBlob", columnIndex, value);
    }


    public void updateBlob(String columnName, Blob value)
          throws SQLException {
        log.call("updateBlob", columnName, value);
    }


    public void updateClob(int columnIndex, Clob value)
          throws SQLException {
        log.call("updateClob", columnIndex, value);
    }


    public void updateClob(String columnName, Clob value)
          throws SQLException {
        log.call("updateClob", columnName, value);
    }


    public void updateArray(int columnIndex, Array value)
          throws SQLException {
        log.call("updateArray", columnIndex, value);
    }


    public void updateArray(String columnName, Array value)
          throws SQLException {
        log.call("updateArray", columnName, value);
    }
}
