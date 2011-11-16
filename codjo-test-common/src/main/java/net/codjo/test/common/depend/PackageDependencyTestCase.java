/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.depend;
import java.io.IOException;
import junit.framework.TestCase;
/**
 * TestCase permettant de tester les dépendances entre package. <p><b>Exemple</b>:</p> <p> <u>Testcase</u>
 * <pre>
 * private static class MyTestCase extends PackageDependencyTestCase {
 *    public void test_dependency() throws Exception {
 *       Dependency dependency = createDependency();
 *       dependency.assertDependency("dependency.txt");
 *       dependency.assertNoCycle();
 *    }
 *    public void test_dependencyTest() throws Exception {
 *       Dependency dependency = createTestDependency();
 *       dependency.assertDependency("dependencyTest.txt");
 *       dependency.assertNoCycle();
 *    }
 * }</pre>
 * <u>Fichier texte (dependency.txt)</u>
 * <pre>
 * // Couche agent
 * net.codjo.agent
 *    -> jade.core
 *    -> jade.fipa
 * net.codjo.agent.jdbc
 *    -> jade.jdbc
 * </pre>
 * </p>
 *
 * @noinspection JUnitTestCaseInProductSource
 */
public abstract class PackageDependencyTestCase extends TestCase {
    protected Dependency createDependency() throws IOException {
        return createDependency("classes");
    }


    protected Dependency createTestDependency() throws IOException {
        Dependency dependency = createDependency("test-classes");
        dependency.addIgnoredPackage("junit.framework");
        dependency.addIgnoredPackage("org.junit");
        dependency.addIgnoredPackage("org.hamcrest");
        dependency.addIgnoredPackage("net.codjo.test.common.depend");
        return dependency;
    }


    private Dependency createDependency(String classesDirectory)
          throws IOException {
        Dependency dependency = new Dependency(getClass(), classesDirectory);

        // Filtre les package java et log4j
        dependency.addIgnoredPackage("java");
        dependency.addIgnoredPackage("javax");
        dependency.addIgnoredPackage("org.apache.log4j");
        return dependency;
    }
}
