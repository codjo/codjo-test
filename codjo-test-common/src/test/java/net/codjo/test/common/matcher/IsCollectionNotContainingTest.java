package net.codjo.test.common.matcher;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
/**
 *
 */
public class IsCollectionNotContainingTest {
    private List<String> list;


    @Before
    public void setUp() {
        list = Arrays.asList("A", "B");
    }


    @Test
    public void testWithElement() {
        try {
            assertThat(list, IsCollectionNotContaining.hasNoItem(JUnitMatchers.equalTo("A")));
            fail();
        }
        catch (AssertionError e) {
            //OK
        }

        try {
            assertThat(list, IsCollectionNotContaining.hasNoItem(JUnitMatchers.equalTo("B")));
            fail();
        }
        catch (AssertionError e) {
            //OK
        }
    }


    @Test
    public void testWithoutElement() {
        assertThat(list, IsCollectionNotContaining.hasNoItem(JUnitMatchers.equalTo("Z")));
    }
}
