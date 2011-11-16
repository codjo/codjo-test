package net.codjo.test.common;
import java.util.regex.Pattern;
import junit.framework.AssertionFailedError;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
public class LogStringTest {
    private LogString logString = new LogString();


    @Test
    public void test_assertContentEllipsis() throws Exception {
        try {
            logString.assertContent("initialize()", "run()");
        }
        catch (AssertionFailedError ex) {
            assertEquals("assertContent - array length expected:<2> but was:<0>", ex.getMessage());
        }

        logString.call("setUp", "connection/1234");
        logString.call("doIt", "pim", "pam", "poum");
        logString.call("tearDown", "connection/1234");
        logString.call("methodWithEmptyArgs", new Object[0]);

        logString.assertContent("setUp(connection/1234)", "doIt(pim, pam, poum)",
                                "tearDown(connection/1234)", "methodWithEmptyArgs()");

        try {
            logString.assertContent("doIt(pim, pam, poum)", "tearDown(connection/1234)");
        }
        catch (AssertionFailedError ex) {
            assertEquals("assertContent - array length expected:<2> but was:<4>", ex.getMessage());
        }

        try {
            logString.assertContent("doIt(pim, pam, poum)",
                                    "setUp(connection/1234)",
                                    "tearDown(connection/1234)",
                                    "methodWithEmptyArgs()");
        }
        catch (AssertionFailedError ex) {
            assertEquals(
                  "assertContent - expected:<[doIt(pim, pam, poum])> but was:<[setUp(connection/1234])>",
                  ex.getMessage());
        }
    }


    @Test
    public void test_assertContentPattern() throws Exception {
        try {
            logString.assertContent(Pattern.compile("^initialize\\(\\), run\\(\\)$"));
        }
        catch (AssertionFailedError ex) {
            assertEquals("assertContent - Pattern does not match <>", ex.getMessage());
        }

        logString.call("setUp", "connection/1234");
        logString.call("doIt");
        logString.call("tearDown", "connection/1234");

        logString.assertContent(Pattern.compile(
              "setUp\\(connection/(\\d+)\\), doIt\\(\\), tearDown\\(connection/\\1\\)"));

        try {
            logString.assertContent(Pattern.compile("doIt\\(\\), tearDown\\(.*\\)"));
        }
        catch (AssertionFailedError ex) {
            assertEquals(
                  "assertContent - Pattern does not match <setUp(connection/1234), doIt(), tearDown(connection/1234)>",
                  ex.getMessage());
        }
    }


    @Test
    public void test_assertAndClearPattern() throws Exception {
        logString.call("setUp", "connection/1234");
        logString.call("doIt");
        logString.call("tearDown", "connection/6789");

        try {
            logString.assertAndClear(Pattern.compile(
                  "setUp\\(connection/(\\d+)\\), doIt\\(\\), tearDown\\(connection/\\1\\)"));
        }
        catch (AssertionFailedError ex) {
            assertEquals(
                  "assertContent - Pattern does not match <setUp(connection/1234), doIt(), tearDown(connection/6789)>",
                  ex.getMessage());
        }

        logString.assertContent("setUp(connection/1234), doIt(), tearDown(connection/6789)");

        logString.assertAndClear(Pattern.compile(
              "setUp\\(connection/\\d+\\), doIt\\(\\), tearDown\\(connection/\\d+\\)"));

        logString.assertContent("");
    }


    @Test
    public void test_assertContent() throws Exception {
        try {
            logString.assertContent("doIt()");

            fail();
        }
        catch (AssertionFailedError ex) {
            assertEquals("assertContent - expected:<[doIt()]> but was:<[]>", ex.getMessage());
        }

        logString.call("doIt");

        logString.assertContent("doIt()");
    }


    @Test
    public void test_assertAndClear() throws Exception {
        try {
            logString.assertAndClear("doIt()");

            fail();
        }
        catch (AssertionFailedError ex) {
            assertEquals("assertContent - expected:<[doIt()]> but was:<[]>", ex.getMessage());
        }

        logString.call("doIt");

        logString.assertAndClear("doIt()");

        logString.assertContent("");
    }


    @Test
    public void test_call() throws Exception {
        logString.call("doIt");
        assertEquals("doIt()", logString.getContent());

        logString.clear();
        logString.call("oneArg", "argA");
        logString.call("twoArg", "argA", "argB");
        logString.call("threeArg", "argA", "argB", "argC");
        logString.call("fourArg", "argA", "argB", "argC", "argD");
        assertEquals(
              "oneArg(argA), twoArg(argA, argB), threeArg(argA, argB, argC), fourArg(argA, argB, argC, argD)",
              logString.getContent());
    }


    @Test
    public void test_info() throws Exception {
        logString.info("mon log");

        assertEquals("mon log", logString.getContent());
    }


    @Test
    public void test_info_morelogs() throws Exception {
        logString.info("erreur");
        logString.info("reussi");
        logString.info("1");
        assertEquals("erreur, reussi, 1", logString.getContent());
    }


    @Test
    public void test_clear() throws Exception {
        logString.info("erreur");
        logString.clear();
        logString.info("1");
        assertEquals("1", logString.getContent());
    }


    @Test
    public void test_prefix_log() throws Exception {
        logString.setPrefix("prefA");
        logString.info("message");
        assertEquals("prefA.message", logString.getContent());
    }


    @Test
    public void test_prefix_composite() throws Exception {
        LogString prefixedLog = new LogString("MyPrefix", logString);

        prefixedLog.info("message");

        assertEquals("MyPrefix.message", logString.getContent());
        assertEquals("MyPrefix.message", prefixedLog.getContent());
    }
}
