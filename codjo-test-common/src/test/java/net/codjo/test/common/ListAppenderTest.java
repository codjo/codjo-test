package net.codjo.test.common;
import java.sql.SQLException;
import net.codjo.test.common.ListAppender.Count;
import net.codjo.test.common.ListAppender.LogEntryMatcher;
import net.codjo.test.common.matcher.JUnitMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;
import static net.codjo.test.common.ListAppender.Count.count;
import static net.codjo.test.common.ListAppender.Count.none;
import static net.codjo.test.common.ListAppenderTest.FailureType.NONE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 *
 */
@RunWith(Theories.class)
public class ListAppenderTest extends AbstractListAppenderTest {
    @DataPoint
    public static final FailureType WRONG_CLASS = FailureType.WRONG_CLASS;
    @DataPoint
    public static final FailureType WRONG_MESSAGE = FailureType.WRONG_MESSAGE;
    @DataPoint
    public static final FailureType WRONG_CLASS_AND_MESSAGE = FailureType.WRONG_CLASS_AND_MESSAGE;


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


    @Test
    public void testHasLogEntry_messagePresent_expectPresent() throws Exception {
        testHasLogEntry(true, count(1));
    }


    @Test
    public void testHasLogEntry_messagePresent_expectAbsent() throws Exception {
        testHasLogEntry(true, none());
    }


    @Test
    public void testHasLogEntry_messageAbsent_expectPresent() throws Exception {
        testHasLogEntry(false, count(1));
    }


    @Test
    public void testHasLogEntry_messageAbsent_expectAbsent() throws Exception {
        testHasLogEntry(false, none());
    }


    @Test
    public void testHasLogEntry_exceptionPresentAtTopLevel_expectPresent() throws Exception {
        testHasLogEntry_exception(0, NONE, count(1));
    }


    @Test
    public void testHasLogEntry_exceptionPresentAtIntermediateLevel_expectPresent() throws Exception {
        testHasLogEntry_exception(1, NONE, count(1));
    }


    @Test
    public void testHasLogEntry_exceptionPresentAtLowestLevel_expectPresent() throws Exception {
        testHasLogEntry_exception(2, NONE, count(1));
    }


    @Theory
    public void testHasLogEntry_exceptionPresentAtTopLevel_expectAbsent(FailureType failureType) throws Exception {
        testHasLogEntry_exception(0, failureType, none());
    }


    @Theory
    public void testHasLogEntry_exceptionPresentAtIntermediateLevel_expectAbsent(FailureType failureType)
          throws Exception {
        testHasLogEntry_exception(1, failureType, none());
    }


    @Theory
    public void testHasLogEntry_exceptionPresentAtLowestLevel_expectAbsent(FailureType failureType) throws Exception {
        testHasLogEntry_exception(2, failureType, none());
    }


    public static enum FailureType {
        NONE(false, false),
        WRONG_CLASS(true, false),
        WRONG_MESSAGE(false, true),
        WRONG_CLASS_AND_MESSAGE(true, true);

        private final boolean wrongMessage;
        private final boolean wrongClass;


        FailureType(boolean wrongClass, boolean wrongMessage) {
            this.wrongClass = wrongClass;
            this.wrongMessage = wrongMessage;
        }
    }


    private void testHasLogEntry_exception(int testedLevel, FailureType failureType, final Count count)
          throws Exception {
        Throwable[] exceptions = buildExceptionChain(testedLevel, failureType);
        Throwable testedException = exceptions[testedLevel];
        Class<? extends Throwable> exceptionClass = testedException.getClass();
        String exceptionMessage = testedException.getMessage();
        testHasLogEntry(exceptions[0], exceptionClass, exceptionMessage, failureType != NONE, count);
    }


    private Throwable[] buildExceptionChain(int testedLevel, FailureType failureType) {
        Throwable[] exceptions = new Throwable[3];

        exceptions[2] = createThrowable(testedLevel, 2, failureType);

        exceptions[1] = createThrowable(testedLevel, 1, failureType);
        exceptions[1].initCause(exceptions[2]);

        exceptions[0] = createThrowable(testedLevel, 0, failureType);
        exceptions[0].initCause(exceptions[1]);

        return exceptions;
    }


    private Throwable createThrowable(int testedLevel, int currentLevel, FailureType failureType) {
        String message = "error" + currentLevel;
        if (testedLevel == currentLevel) {
            message += (failureType.wrongMessage ? "ZZZ" : "");
            if (failureType.wrongClass) {
                return new IllegalStateException(message);
            }
            else {
                return new SQLException(message);
            }
        }
        else {
            return new IllegalArgumentException(message);
        }
    }


    private void testHasLogEntry(final Throwable exception, final Class<? extends Throwable> exceptionClass,
                                 final String exceptionMessage, final boolean expectFailure, final Count count)
          throws Exception {
        new TestTemplate(exception) {
            @Override
            protected void runTest(ListAppender appender, String[] lines) throws Exception {
                AssertionError error = null;
                try {
                    LogEntryMatcher logEntryMatcher = (exceptionMessage != null) ?
                                                      LogEntryMatcher.exception(exceptionClass, exceptionMessage) :
                                                      LogEntryMatcher.exception(exceptionClass);
                    appender.assertHasLog(logEntryMatcher, count);
                }
                catch (AssertionError afe) {
                    error = afe;
                }

                if (expectFailure) {
                    if (error == null) {
                        fail("assertHasLog must throw an AssertionError");
                    }
                }
                else {
                    if (error != null) {
                        error.printStackTrace();
                        fail("assertHasLog mustn't throw an AssertionError : " + error.getMessage());
                    }
                }
            }
        }.run();
    }


    private void testHasLogEntry(final boolean useExistingMessage, final Count count) throws Exception {
        new TestTemplate() {
            @Override
            protected void runTest(ListAppender appender, String[] lines) throws Exception {
                AssertionError error = null;
                try {
                    String expectedLog = getExpectedLog(lines, useExistingMessage);

                    LogEntryMatcher logEntryMatcher = new LogEntryMatcher(JUnitMatchers.equalTo(expectedLog));
                    appender.assertHasLog(logEntryMatcher, count);
                }
                catch (AssertionError afe) {
                    error = afe;
                }

                boolean expectMessage = (count.getExpectedCount() > 0);
                if (useExistingMessage != expectMessage) {
                    if (error == null) {
                        fail("assertHasLog must throw an AssertionError");
                    }
                }
                else {
                    if (error != null) {
                        fail("assertHasLog mustn't throw an AssertionError");
                    }
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
        private final Throwable exception;


        public TestTemplate() {
            this(null);
        }


        public TestTemplate(Throwable exception) {
            this.exception = exception;
        }


        public void run() throws Exception {
            ListAppender appender = ListAppender.createAndAddToRootLogger();

            try {
                String[] lines = (exception == null) ? logLines() : logLines(exception);
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
