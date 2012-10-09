package net.codjo.test.common;
import java.net.URL;
import org.junit.Test;
import static net.codjo.test.common.matcher.JUnitMatchers.*;
public class IsXsdCompliantTest {
    private static final String XSD_DEFINING_TAG = "<xs:schema xmlns:xs='http://www.w3.org/2001/XMLSchema' elementFormDefault='qualified'>"
                                                   + "    <xs:element name='tag' type='tag-type'/>"
                                                   + "    <xs:complexType name='tag-type'/>"
                                                   + "</xs:schema>";


    @Test
    public void testIsCompliant() throws Exception {
        assertThat("<tag/>", is(xsdCompliantWith(XSD_DEFINING_TAG)));
    }


    @Test
    public void testIsNotCompliant() throws Exception {
        assertThat("<unknown-tag/>", is(not(xsdCompliantWith(XSD_DEFINING_TAG))));
    }


    @Test
    public void testFailingMessageGivesSomeClue() throws Exception {
        try {
            assertThat("<unknown-tag/>", is(xsdCompliantWith(XSD_DEFINING_TAG)));
        }
        catch (AssertionError failureMessage) {
            assertThat(failureMessage.getMessage(), containsString("Not compliant with XSD"));
            assertThat(failureMessage.getMessage(), containsString("<unknown-tag/>"));
        }
    }


    @Test
    public void testIsCompliantFromXsdFile() throws Exception {
        assertThat("<release-test/>", is(xsdCompliantWith(xsdFile())));
    }


    @Test
    public void testIsNotCompliantFromXsdFile() throws Exception {
        assertThat("<unknown-tag/>", is(not(xsdCompliantWith(xsdFile()))));
    }


    @Test
    public void testBadXsdUrl() throws Exception {
        try {
            assertThat("<unused/>", is(xsdCompliantWith(new URL("file://well-formated-url/but/inexistant.xsd"))));
        }
        catch (AssertionError failureMessage) {
            assertThat(failureMessage.getMessage(), containsString("Unable to load XSD file from 'file://well-formated-url/but/inexistant.xsd'"));
        }
    }


    private URL xsdFile() {
        return getClass().getResource("IsXsdCompliantTest-simple.xsd");
    }
}
