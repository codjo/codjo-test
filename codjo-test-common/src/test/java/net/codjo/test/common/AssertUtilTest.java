/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 *
 */
public class AssertUtilTest extends TestCase {
    public void test_assertArraysEquality() throws Exception {
        AssertUtil.assertEquals(null, null);
        AssertUtil.assertEquals(new Object[0], new Object[0]);
        AssertUtil.assertEquals(new String[]{"un", "deux"}, new String[]{"un", "deux"});

        assertEqualsFailure(null, new String[]{"toto"});
        assertEqualsFailure(new String[]{"toto"}, null);
        assertEqualsFailure(new String[]{"un"}, new String[]{"un", "deux"});
        assertEqualsFailure(new String[]{"un", "deux"}, new String[]{"un"});
        assertEqualsFailure(new String[]{"un", "deux"}, new String[]{"deux", "un"});
        assertEqualsFailure(new String[]{"un"}, new String[]{"deux"});
    }


    public void test_assertArraysWithConverterEquality()
          throws Exception {
        AssertUtil.assertEquals(new String[]{"0.1", "0.2"}, new Float[]{new Float(0.1), new Float(0.2)},
                                new Converter() {
                                    public Object convert(Object value) {
                                        return value.toString();
                                    }
                                });

        assertEqualsFailure(new String[]{"0.1", "0.25"}, new Float[]{new Float(0.1), new Float(0.2)},
                            new Converter() {
                                public Object convert(Object value) {
                                    return value.toString();
                                }
                            });
    }


    public void test_assertUnorderedArraysWithConverterEquality() {
        AssertUtil.assertUnorderedEquals(new String[]{"0.1", "0.2"},
                                         new Float[]{new Float(0.2), new Float(0.1)},
                                         new Converter() {
                                             public Object convert(Object value) {
                                                 return value.toString();
                                             }
                                         });

        assertUnorderedEqualsFailure(new String[]{"0.1", "0.25"},
                                     new Float[]{new Float(0.1), new Float(0.2)},
                                     new Converter() {
                                         public Object convert(Object value) {
                                             return value.toString();
                                         }
                                     });
    }


    public void test_assertUnorderedArraysEquality()
          throws Exception {
        AssertUtil.assertUnorderedEquals(null, null);
        AssertUtil.assertUnorderedEquals(new Object[0], new Object[0]);
        AssertUtil.assertUnorderedEquals(new String[]{"un", "deux"}, new String[]{"un", "deux"});
        AssertUtil.assertUnorderedEquals(new String[]{"un", "deux"}, new String[]{"deux", "un"});

        assertUnorderedEqualsFailure(null, new String[]{"toto"});
        assertUnorderedEqualsFailure(new String[]{"toto"}, null);
        assertUnorderedEqualsFailure(new String[]{"un"}, new String[]{"un", "un"});
        assertUnorderedEqualsFailure(new String[]{"un"}, new String[]{"un", "deux"});
        assertUnorderedEqualsFailure(new String[]{"un", "deux"}, new String[]{"un"});
        assertUnorderedEqualsFailure(new String[]{"un", "un"}, new String[]{"un"});
        assertUnorderedEqualsFailure(new String[]{"un"}, new String[]{"deux"});
        assertUnorderedEqualsFailure(new String[]{"un", "deux"}, new String[]{"deux", "un", "trois"});
        assertUnorderedEqualsFailure(new String[]{"un", "deux", "trois"}, new String[]{"deux", "un"});
    }


