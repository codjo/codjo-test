/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.depend;
import java.util.Collection;
import java.util.Iterator;
import jdepend.framework.DependencyConstraint;
import jdepend.framework.JavaPackage;
import junit.framework.TestCase;
/**
 * Classe de test de {@link DependencyConstraintLoader}.
 */
public class DependencyConstraintLoaderTest extends TestCase {
    private DependencyConstraintLoader loader;

    public void test_onePackage() throws Exception {
        DependencyConstraint constraint = loader.load("DependencyConstraintLoaderTest_onePackage.txt");

        assertEquals(1, constraint.getPackages().size());

        JavaPackage agent = getPackage("net.codjo.agent", constraint.getPackages());
        assertNotNull(agent);
    }


    public void test_twoPackages() throws Exception {
        DependencyConstraint constraint = loader.load("DependencyConstraintLoaderTest_twoPackages.txt");

        assertEquals(2, constraint.getPackages().size());

        assertNotNull(getPackage("net.codjo.agent", constraint.getPackages()));
        assertNotNull(getPackage("net.codjo.agent.jdbc", constraint.getPackages()));
    }


    public void test_withDependencies() throws Exception {
        DependencyConstraint constraint = loader.load("DependencyConstraintLoaderTest_withDependencies.txt");

        JavaPackage agent = getPackage("net.codjo.agent", constraint.getPackages());
        assertNotNull(agent);
        assertNotNull(getPackage("jade.core", agent.getEfferents()));
        assertNotNull(getPackage("jade.fipa", agent.getEfferents()));

        JavaPackage jdbc = getPackage("net.codjo.agent.jdbc", constraint.getPackages());
        assertNotNull(jdbc);
        assertNotNull(getPackage("jade.jdbc", jdbc.getEfferents()));
    }


    public void test_badSyntax() throws Exception {
        try {
            loader.load("DependencyConstraintLoaderTest_badSyntax.txt");
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("[Ligne 1] Syntaxe incorrecte : Pas de package racine pour la dépendance",
                ex.getMessage());
        }
    }


    public void test_dependeciesToString() throws Exception {
        DependencyConstraint constraint = new DependencyConstraint();

        JavaPackage jdbc = constraint.addPackage("net.codjo.agent.jdbc");
        jdbc.dependsUpon(constraint.addPackage("jade.core"));

        JavaPackage agent = constraint.addPackage("net.codjo.agent");
        agent.dependsUpon(constraint.addPackage("jade.core"));
        agent.dependsUpon(constraint.addPackage("jade.aa"));

        String expected = "jade.aa\n";
        expected += "jade.core\n";
        expected += "net.codjo.agent\n\t-> jade.aa\n\t-> jade.core\n";
        expected += "net.codjo.agent.jdbc\n\t-> jade.core\n";
        assertEquals(expected, loader.toString(constraint.getPackages()));
    }


    @Override
    protected void setUp() throws Exception {
        loader = new DependencyConstraintLoader(DependencyConstraintLoaderTest.class);
    }


    private JavaPackage getPackage(String name, Collection packages) {
        for (Iterator iter = packages.iterator(); iter.hasNext();) {
            JavaPackage aPackage = (JavaPackage)iter.next();
            if (name.equals(aPackage.getName())) {
                return aPackage;
            }
        }
        return null;
    }
}
