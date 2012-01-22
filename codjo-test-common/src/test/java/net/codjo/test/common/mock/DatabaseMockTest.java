/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.mock;
import net.codjo.test.common.LogCallAssert;
import net.codjo.test.common.LogString;
import junit.framework.TestCase;
import org.exolab.castor.jdo.Database;
import org.junit.Before;
import org.junit.Test;
/**
 * Classe de test de {@link DatabaseMock}.
 */
public class DatabaseMockTest {
    private DatabaseMock mock;
    private LogString logString;


    @Test
    public void test_message() throws Exception {
        LogCallAssert logCallAssert = new LogCallAssert(Database.class);

        logCallAssert.assertCalls(mock, logString);
    }


    @Before
    protected void setUp() throws Exception {
        logString = new LogString();
        mock = new DatabaseMock(logString);
    }
}
