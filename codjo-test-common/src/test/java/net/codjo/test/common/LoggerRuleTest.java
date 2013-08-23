package net.codjo.test.common;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
/**
 *
 */
@RunWith(Theories.class)
public class LoggerRuleTest extends AbstractListAppenderTest {
    private static final Logger LOG = Logger.getLogger(LoggerRuleTest.class);

    @DataPoint
    public static final RuleRestriction RR_DEFAULT = new RuleRestriction();
    @DataPoint
    public static final RuleRestriction RR_ONLY_LOG_LEVEL = new RuleRestriction();
    @DataPoint
    public static final RuleRestriction RR_ONLY_ANOTHER_LEVEL = new RuleRestriction();
    @DataPoint
    public static final RuleRestriction RR_ALL_EXCEPT_LOG_LEVEL = new RuleRestriction();
    @DataPoint
    public static final RuleRestriction RR_IGNORE_ALL_LEVELS = new RuleRestriction();

    @Rule
    public LoggerRule LR_DEFAULT = new LoggerRule();
    @Rule
    public LoggerRule LR_ONLY_LOG_LEVEL = new LoggerRule(AbstractListAppenderTest.ONLY_LOG_LEVEL.onlyLevels);
    @Rule
    public LoggerRule LR_ONLY_ANOTHER_LEVEL = new LoggerRule(AbstractListAppenderTest.ONLY_ANOTHER_LEVEL.onlyLevels);
    @Rule
    public LoggerRule LR_ALL_EXCEPT_LOG_LEVEL
          = new LoggerRule(AbstractListAppenderTest.ALL_EXCEPT_LOG_LEVEL.onlyLevels);
    @Rule
    public LoggerRule LR_IGNORE_ALL_LEVELS = new LoggerRule(AbstractListAppenderTest.IGNORE_ALL_LEVELS.onlyLevels);


    @Theory
    public void aTestThatLogsSomething(RuleRestriction rr) throws Exception {
        logLines(rr.getLevel());

        LoggerRule rule = rr.getLoggerRule(this);
        assertNotNull("appender", rule.getAppender());
        assertLogged(rr.getLevel(), rule.getAppender(), rr.expectLogs());
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

            Level result = Level.INFO;
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
