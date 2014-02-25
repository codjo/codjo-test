/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
/**
 * Représente un répertoire.
 */
public class Directory extends File {
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
    private static boolean IS_WINDOWS = OS_NAME.contains("windows");
    private static final int DELETE_RETRY_SLEEP_MILLIS = 10;
    private String lastCreatedSubDirectory;


    public Directory(String rootPath) {
        super(rootPath);
        lastCreatedSubDirectory = null;
    }


    public void deleteRecursively() throws NotDeletedException {
        final File[] files = listFiles();
        if (null != files) {
            for (File file : files) {
                new Directory(file.getPath()).deleteRecursively();
            }
        }
        deleteAndRetryOnError();
        if (exists()) {
            throw new NotDeletedException(getPath());
        }
    }


    public void make() {
        lastCreatedSubDirectory = getPath();
        mkdir();
    }


    public void makeSubDirectory(String subdirectoryName) {
        lastCreatedSubDirectory = getPath() + File.separator + subdirectoryName;
        new File(getPath(), subdirectoryName).mkdirs();
    }


    public String lastCreated() {
        return lastCreatedSubDirectory;
    }


    private void deleteAndRetryOnError() {
        if (!delete()) {
            if (IS_WINDOWS) {
                System.gc();
            }
            waitForAWhile(DELETE_RETRY_SLEEP_MILLIS);
            delete();
        }
    }


    protected static void waitForAWhile(int millisToWait) {
        try {
            Thread.sleep(millisToWait);
        }
        catch (InterruptedException e1) {
            ;
        }
    }


    /**
     * Exception levée lorsqu'un répertoire ne peut pas être supprimé.
     */
    public static class NotDeletedException extends IOException {
        public NotDeletedException(String path) {
            super(path);
        }
    }
}
