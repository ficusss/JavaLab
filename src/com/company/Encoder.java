package com.company;

import java.util.Map;

public interface Encoder {

    /**
     * Encodes a byte.
     * @param byte_ - byte to encode.
     * @param configData - table with supporting data from config file.
     * @return encoded byte.
     * @throws InvalidConfigurationData - an exception occurs if configData does not meet expectations.
     */
    byte encode(byte byte_, Map configData) throws InvalidConfigurationData;
}
