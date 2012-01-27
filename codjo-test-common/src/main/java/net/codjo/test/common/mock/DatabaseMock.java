/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common.mock;
import net.codjo.test.common.LogString;
import org.exolab.castor.jdo.ClassNotPersistenceCapableException;
import org.exolab.castor.jdo.Database;
import org.exolab.castor.jdo.DuplicateIdentityException;
import org.exolab.castor.jdo.LockNotGrantedException;
import org.exolab.castor.jdo.OQLQuery;
import org.exolab.castor.jdo.ObjectNotFoundException;
import org.exolab.castor.jdo.ObjectNotPersistentException;
import org.exolab.castor.jdo.PersistenceException;
import org.exolab.castor.jdo.Query;
import org.exolab.castor.jdo.QueryException;
import org.exolab.castor.jdo.TransactionAbortedException;
import org.exolab.castor.jdo.TransactionNotInProgressException;
import org.exolab.castor.persist.PersistenceInfoGroup;
import org.exolab.castor.persist.spi.Complex;
/**
 * Mock un Database testeur.
 */
@SuppressWarnings({"UnusedDeclaration"})
public class DatabaseMock {
    private final LogString log;


    public DatabaseMock() {
        this(new LogString());
    }


    public DatabaseMock(LogString connectionLog) {
        log = connectionLog;
    }


    public Database getStub() {
        return ProxyDelegatorFactory.getProxy(this, Database.class);
    }


    public String callList() {
        return log.getContent();
    }


    public OQLQuery getOQLQuery() {
        log.call("getOQLQuery");
        return null;
    }


    public OQLQuery getOQLQuery(String query) throws QueryException {
        log.call("getOQLQuery", query);
        return null;
    }


    public Query getQuery() {
        log.call("getQuery");
        return null;
    }


    public PersistenceInfoGroup getScope() {
        log.call("getScope");
        return null;
    }


    public Object load(Class aClass, Object object)
          throws TransactionNotInProgressException, ObjectNotFoundException, LockNotGrantedException {
        log.call("load", aClass, object);
        return null;
    }


    public Object load(Class aClass, Complex complex)
          throws ObjectNotFoundException, LockNotGrantedException, TransactionNotInProgressException {
        log.call("load", aClass, complex);
        return null;
    }


    public Object load(Class aClass, Object object, short value)
          throws TransactionNotInProgressException, ObjectNotFoundException, LockNotGrantedException {
        return doLoad(aClass, object, (int)value);
    }


    public Object load(Class aClass, Complex complex, short value)
          throws ObjectNotFoundException, LockNotGrantedException, TransactionNotInProgressException {
        return doLoad(aClass, complex, (int)value);
    }


    public Object load(Class aClass, Object object, Object o1)
          throws ObjectNotFoundException, LockNotGrantedException, TransactionNotInProgressException {
        return doLoad(aClass, object, o1);
    }


    public void create(Object object)
          throws ClassNotPersistenceCapableException, DuplicateIdentityException,
                 TransactionNotInProgressException {
        log.call("create", object);
    }


    public void remove(Object object)
          throws ObjectNotPersistentException, LockNotGrantedException, TransactionNotInProgressException {
        log.call("remove", object);
    }


    public void update(Object object)
          throws ClassNotPersistenceCapableException, TransactionNotInProgressException {
        log.call("update", object);
    }


    public void lock(Object object)
          throws LockNotGrantedException, ObjectNotPersistentException, TransactionNotInProgressException {
        log.call("lock", object);
    }


    public void begin() throws PersistenceException {
        log.call("begin");
    }


    public boolean isAutoStore() {
        log.call("isAutoStore");
        return false;
    }


    public void setAutoStore(boolean autoStore) {
        log.call("setAutoStore", "" + autoStore);
    }


    public void commit() throws TransactionNotInProgressException, TransactionAbortedException {
        log.call("commit");
    }


    public void rollback() throws TransactionNotInProgressException {
        log.call("rollback");
    }


    public boolean isActive() {
        log.call("isActive");
        return false;
    }


    public boolean isClosed() {
        log.call("isClosed");
        return false;
    }


    public void close() throws PersistenceException {
        log.call("close");
    }


    public boolean isPersistent(Object object) {
        log.call("isPersistent", object);
        return false;
    }


    public Object getIdentity(Object object) {
        log.call("getIdentity", object);
        return null;
    }


    public ClassLoader getClassLoader() {
        log.call("getClassLoader");
        return null;
    }


    public void makePersistent(Object object)
          throws ClassNotPersistenceCapableException, DuplicateIdentityException {
        log.call("makePersistent", object);
    }


    public void deletePersistent(Object object) throws ObjectNotPersistentException, LockNotGrantedException {
        log.call("deletePersistent", object);
    }


    public void checkpoint() throws TransactionNotInProgressException, TransactionAbortedException {
        log.call("checkpoint");
    }


    public void flush() throws TransactionAbortedException {
        log.call("flush");
    }


    private Object doLoad(Class aClass, Object first, Object second) {
        log.call("load", aClass, first, second);
        return null;
    }
}
