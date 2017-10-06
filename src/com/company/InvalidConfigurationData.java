package com.company;

/**
 * Created by Grigory on 05.10.2017.
 */
public class InvalidConfigurationData extends Exception {
    public InvalidConfigurationData(String message) {
        super("Invalid data in configuration file. " + message);
    }
}
