package net.codjo.test.common.matcher;
import java.net.URL;
import net.codjo.test.common.IsXsdCompliant;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.internal.ArrayComparisonFailure;
import org.junit.internal.matchers.CombinableMatcher;
import org.junit.internal.matchers.Each;
import org.junit.internal.matchers.IsCollectionContaining;
import org.junit.internal.matchers.StringContains;
/**
 *
 */
public class JUnitMatchers {
    private JUnitMatchers() {
    }


    public static <T> void assertThat(T actual, Matcher<T> matcher) {
        Assert.assertThat(actual, matcher);
    }


    static public void fail(String message) {
        Assert.fail(message);
    }


    static public void fail() {
        Assert.fail();
    }


    public static <T> void assumeThat(T value, Matcher<T> assumption) {
        Assume.assumeThat(value, assumption);
    }


    public static void assumeNotNull(Object... objects) {
        Assume.assumeNotNull(objects);
    }


    public static void assumeNoException(Throwable throwable) {
        Assume.assumeNoException(throwable);
    }


    public static void assumeTrue(boolean expected) {
        Assume.assumeTrue(expected);
    }


    public static IsXsdCompliant xsdCompliantWith(String xsdContent) {
        return IsXsdCompliant.xsdCompliantWith(xsdContent);
    }


    public static IsXsdCompliant xsdCompliantWith(URL url) {
        return IsXsdCompliant.xsdCompliantWith(url);
    }


    public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItem(T element) {
        return IsCollectionContaining.hasItem(element);
    }


    public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItem(org.hamcrest.Matcher<? extends T> elementMatcher) {
        return IsCollectionContaining.hasItem(elementMatcher);
    }


    public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(org.hamcrest.Matcher<? extends T>... elementMatchers) {
        return IsCollectionContaining.hasItems(elementMatchers);
    }


    public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> hasItems(T... elements) {
        return IsCollectionContaining.hasItems(elements);
    }


    public static org.hamcrest.Matcher<java.lang.String> containsString(java.lang.String substring) {
        return StringContains.containsString(substring);
    }


    public static <T> Matcher<Iterable<T>> each(final Matcher<T> individual) {
        return Each.each(individual);
    }


    public static <T> CombinableMatcher<T> both(Matcher<T> matcher) {
        return new CombinableMatcher<T>(matcher);
    }


    public static <T> CombinableMatcher<T> either(Matcher<T> matcher) {
        return new CombinableMatcher<T>(matcher);
    }


    public static void assertArrayEquals(String message, Object[] expecteds, Object[] actuals)
          throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }


    public static void assertArrayEquals(Object[] expecteds, Object[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }


    public static void assertArrayEquals(String message, byte[] expecteds, byte[] actuals)
          throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }


    public static void assertArrayEquals(byte[] expecteds, byte[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }


    public static void assertArrayEquals(String message, char[] expecteds, char[] actuals)
          throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }


    public static void assertArrayEquals(char[] expecteds, char[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }


    public static void assertArrayEquals(String message, short[] expecteds, short[] actuals)
          throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }


    public static void assertArrayEquals(short[] expecteds, short[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }


    public static void assertArrayEquals(String message, int[] expecteds, int[] actuals)
          throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }


    public static void assertArrayEquals(int[] expecteds, int[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }


    public static void assertArrayEquals(String message, long[] expecteds, long[] actuals)
          throws ArrayComparisonFailure {
        Assert.assertArrayEquals(message, expecteds, actuals);
    }


    public static void assertArrayEquals(long[] expecteds, long[] actuals) {
        Assert.assertArrayEquals(expecteds, actuals);
    }


    public static <T> Matcher<T> is(Matcher<T> matcher) {
        return CoreMatchers.is(matcher);
    }


    public static <T> Matcher<T> is(T actual) {
        return CoreMatchers.is(actual);
    }


    public static Matcher<Object> is(Class<?> aClass) {
        return CoreMatchers.is(aClass);
    }


    public static <T> Matcher<T> not(Matcher<T> matcher) {
        return CoreMatchers.not(matcher);
    }


    public static <T> Matcher<T> not(T actual) {
        return CoreMatchers.not(actual);
    }


    public static <T> Matcher<T> equalTo(T actual) {
        return CoreMatchers.equalTo(actual);
    }


    public static Matcher<Object> instanceOf(Class<?> aClass) {
        return CoreMatchers.instanceOf(aClass);
    }


    public static <T> Matcher<T> allOf(Matcher<? extends T>... matchers) {
        return CoreMatchers.allOf(matchers);
    }


    public static <T> Matcher<T> allOf(Iterable<Matcher<? extends T>> iterable) {
        return CoreMatchers.allOf(iterable);
    }


    public static <T> Matcher<T> anyOf(Matcher<? extends T>... matchers) {
        return CoreMatchers.anyOf(matchers);
    }


    public static <T> Matcher<T> anyOf(Iterable<Matcher<? extends T>> iterable) {
        return CoreMatchers.anyOf(iterable);
    }


    public static <T> Matcher<T> sameInstance(T actual) {
        return CoreMatchers.sameInstance(actual);
    }


    public static <T> Matcher<T> anything() {
        return CoreMatchers.anything();
    }


    public static <T> Matcher<T> anything(String string) {
        return CoreMatchers.anything(string);
    }


    public static <T> Matcher<T> any(Class<T> aClass) {
        return CoreMatchers.any(aClass);
    }


    public static <T> Matcher<T> nullValue() {
        return CoreMatchers.nullValue();
    }


    public static <T> Matcher<T> nullValue(Class<T> aClass) {
        return CoreMatchers.nullValue(aClass);
    }


    public static <T> Matcher<T> notNullValue() {
        return CoreMatchers.notNullValue();
    }


    public static <T> Matcher<T> notNullValue(Class<T> aClass) {
        return CoreMatchers.notNullValue(aClass);
    }


    public static <T> Matcher<T> describedAs(String description, Matcher<T> matcher, Object... objects) {
        return CoreMatchers.describedAs(description, matcher, objects);
    }
}
