package net.codjo.test.common;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import net.codjo.util.file.FileUtil;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
/**
 * <b> Code below should be moved in codjo-test-common </b>
 */
public class IsXsdCompliant extends TypeSafeMatcher<String> {
    private String xsdContent;
    private Exception failingValidation;


    public IsXsdCompliant(URL xsd) {
        try {
            this.xsdContent = FileUtil.loadContent(xsd);
        }
        catch (IOException e) {
            Assert.fail("Unable to load XSD file from '" + xsd + "' due to : " + e.getLocalizedMessage());
        }
    }


    public IsXsdCompliant(String xsdContent) {
        this.xsdContent = xsdContent;
    }


    public static IsXsdCompliant xsdCompliantWith(URL xsd) {
        return new IsXsdCompliant(xsd);
    }


    public static IsXsdCompliant xsdCompliantWith(String xsdContent) {
        return new IsXsdCompliant(xsdContent);
    }


    @Override
    public boolean matchesSafely(String xml) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(new StringReader(xsdContent)));
            Validator validator = schema.newValidator();

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);

            DocumentBuilder parser = builderFactory.newDocumentBuilder();
            Document document = parser.parse(new InputSource(new StringReader(xml)));
            validator.validate(new DOMSource(document));
            return true;
        }
        catch (Exception e) {
            this.failingValidation = e;
            return false;
        }
    }


    public void describeTo(Description description) {
        if (failingValidation != null) {
            description.appendText("Not compliant with XSD : " + failingValidation.getLocalizedMessage());
        }
        else {
            description.appendText("Compliant with XSD");
        }
    }
}
