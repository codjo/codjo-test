package net.codjo.test.common.matcher;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
/**
 *
 */
public class StringMatchesTest {
    private static final String SEPARATOR = System.getProperty("line.separator");
    private static final String STRING = "ABCD";
    private static final String MULTILINE_STRING = "A" + SEPARATOR + "B" + SEPARATOR + "CD";


    @Test
    public void testMatches() {
        assertThat(STRING, StringMatches.matches("(.*)B(.*)"));
    }


    @Test
    public void testDoesNotMatch() {
        try {
            assertThat(STRING, StringMatches.matches("(.*)Z(.*)"));
            fail();
        }
        catch (AssertionError e) {
            //OK
        }
    }


    @Test
    public void testMatches_multiline() {
        assertThat(MULTILINE_STRING, StringMatches.matches("(.*)B(.*)"));
    }


    @Test
    public void testDoesNotMatch_multiline() {
        try {
            assertThat(MULTILINE_STRING, StringMatches.matches("(.*)Z(.*)"));
            fail();
        }
        catch (AssertionError e) {
            //OK
        }
    }
}
