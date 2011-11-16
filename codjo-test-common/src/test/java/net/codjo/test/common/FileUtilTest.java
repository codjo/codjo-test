/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import net.codjo.test.common.fixture.DirectoryFixture;
import java.io.File;
import java.io.StringReader;
import junit.framework.TestCase;
/**
 * Classe de test de {@link FileUtil}.
 */
public class FileUtilTest extends TestCase {
    private DirectoryFixture directoryFixture = DirectoryFixture.newTemporaryDirectoryFixture("FileUtilTest");


    public void test_loadContent() throws Exception {
        String expected = "sdkjfhsjkfd\nfvgdtgfrg";

        assertEquals(expected, FileUtil.loadContent(new StringReader(expected)));
    }


    public void test_loadContent_fromFile() throws Exception {
        String expected = "file content";

        assertEquals(expected, FileUtil.loadContent(createFile("FileUtilTest.properties")));
    }


    public void test_loadContent_fromUrl() throws Exception {
        String expected = "file content";

        assertEquals(expected, FileUtil.loadContent(getClass().getResource("FileUtilTest.properties")));
    }


    public void test_loadContent_fromUTF16() throws Exception {
        XmlUtil.assertEquivalent("<todo>"
                                 + "    fêter la victoire de Bordeaux."
                                 + "</todo>",
                                 FileUtil.loadContent(getClass().getResource("UTF16.xml"), "UTF16"));
    }


    public void test_saveContent() throws Exception {
        String content = "file content";
        File file = new File(directoryFixture, "myFile");

        FileUtil.saveContent(file, content);

        assertEquals(content, FileUtil.loadContent(file));
    }


    public void test_saveContent_withUTF16() throws Exception {
        File file = new File(directoryFixture, "myFile");

        FileUtil.saveContent(file,
                             "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\r\n"
                             + "<todo>\r\n"
                             + "    fêter la victoire de Bordeaux.\r\n"
                             + "</todo>",
                             "UTF16");

        File expected = new File(getClass().getResource("UTF16.xml").getFile());
        assertTrue(new FileComparator("*").equals(expected, file));
    }


    @Override
    protected void setUp() throws Exception {
        directoryFixture.doSetUp();
    }


    @Override
    protected void tearDown() throws Exception {
        directoryFixture.doTearDown();
    }


    private File createFile(String name) {
        return new File(getClass().getResource(name).getFile());
    }
}
