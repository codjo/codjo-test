/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.depend;
import java.util.Collection;
import jdepend.framework.PackageFilter;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
/**
 * Classe de test de {@link Dependency}.
 */
public class DependencyTest extends TestCase {
    private Dependency dependency;


    public void test_ignoredPackage() throws Exception {
        Collection ignored = dependency.getIgnoredPackage();
        assertEquals(0, ignored.size());

        dependency.addIgnoredPackage("junit");
        assertEquals(1, ignored.size());
        assertEquals("[junit]", ignored.toString());

        dependency.clearIgnoredPackage();
        assertEquals(0, ignored.size());
    }


    public void test_analyse() throws Exception {
        setFilterForTest();
        dependency.assertDependency("DependencyTest_ok.txt");
        dependency.assertNoCycle();
    }


    public void test_analyse_noDependencies() throws Exception {
        dependency.getJDepend().setFilter(new PackageFilter() {
            @Override
            public boolean accept(String string) {
                return false;
            }
        });
        dependency.assertDependency("DependencyTest_empty.txt");
        dependency.assertNoCycle();
    }


    public void test_analyse_dependencyFailure() throws Exception {
        setFilterForTest();
        try {
            dependency.assertDependency("DependencyTest_bad.txt");
            throw new Error("should have failed");
        }
        catch (AssertionFailedError e) {
        }
    }


    @Override
    protected void setUp() throws Exception {
        dependency = new Dependency(DependencyTest.class, "classes");
    }


    private void setFilterForTest() {
        dependency.getJDepend().setFilter(new PackageFilter() {
            @Override
            public boolean accept(String string) {
                return "net.codjo.test.common.depend".equals(string) || string.startsWith("junit");
            }
        });
    }
}
