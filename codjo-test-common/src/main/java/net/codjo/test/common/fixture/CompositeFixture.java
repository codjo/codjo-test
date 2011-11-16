/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.fixture;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe gérant les compositions de fixtures de test.
 */
public class CompositeFixture implements Fixture {
    private List<Fixture> fixtureList = new ArrayList<Fixture>();
    private List<Fixture> succeedSetUpList = new ArrayList<Fixture>();


    public CompositeFixture() {
    }


    public CompositeFixture(Fixture fixture) {
        addFixture(fixture);
    }


    public CompositeFixture(Fixture fixture, Fixture fixture1) {
        this(fixture);
        addFixture(fixture1);
    }


    public CompositeFixture(Fixture fixture, Fixture fixture1, Fixture fixture2) {
        this(fixture, fixture1);
        addFixture(fixture2);
    }


    public void addFixture(Fixture fixture) {
        fixtureList.add(fixture);
    }


    public void doSetUp() throws Exception {
        try {
            for (Fixture fixture : fixtureList) {
                fixture.doSetUp();
                succeedSetUpList.add(fixture);
            }
        }
        catch (Exception error) {
            doTearDown();
            throw error;
        }
    }


    public void doTearDown() throws Exception {
        Exception tearDownFailure = null;
        for (Fixture fixture : succeedSetUpList) {
            try {
                fixture.doTearDown();
            }
            catch (Exception e) {
                tearDownFailure = e;
            }
        }
        if (tearDownFailure != null) {
            throw tearDownFailure;
        }
    }


    public Fixture get(Class fixtureClass) {
        List<Fixture> exactMatch = new ArrayList<Fixture>();
        List<Fixture> instanceOfMatch = new ArrayList<Fixture>();

        for (Fixture searchedFixture : fixtureList) {
            if (searchedFixture.getClass() == fixtureClass) {
                exactMatch.add(searchedFixture);
            }
            else if (fixtureClass.isAssignableFrom(searchedFixture.getClass())) {
                instanceOfMatch.add(searchedFixture);
            }
        }

        if (exactMatch.size() == 1) {
            return exactMatch.get(0);
        }
        else if (exactMatch.size() == 0 && instanceOfMatch.size() == 1) {
            return instanceOfMatch.get(0);
        }
        else if (exactMatch.size() == 0 && instanceOfMatch.size() == 0) {
            return null;
        }
        throw new AmbiguousFixtureResolutionException();
    }


    /**
     * Exception levée lorsque plusieurs fixtures sont disponibles.
     */
    public class AmbiguousFixtureResolutionException extends RuntimeException {
    }
}
