/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.flexible;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.jmock.core.Constraint;
/**
 * Contraintes pour la gestion de fichier en rapport avec {@link
 * net.codjo.test.common.flexible.FlexibleTestCase}.
 */
final class FileConstraints {
    private FileConstraints() {}

    public static class Exists implements Constraint {
        public boolean eval(Object object) {
            boolean result = false;
            if (object instanceof File) {
                File file = (File)object;
                result = file.exists();
            }
            return result;
        }


        public StringBuffer describeTo(StringBuffer buffer) {
            return buffer.append("an existing file");
        }
    }


    public static class IsContentEqual implements Constraint {
        private List attemptedLines;
        private String problematicFileLine;
        private int problematicFileIndex;

        public IsContentEqual(List lines) {
            attemptedLines = lines;
        }

        public boolean eval(Object object) {
            setProblematicInformations("", 0);
            if (!(object instanceof File)) {
                return false;
            }
            BufferedReader bufferedReader;
            try {
                File file = (File)object;
                FileReader fileReader = new FileReader(file.getPath());
                bufferedReader = new BufferedReader(fileReader);
                String fileLine = bufferedReader.readLine();
                int index = 0;
                while (fileLine != null) {
                    if (index >= attemptedLines.size()) {
                        setProblematicInformations("<NONE>", index);
                        tryToClose(bufferedReader);
                        return false;
                    }
                    String attemptedLine = (String)attemptedLines.get(index);
                    if (!fileLine.equals(attemptedLine)) {
                        setProblematicInformations(attemptedLine, index);
                        tryToClose(bufferedReader);
                        return false;
                    }
                    fileLine = bufferedReader.readLine();
                    index++;
                }
                tryToClose(bufferedReader);
                if (index != attemptedLines.size()) {
                    setProblematicInformations("<NONE>", index);
                    tryToClose(bufferedReader);
                    return false;
                }
            }
            catch (IOException e) {
                return false;
            }

            return true;
        }


        private void setProblematicInformations(String attemptedLine, int lineIndex) {
            problematicFileLine = attemptedLine;
            problematicFileIndex = lineIndex;
        }


        private void tryToClose(BufferedReader bufferedReader) {
            if (null == bufferedReader) {
                return;
            }
            try {
                bufferedReader.close();
            }
            catch (IOException e) {
                ;
            }
        }


        public StringBuffer describeTo(StringBuffer buffer) {
            return buffer.append("a file containing as ").append(problematicFileIndex)
                         .append("th line the following one [").append(problematicFileLine).append("]");
        }
    }
}
