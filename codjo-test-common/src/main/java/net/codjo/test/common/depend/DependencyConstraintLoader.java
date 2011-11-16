/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.depend;
import net.codjo.util.file.FileUtil;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import jdepend.framework.DependencyConstraint;
import jdepend.framework.JavaPackage;
/**
 *
 */
class DependencyConstraintLoader {
    private static final String DEPENDS_UPON = "->";
    private static final String COMMENT = "//";
    private Class aClass;


    DependencyConstraintLoader(Class aClass) {
        this.aClass = aClass;
    }


    public DependencyConstraint load(String dependencyFile)
          throws IOException {
        DependencyConstraint constraint = new DependencyConstraint();

        String result = loadDependencyText(dependencyFile);

        parse(constraint, result);

        return constraint;
    }


    private void parse(DependencyConstraint constraint, String result)
          throws IOException {
        LineNumberReader reader = new LineNumberReader(new StringReader(result));
        String line = reader.readLine();
        JavaPackage lastRootPackage = null;

        while (line != null) {
            line = line.trim();

            if (line.startsWith(DEPENDS_UPON)) {
                if (lastRootPackage == null) {
                    throw new IllegalArgumentException("[Ligne " + reader.getLineNumber()
                                                       + "] Syntaxe incorrecte : Pas de package racine pour la dépendance");
                }
                String name = line.substring(DEPENDS_UPON.length()).trim();
                lastRootPackage.dependsUpon(constraint.addPackage(name));
            }
            else if (line.length() != 0 && !line.startsWith(COMMENT)) {
                lastRootPackage = constraint.addPackage(line);
            }
            line = reader.readLine();
        }
    }


    private String loadDependencyText(String dependencyFile)
          throws IOException {
        Reader srcReader =
              new InputStreamReader(aClass.getResourceAsStream(dependencyFile));
        try {
            return FileUtil.loadContent(srcReader);
        }
        finally {
            srcReader.close();
        }
    }


    public String toString(Collection<JavaPackage> packages) throws IOException {
        StringWriter expected = new StringWriter();
        printDependencies(expected, packages);
        return expected.toString();
    }


    private void printDependencies(Writer writer, Collection<JavaPackage> analyzedPackages)
          throws IOException {
        for (JavaPackage javaPackage : sort(analyzedPackages)) {
            writer.write(javaPackage.getName());
            writer.write("\n");
            printRelated(writer, DEPENDS_UPON, sort(javaPackage.getEfferents()));
        }
    }


    private Collection<JavaPackage> sort(Collection<JavaPackage> packages) {
        List<JavaPackage> sortedList = new ArrayList<JavaPackage>(packages);
        Collections.sort(sortedList, new JavaPackageComparator());
        return sortedList;
    }


    private void printRelated(Writer writer, String dependencySign,
                              Collection<JavaPackage> dependencies) throws IOException {
        for (JavaPackage afferent : dependencies) {
            writer.write("\t" + dependencySign + " " + afferent.getName() + "\n");
        }
    }


    private static class JavaPackageComparator implements Comparator<JavaPackage> {

        public int compare(JavaPackage o1, JavaPackage o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
