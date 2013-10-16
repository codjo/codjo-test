package net.codjo.test.common;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.experimental.theories.DataPoint;

import static net.codjo.test.common.ListAppender.ALL_LEVELS;
import static org.junit.Assert.assertEquals;
/**
 *
 */
abstract public class AbstractListAppenderTest {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    static final String[] LINES = {"line1", "line2", "line3"};


    static final String linePrefix(Level level, boolean usePrefix) {
        return usePrefix ? (level.toString() + ": ") : "";
    }


    @DataPoint
    public static final Restriction NO_RESTRICTION = new Restriction(Level.INFO,
                                                                     ALL_LEVELS.toArray(new Level[ALL_LEVELS.size()]));
    @DataPoint
    public static final Restriction ONLY_LOG_LEVEL = new Restriction(Level.ERROR, Level.ERROR);
    @DataPoint
    public static final Restriction ONLY_ANOTHER_LEVEL = new Restriction(Level.INFO, Level.DEBUG);
    @DataPoint
    public static final Restriction ALL_EXCEPT_LOG_LEVEL = new Restriction(Level.WARN, allExcept(Level.WARN));

    @DataPoint
    public static final PrefixType WITH_PREFIX = PrefixType.WITH_PREFIX;
    @DataPoint
    public static final PrefixType WITHOUT_PREFIX = PrefixType.WITHOUT_PREFIX;


    public static String[] logLines() {
        return logLines(Level.INFO);
    }


    public static String[] logLines(Level level) {
        Logger logger = getTestLogger();
        for (String line : LINES) {
            logger.log(level, line);
        }
        return LINES;
    }


    void assertLogged(boolean removeAfterAdd, boolean usePrefix) throws Exception {
        assertLogged(Level.INFO, removeAfterAdd, !removeAfterAdd, usePrefix);
    }


    void assertLogged(Level level, boolean removeAfterAdd, boolean expectLogs, boolean usePrefix) throws Exception {
        ListAppender appender = ListAppender.createAndAddToRootLogger();
        assertLogged(level, removeAfterAdd, expectLogs, appender, usePrefix);
    }


    void assertLogged(Level level, boolean removeAfterAdd, boolean expectLogs, ListAppender appender, boolean usePrefix)
          throws Exception {
        Assert.assertNotNull(appender);

        if (removeAfterAdd) {
            appender.removeFromRootLogger();
        }

        boolean appenderPresent = !removeAfterAdd;
        Enumeration<Appender> appenders = Logger.getRootLogger().getAllAppenders();
        while (appenders.hasMoreElements()) {
            if (appenders.nextElement().equals(appender)) {
                appenderPresent = true;
                break;
            }
        }
        Assert.assertEquals("appenderPresent", !removeAfterAdd, appenderPresent);

        String aMessage = "a message";
        getTestLogger().log(level, aMessage);
        if (expectLogs) {
            Assert.assertEquals("expectedLog", linePrefix(level, usePrefix) + aMessage, appender.toString());
        }
        else {
            Assert.assertEquals("expectedLog", "", appender.toString());
        }
    }


    static Logger getTestLogger() {
        return Logger.getLogger("test");
    }


    static class Restriction {
        final Level[] onlyLevels;
        final Level logLevel;


        public Restriction(Level logLevel, Level... onlyLevels) {
            this.onlyLevels = onlyLevels;
            this.logLevel = logLevel;
        }


        public boolean expectLogs() {
            boolean result = false;
            for (Level l : onlyLevels) {
                if (l.equals(logLevel)) {
                    result = true;
                    break;
                }
            }
            return result;
        }
    }


    static Level[] allExcept(Level... levels) {
        List<Level> result = new ArrayList<Level>(ALL_LEVELS);
        result.removeAll(Arrays.asList(levels));
        return result.toArray(new Level[result.size()]);
    }


    public static void assertLogged(ListAppender appender, boolean expectLogged, boolean usePrefix) {
        assertLogged(Level.INFO, appender, expectLogged, usePrefix);
    }


    public static void assertLogged(Level level, ListAppender appender, boolean expectLogged, boolean usePrefix) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        appender.printTo(new PrintStream(baos));

        String expectedContent = buildExpectedContent(level, expectLogged, usePrefix).toString();

        assertEquals("logContent", expectedContent, baos.toString());
    }


    public static String buildExpectedContent(Level level, boolean expectNotEmpty, boolean usePrefix) {
        StringBuilder expectedContent = new StringBuilder("--- Actual content of logs ---" + LINE_SEPARATOR);
        if (expectNotEmpty) {
            for (String line : LINES) {
                expectedContent.append(linePrefix(level, usePrefix) + line + LINE_SEPARATOR);
            }
        }
        expectedContent.append("--- end of LogString content ---" + LINE_SEPARATOR);
        return expectedContent.toString();
    }


    public static enum PrefixType {
        WITH_PREFIX,
        WITHOUT_PREFIX;
    }
}
