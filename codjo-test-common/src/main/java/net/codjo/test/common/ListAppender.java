package net.codjo.test.common;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static java.lang.System.getProperty;
import static net.codjo.test.common.matcher.JUnitMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.matchers.JUnitMatchers.hasItem;

/**
 * This is a log4j Appender that can be used in tests. The typical usage :<br/> <code> public void testIt() {} final
 * ListAppender appender = ListAppender.createAndAddToRootLogger();
 *
 * try { // run some tests that should log something ... } catch (Exception e) { // in case of error (normally a test
 * failure), dump the logs appender.printTo(System.out); throw e; } finally { appender.removeFromRootLogger(); } }
 * </code>
 */
public class ListAppender extends AppenderSkeleton {
    public static final List<Level> ALL_LEVELS = Arrays.asList(new Level[]{
          Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.TRACE
    });

    private final List<LogEntry> logs = new ArrayList<LogEntry>();
    private List<Level> onlyLevels;


    /**
     * Factory method to create an instance of {@ListAppender} and add it to the log4j's root logger.
     *
     * @return The created appender.
     */
    public static ListAppender createAndAddToRootLogger() {
        ListAppender result = new ListAppender();
        Logger.getRootLogger().addAppender(result);
        return result;
    }


    @Override
    protected void append(LoggingEvent event) {
        if ((onlyLevels == null) || (onlyLevels.indexOf(event.getLevel()) >= 0)) {
            logs.add(new LogEntry(event));
        }
    }


    private String renderMessage(LoggingEvent event) {
        StringBuilder log = new StringBuilder();
        boolean ignoresThrowable;
        if (getLayout() == null) {
            String level = event.getLevel().toString();
            log.append(level).append(": ").append(event.getMessage());
            ignoresThrowable = true;
        }
        else {
            ignoresThrowable = getLayout().ignoresThrowable();
            log.append(getLayout().format(event));
        }

        if (!ignoresThrowable) {
            String lineSeparator = getProperty("line.separator");
            String[] traces = event.getThrowableStrRep();
            if (traces != null) {
                for (String trace : traces) {
                    log.append(lineSeparator).append(trace);
                }
            }
        }
        return log.toString();
    }


    @Override
    public boolean requiresLayout() {
        return true;
    }


    @Override
    public void close() {
    }


    /**
     * <b></strong>This is a workaround since Pattern.compile doesn't seem to work with multiline text.</b> TODO upgrade
     * java 5 ?
     *
     * @return true if one of the lines of this appender matches the given pattern.
     */
    public boolean matchesOneLine(String regex) {
        boolean result = false;
        for (LogEntry logEntry : logs) {
            if (logEntry.getRenderedMessage().matches(regex)) {
                result = true;
                break;
            }
        }
        return result;
    }


    /**
     * Asserts that the log contains one entry for which the {@link Matcher} returns true.
     */
    public void assertHasLog(Matcher<String> messageMatcher) {
        assertHasLog(messageMatcher, null);
    }


    /**
     * Asserts that the log contains one entry for which the {@link Matcher} returns true. Each log entry is transformed
     * with the given <code>transformer</code> before sending it to the matcher.
     *
     * @param transformer The transformer to use for each log entry.
     */
    public void assertHasLog(Matcher<String> messageMatcher, LogTransformer transformer) {
        assertThat(transformedLogs(transformer), hasItem(messageMatcher));
    }


    /**
     * Asserts that the log doesn't contain one entry for which the {@link Matcher} returns true.
     */
    public void assertHasNoLog(Matcher<String> messageMatcher) {
        assertHasNoLog(messageMatcher, null);
    }


    /**
     * Asserts that the log doesn't contain one entry for which the {@link Matcher} returns true. Each log entry is
     * transformed with the given <code>transformer</code> before sending it to the matcher.
     *
     * @param transformer The transformer to use for each log entry.
     */
    public void assertHasNoLog(Matcher<String> messageMatcher, LogTransformer transformer) {
        assertThat(transformedLogs(transformer), hasNoItem(messageMatcher));
    }


    /**
     * Asserts that the log contains one entry for which the {@link Matcher} returns true.
     */
    public void assertHasLog(LogEntryMatcher matcher, Count expectedCount) {
        int actualCount = 0;
        for (LogEntry logEntry : logs) {
            if (matcher.matches(logEntry)) {
                if (expectedCount.getExpectedCount() == 0) {
                    fail("At least one log was found for matcher " + matcher.toString());
                }
                else {
                    actualCount++;
                }
            }
        }

        assertEquals("Wrong number of log that " + matcher.toString(), expectedCount.getExpectedCount(), actualCount);
    }


