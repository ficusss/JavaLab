package com.company.AllExceptions;

/**
 * An exception occurs if any data does not meet expectations.
 */
public class InvalidConfigurationData extends Exception {
    public InvalidConfigurationData(String message) {
        super("Invalid data in configuration file. " + message);
    }
}
