/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import java.io.StringReader;
import junit.framework.TestCase;
/**
 * Classe de test de {@link FileComparator}.
 */
public class FileComparatorTest extends TestCase {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private FileComparator comparator;


    public void test_isEqual() throws Exception {
        StringReader reader = new StringReader("le petit chien est grand");
        StringReader model = new StringReader("le petit chien est grand");

        assertTrue(comparator.equals(model, reader));
    }


    public void test_isEqual_Bug() throws Exception {
        StringReader actual =
              new StringReader("9557VL 2001-10-290000000000102.000000EUR20011120.0000AURORE\rff");
        StringReader model =
              new StringReader("9557VL 2001-10-290000000000102.000000EUR*************AURORE" + LINE_SEPARATOR
                               + "ff");

        assertTrue(comparator.equals(model, actual));
    }


    public void test_isEqual_rowNotSameSize() throws Exception {
        StringReader current = new StringReader("9557VL ");
        StringReader expected = new StringReader("9557VL 2001-10-29000");

        assertTrue(!comparator.equals(expected, current));

        current = new StringReader("9557VL ");
        expected = new StringReader("9557VL 2001-10-29000");

        assertTrue(!comparator.equals(current, expected));
    }


    public void test_isEqual_MultiLine() throws Exception {
        StringReader actual = new StringReader("le petit \nchien est grand");
        StringReader model = new StringReader("le petit " + LINE_SEPARATOR + "chien est grand");

        assertTrue(comparator.equals(model, actual));
    }


    public void test_isEqual_MultiLine_NotOrdered()
          throws Exception {
        StringReader actual = new StringReader("le petit \nchien est grand");
        StringReader model = new StringReader("chien est grand" + LINE_SEPARATOR + "le petit ");

        assertTrue(comparator.equalsNotOrdered(model, actual));
    }


    public void test_isEqual_MultiLine_NotOrdered_Failure()
          throws Exception {
        StringReader actual = new StringReader("le petit \nchien est pas grand");
        StringReader model = new StringReader("chien est grand" + LINE_SEPARATOR + "le petit ");

        assertTrue(!comparator.equalsNotOrdered(model, actual));
    }


    public void test_isEqual_false() throws Exception {
        StringReader actual = new StringReader("le petit chien est grand");
        StringReader model = new StringReader("les petit chien est grand");
        assertTrue("Différence : ", !comparator.equals(model, actual));
    }


    public void test_isEqual_withJOKER() throws Exception {
        StringReader actualA = new StringReader("le petit chien est grand");
        StringReader actualB = new StringReader("le petit doggy est grand");
        StringReader model = new StringReader("le petit ***** est grand");

        assertTrue("equals(model, actualA)", comparator.equals(model, actualA));
        model.reset();
        actualA.reset();
        assertTrue("equals(actualA, model)", comparator.equals(actualA, model));
        model.reset();
        actualB.reset();
        assertTrue("equals(model, actualB)", comparator.equals(model, actualB));
        model.reset();
        actualB.reset();
        assertTrue("equals(actualB, model)", comparator.equals(actualB, model));
    }


    public void test_isEqual_withJOKER_false() throws Exception {
        StringReader actual = new StringReader("le petit chienX est grand");
        StringReader model = new StringReader("le petit ***** est grand");

        assertTrue(!comparator.equals(model, actual));
    }


    @Override
    protected void setUp() throws Exception {
        comparator = new FileComparator("*");
    }
}