    public void test_assertResultSetSuccessful() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);

        String[] columnNames = new String[]{"one", "two", "three"};
        when(metaData.getColumnCount()).thenReturn(columnNames.length);
        for (int i = 0; i < columnNames.length; i++) {
            when(metaData.getColumnName(i + 1)).thenReturn(columnNames[i]);
        }

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getMetaData()).thenReturn(metaData);

        when(resultSet.getString("one")).thenReturn("rowOne:valueOne", "rowTwo:valueOne");
        when(resultSet.getString("two")).thenReturn("rowOne:valueTwo", "rowTwo:valueTwo");
        when(resultSet.getString("three")).thenReturn("rowOne:valueThree", "rowTwo:valueThree");

        AssertUtil.assertResultSetRow(new String[][]{
              {"one", "rowOne:valueOne"},
              {"two", "rowOne:valueTwo"},
              {"three", "rowOne:valueThree"}},
                                      resultSet);

        assertTrue(resultSet.next());

        AssertUtil.assertResultSetRow(new String[][]{
              {"one", "rowTwo:valueOne"},
              {"two", "rowTwo:valueTwo"},
              {"three", "rowTwo:valueThree"}},
                                      resultSet);

        assertFalse(resultSet.next());
    }


    public void test_assertResultSetFailure() throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);

        String[] columnNames = new String[]{"one", "two", "three"};
        when(metaData.getColumnCount()).thenReturn(columnNames.length);
        for (int i = 0; i < columnNames.length; i++) {
            when(metaData.getColumnName(i + 1)).thenReturn(columnNames[i]);
        }

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getMetaData()).thenReturn(metaData);

        when(resultSet.getString("one")).thenReturn("rowOne:valueOne", "rowTwo:valueOne");
        when(resultSet.getString("two")).thenReturn("rowOne:valueTwo", "BAD");
        when(resultSet.getString("three")).thenReturn("rowOne:valueThree", "rowTwo:valueThree");

        AssertUtil.assertResultSetRow(new String[][]{
              {"one", "rowOne:valueOne"},
              {"two", "rowOne:valueTwo"},
              {"three", "rowOne:valueThree"}},
                                      resultSet);

        assertTrue(resultSet.next());

        try {
            AssertUtil.assertResultSetRow(new String[][]{
                  {"one", "rowTwo:valueOne"},
                  {"two", "rowTwo:valueTwo"},
                  {"three", "rowTwo:valueThree"}},
                                          resultSet);
            fail();
        }
        catch (AssertionFailedError assertionFailedError) {
            assertEquals("Erreur de comparaison \n"
                         + "Colonne 'two' : expected >rowTwo:valueTwo< but was >BAD<\n",
                         assertionFailedError.getMessage());
        }
    }


    private void assertEqualsFailure(Object[] expected, Object[] actual) {
        try {
            AssertUtil.assertEquals(expected, actual);
            fail("Test failed");
        }
        catch (AssertionFailedError error) {
            assertFalse("Test failed".equals(error.getLocalizedMessage()));
        }
    }


    private void assertEqualsFailure(Object[] expected, Object[] actual, Converter converter) {
        try {
            AssertUtil.assertEquals(expected, actual, converter);
            fail("Test failed");
        }
        catch (AssertionFailedError error) {
            assertFalse("Test failed".equals(error.getLocalizedMessage()));
        }
    }


    private void assertUnorderedEqualsFailure(Object[] expected, Object[] actual) {
        try {
            AssertUtil.assertUnorderedEquals(expected, actual);
            fail("Test failed");
        }
        catch (AssertionFailedError error) {
            assertFalse("Test failed".equals(error.getLocalizedMessage()));
        }
    }


    private void assertUnorderedEqualsFailure(Object[] expected, Object[] actual, Converter converter) {
        try {
            AssertUtil.assertUnorderedEquals(expected, actual, converter);
            fail("Test failed");
        }
        catch (AssertionFailedError error) {
            assertFalse("Test failed".equals(error.getLocalizedMessage()));
        }
    }


    public void test_equalsRow() throws Exception {
        final char jokerChar = '*';
        assertEquals(-1, AssertUtil.equalsRow("A", "A", jokerChar));
        assertEquals(-1, AssertUtil.equalsRow("CABAF", "*A*A*", jokerChar));
        assertEquals(-1, AssertUtil.equalsRow("", "", jokerChar));
        assertEquals(0, AssertUtil.equalsRow("", "A", jokerChar));
        assertEquals(0, AssertUtil.equalsRow("", "*", jokerChar));
        assertEquals(1, AssertUtil.equalsRow("A", "A*", jokerChar));
        assertEquals(2, AssertUtil.equalsRow("AA", "A*B", jokerChar));
    }
}
