/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import junit.framework.AssertionFailedError;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class XmlUtilTest {

    @Test
    public void test_assertEquals() throws Exception {
        XmlUtil.assertEquals("<a attribute='bobo'    />", "<a   attribute=\"bobo\" />");
        XmlUtil.assertEquals("<a></a>", "<a/>");
        XmlUtil.assertEquals("<a>   </a>", "<a/>");
        XmlUtil.assertEquals("<a> my comment   </a>", "<a>my comment</a>");
    }


    @Test
    public void test_assertEquals_bug() throws Exception {
        XmlUtil.assertEquals("<result>  <row>  <field name='result'> <![CDATA[v]]></field> </row>  </result>",
                             "<result><row><field name=\"result\"><![CDATA[v]]></field></row></result>");
    }


    @Test
    public void test_assertEquals_failure() throws Exception {
        try {
            XmlUtil.assertEquals("<a>", "....");
            fail();
        }
        catch (IllegalArgumentException afe) {
            assertEquals(
                  "'<a>' n'est pas valide : XML document structures must start and end within the same entity.",
                  afe.getMessage());
        }

        try {
            XmlUtil.assertEquals("<a attribute='bobo'    />", "<a   attribute=\"bob o\" />");
            forceTestFailure();
        }
        catch (AssertionFailedError afe) {
            assertEquals("\n"
                         + "expected  = ...<a attribute=\"bobo\"/>...\n"
                         + "but found = ...<a attribute=\"bob o\"/>...",
                         afe.getMessage());
        }
    }


    @Test
    public void test_areEquals() throws Exception {
        assertTrue(XmlUtil.areEquals("<a attribute='seb'  id='toto'>"
                                     + "  <field id='0' value='zero'/> "
                                     + "  <field id='1' value='one'/> "
                                     + "</a>",
                                     "<a attribute='seb' id='toto'>"
                                     + "  <field id='0' value='zero'/> "
                                     + "  <field id='1' value='one'/> "
                                     + "</a>"));
    }


    @Test
    public void test_areEquals_failure() throws Exception {
        assertFalse(XmlUtil.areEquals("<a attribute='seb' id='toto'>"
                                      + "  <field id='0' value='zero'/> "
                                      + "  <field id='1' value='one'/> "
                                      + "</a>",
                                      "<a attribute='seb' id='toto'>"
                                      + "  <field id='1' value='one'/> "
                                      + "  <field id='0' value='zero'/> "
                                      + "</a>"));
    }


    @Test
    public void test_assertEquivalent() throws Exception {
        XmlUtil.assertEquivalent("<a attribute='seb' id='toto'>"
                                 + "  <field id='0' value='zero'/> "
                                 + "  <field id='1' value='one'/> "
                                 + "</a>",
                                 "<a attribute='seb' id='toto'>"
                                 + "  <field id='1' value='one'/> "
                                 + "  <field id='0' value='zero'/> "
                                 + "</a>");
    }


    @Test
    public void test_areEquivalent() throws Exception {
        assertTrue(XmlUtil.areEquivalent("<a attribute='seb' id='toto'>"
                                         + "  <field id='0' value='zero'/> "
                                         + "  <field id='1' value='one'/> "
                                         + "</a>",
                                         "<a attribute='seb' id='toto'>"
                                         + "  <field id='1' value='one'/> "
                                         + "  <field id='0' value='zero'/> "
                                         + "</a>"));
    }


    @Test
    public void test_areEquivalent_failure() throws Exception {
        assertFalse(XmlUtil.areEquivalent("<a attribute='seb' id='toto'>"
                                          + "  <field id='0' value='zero'/> "
                                          + "  <field id='1' value='one'/> "
                                          + "</a>",
                                          "<a attribute='seb' id='toto'>"
                                          + "  <field id='0' value='zero'/> "
                                          + "</a>"));
    }


    private void forceTestFailure() {
        throw new IllegalStateException("Test en echec");
    }
}
