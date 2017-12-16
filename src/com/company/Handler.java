package com.company;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Interface class for connecting handling methods.
 */
public interface Handler {
    boolean isReturn();
    Object getOutput(Class c) throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException, ClassNotFoundException;
    Map<String, Class> getReturnedTypes();
}
