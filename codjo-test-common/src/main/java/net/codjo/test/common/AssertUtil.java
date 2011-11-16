/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
/**
 *
 */
public final class AssertUtil {
    public static final Integer[] NOT_ALLOWED_CHAR = {10, 13};


    private AssertUtil() {
    }


    public static void assertEquals(Object[] expected, Object[] actual) {
        assertEquals(expected, actual, Converter.IDENTITY);
    }


    public static void assertEquals(Object[] expected, Object[] actual, Converter converter) {
        if (expected == null || actual == null) {
            Assert.assertEquals(expected, actual);
            return;
        }
        Assert.assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], converter.convert(actual[i]));
        }
    }


    public static void assertUnorderedEquals(Object[] expected, Object[] actual) {
        assertUnorderedEquals(null, expected, actual, Converter.IDENTITY);
    }


    public static void assertUnorderedEquals(String message,
                                             Object[] expected, Object[] actual) {
        assertUnorderedEquals(message, expected, actual, Converter.IDENTITY);
    }


    public static void assertUnorderedEquals(Object[] expected, Object[] actual, Converter converter) {
        assertUnorderedEquals(null, expected, actual, converter);
    }


    public static void assertUnorderedEquals(String message,
                                             Object[] expected, Object[] actual, Converter converter) {
        if (expected == null || actual == null) {
            Assert.assertEquals(message, expected, actual);
            return;
        }
        if (expected.length == 1 && actual.length == 1) {
            if (expected[0] instanceof String && actual[0] instanceof String) {
                Assert.assertEquals(message, (String)expected[0], (String)actual[0]);
            }
            else {
                Assert.assertEquals(message, expected[0], actual[0]);
            }
        }

        List expectedList = Arrays.asList(expected);
        List<Object> actualList = new ArrayList<Object>();
        for (Object value : actual) {
            actualList.add(converter.convert(value));
        }

        for (Object expectedItem : expectedList) {
            if (!actualList.contains(expectedItem)) {
                fail(message, expectedItem, actual, converter);
            }
            actualList.remove(expectedItem);
        }

        Assert.assertEquals(message, expected.length, actual.length);
    }


    public static void assertResultSetRow(String[][] expected, ResultSet resultSet) throws SQLException {
        Assert.assertEquals("Nombre de colonnes attendues :", expected.length,
                            resultSet.getMetaData().getColumnCount());

        StringBuilder error = new StringBuilder();
        StringBuilder actual = new StringBuilder("assertResultSetRow(new String[][]{\n");
        int index = 1;
        for (String[] fields : expected) {
            String expectedFieldValue = (fields.length == 1 ? null : fields[1]);
            String expectedColumnName = fields[0];

            String realColumnName = resultSet.getMetaData().getColumnName(index);
            String realFieldValue = resultSet.getString(realColumnName);

            actual.append(" {\"").append(realColumnName)
                  .append("\", ")
                  .append((realFieldValue == null ? "null" : "\"" + realFieldValue + "\""))
                  .append("},\n");

            if (!(expectedColumnName != null && expectedColumnName.equals(realColumnName))) {
                error.append("Column name expected >").append(expectedColumnName).append("< but was >")
                      .append(realColumnName).append("<\n");
            }
            else if (!(expectedFieldValue == null && realFieldValue == null
                       || (expectedFieldValue != null && expectedFieldValue.equals(realFieldValue)))) {
                error.append("Colonne '").append(expectedColumnName).append("' : ")
                      .append("expected >").append(expectedFieldValue).append("< ")
                      .append("but was >").append(realFieldValue).append("<\n");
            }
            index++;
        }
        actual.append("}, resultSet);");
        if (error.length() > 0) {
            System.out.println("Actual complet :\n----------------\n" + actual);
        }
        Assert.assertFalse("Erreur de comparaison \n" + error, error.length() > 0);
    }


    private static void fail(String message,
                             Object expectedItem, Object[] actual, Converter converter) {
        StringBuilder builder = new StringBuilder();
        if (message != null) {
            builder.append(message);
        }
        builder.append("The value '").append(expectedItem).append("' is ");

        if (actual.length > 10) {
            builder.append("absent");
        }
        else {
            builder.append("not in [");
            for (int i = 0; i < actual.length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(converter.convert(actual[i]));
            }
            builder.append("]");
        }
        Assert.fail(builder.toString());
    }


    /**
     * Construit une liste des caractères non permis pour la comparaison.
     *
     * @return Liste de caractères.
     */
    static List<Integer> builtNotAllowedCharList() {
        List<Integer> charList = new ArrayList<Integer>();
        for (Integer aChar : NOT_ALLOWED_CHAR) {
            charList.add(aChar);
        }
        return charList;
    }


    /**
     * @return Position du caractère qui diffère entre les deux chaînes passées en paramètre
     */
    public static int equalsRow(String expected, String actual, char jokerChar) {
        List<Integer> notAllowedChars = builtNotAllowedCharList();
        int charNumber = 0;
        while (charNumber < expected.length() && charNumber < actual.length()) {
            if (((expected.charAt(charNumber) != jokerChar) && (actual.charAt(charNumber) != jokerChar))
                && (expected.charAt(charNumber) != actual.charAt(charNumber))
                && (!notAllowedChars.contains(new Integer(expected.charAt(charNumber))))
                && (!notAllowedChars.contains(new Integer(actual.charAt(charNumber))))) {
                return charNumber;
            }
            charNumber++;
        }
        if (expected.length() != actual.length()) {
            return charNumber;
        }
        return -1;
    }
}
