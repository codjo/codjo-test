/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.fixture;
import net.codjo.test.common.Directory;
import java.io.File;
import junit.framework.Assert;
/**
 * Fixture de gestion des repertoires.
 */
public class DirectoryFixture extends Directory implements Fixture {
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
        deleteRecursively();
    }
}
