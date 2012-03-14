/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.fixture;
import java.io.File;
import junit.framework.Assert;
import net.codjo.test.common.Directory;
import org.apache.log4j.Logger;
/**
 * Fixture de gestion des repertoires.
 */
public class DirectoryFixture extends Directory implements Fixture {
    private static final int MAX_DELETE_TRY_COUNT = 10;


    public DirectoryFixture(String rootPath) {
        super(rootPath);
    }


    public static DirectoryFixture newTemporaryDirectoryFixture() {
        return newTemporaryDirectoryFixture("TemporaryDirectory");
    }


    public static DirectoryFixture newTemporaryDirectoryFixture(String directoryName) {
        return new DirectoryFixture(System.getProperty("java.io.tmpdir") + File.separator + directoryName);
    }


    public void doSetUp() throws NotDeletedException {
        deleteRecursively();

        make();
        Assert.assertTrue(exists());
    }


    public void doTearDown() throws NotDeletedException {
        for (int currentTryCount = 0; true; currentTryCount++) {
            try {
                deleteRecursively();
                return;
            }
            catch (NotDeletedException e) {
                if (currentTryCount >= MAX_DELETE_TRY_COUNT) {
                    throw e;
                }
                Logger.getLogger(DirectoryFixture.class).info("Unable to delete " + e.getMessage()
                                                              + ". Retry in 20ms...");
                waitForAWhile();
            }
        }
    }


    private static void waitForAWhile() {
        try {
            Thread.sleep(20);
        }
        catch (InterruptedException e1) {
            ;
        }
    }
}
