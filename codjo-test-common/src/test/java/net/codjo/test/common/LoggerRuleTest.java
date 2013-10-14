package net.codjo.test.common;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import org.apache.commons.lang.mutable.MutableObject;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import static junit.framework.Assert.assertTrue;
import static net.codjo.test.common.matcher.JUnitMatchers.*;
import static org.apache.log4j.Level.INFO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 */
@RunWith(Theories.class)
public class LoggerRuleTest extends AbstractListAppenderTest {
    @DataPoint
    public static final boolean DO_NOT_DUMP_LOGS_ON_FAILURE = false;
    @DataPoint
    public static final boolean DUMP_LOGS_ON_FAILURE = true;

    @DataPoint
    public static final RuleRestriction RR_DEFAULT = new RuleRestriction();
    @DataPoint
    public static final RuleRestriction RR_ONLY_LOG_LEVEL = new RuleRestriction();
    @DataPoint
    public static final RuleRestriction RR_ONLY_ANOTHER_LEVEL = new RuleRestriction();
    @DataPoint
    public static final RuleRestriction RR_ALL_EXCEPT_LOG_LEVEL = new RuleRestriction();

    @Rule
    public LoggerRule LR_DEFAULT = new LoggerRule();
    @Rule
    public LoggerRule LR_ONLY_LOG_LEVEL = new LoggerRule(AbstractListAppenderTest.ONLY_LOG_LEVEL.onlyLevels);
    @Rule
    public LoggerRule LR_ONLY_ANOTHER_LEVEL = new LoggerRule(AbstractListAppenderTest.ONLY_ANOTHER_LEVEL.onlyLevels);
    @Rule
    public LoggerRule LR_ALL_EXCEPT_LOG_LEVEL
          = new LoggerRule(AbstractListAppenderTest.ALL_EXCEPT_LOG_LEVEL.onlyLevels);

    @DataPoint
    public static final Error ASSERTION_ERROR = new AssertionError("An error for testing");
    //note we don't test with other type of errors because they might be critical like OutOfMemoryError ...

    @DataPoint
    public static final Exception EXCEPTION = new Exception("An error for testing");


    @Test
    public void testConstructor_withLayout() throws Throwable {
        Layout expectedLayout = new PatternLayout("%-5p [%t]: %m");
        final LoggerRule rule = new LoggerRule(expectedLayout);
        final MutableObject expectedLogs = new MutableObject();
        final Statement statement = rule.apply(new Statement() {
            @Override
            public void evaluate() throws Throwable {
                expectedLogs.setValue(logLines());
            }
        }, null, null);
        statement.evaluate();

        assertEquals(expectedLayout, rule.getAppender().getLayout());
        for (String expectedLog : (String[])expectedLogs.getValue()) {
            rule.getAppender().assertHasLog(equalTo("INFO  [main]: " + expectedLog));
        }
    }


    @Theory
    public void testCleanUpAfterFailure(final Throwable error, boolean dumpLogsOnFailure) {
        final LoggerRule rule = new LoggerRule();

        ByteArrayOutputStream actualContent = new ByteArrayOutputStream();
        boolean expectFailure = AssertionError.class.isAssignableFrom(error.getClass());
        boolean expectDump = expectFailure && dumpLogsOnFailure;
        if (expectDump) {
            rule.setDumpOutputOnFailure(new PrintStream(actualContent));
        }
        else {
            rule.setDumpOutputOnFailure(null);
        }

        assertAppenderIsNotEnabled(rule, error);

        final Statement statement = rule.apply(new Statement() {
            @Override
            public void evaluate() throws Throwable {
                assertAppenderIsEnabled(rule, error);
                throw error;
            }
        }, null, null);

        try {
            statement.evaluate();
        }
        catch (Throwable throwable) {
            assertEquals(error, throwable);
        }

        String expectedStdOut = expectDump ? buildExpectedContent(INFO, false, true) : "";
        assertEquals(expectedStdOut, actualContent.toString());

        assertAppenderIsNotEnabled(rule, error);
    }


    private void assertAppenderIsEnabled(LoggerRule rule, Throwable error) {
        assertTrue("appender is not enabled for " + error.getClass().getSimpleName(),
                   appenderIsEnabled(rule.getAppender()));
    }


    private void assertAppenderIsNotEnabled(LoggerRule rule, Throwable error) {
        Assert.assertFalse("appender is enabled for " + error.getClass().getSimpleName(),
                           appenderIsEnabled(rule.getAppender()));
    }


    private boolean appenderIsEnabled(Appender appender) {
        Enumeration e = Logger.getRootLogger().getAllAppenders();
        boolean result = false;
        while (e.hasMoreElements()) {
            Appender a = (Appender)e.nextElement();
            if (a == appender) {
                result = true;
                break;
            }
        }
        return result;
    }


    @Theory
    public void testLogsAreSaved(RuleRestriction rr) throws Exception {
        logLines(rr.getLevel());

        LoggerRule rule = rr.getLoggerRule(this);
        assertNotNull("appender", rule.getAppender());
        assertLogged(rr.getLevel(), rule.getAppender(), rr.expectLogs(), true);
    }


    private static class RuleRestriction {
        public LoggerRule getLoggerRule(LoggerRuleTest test) throws Exception {
            String name = getStaticFieldName(false);
            name = "L" + name.substring(1);

            return (LoggerRule)test.getClass().getField(name).get(test);
        }


        private String getStaticFieldName(boolean removeLRPrefix) throws IllegalAccessException {
            String name = "";
            for (Field f : LoggerRuleTest.class.getDeclaredFields()) {
                int m = f.getModifiers();
                if (((m & Modifier.STATIC) == Modifier.STATIC) && ((m & Modifier.PUBLIC) == Modifier.PUBLIC) && (
                      f.get(null) == this)) {
                    name = f.getName();

                    if (removeLRPrefix) {
                        name = name.substring(3);
                    }
                    break;
                }
            }
            return name;
        }


        public boolean expectLogs() throws Exception {
            String name = getStaticFieldName(true);

            boolean result = true;
            if (!"DEFAULT".equals(name)) {
                Restriction r = getRestriction(name);
                result = r.expectLogs();
            }

            System.out.println("expectLogs(" + name + ")=" + result);
            return result;
        }


        public Level getLevel() throws Exception {
            String name = getStaticFieldName(true);

            Level result = INFO;
            if (!"DEFAULT".equals(name)) {
                Restriction r = getRestriction(name);
                result = r.logLevel;
            }

            System.out.println("level(" + name + ")=" + result);
            return result;
        }


        private Restriction getRestriction(String name) throws IllegalAccessException, NoSuchFieldException {
            return (Restriction)AbstractListAppenderTest.class.getField(name).get(null);
        }
    }
}
