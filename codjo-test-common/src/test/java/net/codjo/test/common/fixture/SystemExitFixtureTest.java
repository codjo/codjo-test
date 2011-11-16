/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.fixture;
import net.codjo.test.common.LogString;
import junit.framework.TestCase;
/**
 * Classe de test de {@link SystemExitFixture}.
 */
public class SystemExitFixtureTest extends TestCase {
    private LogString log = new LogString();
    private SystemExitFixture fixture;

    public void test_blockSystemExit() throws Exception {
        fixture.doSetUp();
        try {
            System.exit(0);
            fail();
        }
        catch (SecurityException ex) {
            assertEquals(SystemExitFixture.BLOCK_MESSAGE, ex.getMessage());
        }
        fixture.doTearDown();
        assertNull(System.getSecurityManager());
        fixture.getLog().assertContent("System.exit(0)");
    }


    public void test_getFirstExitValue() throws Exception {
        fixture.doSetUp();
        try {
            System.exit(0);
            fail();
        }
        catch (SecurityException ex) {
            ;
        }
        try {
            System.exit(-1);
            fail();
        }
        catch (SecurityException ex) {
            ;
        }
        fixture.doTearDown();

        assertEquals(0, fixture.getFirstExitValue());
        fixture.getLog().assertContent("System.exit(0), System.exit(-1)");
    }


    public void test_log() throws Exception {
        fixture.doSetUp(log);
        assertSame(log, fixture.getLog());
    }


    @Override
    protected void setUp() throws Exception {
        fixture = new SystemExitFixture();
    }


    @Override
    protected void tearDown() throws Exception {
        fixture.doTearDown();
    }
}
