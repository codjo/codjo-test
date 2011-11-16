/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.fixture;
import net.codjo.test.common.LogString;
import java.security.Permission;
/**
 * 
 */
public class SystemExitFixture implements Fixture {
    public static final String BLOCK_MESSAGE = "System.exit() intercepte pour les tests";
    private LogString log;
    private int firstExitValue;
    private boolean firstExit;

    public void doSetUp() {
        doSetUp(new LogString());
    }


    public void doSetUp(LogString logString) {
        this.log = logString;
        firstExit = true;
        blockExitWithSecurityManager();
    }


    public void doTearDown() {
        rollbackSecurityManager();
    }


    public LogString getLog() {
        return log;
    }


    private void blockExitWithSecurityManager() {
        System.setSecurityManager(new SecurityManager() {
                @Override
                public void checkExit(int status) {
                    log.call("System.exit", "" + status);
                    if (firstExit) {
                        firstExitValue = status;
                        firstExit = false;
                    }
                    throw new SecurityException(BLOCK_MESSAGE);
                }


                @Override
                public void checkPermission(Permission perm) {}
            });
    }


    void rollbackSecurityManager() {
        System.setSecurityManager(null);
    }


    public int getFirstExitValue() {
        return firstExitValue;
    }
}
