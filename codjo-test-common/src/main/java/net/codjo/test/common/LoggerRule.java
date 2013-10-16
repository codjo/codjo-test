package net.codjo.test.common;
import java.io.PrintStream;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * This is a junit {@link #org.junit.Rule} that can be used in tests. Note that of a test failure, the content of the
 * associated {@link ListAppender} is dumped to {@link System#out}. The typical usage :<br/> <code> public class MyTest
 * {
 *
 * @Rule public LoggerRule rule = new LoggerRule();
 * @Test public void testMyMethod() {} // run some tests that should log something ...
 *
 * // get the appender ListAppender appender = rule.getAppender();
 *
 * // do some assertions by using the returned ListAppender ... } } </code>
 */
public class LoggerRule implements MethodRule {
    static final Level[] IGNORE_ALL = new Level[0];
    private static final Level[] NO_RESTRICTION = new Level[0];

    private final Level[] onlyLevels;
    private final Layout layout;
    private PrintStream dumpOutputOnFailure = System.out;

    private ListAppender appender;


    public LoggerRule() {
        this(null, NO_RESTRICTION);
    }


    /**
     * @param onlyLevels a null or empty array to save all level, otherwise save only messages at given levels.
     */
    public LoggerRule(Level... onlyLevels) {
        this(null, onlyLevels);
    }


    public LoggerRule(Layout layout) {
        this(layout, NO_RESTRICTION);
    }


    /**
     * @param onlyLevels a null or empty array to save all level, otherwise save only messages at given levels.
     */
    public LoggerRule(Layout layout, Level... onlyLevels) {
        this.onlyLevels = ((onlyLevels == null) || (onlyLevels.length == 0)) ? NO_RESTRICTION : onlyLevels;
        this.layout = layout;
    }


    /**
     * {@inheritDoc}
     */
    public final Statement apply(final Statement base,
                                 FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                appender = ListAppender.createAndAddToRootLogger();
                if ((onlyLevels != null) && (onlyLevels != NO_RESTRICTION)) {
                    appender.setOnlyLevels(onlyLevels);
                }
                if (layout != null) {
                    appender.setLayout(layout);
                }

                try {
                    base.evaluate();
                }
                catch (AssertionError ae) {
                    if (dumpOutputOnFailure != null) {
                        appender.printTo(dumpOutputOnFailure);
                    }
                    throw ae;
                }
                finally {
                    appender.removeFromRootLogger();
                }
            }
        };
    }


    /**
     * Gets the {@link ListAppender} associated with this rule.
     */
    public ListAppender getAppender() {
        return appender;
    }


    /**
     * Should we dump log content on test failure ?
     *
     * @param dumpOutputOnFailure a non-null value to dump log content when there is a test failure.
     */
    public void setDumpOutputOnFailure(PrintStream dumpOutputOnFailure) {
        this.dumpOutputOnFailure = dumpOutputOnFailure;
    }
}
