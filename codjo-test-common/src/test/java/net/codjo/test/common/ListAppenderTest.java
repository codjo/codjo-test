package net.codjo.test.common;
import net.codjo.test.common.matcher.JUnitMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 *
 */
@RunWith(Theories.class)
public class ListAppenderTest extends AbstractListAppenderTest {
    @Theory
    public void testOnlyLevels(Restriction restriction) throws Exception {
        ListAppender appender = ListAppender.createAndAddToRootLogger();
        try {
            appender.setOnlyLevels(restriction.onlyLevels);
            assertLogged(restriction.logLevel, false, restriction.expectLogs(), appender, true);
        }
        finally {
            appender.removeFromRootLogger();
        }
    }


    @Test
    public void testCreateAndAddToRootLogger() throws Exception {
        assertLogged(false, true);
    }


    @Test
    public void testIsEmpty_init() {
        ListAppender appender = ListAppender.createAndAddToRootLogger();

        try {
            Assert.assertTrue("isEmpty", appender.isEmpty());
        }
        finally {
            appender.removeFromRootLogger();
        }
    }


    @Test
    public void testIsEmpty_afterLogs() {
        ListAppender appender = ListAppender.createAndAddToRootLogger();

        try {
            logLines();

            Assert.assertFalse("isEmpty", appender.isEmpty());
        }
        finally {
            appender.removeFromRootLogger();
        }
    }


    @Test
    public void testMatchesOneLine() throws Exception {
        ListAppender appender = ListAppender.createAndAddToRootLogger();

        try {
            String[] lines = logLines();

            assertTrue(appender.matchesOneLine(getExpectedLog(lines, true)));
            assertFalse(appender.matchesOneLine(getExpectedLog(lines, false)));
        }
        catch (AssertionError afe) {
            appender.printTo(System.out);
            throw afe;
        }
        finally {
            appender.removeFromRootLogger();
        }
    }


    @Test
    public void testHasLog_logPresent() throws Exception {
        new TestTemplate() {
            @Override
            protected void runTest(ListAppender appender, String[] lines) throws Exception {
                try {
                    appender.assertHasLog(JUnitMatchers.equalTo(getExpectedLog(lines, true)));
                    //OK
                }
                catch (AssertionError afe) {
                    fail();
                }
            }
        }.run();
    }


    @Test
    public void testHasLog_logAbsent() throws Exception {
        new TestTemplate() {
            @Override
            protected void runTest(ListAppender appender, String[] lines) throws Exception {
                boolean error = false;
                try {
                    appender.assertHasLog(JUnitMatchers.equalTo(getExpectedLog(lines, false)));
                    error = true;
                    Assert.fail();
                }
                catch (AssertionError afe) {
                    if (error) {
                        throw afe;
                    }
                    // else OK
                }
            }
        }.run();
    }


    @Test
    public void testHasNoLog_logPresent() throws Exception {
        new TestTemplate() {
            @Override
            protected void runTest(ListAppender appender, String[] lines) throws Exception {
                boolean error = false;
                try {
                    appender.assertHasNoLog(JUnitMatchers.equalTo(getExpectedLog(lines, true)));
                    error = true;
                    Assert.fail();
                }
                catch (AssertionError afe) {
                    if (error) {
                        throw afe;
                    }
                    // else OK
                }
            }
        }.run();
    }


    @Test
    public void testHasNoLog_logAbsent() throws Exception {
        new TestTemplate() {
            @Override
            protected void runTest(ListAppender appender, String[] lines) throws Exception {
                try {
                    appender.assertHasNoLog(JUnitMatchers.equalTo(getExpectedLog(lines, false)));
                    // OK
                }
                catch (AssertionError afe) {
                    Assert.fail();
                }
            }
        }.run();
    }


    private String getExpectedLog(String[] lines, boolean existing) {
        return "INFO: " + (existing ? lines[1] : "line666");
    }


    @Test
    public void testRemoveFromRootLogger() throws Exception {
        assertLogged(true, true);
    }


    @Test
    public void testPrintTo() throws Exception {
        ListAppender appender = ListAppender.createAndAddToRootLogger();

        try {
            logLines();
            assertLogged(appender, true, true);
        }
        finally {
            appender.removeFromRootLogger();
        }
    }


    private static abstract class TestTemplate {
        public void run() throws Exception {
            ListAppender appender = ListAppender.createAndAddToRootLogger();

            try {
                String[] lines = logLines();
                runTest(appender, lines);
            }
            catch (AssertionError afe) {
                appender.printTo(System.out);
                throw afe;
            }
            finally {
                appender.removeFromRootLogger();
            }
        }


        abstract protected void runTest(ListAppender appender, String[] lines) throws Exception;
    }
}
