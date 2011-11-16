/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.flexible;
import java.util.List;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
/**
 * TestCase évolué permettant la combinaison de contraintes.
 *
 * @see <a href="http://a7wj111:8080/agf-lib/docs/agf-maven-agf/xref-test/net.codjo/maven/agf/SqlCommandsTest.html">sample</a>.
 * @see <a href="http://joe.truemesh.com/blog/000511.html">Flexible TestCase</a>
 * @see net.codjo.test.common.flexible.FileConstraints
 */
public abstract class FlexibleTestCase extends MockObjectTestCase {
    protected void assertThat(Object something, Constraint matches) {
        if (!matches.eval(something)) {
            StringBuffer message = new StringBuffer("\nExpected: ");
            matches.describeTo(message);
            message.append("\nbut got : ").append(something).append('\n');
            fail(message.toString());
        }
    }


    protected Constraint exists() {
        return new FileConstraints.Exists();
    }


    protected Constraint isContentEqual(List lines) {
        return new FileConstraints.IsContentEqual(lines);
    }
}
