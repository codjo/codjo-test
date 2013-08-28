package net.codjo.test.common;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

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
            assertLogged(restriction.logLevel, false, restriction.expectLogs(), appender);
        }
        finally {
            appender.removeFromRootLogger();
        }
    }


    @Test
    public void testCreateAndAddToRootLogger() throws Exception {
        assertLogged(false);
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

            appender.matchesOneLine(lines[1]);
        }
        catch (AssertionFailedError afe) {
            appender.printTo(System.out);
            throw afe;
        }
        finally {
            appender.removeFromRootLogger();
        }
    }


    @Test
    public void testRemoveFromRootLogger() throws Exception {
        assertLogged(true);
    }


    @Test
    public void testPrintTo() throws Exception {
        ListAppender appender = ListAppender.createAndAddToRootLogger();

        try {
            logLines();
            assertLogged(appender, true);
        }
        finally {
            appender.removeFromRootLogger();
        }
    }
}
