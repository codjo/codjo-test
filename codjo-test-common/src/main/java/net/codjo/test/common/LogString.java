package net.codjo.test.common;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import junit.framework.Assert;

public class LogString {
    private static final String SEPARATOR = ", ";
    private static final String OPENING = "(";
    private static final String CLOSING = ")";
    private StringBuffer log = new StringBuffer();
    private String prefix;


    public LogString() {
    }


    public LogString(String prefix, LogString logString) {
        this.log = logString.log;
        setPrefix(prefix);
    }


    public synchronized String getContent() {
        return log.toString();
    }


    public synchronized void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    public synchronized void clear() {
        log.setLength(0);
    }


    public synchronized void info(String message) {
        if (log.length() != 0) {
            log.append(SEPARATOR);
        }
        if (prefix != null) {
            log.append(prefix);
            log.append(".");
        }
        log.append(message);
    }


    public void call(String methodName) {
        info(methodName + "()");
    }


    public void call(String methodName, Object arg1) {
        call(methodName, new Object[]{arg1});
    }


    public void call(String methodName, Object arg1, Object arg2) {
        call(methodName, new Object[]{arg1, arg2});
    }


    public void call(String methodName, Object arg1, Object arg2, Object arg3) {
        call(methodName, new Object[]{arg1, arg2, arg3});
    }


    public void call(String methodName, Object arg1, Object arg2, Object arg3, Object arg4) {
        call(methodName, new Object[]{arg1, arg2, arg3, arg4});
    }


    public void call(String methodName, Object... parameters) {
        StringBuilder args = new StringBuilder();
        for (Object parameter : parameters) {
            args.append(parameter).append(SEPARATOR);
        }
        if (args.length() > 0) {
            args.delete(args.length() - SEPARATOR.length(), args.length());
        }
        info(methodName + OPENING + args + CLOSING);
    }


    public synchronized void assertContent(String expectedContent) {
        Assert.assertEquals("assertContent -", expectedContent, getContent());
    }


    public synchronized void assertContent(String... expectedContent) {
        String[] contents = new String[0];
        if (0 < getContent().length()) {
            //do not split comma-separated list of arguments in method calls
            contents = getContent().split("\\)" + SEPARATOR);
        }
        Assert.assertEquals("assertContent - array length", expectedContent.length, contents.length);
        for (int i = 0; i < expectedContent.length; i++) {
            String actualContent = contents[i];
            if (i < expectedContent.length - 1) {
                //add closing parenthesis to method call log 
                actualContent = actualContent + ")";
            }
            Assert.assertEquals("assertContent -", expectedContent[i], actualContent);
        }
    }


    public synchronized void assertContent(Pattern expectedContent) {
        Matcher matcher = expectedContent.matcher(getContent());
        Assert.assertTrue(String.format("assertContent - Pattern does not match <%s>", getContent()),
                          matcher.matches());
    }


    public synchronized void assertAndClear(String expectedContent) {
        assertContent(expectedContent);
        clear();
    }


    public synchronized void assertAndClear(String... expectedContent) {
        assertContent(expectedContent);
        clear();
    }


    public synchronized void assertAndClear(Pattern expectedContent) {
        assertContent(expectedContent);
        clear();
    }
}
