/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.mock;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Arrays;
import net.codjo.test.common.LogString;
/**
 * Mock d'un statment jdbc.
 */
@SuppressWarnings({"UnusedDeclaration"})
public class StatementMock {
    private final LogString log;
    protected ResultSet resultSet;


    public StatementMock() {
        this(new LogString());
    }


    public StatementMock(LogString connectionLog) {
        log = connectionLog;
    }


    public Statement getStub() {
        return ProxyDelegatorFactory.getProxy(this, Statement.class);
    }


    public String callList() {
        return log.getContent();
    }


    public StatementMock mockResultSet(ResultSet mock) {
        this.resultSet = mock;
        return this;
    }


    public int getFetchDirection() throws SQLException {
        log.call("getFetchDirection");
        return 0;
    }


    public int getFetchSize() throws SQLException {
        log.call("getFetchSize");
        return 0;
    }


    public int getMaxFieldSize() throws SQLException {
        log.call("getMaxFieldSize");
        return 0;
    }


    public int getMaxRows() throws SQLException {
        log.call("getMaxRows");
        return 0;
    }


    public int getQueryTimeout() throws SQLException {
        log.call("getQueryTimeout");
        return 0;
    }


    public int getResultSetConcurrency() throws SQLException {
        log.call("getResultSetConcurrency");
        return 0;
    }


    public int getResultSetHoldability() throws SQLException {
        log.call("getResultSetHoldability");
        return 0;
    }


    public int getResultSetType() throws SQLException {
        log.call("getResultSetType");
        return 0;
    }


    public int getUpdateCount() throws SQLException {
        log.call("getUpdateCount");
        return 0;
    }


    public void cancel() throws SQLException {
        log.call("cancel");
    }


    public void clearBatch() throws SQLException {
        log.call("clearBatch");
    }


    public void clearWarnings() throws SQLException {
        log.call("clearWarnings");
    }


    public void close() throws SQLException {
        log.call("close");
    }


    public boolean getMoreResults() throws SQLException {
        log.call("getMoreResults");
        return false;
    }


    public int[] executeBatch() throws SQLException {
        log.call("executeBatch");
        return new int[0];
    }


    public void setFetchDirection(int direction) throws SQLException {
        log.call("setFetchDirection", Integer.toString(direction));
    }


    public void setFetchSize(int rows) throws SQLException {
        log.call("setFetchSize", Integer.toString(rows));
    }


    public void setMaxFieldSize(int max) throws SQLException {
        log.call("setMaxFieldSize", Integer.toString(max));
    }


    public void setMaxRows(int max) throws SQLException {
        log.call("setMaxRows", Integer.toString(max));
    }


    public void setQueryTimeout(int seconds) throws SQLException {
        log.call("setQueryTimeout", Integer.toString(seconds));
    }


    public boolean getMoreResults(int current) throws SQLException {
        log.call("getMoreResults", Integer.toString(current));
        return false;
    }


    public void setEscapeProcessing(boolean enable)
          throws SQLException {
        log.call("setEscapeProcessing", enable ? "true" : "false");
    }


    public int executeUpdate(String sql) throws SQLException {
        log.call("executeUpdate", sql);
        return 0;
    }


    public void addBatch(String sql) throws SQLException {
        log.call("addBatch", sql);
    }


    public void setCursorName(String name) throws SQLException {
        log.call("setCursorName", name);
    }


    public boolean execute(String sql) throws SQLException {
        log.call("execute", sql);
        return false;
    }


    public int executeUpdate(String sql, int autoGeneratedKeys)
          throws SQLException {
        log.call("executeUpdate", sql, Integer.toString(autoGeneratedKeys));
        return 0;
    }


    public boolean execute(String sql, int autoGeneratedKeys)
          throws SQLException {
        log.call("execute", sql, Integer.toString(autoGeneratedKeys));
        return false;
    }


    public int executeUpdate(String sql, int[] columnIndexes)
          throws SQLException {
        log.call("executeUpdate", sql, columnIndexes);
        return 0;
    }


    public boolean execute(String sql, int[] columnIndexes)
          throws SQLException {
        log.call("execute", sql, columnIndexes);
        return false;
    }


    public Connection getConnection() throws SQLException {
        log.call("getConnection");
        return null;
    }


    public ResultSet getGeneratedKeys() throws SQLException {
        log.call("getGeneratedKeys");
        return resultSet;
    }


    public ResultSet getResultSet() throws SQLException {
        log.call("getResultSet");
        return resultSet;
    }


    public SQLWarning getWarnings() throws SQLException {
        log.call("getWarnings");
        return null;
    }


    public int executeUpdate(String sql, String[] columnNames)
          throws SQLException {
        log.call("executeUpdate", sql, (columnNames != null) ? Arrays.asList(columnNames) : null);
        return 0;
    }


    public boolean execute(String sql, String[] columnNames)
          throws SQLException {
        log.call("execute", sql, (columnNames != null) ? Arrays.asList(columnNames) : null);
        return false;
    }


    public ResultSet executeQuery(String sql) throws SQLException {
        log.call("executeQuery", sql);
        return resultSet;
    }
}