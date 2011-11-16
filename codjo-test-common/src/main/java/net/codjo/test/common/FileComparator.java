/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
/**
 * Compare deux fichiers.
 */
public class FileComparator {
    private static final Logger LOGGER = Logger.getLogger(FileComparator.class);

    private char joker = '*';


    /**
     * Constructeur complet
     *
     * @param joker Caractère générique.
     *
     * @throws IllegalStateException caractère générique trop long
     */
    public FileComparator(String joker) {
        if (joker.length() != 1) {
            throw new IllegalStateException("Le caractère générique >" + joker + "< est trop long");
        }

        this.joker = joker.charAt(0);
    }


    /**
     * Comparaison de deux fichiers.
     */
    public boolean equals(File first, File second) throws IOException {
        FileReader firstReader = new FileReader(first);
        FileReader secondReader = new FileReader(second);
        try {
            return equals(firstReader, secondReader);
        }
        finally {
            firstReader.close();
            secondReader.close();
        }
    }


    public boolean equalsNotOrdered(File first, File second) throws IOException {
        FileReader firstReader = new FileReader(first);
        FileReader secondReader = new FileReader(second);
        try {
            return equalsNotOrdered(firstReader, secondReader);
        }
        finally {
            firstReader.close();
            secondReader.close();
        }
    }


    public boolean equalsNotOrdered(Reader firstReader, Reader secondReader) throws IOException {
        BufferedReader readerA = new BufferedReader(firstReader);
        BufferedReader readerB = new BufferedReader(secondReader);
        List<String> firstList = loadFile(readerA);
        List<String> secondList = loadFile(readerB);
        List<String> firstListBackUp = new ArrayList<String>(firstList);
        long nbLineInB = secondList.size();

        firstList.removeAll(secondList);
        secondList.removeAll(firstListBackUp);

        boolean equals = firstList.size() == 0 && secondList.size() == 0;
        if (!equals) {
            LOGGER.info("#############################################");
            LOGGER.info("## nombre de lignes dans A : " + firstListBackUp.size());
            LOGGER.info("## nombre de lignes dans B : " + nbLineInB);
            LOGGER.info("## nombre de lignes communes : " + (nbLineInB - secondList.size()));
            LOGGER.info("#############################################");
            LOGGER.info("->" + firstList.size() + " Ligne(s) présente(s) dans A mais pas dans B");
            displayList("A>", firstList);
            LOGGER.info("---------------------------------------------");
            LOGGER.info("->" + secondList.size() + " Ligne(s) présente(s) dans B mais pas dans A");
            displayList("B>", secondList);
        }

        return equals;
    }


    public boolean equals(Reader firstReader, Reader secondReader) throws IOException {
        BufferedReader readerA = new BufferedReader(firstReader);
        BufferedReader readerB = new BufferedReader(secondReader);
        String expectedLine = readerA.readLine();
        String actualLine = readerB.readLine();
        int lineNumber = 1;
        while (expectedLine != null && actualLine != null) {
            int errorCol = AssertUtil.equalsRow(expectedLine, actualLine, joker);
            if (errorCol != -1) {
                LOGGER.info("----------- Erreur de comparaison ");
                LOGGER.info("ligne = " + lineNumber);
                LOGGER.info("colonne = " + errorCol);
                LOGGER.info("Attendu >\"" + expectedLine + "\"");
                LOGGER.info("Actuel  >\"" + actualLine + "\"");
                StringBuilder message = new StringBuilder("Ecart   >_");
                for (int i = 0; i < errorCol; i++) {
                    message.append("_");
                }
                LOGGER.info(message.append("*"));

                return false;
            }
            expectedLine = readerA.readLine();
            actualLine = readerB.readLine();
            lineNumber++;
        }

        if ((expectedLine == null && actualLine == null) || (expectedLine != null && expectedLine.equals(
              actualLine))) {
            return true;
        }

        LOGGER.info("----------- Erreur de comparaison ");
        LOGGER.info("lineA : " + expectedLine);
        LOGGER.info("lineB : " + actualLine);
        return false;
    }


    private List<String> loadFile(BufferedReader reader) throws IOException {
        List<String> list = new ArrayList<String>();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }


    private void displayList(String label, List<String> firstList) {
        for (String aFirstList : firstList) {
            LOGGER.info(label + " " + aFirstList);
        }
    }


    char getJoker() {
        return joker;
    }
}
