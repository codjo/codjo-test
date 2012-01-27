/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import junit.framework.Assert;

import static net.codjo.test.common.matcher.JUnitMatchers.*;
/**
 * Classe permettant d'appeler par instrospection les méthodes d'une instance et de vérifier l'appel en regardant dans
 * un {@link LogString}.
 */
public class LogCallAssert<T> {
    private final Class aClass;


    public LogCallAssert(Class<T> aClass) {
        this.aClass = aClass;
    }


    public void assertCalls(T instance, LogString logString) throws Exception {
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            assertCall(method, instance, logString);
            logString.clear();
        }
    }


    public void assertCalls(T instance, LogString logString, String[] methodList) throws Exception {
        Set<String> methodsToBeCalled = new HashSet<String>(Arrays.asList(methodList));

        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            if (methodsToBeCalled.remove(method.toGenericString())) {
                assertCall(method, instance, logString);
                logString.clear();
            }
        }

        assertThat(methodsToBeCalled.toArray(new String[methodsToBeCalled.size()]), is(new String[0]));
    }


    protected void assertCall(Method method, Object instance, LogString logString)
          throws IllegalAccessException, InvocationTargetException {
        Class[] parameterTypes = method.getParameterTypes();

        Object[] args = getDefaultArgumentFor(parameterTypes);

        method.invoke(instance, args);

        Assert.assertEquals(buildAssertMessage(method),
                            method.getName() + "(" + argumentsToString(args) + ")", logString.getContent());
    }


    protected String buildAssertMessage(Method method) {
        return "La méthode " + method.getName() + " n'est pas appelée";
    }


    protected String argumentsToString(Object[] args) {
        StringBuilder buffer = new StringBuilder();
        for (Object arg : args) {
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append(arg);
        }
        return buffer.toString();
    }


    protected Object[] getDefaultArgumentFor(Class[] parameterTypes) {
        Object[] values = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            values[i] = getDefaultArgumentFor(parameterTypes[i]);
        }
        return values;
    }


    protected Object getDefaultArgumentFor(Class clazz) {
        if (String.class == clazz) {
            return "argStr";
        }
        else if (boolean.class == clazz) {
            return Boolean.TRUE;
        }
        else if (int.class == clazz) {
            return 1;
        }
        else if (short.class == clazz) {
            return (short)1;
        }
        else {
            return null;
        }
    }
}
