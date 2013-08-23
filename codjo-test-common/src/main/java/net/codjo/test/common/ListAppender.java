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

    private final List<String> logs = new ArrayList<String>();
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
            String level = event.getLevel().toString();
            logs.add(level + ": " + event.getRenderedMessage());
        }
    }


    @Override
    public boolean requiresLayout() {
        return false;
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
        for (String line : logs) {
            if (line.matches(regex)) {
                result = true;
                break;
            }
        }
        return result;
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
        return StringUtils.join(logs, System.getProperty("line.separator"));
    }


    /**
     * Prints this appender lines to the given {@link PrintStream}.
     */
    public void printTo(PrintStream out) {
        out.println("--- Actual content of logs ---");
        for (String line : logs) {
            out.println(line);
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
}