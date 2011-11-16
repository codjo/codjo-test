/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.depend;
import net.codjo.test.common.PathUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import jdepend.framework.DependencyConstraint;
import jdepend.framework.JDepend;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
/**
 *
 */
public class Dependency {
    private final JDepend jDepend;
    private DependencyConstraintLoader util;


    Dependency(Class aClass, String classesDirectory)
          throws IOException {
        jDepend = new JDepend();
        File classes = new File(PathUtil.findTargetDirectory(aClass), classesDirectory);
        if (classes.exists()) {
            jDepend.addDirectory(classes.getAbsolutePath());
        }
        util = new DependencyConstraintLoader(aClass);
    }


    public Collection getIgnoredPackage() {
        return jDepend.getFilter().getFilters();
    }


    public void addIgnoredPackage(String packageName) {
        jDepend.getFilter().addPackage(packageName);
    }


    public void clearIgnoredPackage() {
        jDepend.getFilter().getFilters().clear();
    }


    public void assertDependency(String name) throws IOException {
        assertDependency(util.load(name), jDepend.analyze());
    }


    public void assertNoCycle() {
        if (jDepend.containsCycles()) {
            String cycles = new CyclePrinter().getCycles(jDepend.getPackages());
            throw new AssertionFailedError("Dépendances cycliques détectées :\n" + cycles);
        }
    }


    private void assertDependency(DependencyConstraint constraint, Collection packages)
          throws IOException {
        if (constraint.getPackages().isEmpty() && packages.isEmpty()) {
            return;
        }

        String expected = util.toString(constraint.getPackages());
        String actual = util.toString(packages);

        if (!constraint.match(packages)) {
            Assert.assertEquals(expected, actual);
            Assert.fail();
        }
        Assert.assertEquals(expected, actual);
    }


    public JDepend getJDepend() {
        return jDepend;
    }


    private static class CyclePrinter extends jdepend.textui.JDepend {
        public String getCycles(Collection collection) {
            StringWriter writer = new StringWriter();
            setWriter(new PrintWriter(writer));
            super.printCycles(collection);
            return writer.toString();
        }
    }
}