    private List<String> transformedLogs(LogTransformer transformer) {
        List<String> result = new ArrayList<String>(logs.size());
        for (LogEntry logEntry : logs) {
            String message = logEntry.getRenderedMessage();
            String transformed = (transformer == null) ? message : transformer.transform(message);
            if (transformed != null) {
                result.add(transformed);
            }
        }
        return result;
    }


    /**
     * Interface of a transformer that transforms a log entry.
     */
    public static interface LogTransformer {
        String transform(String log);
    }


    /**
     * @return true if this appender contains at least one line.
     */
    public boolean isEmpty() {
        return logs.isEmpty();
    }


    /**
     * Removes this appender from the root logger.
     */
    public void removeFromRootLogger() {
        Logger.getRootLogger().removeAppender(this);
    }


    @Override
    public String toString() {
        return StringUtils.join(logs, getProperty("line.separator"));
    }


    /**
     * Prints this appender lines to the given {@link PrintStream}.
     */
    public void printTo(PrintStream out) {
        out.println("--- Actual content of logs ---");
        for (LogEntry logEntry : logs) {
            out.println(logEntry);
        }
        out.println("--- end of LogString content ---");
    }


    /**
     * Sets the logging levels that should be saved by this appender. Message at other levels wo'nt be saved by this
     * appender. By default, all messages are saved.
     *
     * @param levels The levels to save.
     */
    public void setOnlyLevels(Level... levels) {
        this.onlyLevels = Arrays.asList(levels);
    }


    public class LogEntry {
        private final String renderedMessage;
        private final Throwable exception;


        private LogEntry(LoggingEvent event) {
            renderedMessage = renderMessage(event);
            exception = (event.getThrowableInformation() != null) ?
                        event.getThrowableInformation().getThrowable() :
                        null;
        }


        public String getRenderedMessage() {
            return renderedMessage;
        }


        @Override
        public String toString() {
            return renderedMessage;
        }


        public Throwable getException() {
            return exception;
        }
    }

    public static class LogEntryMatcher extends BaseMatcher<LogEntry> implements Matcher<LogEntry> {
        private final Matcher<String> messageMatcher;
        private final Matcher<Throwable> exceptionMatcher;


        public LogEntryMatcher(Matcher<String> messageMatcher) {
            this.messageMatcher = messageMatcher;
            this.exceptionMatcher = null;
        }


        public LogEntryMatcher(final Class<? extends Throwable> exceptionClass, final String exceptionMessage) {
            this.messageMatcher = null;
            this.exceptionMatcher = new BaseMatcher<Throwable>() {
                public boolean matches(Object o) {
                    Throwable exception = (Throwable)o;
                    while (exception != null) {
                        if (matchesClass(exception, exceptionClass) && matchesMessage(exception, exceptionMessage)) {
                            return true;
                        }
                        exception = exception.getCause();
                    }
                    return false;
                }


                private boolean matchesMessage(Throwable exception, String exceptionMessage) {
                    return (exceptionMessage == null) || exceptionMessage.equals(exception.getMessage());
                }


                private boolean matchesClass(Throwable exception, Class<? extends Throwable> exceptionClass) {
                    return exception.getClass().equals(exceptionClass);
                }


                public void describeTo(Description description) {
                    description.appendText("match exception(");
                    description.appendText(exceptionClass.getName());
                    description.appendText(", '");
                    description.appendText(exceptionMessage);
                    description.appendText("')");
                }
            };
        }


        public final boolean matches(Object o) {
            LogEntry logEntry = (LogEntry)o;
            boolean messageOK = (messageMatcher == null) ? true : messageMatcher.matches(logEntry.getRenderedMessage());
            boolean exceptionOK = (exceptionMatcher == null) ? true : exceptionMatcher.matches(logEntry.getException());
            return messageOK && exceptionOK;
        }


        public void describeTo(Description description) {
            if (messageMatcher != null) {
                description.appendDescriptionOf(messageMatcher);
            }
            if ((messageMatcher != null) && (exceptionMatcher != null)) {
                description.appendText(" AND ");
            }
            if (exceptionMatcher != null) {
                description.appendDescriptionOf(exceptionMatcher);
            }
        }


        public static LogEntryMatcher exception(Class<? extends Throwable> exceptionClass, String exceptionMessage) {
            return new LogEntryMatcher(exceptionClass, exceptionMessage);
        }


        public static LogEntryMatcher exception(Class<? extends Throwable> exceptionClass) {
            return new LogEntryMatcher(exceptionClass, null);
        }
    }

    public static class Count {
        private static final Count NONE = count(0);
        private final int expectedCount;


        public static Count count(int count) {
            return new Count(count);
        }


        public static Count none() {
            return NONE;
        }


        private Count(int expectedCount) {
            this.expectedCount = expectedCount;
        }


        public int getExpectedCount() {
            return expectedCount;
        }
    }
}