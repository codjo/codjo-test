package net.codjo.test.common.matcher;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.SubstringMatcher;
/**
 *
 */
public class StringMatches extends SubstringMatcher {
    public StringMatches(String pattern) {
        super(pattern);
    }


    /**
     * @return true if the string matches.
     */
    @Override
    protected boolean evalSubstringOf(String s) {
        s = s.replaceAll(System.getProperty("line.separator"), " ");
        return s.matches(substring);
    }


    @Override
    protected String relationship() {
        return "matching";
    }


    @Factory
    public static Matcher<String> matches(String pattern) {
        return new StringMatches(pattern);
    }
}
