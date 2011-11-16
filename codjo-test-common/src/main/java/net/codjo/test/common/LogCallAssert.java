/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import junit.framework.Assert;
/**
 * Classe permettant d'appeler par instrospection les méthodes d'une instance et de vérifier l'appel en
 * regardant dans un {@link LogString}.
 */
public class LogCallAssert {
    private final Class aClass;

/**
     * Constructeur.
     *
     * @param aClass Classe contenant les méthodes à appeler.
     */
    public LogCallAssert(Class aClass) {
        this.aClass = aClass;
    }

    public void assertCalls(Object instance, LogString logString)
            throws Exception {
        Method[] methods = aClass.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            assertCall(method, instance, logString);
            logString.clear();
        }
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
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
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
            return new Integer(1);
        }
        else if (short.class == clazz) {
            return new Short((short)1);
        }
        else {
            return null;
        }
    }
}
