package com.company;

import java.util.Map;

/**
 * Interface class for connecting handling methods.
 */
public interface Handler {
    boolean isReturn();
    Map<String, InnerConverter> getReturnedTypes();
    Class getOutputData();
}