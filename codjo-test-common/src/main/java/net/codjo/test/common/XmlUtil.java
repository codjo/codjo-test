/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import junit.framework.AssertionFailedError;
import org.saxstack.comparator.XmlComparator;
import org.saxstack.utils.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
/**
 * Classe permettant de comparer des strings XML sans tenir compte des espaces vide entre les
 * tags.<p>Exemples
 * :<pre>   XmlUtil.assertEquals("&lt;a&gt;   &lt;/a&gt;", "&lt;a/&gt;");</pre></p>
 */
public final class XmlUtil {
    public static final int MESSAGE_LENGTH = 60;


    private XmlUtil() {
    }


    public static void assertEquals(String expected, String actual) {
        String expectedTranslated = translate(expected);
        String actualTranslated = translate(actual);
        if (!areEqualsWithoutTranslation(expectedTranslated, actualTranslated)) {
            showStringDiff(expectedTranslated, actualTranslated);
        }
    }


    public static boolean areEquals(String expected, String actual) {
        return areEqualsWithoutTranslation(translate(expected), translate(actual));
    }


    public static void assertEquivalent(String expected, String actual) {
        SAXParser parser = new SAXParser();
        try {
            if (!areEquivalent(expected, actual, parser)) {
                showStringDiff(expected, actual, parser);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean areEquivalent(String expected, String actual) {
        return areEquivalent(expected, actual, new SAXParser());
    }


    private static void showStringDiff(String expected, String actual, SAXParser parser) throws IOException {
        assertEquals(XmlUtils.format(expected, parser, 4),
                     XmlUtils.format(actual, parser, 4));
    }


    private static boolean areEquivalent(String expected, String actual, SAXParser parser) {
        try {
            return XmlComparator.areEquivalent(expected, actual, parser);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }


    private static String translate(String xmlString) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);

            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));

            cleanUpDocument(document);

            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            StringWriter result = new StringWriter();
            xformer.transform(new DOMSource(document), new StreamResult(result));

            return result.toString();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("'" + xmlString + "' n'est pas valide : " + e.getMessage());
        }
    }


    private static void showStringDiff(String expectedTranslated, String actualTranslated) {
        for (int i = 0; i < expectedTranslated.length(); i++) {
            if (!actualTranslated.startsWith(expectedTranslated.substring(0, i))) {
                int min = Math.max(0, i - MESSAGE_LENGTH);
                String expectedInfo =
                      "..." + expectedTranslated.substring(min,
                                                           Math.min(i + MESSAGE_LENGTH,
                                                                    expectedTranslated.length()))
                      + "...";
                String actualInfo =
                      "..." + actualTranslated.substring(min,
                                                         Math.min(i + MESSAGE_LENGTH,
                                                                  actualTranslated.length())) + "...";

                actualInfo = actualInfo.replace('\r', ' ');
                actualInfo = actualInfo.replace('\n', ' ');

                expectedInfo = expectedInfo.replace('\r', ' ');
                expectedInfo = expectedInfo.replace('\n', ' ');

                throw new AssertionFailedError("\nexpected  = " + expectedInfo + "\nbut found = "
                                               + actualInfo);
            }
        }
    }


    private static boolean areEqualsWithoutTranslation(String expected, String actual) {
        return expected.equals(actual);
    }


    private static void cleanUpDocument(Node rootNode) {
        if (rootNode.getChildNodes() == null) {
            return;
        }

        NodeList nodeList = rootNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            boolean removed = false;
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.TEXT_NODE) {
                if (node.getNodeValue() != null) {
                    node.setNodeValue(node.getNodeValue().trim());
                    if ("".equals(node.getNodeValue())) {
                        rootNode.removeChild(node);
                        i--;
                        removed = true;
                    }
                }
            }
            if (!removed) {
                cleanUpDocument(node);
            }
        }
    }
}
