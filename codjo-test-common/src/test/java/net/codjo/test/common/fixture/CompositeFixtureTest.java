/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.fixture;
import net.codjo.test.common.LogString;
import junit.framework.Assert;
import junit.framework.TestCase;
/**
 * Classe de test de {@link net.codjo.test.common.fixture.CompositeFixture}.
 */
public class CompositeFixtureTest extends TestCase {
    private CompositeFixture composite;

    public void test_addFixtureUsingConstructor()
            throws Exception {
        FixtureMock mock = new FixtureMock();
        composite = new CompositeFixture(mock);
        composite.doSetUp();
        mock.log.assertContent("doSetUp()");
        mock.log.clear();

        composite = new CompositeFixture(mock, mock);
        composite.doSetUp();
        mock.log.assertContent("doSetUp(), doSetUp()");
        mock.log.clear();

        composite = new CompositeFixture(mock, mock, mock);
        composite.doSetUp();
        mock.log.assertContent("doSetUp(), doSetUp(), doSetUp()");
    }


    public void test_addFixture_oneFixture() throws Exception {
        FixtureMock mock = new FixtureMock();
        composite.addFixture(mock);

        composite.doSetUp();
        composite.doTearDown();

        Assert.assertEquals("doSetUp(), doTearDown()", mock.log.getContent());
    }


    public void test_addFixture_moreFixtures() throws Exception {
        LogString log = new LogString();
        FixtureMock mockA = new FixtureMock(new LogString("mockA", log));
        FixtureMock mockB = new FixtureMock(new LogString("mockB", log));

        composite.addFixture(mockA);
        composite.addFixture(mockB);

        composite.doSetUp();
        composite.doTearDown();

        assertEquals("mockA.doSetUp(), mockB.doSetUp(), mockA.doTearDown(), mockB.doTearDown()",
            log.getContent());
    }


    public void test_doSetup_failed() throws Exception {
        LogString log = new LogString();
        composite.addFixture(new CrashFixtureMock(new LogString("mockA", log), CrashFixtureMock.SETUP_FAILURE));
        composite.addFixture(new FixtureMock(new LogString("mockB", log)));

        try {
            composite.doSetUp();
            fail("Echec causé par mockA");
        }
        catch (IllegalArgumentException ex) {
            ; // Ok
        }

        assertEquals("mockA.doSetUp()", log.getContent());
    }


    public void test_doSetup_secondFixtureFailed()
            throws Exception {
        LogString log = new LogString();
        composite.addFixture(new FixtureMock(new LogString("mockA", log)));
        composite.addFixture(new CrashFixtureMock(new LogString("mockB", log), CrashFixtureMock.SETUP_FAILURE));

        try {
            composite.doSetUp();
            fail("Echec causé par mockB");
        }
        catch (IllegalArgumentException ex) {
            ; // Ok
        }

        assertEquals("mockA.doSetUp(), mockB.doSetUp(), mockA.doTearDown()", log.getContent());
    }


    public void test_getFixture() throws Exception {
        FixtureMock fixtureMock = new FixtureMock();
        composite.addFixture(new CrashFixtureMock(new LogString(), 0));
        composite.addFixture(fixtureMock);
        assertSame(fixtureMock, composite.get(FixtureMock.class));
    }


    public void test_getFixture_noMatch() throws Exception {
        composite.addFixture(new FixtureMock());
        assertNull(composite.get(CrashFixtureMock.class));
    }


    public void test_getFixture_mulitpleResult() throws Exception {
        FixtureMock first = new FixtureMock();
        FixtureMock second = new FixtureMock();
        composite.addFixture(first);
        composite.addFixture(second);

        try {
            composite.get(FixtureMock.class);

            fail("Appel à la méthode est ambigue (2 de même type)");
        }
        catch (CompositeFixture.AmbiguousFixtureResolutionException error) {
            ; // Ok
        }
    }


    public void test_getFixture_onlysubClass() throws Exception {
        FixtureMock subClassOfMock = new FixtureMock() {}
        ;

        composite.addFixture(subClassOfMock);
        assertSame(subClassOfMock, composite.get(FixtureMock.class));
    }


    public void test_getFixture_subClassAndRealInstance()
            throws Exception {
        FixtureMock realInstance = new FixtureMock();
        composite.addFixture(realInstance);
        composite.addFixture(new FixtureMock() {}
        );
        assertSame(realInstance, composite.get(FixtureMock.class));
    }


    public void test_getFixture_subClassAnd2RealInstance()
            throws Exception {
        composite.addFixture(new FixtureMock());
        composite.addFixture(new FixtureMock());
        composite.addFixture(new FixtureMock() {}
        );

        try {
            composite.get(FixtureMock.class);

            fail("Appel à la méthode est ambigue (2 de même type)");
        }
        catch (CompositeFixture.AmbiguousFixtureResolutionException error) {
            ; // Ok
        }
    }


    public void test_doTearDown_noSetup() throws Exception {
        LogString log = new LogString();
        composite.addFixture(new FixtureMock(new LogString("mockA", log)));

        composite.doTearDown();

        assertEquals("", log.getContent());
    }


    public void test_doTearDown_failed() throws Exception {
        LogString log = new LogString();
        composite.addFixture(new CrashFixtureMock(new LogString("mockA", log),
                CrashFixtureMock.TEARDOWN_FAILURE));
        composite.addFixture(new FixtureMock(new LogString("mockB", log)));

        composite.doSetUp();
        log.clear();
        try {
            composite.doTearDown();
            fail("Echec causé par mockA");
        }
        catch (IllegalArgumentException ex) {
            ; // Ok
        }

        assertEquals("mockA.doTearDown(), mockB.doTearDown()", log.getContent());
    }


    @Override
    protected void setUp() throws Exception {
        composite = new CompositeFixture();
    }

    private static class FixtureMock implements Fixture {
        private LogString log = new LogString();

        FixtureMock() {}


        FixtureMock(LogString log) {
            this.log = log;
        }

        public void doSetUp() throws Exception {
            log.call("doSetUp");
        }


        public void doTearDown() throws Exception {
            log.call("doTearDown");
        }
    }


    private static class CrashFixtureMock extends FixtureMock {
        public static final int SETUP_FAILURE = 0;
        private static final int TEARDOWN_FAILURE = 1;
        private int failureLocation;

        CrashFixtureMock(LogString log, int failureLocation) {
            super(log);
            this.failureLocation = failureLocation;
        }

        @Override
        public void doSetUp() throws Exception {
            super.doSetUp();
            if (SETUP_FAILURE == failureLocation) {
                throw new IllegalArgumentException();
            }
        }


        @Override
        public void doTearDown() throws Exception {
            super.doTearDown();
            if (TEARDOWN_FAILURE == failureLocation) {
                throw new IllegalArgumentException();
            }
        }
    }
}
