/*
 * Team : AGF AM / OSI / SI / BO
 *
 * Copyright (c) 2001 AGF Asset Management.
 */
package net.codjo.test.common;
/**
 *
 */
public interface Converter {
    Converter IDENTITY =
        new Converter() {
            public Object convert(Object value) {
                return value;
            }
        };

    Object convert(Object value);
}
