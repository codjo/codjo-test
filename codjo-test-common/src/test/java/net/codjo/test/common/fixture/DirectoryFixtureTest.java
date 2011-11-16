/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.fixture;
import net.codjo.test.common.Directory;
import static net.codjo.test.common.DirectoryTest.deleteRootDirectory;
import java.io.File;
import junit.framework.TestCase;
/**
 * Classe de test de {@link net.codjo.test.common.fixture.DirectoryFixture}.
 */
public class DirectoryFixtureTest extends TestCase {
    private String rootDirectoryPath;


    public void test_temporaryDirectoryFixture() {
        final String directoryName = getClass().getName();
        final Directory temporaryDirectory = DirectoryFixture.newTemporaryDirectoryFixture(directoryName);
        assertEquals(new DirectoryFixture(tmpDir(directoryName)), temporaryDirectory);

        assertFalse(new File(temporaryDirectory.getPath()).exists());

        temporaryDirectory.make();
        assertTrue(new File(temporaryDirectory.getPath()).exists());
    }


    public void test_newTemporaryDirectoryFixture() throws Exception {
        DirectoryFixture fixture = DirectoryFixture.newTemporaryDirectoryFixture();
        assertNotNull(fixture);

        File expected = new File(tmpDir("TemporaryDirectory"));
        assertEquals(expected.getAbsolutePath(), fixture.getAbsolutePath());
    }


    public void test_make() {
        final DirectoryFixture directory = new DirectoryFixture(rootDirectoryPath);
        assertFalse(new File(rootDirectoryPath).exists());

        directory.make();
        assertTrue(new File(rootDirectoryPath).exists());

        final String subdirectoryName = "subdirectory";
        directory.makeSubDirectory(subdirectoryName);
        assertTrue(new File(rootDirectoryPath, subdirectoryName).exists());

        final String anotherSubdirectoryName = "anotherSubdirectoryName";
        directory.makeSubDirectory(anotherSubdirectoryName);
        assertTrue(new File(rootDirectoryPath, anotherSubdirectoryName).exists());
    }


    public void test_exists() throws Directory.NotDeletedException {
        final DirectoryFixture directory = new DirectoryFixture(rootDirectoryPath);
        assertFalse(directory.exists());

        directory.makeSubDirectory("subdirectory");
        assertTrue(directory.exists());

        directory.deleteRecursively();
        assertFalse(directory.exists());
    }


    public void test_getPath() {
        final DirectoryFixture directory = new DirectoryFixture(rootDirectoryPath);
        assertEquals(new File(rootDirectoryPath).getPath(), directory.getPath());
    }


    public void test_lastCreated() throws Directory.NotDeletedException {
        final DirectoryFixture directory = new DirectoryFixture(rootDirectoryPath);
        assertNull(directory.lastCreated());

        directory.make();
        assertEquals(directory.getPath(), directory.lastCreated());
        assertEquals(directory.getPath(), directory.lastCreated());
        assertEquals(directory.getPath(), directory.lastCreated());

        final String subDirectoryName = "totoSubDir";
        directory.makeSubDirectory(subDirectoryName);
        assertEquals(directory.getPath() + File.separator + subDirectoryName, directory.lastCreated());

        directory.deleteRecursively();
        assertEquals(directory.getPath() + File.separator + subDirectoryName, directory.lastCreated());
    }


    @Override
    protected void tearDown() throws Exception {
        deleteRootDirectory(rootDirectoryPath);
    }


    @Override
    protected void setUp() throws Exception {
        rootDirectoryPath = tmpDir(getClass().getName());
        deleteRootDirectory(rootDirectoryPath);
    }


    private String tmpDir(String directory) {
        return System.getProperty("java.io.tmpdir") + File.separator + directory;
    }
}
