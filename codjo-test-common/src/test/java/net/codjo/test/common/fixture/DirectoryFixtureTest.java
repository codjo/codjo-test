/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.fixture;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import junit.framework.TestCase;
import net.codjo.test.common.Directory;
import net.codjo.test.common.Directory.NotDeletedException;
import org.junit.Test;

import static net.codjo.test.common.DirectoryTest.deleteRootDirectory;
import static net.codjo.test.common.matcher.JUnitMatchers.*;
/**
 * Classe de test de {@link net.codjo.test.common.fixture.DirectoryFixture}.
 */
public class DirectoryFixtureTest extends TestCase {
    private String rootDirectoryPath;
    private ExecutorService executor;


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


    @Test
    public void test_teardown_retryDelete() throws Exception {
        executor = Executors.newFixedThreadPool(1);

        DirectoryFixture directory = new DirectoryFixture(rootDirectoryPath);
        directory.doSetUp();

        FileWriter writer = blockTearDownDelete(directory);

        FutureTask<NotDeletedException> tearDownResultingException = doTearDownInAnotherThread(directory);

        Thread.sleep(40);

        unblockTearDownDelete(writer);

        assertThat(tearDownResultingException.get(), nullValue());
        assertThat(directory.exists(), is(false));
    }


    private void unblockTearDownDelete(FileWriter writer) throws IOException {
        writer.close();
    }


    private FileWriter blockTearDownDelete(DirectoryFixture directory) throws IOException {
        File blockDelete = new File(directory, "block-delete.txt");
        FileWriter writer = new FileWriter(blockDelete);
        writer.write("unused");
        return writer;
    }


    private FutureTask<NotDeletedException> doTearDownInAnotherThread(final DirectoryFixture directory) {
        FutureTask<NotDeletedException> future = new FutureTask<NotDeletedException>(
              new Callable<NotDeletedException>() {
                  public NotDeletedException call() throws InterruptedException {
                      try {
                          directory.doTearDown();
                          return null;
                      }
                      catch (NotDeletedException e) {
                          return e;
                      }
                  }
              });

        executor.execute(future);
        return future;
    }


    @Override
    protected void tearDown() throws Exception {
        deleteRootDirectory(rootDirectoryPath);
        if (executor != null) {
            executor.shutdownNow();
        }
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
