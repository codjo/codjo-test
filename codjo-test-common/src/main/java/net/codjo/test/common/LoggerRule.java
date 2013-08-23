package net.codjo.test.common;
import junit.framework.AssertionFailedError;
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
    private final Level[] onlyLevels;

    private ListAppender appender;


    public LoggerRule() {
        this.onlyLevels = null;
    }


    public LoggerRule(Level... onlyLevels) {
        this.onlyLevels = onlyLevels;
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
                if (onlyLevels != null) {
                    appender.setOnlyLevels(onlyLevels);
                }

                try {
                    base.evaluate();
                }
                catch (AssertionFailedError afe) {
                    appender.printTo(System.out);
                    throw afe;
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
}
