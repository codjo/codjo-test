/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
/**
 * Classe utilitaire sur les fichiers
 */
public final class FileUtil {
    private FileUtil() {
    }


    @Deprecated
    public static String loadContent(Reader reader) throws IOException {
        return net.codjo.util.file.FileUtil.loadContent(reader);
    }


    @Deprecated
    public static String loadContent(URL url) throws IOException {
        return net.codjo.util.file.FileUtil.loadContent(url);
    }


    @Deprecated
    public static String loadContent(URL url, String charset) throws IOException {
        return net.codjo.util.file.FileUtil.loadContent(url, charset);
    }


    @Deprecated
    public static String loadContent(File file) throws IOException {
        return net.codjo.util.file.FileUtil.loadContent(file);
    }


    @Deprecated
    public static void saveContent(File file, String fileContent) throws IOException {
        net.codjo.util.file.FileUtil.saveContent(file, fileContent);
    }


    @Deprecated
    public static void saveContent(File file, String fileContent, String charset) throws IOException {
        net.codjo.util.file.FileUtil.saveContent(file, fileContent, charset);
    }
}
