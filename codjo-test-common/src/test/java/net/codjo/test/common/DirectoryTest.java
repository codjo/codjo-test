/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import net.codjo.test.common.fixture.DirectoryFixture;
import java.io.File;
import junit.framework.TestCase;
/**
 * Classe de test de {@link net.codjo.test.common.fixture.DirectoryFixture}.
 */
public class DirectoryTest extends TestCase {
    private String rootDirectoryPath;


    public void test_deleteRecursively() throws Directory.NotDeletedException {
        final DirectoryFixture directory = new DirectoryFixture(rootDirectoryPath);
        directory.makeSubDirectory("subdirectory");
        directory.makeSubDirectory("anotherSubdirectoryName");
        directory.makeSubDirectory("anotherSubdirectoryName\\subSub\\subsubsubsub");
        assertTrue(new File(rootDirectoryPath).exists());

        directory.deleteRecursively();
        assertFalse(new File(rootDirectoryPath).exists());
    }


    @Override
    protected void tearDown() throws Exception {
        deleteRootDirectory(rootDirectoryPath);
    }


    @Override
    protected void setUp() throws Exception {
        rootDirectoryPath = System.getProperty("java.io.tmpdir") + File.separator + getClass().getName();
        deleteRootDirectory(rootDirectoryPath);
    }


    public static void deleteRootDirectory(final String path) {
        final File root = new File(path);
        final File[] files = root.listFiles();
        if (null != files) {
            for (final File file : files) {
                if (file.isDirectory()) {
                    deleteRootDirectory(file.getPath());
                }
                else {
                    file.delete();
                }
            }
        }
        root.delete();
        assertFalse(root.exists());
    }
}
