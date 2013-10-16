package net.codjo.test.common.matcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

/**
 * This is the opposite function of {@link org.junit.internal.matchers.IsCollectionContaining}.
 */
public class IsCollectionNotContaining<T> extends TypeSafeMatcher<Iterable<T>> {
    private final Matcher<? extends T> elementMatcher;


    public IsCollectionNotContaining(Matcher<? extends T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }


    /**
     * @return false if we find an element in <code>collection</code> that matches, otherwise returns true.
     */
    @Override
    public boolean matchesSafely(Iterable<T> collection) {
        for (T item : collection) {
            if (elementMatcher.matches(item)) {
                return false;
            }
        }
        return true;
    }


    public void describeTo(Description description) {
        description
              .appendText("a collection not containing ")
              .appendDescriptionOf(elementMatcher);
    }


    @Factory
    public static <T> Matcher<Iterable<T>> hasNoItem(Matcher<? extends T> elementMatcher) {
        return new IsCollectionNotContaining<T>(elementMatcher);
    }
}
