/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import java.io.File;
import java.io.IOException;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
/**
 * Classe de test de {@link PathUtil}.
 */
public class PathUtilTest extends TestCase {
    public void test_findTargetDirectory() throws Exception {
        assertEquals(findTargetDirectory(), PathUtil.findTargetDirectory(PathUtilTest.class));
        assertEquals(findTargetDirectory(), new PathUtil(PathUtilTest.class).findTargetDirectory());
    }


    public void test_findClassDirectory() throws Exception {
        assertEquals(findJavaFileDirectory(), PathUtil.findJavaFileDirectory(PathUtilTest.class));
        assertEquals(findJavaFileDirectory(), new PathUtil(PathUtilTest.class).findJavaFileDirectory());
    }


    public void test_findTestDirectory() throws Exception {
        assertEquals(findTestDirectory(), PathUtil.findTestDirectory(PathUtilTest.class));
        assertEquals(findTestDirectory(), new PathUtil(PathUtilTest.class).findTestDirectory());
    }


    public void test_findTestResourcesDirectory() throws Exception {
        assertEquals(findTestResourcesDirectory(), PathUtil.findTestResourcesDirectory(PathUtilTest.class));
        assertEquals(findTestResourcesDirectory(),
                     new PathUtil(PathUtilTest.class).findTestResourcesDirectory());
    }


    public void test_findSrcDirectory() throws Exception {
        assertEquals(findSrcDirectory(), PathUtil.findSrcDirectory(PathUtilTest.class));
        assertEquals(findSrcDirectory(), new PathUtil(PathUtilTest.class).findSrcDirectory());
    }


    public void test_findBaseDirectory() throws Exception {
        assertEquals(findBaseDirectory(), PathUtil.findBaseDirectory(PathUtilTest.class));
        assertEquals(findBaseDirectory(), new PathUtil(PathUtilTest.class).findBaseDirectory());
    }


    public void test_find_existingResource() throws Exception {
        assertFindExistingResource(PathUtil.find(PathUtil.class, "PathUtilTest.properties"));
        assertFindExistingResource(new PathUtil(PathUtil.class).find("PathUtilTest.properties"));
    }


    private void assertFindExistingResource(File file) {
        assertNotNull(file);
        assertTrue(file.exists());
        assertTrue(file.getName().endsWith("PathUtilTest.properties"));
    }


    public void test_find_unknownResource() throws Exception {
        try {
            PathUtil.find(PathUtil.class, "UnknownResource.properties");
            throw new Error("Should have failed");
        }
        catch (AssertionFailedError ex) {
            assertEquals("Resource 'UnknownResource.properties' est introuvable.", ex.getMessage());
        }
    }


    private Directory findSrcDirectory() throws IOException {
        return new Directory(new File(findTargetDirectory() + "/../" + PathUtil.SRC_DIRECTORY)
              .getCanonicalPath());
    }


    private Directory findTestDirectory() throws IOException {
        return new Directory(new File(findTargetDirectory() + "/../" + PathUtil.TEST_DIRECTORY)
              .getCanonicalPath());
    }


    private Directory findTestResourcesDirectory() throws IOException {
        return new Directory(new File(findTargetDirectory() + "/../" + PathUtil.TEST_RESOURCES_DIRECTORY)
              .getCanonicalPath());
    }


    private Directory findBaseDirectory() throws IOException {
        return new Directory(new File(findTargetDirectory() + "/../").getCanonicalPath());
    }


    private Directory findTargetDirectory() {
        String name = "/" + PathUtilTest.class.getName().replace('.', '/') + ".class";
        String absolutePath = PathUtilTest.class.getResource(name).getFile();
        int endIndex = absolutePath.lastIndexOf(PathUtil.TARGET_DIRECTORY_NAME);
        absolutePath = absolutePath.substring(0, endIndex) + "/" + PathUtil.TARGET_DIRECTORY_NAME;
        absolutePath = new File(absolutePath).getAbsolutePath();
        return new Directory(absolutePath);
    }


    private File findJavaFileDirectory() throws IOException {
        return new File(findTestDirectory(), PathUtilTest.class.getName().replace('.', '/')).getParentFile();
    }
}
